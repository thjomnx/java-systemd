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

    protected boolean isIncluded(final UnitType unit) {
        boolean monitored = false;

        for (MonitoredType type : monitoredTypes) {
            switch (type) {
                case AUTOMOUNT:
                    monitored = unit.isAutomount();
                    break;
                case BUSNAME:
                    monitored = unit.isBusName();
                    break;
                case DEVICE:
                    monitored = unit.isDevice();
                    break;
                case MOUNT:
                    monitored = unit.isMount();
                    break;
                case PATH:
                    monitored = unit.isPath();
                    break;
                case SCOPE:
                    monitored = unit.isScope();
                    break;
                case SERVICE:
                    monitored = unit.isService();
                    break;
                case SLICE:
                    monitored = unit.isSlice();
                    break;
                case SNAPSHOT:
                    monitored = unit.isSnapshot();
                    break;
                case SOCKET:
                    monitored = unit.isSocket();
                    break;
                case SWAP:
                    monitored = unit.isSwap();
                    break;
                case TARGET:
                    monitored = unit.isTarget();
                    break;
                case TIMER:
                    monitored = unit.isTimer();
                    break;
                default:
                    monitored = false;
                    break;
            }

            if (monitored) {
                break;
            }
        }

        return monitored;
    }

    @Override
    public synchronized void reset() {
        monitoredTypes.clear();
        monitoredUnits.clear();
    }

    @Override
    public synchronized void refresh() throws DBusException {
        try {
            monitoredUnits.clear();

            for (UnitType unit : manager.listUnits()) {
                if (isIncluded(unit)) {
                    String name = unit.getUnitName();

                    monitoredUnits.put(Systemd.escapePath(name), manager.getUnit(name));
                }
            }
        }
        finally {
            unitMonitorListeners.forEach(l -> l.monitorRefreshed(monitoredUnits.values()));
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
