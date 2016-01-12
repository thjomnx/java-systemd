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

package de.mnx.java.systemd.utils;

import static de.mnx.java.systemd.Systemd.SYSTEMD_MANAGER_NAME;

import java.util.Vector;

import org.freedesktop.dbus.Variant;

import de.mnx.java.systemd.interfaces.Properties;

public final class PropertyAccessor {

    private Properties iface;

    public PropertyAccessor(final Properties iface) {
        this.iface = iface;
    }

    public Properties getInterface() {
        return iface;
    }

    public Variant<?> getVariant(final String name) {
        return iface.getProperty(SYSTEMD_MANAGER_NAME, name);
    }

    public boolean getBoolean(final String name) {
        return (boolean) getVariant(name).getValue();
    }

    public String getString(final String name) {
        return (String) getVariant(name).getValue();
    }

    @SuppressWarnings("unchecked")
    public <T> Vector<T> getVector(final String name) {
        return (Vector<T>) getVariant(name).getValue();
    }

}
