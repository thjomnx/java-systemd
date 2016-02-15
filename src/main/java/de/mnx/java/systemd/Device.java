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

import org.freedesktop.DBus.Introspectable;
import org.freedesktop.dbus.DBusConnection;
import org.freedesktop.dbus.exceptions.DBusException;

import de.mnx.java.systemd.interfaces.DeviceInterface;

public class Device extends Unit {

    public static final String SERVICE_NAME = Systemd.SERVICE_NAME + ".Device";
    public static final String UNIT_SUFFIX = ".device";

    private final Properties properties;

    private Device(final DBusConnection dbus, final DeviceInterface iface) throws DBusException {
        super(dbus, iface);

        this.properties = Properties.create(dbus, iface.getObjectPath(), SERVICE_NAME);
    }

    static Device create(final DBusConnection dbus, final String objectPath) throws DBusException {
        DeviceInterface iface = dbus.getRemoteObject(Systemd.SERVICE_NAME, objectPath, DeviceInterface.class);

        return new Device(dbus, iface);
    }

    @Override
    public DeviceInterface getInterface() {
        return (DeviceInterface) super.getInterface();
    }

    public String introspect() throws DBusException {
        Introspectable intro = dbus.getRemoteObject(Systemd.SERVICE_NAME, getInterface().getObjectPath(), Introspectable.class);

        return intro.Introspect();
    }

    public String getSysFSPath() {
        return properties.getString("SysFSPath");
    }

}
