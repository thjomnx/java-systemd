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

package de.thjom.java.systemd;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import org.freedesktop.DBus.Properties.PropertiesChanged;
import org.freedesktop.dbus.DBusSigHandler;
import org.freedesktop.dbus.DBusSignal;
import org.freedesktop.dbus.exceptions.DBusException;

abstract class AbstractAdapter {

    protected final List<UnitStateListener> unitStateListeners = new ArrayList<>();

    private final List<ForwardingHandler<? extends DBusSignal>> forwarders = new ArrayList<>();

    private ForwardingHandler<PropertiesChanged> defaultHandler;

    protected AbstractAdapter() {
        super();
    }

    public abstract <T extends DBusSignal> void addHandler(final Class<T> type, final DBusSigHandler<T> handler) throws DBusException;

    public abstract <T extends DBusSignal> void removeHandler(final Class<T> type, final DBusSigHandler<T> handler) throws DBusException;

    public <T extends DBusSignal> void addConsumer(final Class<T> type, final DBusSigHandler<T> handler) throws DBusException {
        SignalConsumer<T> consumer = new SignalConsumer<>(s -> handler.handle(s));
        ForwardingHandler<T> forwarder = new ForwardingHandler<>(consumer);

        synchronized (forwarders) {
            forwarders.add(forwarder);
        }

        forwarder.startConsumer();

        addHandler(type, forwarder);
    }

    public <T extends DBusSignal> void removeConsumer(final Class<T> type, final DBusSigHandler<T> handler) throws DBusException {
        ForwardingHandler<? extends DBusSignal> match = null;

        synchronized (forwarders) {
            for (ForwardingHandler<? extends DBusSignal> forwarder : forwarders) {
                if (Objects.equals(forwarder.getConsumer(), handler)) {
                    match = forwarder;

                    break;
                }
            }

            if (match != null) {
                forwarders.remove(match);
            }
        }

        if (match != null) {
            @SuppressWarnings("unchecked")
            ForwardingHandler<T> forwarder = (ForwardingHandler<T>) match;

            removeHandler(type, forwarder);

            forwarder.stopConsumer();
        }
    }

    public void addListener(final UnitStateListener listener) throws DBusException {
        synchronized (unitStateListeners) {
            if (defaultHandler == null) {
                defaultHandler = new ForwardingHandler<>(createStateConsumer());
                defaultHandler.startConsumer();

                addHandler(PropertiesChanged.class, defaultHandler);
            }

            unitStateListeners.add(listener);
        }
    }

    public void removeListener(final UnitStateListener listener) throws DBusException {
        synchronized (unitStateListeners) {
            unitStateListeners.remove(listener);

            if (unitStateListeners.isEmpty() && defaultHandler != null) {
                removeHandler(PropertiesChanged.class, defaultHandler);

                defaultHandler.stopConsumer();
                defaultHandler = null;
            }
        }
    }

    protected SignalConsumer<PropertiesChanged> createStateConsumer() {
        return new SignalConsumer<>(s -> {});
    }

}
