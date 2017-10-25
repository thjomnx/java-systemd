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

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import org.freedesktop.dbus.DBusConnection;
import org.freedesktop.dbus.DBusInterface;
import org.freedesktop.dbus.DBusSigHandler;
import org.freedesktop.dbus.DBusSignal;
import org.freedesktop.dbus.exceptions.DBusException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.thjom.java.systemd.interfaces.PropertyInterface;

public abstract class InterfaceAdapter extends AbstractAdapter implements DBusInterface {

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

    @Override
    public <T extends DBusSignal> void addHandler(final Class<T> type, final DBusSigHandler<T> handler) throws DBusException {
        dbus.addSigHandler(type, handler);
    }

    @Override
    public <T extends DBusSignal> void removeHandler(final Class<T> type, final DBusSigHandler<T> handler) throws DBusException {
        if (handler != null) {
            dbus.removeSigHandler(type, handler);
        }
    }

    @Override
    public boolean equals(final Object obj) {
        if (this == obj) {
            return true;
        }
        else if (obj != null) {
            return obj.hashCode() == hashCode();
        }

        return false;
    }

    @Override
    public int hashCode() {
        return Objects.hash(dbus.getUniqueName(), getObjectPath());
    }

    protected static class AdapterProperty {

        private static final Logger log = LoggerFactory.getLogger(AdapterProperty.class);

        protected AdapterProperty() {
            // Do nothing (static implementation)
        }

        protected static final String[] getAllNames(final Class<?>... types) {
            List<String> names = new ArrayList<>();

            for (Class<?> type : types) {
                Field[] fields = type.getDeclaredFields();

                for (int i = 0; i < fields.length; i++) {
                    Field field = fields[i];

                    // Exclude synthetic fields (occurs during code coverage analysis)
                    if (!field.isSynthetic()) {
                        Object obj = "";

                        try {
                            obj = field.get(null);
                        }
                        catch (final IllegalAccessException | IllegalArgumentException e) {
                            log.error("Unable to enumerate field names", e);
                        }

                        names.add(obj.toString());
                    }
                }
            }

            return names.toArray(new String[names.size()]);
        }

    }

}
