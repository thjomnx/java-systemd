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

import java.util.List;

import org.freedesktop.dbus.DBusConnection;
import org.freedesktop.dbus.exceptions.DBusException;
import org.systemd.interfaces.PathInterface;
import org.systemd.types.PathInfo;

public class Path extends Unit {

    public static final String SERVICE_NAME = Systemd.SERVICE_NAME + ".Path";
    public static final String UNIT_SUFFIX = ".path";

    public static class Property extends InterfaceAdapter.Property {

        public static final String DIRECTORY_MODE = "DirectoryMode";
        public static final String MAKE_DIRECTORY = "MakeDirectory";
        public static final String PATHS = "Paths";
        public static final String RESULT = "Result";
        public static final String UNIT = "Unit";

        private Property() {
            super();
        }

        public static final String[] getAllNames() {
            return getAllNames(Property.class);
        }

    }

    private Path(final DBusConnection dbus, final PathInterface iface, final String name) throws DBusException {
        super(dbus, iface, name);

        this.properties = Properties.create(dbus, iface.getObjectPath(), SERVICE_NAME);
    }

    static Path create(final DBusConnection dbus, String name) throws DBusException {
        name = Unit.normalizeName(name, UNIT_SUFFIX);

        String objectPath = Unit.OBJECT_PATH + Systemd.escapePath(name);
        PathInterface iface = dbus.getRemoteObject(Systemd.SERVICE_NAME, objectPath, PathInterface.class);

        return new Path(dbus, iface, name);
    }

    @Override
    public PathInterface getInterface() {
        return (PathInterface) super.getInterface();
    }

    public long getDirectoryMode() {
        return properties.getLong(Property.DIRECTORY_MODE);
    }

    public boolean isMakeDirectory() {
        return properties.getBoolean(Property.MAKE_DIRECTORY);
    }

    /*
     * TODO 'Paths', see https://www.freedesktop.org/wiki/Software/systemd/dbus/
     *
     * Paths contains an array of structs. Each struct contains the condition to watch, which can be
     * one of PathExists, PathExistsGlob, PathChanged, PathModified, DirectoryNotEmpty which correspond
     * directly to the matching settings in the path unit files; and the path to watch, possibly including
     * glob expressions.
     *
     * readonly a(ss) Paths = [('PathExistsGlob', '/var/spool/cups/d*')];
     */

    public List<PathInfo> getPaths() {
        return PathInfo.list(properties.getVector(Property.PATHS));
    }

    public String getResult() {
        return properties.getString(Property.RESULT);
    }

    public String getUnit() {
        return properties.getString(Property.UNIT);
    }

}
