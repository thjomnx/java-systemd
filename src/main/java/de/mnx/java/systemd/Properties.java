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

package de.mnx.java.systemd;

import static de.mnx.java.systemd.Systemd.SYSTEMD_MANAGER_NAME;

import java.util.Vector;

import org.freedesktop.dbus.Variant;

import de.mnx.java.systemd.interfaces.PropertyInterface;

public final class Properties extends InterfaceAdapter {

    public static final String NAME_VERSION = "Version";
    public static final String NAME_ARCHITECTURE = "Architecture";
    public static final String NAME_ENVIRONMENT = "Environment";
    public static final String NAME_SHOW_STATUS = "ShowStatus";
    public static final String NAME_SYSTEM_STATE = "SystemState";

    Properties(final PropertyInterface iface) {
        super(iface);
    }

    @Override
    public PropertyInterface getInterface() {
        return (PropertyInterface) super.getInterface();
    }

    public Variant<?> getVariant(final String name) {
        return getInterface().getProperty(SYSTEMD_MANAGER_NAME, name);
    }

    public boolean getBoolean(final String name) {
        Boolean value = (Boolean) getVariant(name).getValue();

        return value.booleanValue();
    }

    public String getString(final String name) {
        return (String) getVariant(name).getValue();
    }

    @SuppressWarnings("unchecked")
    public <T> Vector<T> getVector(final String name) {
        return (Vector<T>) getVariant(name).getValue();
    }

}
