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

import org.freedesktop.dbus.DBusConnection;
import org.freedesktop.dbus.DBusInterface;

public class Service extends Unit {

    public static final String SERVICE_NAME = "org.freedesktop.systemd1.Service";

    public static final String PROPERTY_MAIN_PID= "MainPID";

    public Service(final DBusConnection dbus, final DBusInterface iface) {
        super(dbus, iface);

        initProperties(SERVICE_NAME);
    }

    public int getProcessId() {
        return properties.getInteger(PROPERTY_MAIN_PID);
    }

}
