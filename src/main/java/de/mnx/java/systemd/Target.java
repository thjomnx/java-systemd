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

import de.mnx.java.systemd.interfaces.TargetInterface;

public class Target extends Unit {

    public static final String SERVICE_NAME = Systemd.SERVICE_NAME + ".Target";
    public static final String UNIT_SUFFIX = ".target";

    @SuppressWarnings("unused")
    private final Properties properties;

    private Target(final DBusConnection dbus, final TargetInterface iface) throws DBusException {
        super(dbus, iface);

        this.properties = Properties.create(dbus, iface.getObjectPath(), SERVICE_NAME);
    }

    static Target create(final DBusConnection dbus, final String objectPath) throws DBusException {
        TargetInterface iface = dbus.getRemoteObject(Systemd.SERVICE_NAME, objectPath, TargetInterface.class);

        return new Target(dbus, iface);
    }

    @Override
    public TargetInterface getInterface() {
        return (TargetInterface) super.getInterface();
    }

}
