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

package de.mnx.java.systemd.adapters;

import static de.mnx.java.systemd.Systemd.SYSTEMD_DBUS_NAME;

import java.util.Vector;

import org.freedesktop.dbus.DBusConnection;
import org.freedesktop.dbus.DBusInterface;

public abstract class Unit extends InterfaceAdapter {

    public static final String SERVICE_NAME = SYSTEMD_DBUS_NAME + ".Unit";

    private Properties properties;

    protected Unit(final DBusConnection dbus, final DBusInterface iface) {
        super(dbus, iface);

        this.properties = createProperties(SERVICE_NAME);
    }

    public Vector<String> getWantedBy() {
        return properties.getVector("WantedBy");
    }

    public String getLoadState() {
        return properties.getString("LoadState");
    }

    public String getActiveState() {
        return properties.getString("ActiveState");
    }

}
