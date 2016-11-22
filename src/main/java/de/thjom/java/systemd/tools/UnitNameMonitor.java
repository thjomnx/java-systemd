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

import java.util.HashSet;
import java.util.Set;

import org.freedesktop.dbus.DBusSigHandler;
import org.freedesktop.dbus.exceptions.DBusException;

import de.thjom.java.systemd.Manager;
import de.thjom.java.systemd.Unit;
import de.thjom.java.systemd.interfaces.ManagerInterface.Reloading;
import de.thjom.java.systemd.interfaces.ManagerInterface.UnitFilesChanged;
import de.thjom.java.systemd.interfaces.ManagerInterface.UnitNew;
import de.thjom.java.systemd.interfaces.ManagerInterface.UnitRemoved;

public class UnitNameMonitor extends UnitMonitor {

    protected final Set<String> monitoredNames = new HashSet<>();

    private final ReloadingHandler reloadingHandler = new ReloadingHandler();
    private final UnitFilesChangedHandler unitFilesChangedHandler = new UnitFilesChangedHandler();
    private final UnitNewHandler unitNewHandler = new UnitNewHandler();
    private final UnitRemovedHandler unitRemovedHandler = new UnitRemovedHandler();

    public UnitNameMonitor(final Manager manager) {
        super(manager);
    }

    @Override
    public void attach() throws DBusException {
        manager.subscribe();
        manager.addHandler(Reloading.class, reloadingHandler);
        manager.addHandler(UnitFilesChanged.class, unitFilesChangedHandler);
        manager.addHandler(UnitNew.class, unitNewHandler);
        manager.addHandler(UnitRemoved.class, unitRemovedHandler);
    }

    @Override
    public void detach() throws DBusException {
        manager.removeHandler(Reloading.class, reloadingHandler);
        manager.removeHandler(UnitFilesChanged.class, unitFilesChangedHandler);
        manager.removeHandler(UnitNew.class, unitNewHandler);
        manager.removeHandler(UnitRemoved.class, unitRemovedHandler);
    }

    public synchronized void addUnits(final String... fullUnitNames) throws DBusException {
        for (String unitName : fullUnitNames) {
            monitoredNames.add(unitName);
            monitoredUnits.put(unitName, manager.getUnit(unitName));
        }
    }

    public synchronized void addUnits(final Unit... units) {
        for (Unit unit : units) {
            monitoredNames.add(unit.getId());
            monitoredUnits.put(unit.getId(), unit);
        }
    }

    public synchronized void removeUnits(final String... fullUnitNames) {
        for (String unitName : fullUnitNames) {
            monitoredNames.remove(unitName);
            monitoredUnits.remove(unitName);
        }
    }

    public synchronized void removeUnits(final Unit... units) {
        for (Unit unit : units) {
            monitoredNames.remove(unit.getId());
            monitoredUnits.remove(unit.getId());
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
            }
        }

    }

    public class UnitFilesChangedHandler implements DBusSigHandler<UnitFilesChanged> {

        @Override
        public void handle(final UnitFilesChanged signal) {
            if (log.isDebugEnabled()) {
                log.debug(String.format("Signal received (unit files changed: %s)", signal));
            }
        }

    }

    public class UnitNewHandler implements DBusSigHandler<UnitNew> {

        @Override
        public void handle(final UnitNew signal) {
            if (log.isDebugEnabled()) {
                log.debug(String.format("Signal received (unit new: %s)", signal));
            }

            String id = signal.getId();

            synchronized (UnitNameMonitor.this) {
                if (monitoredNames.contains(id)) {
                    try {
                        monitoredUnits.put(id, manager.getUnit(id));
                    }
                    catch (final DBusException e) {
                        log.error(String.format("Unable to add monitored unit '%s'", id), e);
                    }
                }
            }
        }

    }

    public class UnitRemovedHandler implements DBusSigHandler<UnitRemoved> {

        @Override
        public void handle(final UnitRemoved signal) {
            if (log.isDebugEnabled()) {
                log.debug(String.format("Signal received (unit removed: %s)", signal));
            }

            String id = signal.getId();

            synchronized (UnitNameMonitor.this) {
                monitoredUnits.remove(id);
            }
        }

    }

}
