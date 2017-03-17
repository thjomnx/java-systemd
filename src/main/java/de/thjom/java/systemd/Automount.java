/*
 * Java-systemd implementation
 * Copyright (c) 2016 Markus Enax
 *
 * This program is free software; you can redistribute it and/or modify it under
 * the terms of either the GNU Lesser General Public License Version 2 or the
 * Academic Free Licence Version 3.0.
 *
 * Full licence texts are included in the COPYING file with this program.
 */

package de.thjom.java.systemd;

import org.freedesktop.dbus.exceptions.DBusException;

import de.thjom.java.systemd.interfaces.AutomountInterface;

public class Automount extends Unit {

    public static final String SERVICE_NAME = Systemd.SERVICE_NAME + ".Automount";
    public static final String UNIT_SUFFIX = ".automount";

    public static class Property extends InterfaceAdapter.Property {

        public static final String WHERE = "Where";
        public static final String DIRECTORY_MODE = "DirectoryMode";
        public static final String RESULT = "Result";

        private Property() {
            super();
        }

        public static final String[] getAllNames() {
            return getAllNames(Property.class);
        }

    }

    private Automount(final Manager manager, final AutomountInterface iface, final String name) throws DBusException {
        super(manager, iface, name);

        this.properties = Properties.create(dbus, iface.getObjectPath(), SERVICE_NAME);
    }

    static Automount create(final Manager manager, String name) throws DBusException {
        name = Unit.normalizeName(name, UNIT_SUFFIX);

        String objectPath = Unit.OBJECT_PATH + Systemd.escapePath(name);
        AutomountInterface iface = manager.dbus.getRemoteObject(Systemd.SERVICE_NAME, objectPath, AutomountInterface.class);

        return new Automount(manager, iface, name);
    }

    @Override
    public AutomountInterface getInterface() {
        return (AutomountInterface) super.getInterface();
    }

    public String getWhere() {
        return properties.getString(Property.WHERE);
    }

    public long getDirectoryMode() {
        return properties.getLong(Property.DIRECTORY_MODE);
    }

    public String getResult() {
        return properties.getString(Property.RESULT);
    }

}
