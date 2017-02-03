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

import static de.thjom.java.systemd.Unit.Property.ACTIVE_STATE;
import static de.thjom.java.systemd.Unit.Property.LOAD_STATE;
import static de.thjom.java.systemd.Unit.Property.SUB_STATE;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import org.freedesktop.DBus.Properties.PropertiesChanged;
import org.freedesktop.dbus.DBusSigHandler;
import org.freedesktop.dbus.DBusSignal;
import org.freedesktop.dbus.Variant;
import org.freedesktop.dbus.exceptions.DBusException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.thjom.java.systemd.Manager;
import de.thjom.java.systemd.Unit;
import de.thjom.java.systemd.UnitStateListener;
import de.thjom.java.systemd.UnitStateNotifier;
import de.thjom.java.systemd.utils.ForwardingHandler;
import de.thjom.java.systemd.utils.MessageConsumer;

abstract class UnitMonitor implements UnitStateNotifier {

    protected final Logger log = LoggerFactory.getLogger(getClass());

    protected final Manager manager;
    protected final ConcurrentMap<String, Unit> monitoredUnits = new ConcurrentHashMap<>();

    private final List<UnitStateListener> unitStateListeners = new ArrayList<>();
    private ForwardingHandler<PropertiesChanged> defaultHandler;

    private Timer pollingTimer;

    protected UnitMonitor(final Manager manager) {
        this.manager = Objects.requireNonNull(manager);
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

    public abstract void addDefaultHandlers() throws DBusException;

    public abstract void removeDefaultHandlers() throws DBusException;

    @Override
    public synchronized void addListener(final UnitStateListener listener) throws DBusException {
        if (defaultHandler == null) {
            defaultHandler = new ForwardingHandler<>();
            defaultHandler.forwardTo(new MessageConsumer<PropertiesChanged>(100) {

                @Override
                public void propertiesChanged(final PropertiesChanged signal) {
                    Optional<Unit> unit = getMonitoredUnit(Unit.extractName(signal.getPath()));

                    if (unit.isPresent()) {
                        Map<String, Variant<?>> properties = signal.getChangedProperties();

                        if (properties.containsKey(ACTIVE_STATE) || properties.containsKey(LOAD_STATE) || properties.containsKey(SUB_STATE)) {
                            synchronized (unitStateListeners) {
                                for (UnitStateListener listener : unitStateListeners) {
                                    listener.stateChanged(unit.get(), properties);
                                }
                            }
                        }
                    }
                }

            });

            addHandler(PropertiesChanged.class, defaultHandler);
        }

        unitStateListeners.add(listener);
    }

    @Override
    public synchronized void removeListener(final UnitStateListener listener) throws DBusException {
        unitStateListeners.remove(listener);

        if (unitStateListeners.isEmpty() && defaultHandler != null) {
            removeHandler(PropertiesChanged.class, defaultHandler);

            defaultHandler = null;
        }
    }

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

    public boolean monitorsUnit(final String unitName) {
        return monitoredUnits.containsKey(unitName);
    }

    public Optional<Unit> getMonitoredUnit(final String unitName) {
        return Optional.ofNullable(monitoredUnits.get(unitName));
    }

    public Collection<Unit> getMonitoredUnits() {
        return monitoredUnits.values();
    }

}
