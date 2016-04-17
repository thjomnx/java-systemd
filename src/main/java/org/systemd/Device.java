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

package org.systemd;

import org.freedesktop.dbus.DBusConnection;
import org.freedesktop.dbus.exceptions.DBusException;
import org.systemd.interfaces.DeviceInterface;

public class Device extends Unit {

    public static final String SERVICE_NAME = Systemd.SERVICE_NAME + ".Device";
    public static final String UNIT_SUFFIX = ".device";

    public static class Property extends InterfaceAdapter.Property {

        public static final String SYS_FSPATH = "SysFSPath";

        private Property() {
            super();
        }

        public static final String[] getAllNames() {
            return getAllNames(Property.class);
        }

    }

    private final Properties properties;

    private Device(final DBusConnection dbus, final DeviceInterface iface, final String name) throws DBusException {
        super(dbus, iface, name);

        this.properties = Properties.create(dbus, iface.getObjectPath(), SERVICE_NAME);
    }

    static Device create(final DBusConnection dbus, String name) throws DBusException {
        name = Unit.normalizeName(name, UNIT_SUFFIX);

        String objectPath = Unit.OBJECT_PATH + Systemd.escapePath(name);
        DeviceInterface iface = dbus.getRemoteObject(Systemd.SERVICE_NAME, objectPath, DeviceInterface.class);

        return new Device(dbus, iface, name);
    }

    @Override
    public DeviceInterface getInterface() {
        return (DeviceInterface) super.getInterface();
    }

    public String getSysFSPath() {
        return properties.getString(Property.SYS_FSPATH);
    }

}
