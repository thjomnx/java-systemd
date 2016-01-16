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

import java.util.Vector;

import org.freedesktop.DBus.Introspectable;
import org.freedesktop.dbus.DBusConnection;
import org.freedesktop.dbus.exceptions.DBusException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.mnx.java.systemd.interfaces.ManagerInterface;
import de.mnx.java.systemd.interfaces.PropertyInterface;

public final class Systemd {

    public static final String DBUS_BUS_NAME = "org.freedesktop.systemd1";
    public static final String DBUS_OBJECT_PATH = "/org/freedesktop/systemd1";

    public static final String SYSTEMD_MANAGER_NAME = "org.freedesktop.systemd1.Manager";
    public static final String SYSTEMD_PROPERTIES_NAME = "org.freedesktop.DBus.Properties";

    private static final Logger LOG = LoggerFactory.getLogger(Systemd.class);

    private static Systemd instance;

    private DBusConnection dbus;

    private Manager manager;
    private Properties properties;

    private Systemd() {
        // Do nothing (singleton)
    }

    public static final Systemd bus() throws DBusException {
        if (instance == null) {
            instance = new Systemd();
            instance.connect();

            Runtime.getRuntime().addShutdownHook(new Thread() {

                @Override
                public void run() {
                    instance.disconnect();
                }

            });
        }

        return instance;
    }

    private void connect() throws DBusException {
        try {
            dbus = DBusConnection.getConnection(DBusConnection.SYSTEM);
        }
        catch (final DBusException e) {
            LOG.error("Unable to connect to system bus", e);

            throw e;
        }

        try {
            manager = new Manager(dbus.getRemoteObject(DBUS_BUS_NAME, DBUS_OBJECT_PATH, ManagerInterface.class));
        }
        catch (final DBusException e) {
            LOG.error("Unable to get remote object " + SYSTEMD_MANAGER_NAME, e);

            throw e;
        }

        try {
            properties = new Properties(dbus.getRemoteObject(DBUS_BUS_NAME, DBUS_OBJECT_PATH, PropertyInterface.class));
        }
        catch (final DBusException e) {
            LOG.error("Unable to get remote object " + SYSTEMD_PROPERTIES_NAME, e);

            throw e;
        }
    }

    private void disconnect() {
        if (dbus != null) {
            dbus.disconnect();
        }
    }

    public String introspect() throws DBusException {
        Introspectable intro = dbus.getRemoteObject(DBUS_BUS_NAME, DBUS_OBJECT_PATH, Introspectable.class);

        return intro.Introspect();
    }

    public Manager manager() {
        return manager;
    }

    public String getVersion() {
        return properties.getString(Properties.NAME_VERSION);
    }

    public String getArchitecture() {
        return properties.getString(Properties.NAME_ARCHITECTURE);
    }

    public Vector<String> getEnvironment() {
        return properties.getVector(Properties.NAME_ENVIRONMENT);
    }

    public boolean getStatus() {
        return properties.getBoolean(Properties.NAME_SHOW_STATUS);
    }

    public String getSystemState() {
        return properties.getString(Properties.NAME_SYSTEM_STATE);
    }

}
