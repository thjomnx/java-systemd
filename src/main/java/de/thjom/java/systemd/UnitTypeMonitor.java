/*
 * Java-systemd implementation
 * Copyright (c) 2016 Markus Enax
 *
 * This program is free software; you can redistribute it and/or modify it under
 * the terms of either the GNU Lesser General Public License Version 2 or the
 * Academic Free Licence Version 3.0.
 *
 * Full licence texts are included in the COPYING file with this program.
 */

package de.thjom.java.systemd;

import java.util.EnumSet;

import org.freedesktop.dbus.exceptions.DBusException;

import de.thjom.java.systemd.interfaces.ManagerInterface.Reloading;
import de.thjom.java.systemd.interfaces.ManagerInterface.UnitFilesChanged;
import de.thjom.java.systemd.interfaces.ManagerInterface.UnitNew;
import de.thjom.java.systemd.interfaces.ManagerInterface.UnitRemoved;
import de.thjom.java.systemd.types.UnitType;

public class UnitTypeMonitor extends UnitMonitor {

    public enum MonitoredType {
        AUTOMOUNT,
        BUSNAME,
        DEVICE,
        MOUNT,
        PATH,
        SCOPE,
        SERVICE,
        SLICE,
        SNAPSHOT,
        SOCKET,
        SWAP,
        TARGET,
        TIMER
    }

    protected final EnumSet<MonitoredType> monitoredTypes = EnumSet.noneOf(MonitoredType.class);

    public UnitTypeMonitor(final Manager manager) {
        super(manager);
    }

    @Override
    public final void addDefaultHandlers() throws DBusException {
        manager.subscribe();

        reloadingHandler = new ReloadingHandler();
        manager.addConsumer(Reloading.class, reloadingHandler);

        unitFilesChangedHandler = new UnitFilesChangedHandler();
        manager.addConsumer(UnitFilesChanged.class, unitFilesChangedHandler);

        unitNewHandler = new UnitNewHandler() {

            @Override
            public void handle(final UnitNew signal) {
                super.handle(signal);

                if (!daemonReloading) {
                    try {
                        refresh();
                    }
                    catch (final DBusException e) {
                        log.error(ERROR_MSG_MONITOR_REFRESH, e);
                    }
                }
            }

        };

        manager.addConsumer(UnitNew.class, unitNewHandler);

        unitRemovedHandler = new UnitRemovedHandler() {

            @Override
            public void handle(final UnitRemoved signal) {
                super.handle(signal);

                if (!daemonReloading) {
                    try {
                        refresh();
                    }
                    catch (final DBusException e) {
                        log.error(ERROR_MSG_MONITOR_REFRESH, e);
                    }
                }
            }

        };

        manager.addConsumer(UnitRemoved.class, unitRemovedHandler);
    }

    @Override
    public final void removeDefaultHandlers() throws DBusException {
        manager.removeConsumer(Reloading.class, reloadingHandler);
        manager.removeConsumer(UnitFilesChanged.class, unitFilesChangedHandler);
        manager.removeConsumer(UnitNew.class, unitNewHandler);
        manager.removeConsumer(UnitRemoved.class, unitRemovedHandler);
    }

    @Override
    public synchronized void refresh() throws DBusException {
        monitoredUnits.clear();

        for (UnitType unit : manager.listUnits()) {
            String name = unit.getUnitName();

            if (unit.isAutomount() && monitoredTypes.contains(MonitoredType.AUTOMOUNT)) {
                monitoredUnits.put(Systemd.escapePath(name), manager.getAutomount(name));
            }
            else if (unit.isBusName() && monitoredTypes.contains(MonitoredType.BUSNAME)) {
                monitoredUnits.put(Systemd.escapePath(name), manager.getBusName(name));
            }
            else if (unit.isDevice() && monitoredTypes.contains(MonitoredType.DEVICE)) {
                monitoredUnits.put(Systemd.escapePath(name), manager.getDevice(name));
            }
            else if (unit.isMount() && monitoredTypes.contains(MonitoredType.MOUNT)) {
                monitoredUnits.put(Systemd.escapePath(name), manager.getMount(name));
            }
            else if (unit.isPath() && monitoredTypes.contains(MonitoredType.PATH)) {
                monitoredUnits.put(Systemd.escapePath(name), manager.getPath(name));
            }
            else if (unit.isScope() && monitoredTypes.contains(MonitoredType.SCOPE)) {
                monitoredUnits.put(Systemd.escapePath(name), manager.getScope(name));
            }
            else if (unit.isService() && monitoredTypes.contains(MonitoredType.SERVICE)) {
                monitoredUnits.put(Systemd.escapePath(name), manager.getService(name));
            }
            else if (unit.isSlice() && monitoredTypes.contains(MonitoredType.SLICE)) {
                monitoredUnits.put(Systemd.escapePath(name), manager.getSlice(name));
            }
            else if (unit.isSnapshot() && monitoredTypes.contains(MonitoredType.SNAPSHOT)) {
                monitoredUnits.put(Systemd.escapePath(name), manager.getSnapshot(name));
            }
            else if (unit.isSocket() && monitoredTypes.contains(MonitoredType.SOCKET)) {
                monitoredUnits.put(Systemd.escapePath(name), manager.getSocket(name));
            }
            else if (unit.isSwap() && monitoredTypes.contains(MonitoredType.SWAP)) {
                monitoredUnits.put(Systemd.escapePath(name), manager.getSwap(name));
            }
            else if (unit.isTarget() && monitoredTypes.contains(MonitoredType.TARGET)) {
                monitoredUnits.put(Systemd.escapePath(name), manager.getTarget(name));
            }
            else if (unit.isTimer() && monitoredTypes.contains(MonitoredType.TIMER)) {
                monitoredUnits.put(Systemd.escapePath(name), manager.getTimer(name));
            }
        }
    }

    public final void addMonitoredTypes(final MonitoredType... monitoredTypes) throws DBusException {
        for (MonitoredType monitoredType : monitoredTypes) {
            this.monitoredTypes.add(monitoredType);
        }

        refresh();
    }

    public final void removeMonitoredTypes(final MonitoredType... monitoredTypes) throws DBusException {
        for (MonitoredType monitoredType : monitoredTypes) {
            this.monitoredTypes.remove(monitoredType);
        }

        refresh();
    }

    @Override
    public boolean monitorsUnit(final String unitName) {
        boolean monitored = super.monitorsUnit(unitName);

        if (!monitored) {
            String dot = Systemd.escapePath(".");

            for (MonitoredType monitoredType : monitoredTypes) {
                if (unitName.endsWith(dot + monitoredType.name().toLowerCase())) {
                    monitored = true;

                    break;
                }
            }
        }

        return monitored;
    }

}
