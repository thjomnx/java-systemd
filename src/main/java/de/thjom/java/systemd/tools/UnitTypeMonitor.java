/*
 * Java-systemd implementation
 * Copyright (c) 2016 Markus Enax
 *
 * This program is free software; you can redistribute it and/or modify it under
 * the terms of either the GNU Lesser General Public License Version 2 or the
 * Academic Free Licence Version 2.1.
 *
 * Full licence texts are included in the COPYING file with this program.
 */

package de.thjom.java.systemd.tools;

import java.util.Arrays;
import java.util.EnumSet;

import org.freedesktop.dbus.DBusSigHandler;
import org.freedesktop.dbus.exceptions.DBusException;

import de.thjom.java.systemd.Manager;
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

    protected final EnumSet<MonitoredType> monitoredTypes;

    private final ReloadingHandler reloadingHandler = new ReloadingHandler();
    private final UnitFilesChangedHandler unitFilesChangedHandler = new UnitFilesChangedHandler();
    private final UnitNewHandler unitNewHandler = new UnitNewHandler();
    private final UnitRemovedHandler unitRemovedHandler = new UnitRemovedHandler();

    public UnitTypeMonitor(final Manager manager, final MonitoredType... monitoredTypes) {
        super(manager);

        this.monitoredTypes = EnumSet.copyOf(Arrays.asList(monitoredTypes));
    }

    @Override
    public final void attach() throws DBusException {
        manager.subscribe();
        manager.addHandler(Reloading.class, reloadingHandler);
        manager.addHandler(UnitFilesChanged.class, unitFilesChangedHandler);
        manager.addHandler(UnitNew.class, unitNewHandler);
        manager.addHandler(UnitRemoved.class, unitRemovedHandler);

        mapUnits();
    }

    @Override
    public final void detach() throws DBusException {
        manager.removeHandler(Reloading.class, reloadingHandler);
        manager.removeHandler(UnitFilesChanged.class, unitFilesChangedHandler);
        manager.removeHandler(UnitNew.class, unitNewHandler);
        manager.removeHandler(UnitRemoved.class, unitRemovedHandler);
    }

    public final void addMonitoredTypes(final MonitoredType... monitoredTypes) throws DBusException {
        for (MonitoredType monitoredType : monitoredTypes) {
            this.monitoredTypes.add(monitoredType);
        }

        mapUnits();
    }

    public final void removeMonitoredTypes(final MonitoredType... monitoredTypes) throws DBusException {
        for (MonitoredType monitoredType : monitoredTypes) {
            this.monitoredTypes.remove(monitoredType);
        }

        mapUnits();
    }

    protected final synchronized void mapUnits() throws DBusException {
        for (UnitType unit : manager.listUnits()) {
            String name = unit.getUnitName();

            if (monitoredTypes.contains(MonitoredType.AUTOMOUNT) && unit.isAutomount()) {
                monitoredUnits.put(name, manager.getAutomount(name));
            }

            if (monitoredTypes.contains(MonitoredType.BUSNAME) && unit.isBusName()) {
                monitoredUnits.put(name, manager.getBusName(name));
            }

            if (monitoredTypes.contains(MonitoredType.DEVICE) && unit.isDevice()) {
                monitoredUnits.put(name, manager.getDevice(name));
            }

            if (monitoredTypes.contains(MonitoredType.MOUNT) && unit.isMount()) {
                monitoredUnits.put(name, manager.getMount(name));
            }

            if (monitoredTypes.contains(MonitoredType.PATH) && unit.isPath()) {
                monitoredUnits.put(name, manager.getPath(name));
            }

            if (monitoredTypes.contains(MonitoredType.SCOPE) && unit.isScope()) {
                monitoredUnits.put(name, manager.getScope(name));
            }

            if (monitoredTypes.contains(MonitoredType.SERVICE) && unit.isService()) {
                monitoredUnits.put(name, manager.getService(name));
            }

            if (monitoredTypes.contains(MonitoredType.SLICE) && unit.isSlice()) {
                monitoredUnits.put(name, manager.getSlice(name));
            }

            if (monitoredTypes.contains(MonitoredType.SNAPSHOT) && unit.isSnapshot()) {
                monitoredUnits.put(name, manager.getSnapshot(name));
            }

            if (monitoredTypes.contains(MonitoredType.SOCKET) && unit.isSocket()) {
                monitoredUnits.put(name, manager.getSocket(name));
            }

            if (monitoredTypes.contains(MonitoredType.SWAP) && unit.isSwap()) {
                monitoredUnits.put(name, manager.getSwap(name));
            }

            if (monitoredTypes.contains(MonitoredType.TARGET) && unit.isTarget()) {
                monitoredUnits.put(name, manager.getTarget(name));
            }

            if (monitoredTypes.contains(MonitoredType.TIMER) && unit.isTimer()) {
                monitoredUnits.put(name, manager.getTimer(name));
            }
        }
    }

    public class ReloadingHandler implements DBusSigHandler<Reloading> {

        @Override
        public void handle(final Reloading signal) {
            if (signal.isActive()) {
                if (log.isDebugEnabled()) {
                    log.debug(String.format("Signal received ('daemon-reload' started: %s)", signal));
                }
            }
            else {
                if (log.isDebugEnabled()) {
                    log.debug(String.format("Signal received ('daemon-reload' finished: %s)", signal));
                }

                try {
                    mapUnits();
                }
                catch (final DBusException e) {
                    log.error("Unable to map units", e);
                }
            }
        }

    }

    public class UnitFilesChangedHandler implements DBusSigHandler<UnitFilesChanged> {

        @Override
        public void handle(final UnitFilesChanged signal) {
            if (log.isDebugEnabled()) {
                log.debug(String.format("Signal received (unit files changed: %s)", signal));
            }

            try {
                mapUnits();
            }
            catch (final DBusException e) {
                log.error("Unable to map units", e);
            }
        }

    }

    public class UnitNewHandler implements DBusSigHandler<UnitNew> {

        @Override
        public void handle(final UnitNew signal) {
            if (log.isDebugEnabled()) {
                log.debug(String.format("Signal received (unit new: %s)", signal));
            }

            try {
                mapUnits();
            }
            catch (final DBusException e) {
                log.error("Unable to map units", e);
            }
        }

    }

    public class UnitRemovedHandler implements DBusSigHandler<UnitRemoved> {

        @Override
        public void handle(final UnitRemoved signal) {
            if (log.isDebugEnabled()) {
                log.debug(String.format("Signal received (unit removed: %s)", signal));
            }

            try {
                mapUnits();
            }
            catch (final DBusException e) {
                log.error("Unable to map units", e);
            }
        }

    }

}
