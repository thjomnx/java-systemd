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

import java.util.ArrayList;
import java.util.List;

import org.freedesktop.dbus.exceptions.DBusException;
import org.freedesktop.dbus.interfaces.DBusSigHandler;
import org.freedesktop.dbus.interfaces.Properties.PropertiesChanged;
import org.freedesktop.dbus.messages.DBusSignal;

abstract class AbstractAdapter {

    protected final List<UnitStateListener> unitStateListeners = new ArrayList<>();

    private DBusSigHandler<PropertiesChanged> defaultHandler;

    protected AbstractAdapter() {
        super();
    }

    public abstract <T extends DBusSignal> void addHandler(final Class<T> type, final DBusSigHandler<T> handler) throws DBusException;

    public abstract <T extends DBusSignal> void removeHandler(final Class<T> type, final DBusSigHandler<T> handler) throws DBusException;

    public void addListener(final UnitStateListener listener) throws DBusException {
        synchronized (unitStateListeners) {
            if (defaultHandler == null) {
                defaultHandler = createStateHandler();

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

                defaultHandler = null;
            }
        }
    }

    protected DBusSigHandler<PropertiesChanged> createStateHandler() {
        return signal -> {};
    }

}
