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

import java.util.Collection;
import java.util.Objects;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import org.freedesktop.DBus.Properties.PropertiesChanged;
import org.freedesktop.dbus.DBusSigHandler;
import org.freedesktop.dbus.DBusSignal;
import org.freedesktop.dbus.exceptions.DBusException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.thjom.java.systemd.Manager;
import de.thjom.java.systemd.Unit;
import de.thjom.java.systemd.utils.ForwardingHandler;

abstract class UnitMonitor {

    protected final Logger log = LoggerFactory.getLogger(getClass());

    protected final Manager manager;
    protected final ConcurrentMap<String, Unit> monitoredUnits = new ConcurrentHashMap<>();

    private Timer pollingTimer;

    protected UnitMonitor(final Manager manager) {
        this.manager = Objects.requireNonNull(manager);
    }

    public boolean containsUnit(final String objectPath) {
        return monitoredUnits.containsKey(Unit.extractName(objectPath));
    }

    public <T extends DBusSignal> void addHandler(final Class<T> type, final DBusSigHandler<T> handler) throws DBusException {
        manager.subscribe();
        manager.addHandler(type, handler);
    }

    public <T extends DBusSignal> void removeHandler(final Class<T> type, final DBusSigHandler<T> handler) throws DBusException {
        if (handler instanceof ForwardingHandler) {
            ((ForwardingHandler<?>) handler).forwardTo(null);
        }

        manager.removeHandler(type, handler);
    }

    public void addHandler(final ForwardingHandler<PropertiesChanged> handler) throws DBusException {
        manager.subscribe();

        manager.addHandler(PropertiesChanged.class, handler);
    }

    public void removeHandler(final ForwardingHandler<PropertiesChanged> handler) throws DBusException {
        manager.removeHandler(PropertiesChanged.class, handler);
    }

    public abstract void attach() throws DBusException;

    public abstract void detach() throws DBusException;

    public abstract void refresh() throws DBusException;

    public synchronized void startPolling(final long delay, final long period) {
        TimerTask task = new TimerTask() {

            @Override
            public void run() {
                try {
                    refresh();
                }
                catch (final DBusException e) {
                    log.error("Error while refreshing internal monitor state", e);
                }
            }

        };

        if (pollingTimer != null) {
            pollingTimer.cancel();
        }

        pollingTimer = createTimer();
        pollingTimer.schedule(task, delay, period);
    }

    public synchronized void stopPolling() {
        if (pollingTimer != null) {
            pollingTimer.cancel();
        }
    }

    protected Timer createTimer() {
        return new Timer(getClass().getSimpleName() + "-pollingTimer", true);
    }

    public Collection<Unit> getMonitoredUnits() {
        return monitoredUnits.values();
    }

}
