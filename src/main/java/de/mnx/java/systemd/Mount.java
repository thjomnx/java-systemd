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

import de.mnx.java.systemd.interfaces.MountInterface;

public class Mount extends Unit {

    public static final String SERVICE_NAME = Systemd.SERVICE_NAME + ".Mount";
    public static final String UNIT_SUFFIX = ".mount";

    private final Properties properties;

    private Mount(final DBusConnection dbus, final MountInterface iface) throws DBusException {
        super(dbus, iface);

        this.properties = Properties.create(dbus, iface.getObjectPath(), SERVICE_NAME);
    }

    static Mount create(final DBusConnection dbus, final String objectPath) throws DBusException {
        MountInterface iface = dbus.getRemoteObject(Systemd.SERVICE_NAME, objectPath, MountInterface.class);

        return new Mount(dbus, iface);
    }

    @Override
    public MountInterface getInterface() {
        return (MountInterface) super.getInterface();
    }

}
