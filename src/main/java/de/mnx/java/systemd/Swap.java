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

import org.freedesktop.dbus.DBusConnection;
import org.freedesktop.dbus.exceptions.DBusException;

import de.mnx.java.systemd.interfaces.SwapInterface;

public class Swap extends Unit {

    public static final String SERVICE_NAME = Systemd.SERVICE_NAME + ".Swap";
    public static final String UNIT_SUFFIX = ".swap";

    private final Properties properties;

    private Swap(final DBusConnection dbus, final SwapInterface iface) throws DBusException {
        super(dbus, iface);

        this.properties = Properties.create(dbus, iface.getObjectPath(), SERVICE_NAME);
    }

    static Swap create(final DBusConnection dbus, final String objectPath) throws DBusException {
        SwapInterface iface = dbus.getRemoteObject(Systemd.SERVICE_NAME, objectPath, SwapInterface.class);

        return new Swap(dbus, iface);
    }

    @Override
    public SwapInterface getInterface() {
        return (SwapInterface) super.getInterface();
    }

}
