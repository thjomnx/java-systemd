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

import de.mnx.java.systemd.interfaces.PathInterface;

public class Path extends Unit {

    public static final String SERVICE_NAME = Systemd.SERVICE_NAME + ".Path";
    public static final String UNIT_SUFFIX = ".path";

    private final Properties properties;

    private Path(final DBusConnection dbus, final PathInterface iface) throws DBusException {
        super(dbus, iface);

        this.properties = Properties.create(dbus, iface.getObjectPath(), SERVICE_NAME);
    }

    static Path create(final DBusConnection dbus, final String objectPath) throws DBusException {
        PathInterface iface = dbus.getRemoteObject(Systemd.SERVICE_NAME, objectPath, PathInterface.class);

        return new Path(dbus, iface);
    }

    @Override
    public PathInterface getInterface() {
        return (PathInterface) super.getInterface();
    }

    public String getUnit() {
        return properties.getString("Unit");
    }

/*
readonly a(ss) Paths = [('PathExistsGlob', '/var/spool/cups/d*')];
 */

    public boolean isMakeDirectory() {
        return properties.getBoolean("MakeDirectory");
    }

    public long getDirectoryMode() {
        return properties.getLong("DirectoryMode");
    }

    public String getResult() {
        return properties.getString("Result");
    }

}
