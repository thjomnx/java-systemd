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
import java.util.Collections;
import java.util.EnumSet;
import java.util.HashMap;
import java.util.Map;

import org.freedesktop.dbus.DBusSigHandler;
import org.freedesktop.dbus.exceptions.DBusException;

import de.thjom.java.systemd.Manager;
import de.thjom.java.systemd.Unit;
import de.thjom.java.systemd.interfaces.ManagerInterface.Reloading;
import de.thjom.java.systemd.interfaces.ManagerInterface.UnitFilesChanged;
import de.thjom.java.systemd.interfaces.ManagerInterface.UnitNew;
import de.thjom.java.systemd.interfaces.ManagerInterface.UnitRemoved;
import de.thjom.java.systemd.types.UnitType;

public class UnitMonitor {

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

    protected final Manager manager;
    protected final EnumSet<MonitoredType> monitoredTypes;

    private final Map<String, Unit> monitoredUnits = new HashMap<>();

    private final ReloadingHandler reloadingHandler = new ReloadingHandler();
    private final UnitFilesChangedHandler unitFilesChangedHandler = new UnitFilesChangedHandler();
    private final UnitNewHandler unitNewHandler = new UnitNewHandler();
    private final UnitRemovedHandler unitRemovedHandler = new UnitRemovedHandler();

    public UnitMonitor(final Manager manager, final MonitoredType... monitoredTypes) {
        this.manager = manager;
        this.monitoredTypes = EnumSet.copyOf(Arrays.asList(monitoredTypes));
    }

    public final void attach() throws DBusException {
        manager.subscribe();
        manager.addHandler(Reloading.class, reloadingHandler);
        manager.addHandler(UnitFilesChanged.class, unitFilesChangedHandler);
        manager.addHandler(UnitNew.class, unitNewHandler);
        manager.addHandler(UnitRemoved.class, unitRemovedHandler);

        mapUnits();
    }

    public final void detach() throws DBusException {
        manager.removeHandler(Reloading.class, reloadingHandler);
        manager.removeHandler(UnitFilesChanged.class, unitFilesChangedHandler);
        manager.removeHandler(UnitNew.class, unitNewHandler);
        manager.removeHandler(UnitRemoved.class, unitRemovedHandler);
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

    public Map<String, Unit> getMonitoredUnits() {
        return Collections.unmodifiableMap(monitoredUnits);
    }

    public class ReloadingHandler implements DBusSigHandler<Reloading> {

        @Override
        public void handle(final Reloading signal) {
            if (signal.isActive()) {
                // TODO Log something (give hint that daemon-reload has begun)
            }
            else {
                try {
                    mapUnits();
                }
                catch (final DBusException e) {
                    // TODO Log something, then ignore
                }
            }
        }

    }

    public class UnitFilesChangedHandler implements DBusSigHandler<UnitFilesChanged> {

        @Override
        public void handle(final UnitFilesChanged signal) {
            try {
                mapUnits();
            }
            catch (final DBusException e) {
                // TODO Log something, then ignore
            }
        }

    }

    public class UnitNewHandler implements DBusSigHandler<UnitNew> {

        @Override
        public void handle(final UnitNew signal) {
            try {
                mapUnits();
            }
            catch (final DBusException e) {
                // TODO Log something, then ignore
            }
        }

    }

    public class UnitRemovedHandler implements DBusSigHandler<UnitRemoved> {

        @Override
        public void handle(final UnitRemoved signal) {
            try {
                mapUnits();
            }
            catch (final DBusException e) {
                // TODO Log something, then ignore
            }
        }

    }

}
