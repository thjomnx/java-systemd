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

import de.thjom.java.systemd.interfaces.ManagerInterface.Reloading;
import de.thjom.java.systemd.interfaces.ManagerInterface.UnitFilesChanged;
import de.thjom.java.systemd.interfaces.ManagerInterface.UnitNew;
import de.thjom.java.systemd.interfaces.ManagerInterface.UnitRemoved;

public class UnitNameMonitor extends UnitMonitor {

    protected final Set<String> monitoredNames = new HashSet<>();

    public UnitNameMonitor(final Manager manager) {
        super(manager);
    }

    @Override
    public void addDefaultHandlers() throws DBusException {
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
                    String id = signal.getId();

                    synchronized (UnitNameMonitor.this) {
                        if (monitoredNames.contains(id)) {
                            try {
                                monitoredUnits.put(Systemd.escapePath(id), manager.getUnit(id));
                            }
                            catch (final DBusException e) {
                                log.error(String.format("Unable to add monitored unit '%s'", id), e);
                            }
                        }
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
                    String id = signal.getId();

                    synchronized (UnitNameMonitor.this) {
                        monitoredUnits.remove(Systemd.escapePath(id));
                    }
                }
            }

        };

        manager.addConsumer(UnitRemoved.class, unitRemovedHandler);
    }

    @Override
    public void removeDefaultHandlers() throws DBusException {
        manager.removeConsumer(Reloading.class, reloadingHandler);
        manager.removeConsumer(UnitFilesChanged.class, unitFilesChangedHandler);
        manager.removeConsumer(UnitNew.class, unitNewHandler);
        manager.removeConsumer(UnitRemoved.class, unitRemovedHandler);
    }

    @Override
    public synchronized void refresh() throws DBusException {
        monitoredUnits.clear();

        for (String unitName : monitoredNames) {
            monitoredUnits.put(Systemd.escapePath(unitName), manager.getUnit(unitName));
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
