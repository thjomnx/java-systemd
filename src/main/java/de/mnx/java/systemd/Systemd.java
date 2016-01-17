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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.mnx.java.systemd.adapters.Manager;

public final class Systemd {

    public static final String SYSTEMD_DBUS_NAME = "org.freedesktop.systemd1";
    public static final String SYSTEMD_OBJECT_PATH = "/org/freedesktop/systemd1";

    private static final Logger LOG = LoggerFactory.getLogger(Systemd.class);

    private DBusConnection dbus;

    private Manager manager;

    public Systemd() {
        // Do nothing (singleton)
    }

    public void connect() throws DBusException {
        try {
            dbus = DBusConnection.getConnection(DBusConnection.SYSTEM);
        }
        catch (final DBusException e) {
            LOG.error("Unable to connect to system bus", e);

            throw e;
        }
    }

    public void disconnect() {
        if (dbus != null) {
            dbus.disconnect();
        }
    }

    DBusConnection getConnection() {
        return dbus;
    }

    public String introspect() throws DBusException {
        Introspectable intro = dbus.getRemoteObject(SYSTEMD_DBUS_NAME, SYSTEMD_OBJECT_PATH, Introspectable.class);

        return intro.Introspect();
    }

    public Manager getManager() throws DBusException {
        if (manager == null) {
            manager = Manager.create(dbus);
        }

        return manager;
    }

}
