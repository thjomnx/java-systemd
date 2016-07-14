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
import org.systemd.interfaces.SnapshotInterface;

public class Snapshot extends Unit {

    public static final String SERVICE_NAME = Systemd.SERVICE_NAME + ".Snapshot";
    public static final String UNIT_SUFFIX = ".snapshot";

    public static class Property extends InterfaceAdapter.Property {

        public static final String CLEANUP = "Cleanup";

        private Property() {
            super();
        }

        public static final String[] getAllNames() {
            return getAllNames(Property.class);
        }

    }

    private final Properties properties;

    private Snapshot(final DBusConnection dbus, final SnapshotInterface iface, final String name) throws DBusException {
        super(dbus, iface, name);

        this.properties = Properties.create(dbus, iface.getObjectPath(), SERVICE_NAME);
    }

    static Snapshot create(final DBusConnection dbus, String name) throws DBusException {
        name = Unit.normalizeName(name, UNIT_SUFFIX);

        String objectPath = Unit.OBJECT_PATH + Systemd.escapePath(name);
        SnapshotInterface iface = dbus.getRemoteObject(Systemd.SERVICE_NAME, objectPath, SnapshotInterface.class);

        return new Snapshot(dbus, iface, name);
    }

    @Override
    public SnapshotInterface getInterface() {
        return (SnapshotInterface) super.getInterface();
    }

    @Override
    public Properties getProperties() {
        return properties;
    }

    public boolean isCleanup() {
        return properties.getBoolean(Property.CLEANUP);
    }

}
