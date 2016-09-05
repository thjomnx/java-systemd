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

import java.lang.reflect.Field;
import java.util.Objects;

import org.freedesktop.dbus.DBusConnection;
import org.freedesktop.dbus.DBusInterface;
import org.freedesktop.dbus.DBusSigHandler;
import org.freedesktop.dbus.DBusSignal;
import org.freedesktop.dbus.exceptions.DBusException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.thjom.java.systemd.interfaces.PropertyInterface;

public abstract class InterfaceAdapter implements DBusInterface {

    protected final Logger log = LoggerFactory.getLogger(getClass());

    protected final DBusConnection dbus;

    protected Properties properties;

    private final DBusInterface iface;

    protected InterfaceAdapter(final DBusConnection dbus, final DBusInterface iface) {
        super();

        this.dbus = Objects.requireNonNull(dbus);
        this.iface = Objects.requireNonNull(iface);
    }

    @Override
    public String getObjectPath() {
        return getInterface().getObjectPath();
    }

    @Override
    public boolean isRemote() {
        return getInterface().isRemote();
    }

    public DBusInterface getInterface() {
        return iface;
    }

    /**
     * Returns the {@link PropertyInterface} adapter of this interface adapter.<p>
     *
     * @return The property interface adapter.
     */
    public Properties getProperties() {
        return properties;
    }

    public <T extends DBusSignal> void addHandler(final Class<T> type, final DBusSigHandler<T> handler) throws DBusException {
        dbus.addSigHandler(type, handler);
    }

    public <T extends DBusSignal> void removeHandler(final Class<T> type, final DBusSigHandler<T> handler) throws DBusException {
        dbus.removeSigHandler(type, handler);
    }

    protected static class Property {

        protected Property() {
            // Do nothing (static implementation)
        }

        protected static final String[] getAllNames(final Class<?> type) {
            Field[] fields = type.getDeclaredFields();
            String[] names = new String[fields.length];

            for (int i = 0; i < fields.length; i++) {
                Object obj = "";

                try {
                    obj = fields[i].get(null);
                }
                catch (final IllegalAccessException | IllegalArgumentException e) {
                    e.printStackTrace();
                }

                names[i] = obj.toString();
            }

            return names;
        }

    }

}
