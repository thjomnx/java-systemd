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

import java.util.HashSet;
import java.util.Set;

import org.freedesktop.dbus.exceptions.DBusException;

public class UnitNameMonitor extends UnitMonitor {

    protected final Set<String> monitoredNames = new HashSet<>();

    public UnitNameMonitor(final Manager manager) {
        super(manager);
    }

    @Override
    public synchronized void reset() {
        monitoredNames.clear();
        monitoredUnits.clear();
    }

    @Override
    public synchronized void refresh() throws DBusException {
        try {
            monitoredUnits.clear();

            for (String unitName : monitoredNames) {
                monitoredUnits.put(Systemd.escapePath(unitName), manager.getUnit(unitName));
            }
        }
        finally {
            unitMonitorListeners.forEach(l -> l.monitorRefreshed(monitoredUnits.values()));
        }
    }

    public synchronized void addUnits(final String... fullUnitNames) throws DBusException {
        for (String unitName : fullUnitNames) {
            monitoredNames.add(unitName);
            monitoredUnits.put(Systemd.escapePath(unitName), manager.getUnit(unitName));
        }
    }

    public synchronized void addUnits(final Unit... units) {
        for (Unit unit : units) {
            monitoredNames.add(unit.getId());
            monitoredUnits.put(Systemd.escapePath(unit.getId()), unit);
        }
    }

    public synchronized void removeUnits(final String... fullUnitNames) {
        for (String unitName : fullUnitNames) {
            monitoredNames.remove(unitName);
            monitoredUnits.remove(Systemd.escapePath(unitName));
        }
    }

    public synchronized void removeUnits(final Unit... units) {
        for (Unit unit : units) {
            monitoredNames.remove(unit.getId());
            monitoredUnits.remove(Systemd.escapePath(unit.getId()));
        }
    }

    @Override
    public boolean monitorsUnit(final String unitName) {
        boolean monitored = super.monitorsUnit(unitName);

        if (!monitored) {
            for (String monitoredName : monitoredNames) {
                if (Systemd.escapePath(monitoredName).equals(unitName)) {
                    monitored = true;

                    break;
                }
            }
        }

        return monitored;
    }

}
