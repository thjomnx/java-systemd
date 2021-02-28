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

import java.util.List;

import org.freedesktop.dbus.exceptions.DBusException;

import de.thjom.java.systemd.interfaces.TargetInterface;

public class Target extends Unit {

    public static final String SERVICE_NAME = Systemd.SERVICE_NAME + ".Target";
    public static final String UNIT_SUFFIX = ".target";

    public static class Property extends InterfaceAdapter.AdapterProperty {

        // No properties available so far

        private Property() {
            super();
        }

        public static final List<String> getAllNames() {
            return getAllNames(Property.class);
        }

    }

    private Target(final Manager manager, final TargetInterface iface, final String name) throws DBusException {
        super(manager, iface, name);

        this.properties = Properties.create(dbus, iface.getObjectPath(), SERVICE_NAME);
    }

    static Target create(final Manager manager, String name) throws DBusException {
        name = Unit.normalizeName(name, UNIT_SUFFIX);

        String objectPath = Unit.OBJECT_PATH + Systemd.escapePath(name);
        TargetInterface iface = manager.dbus.getRemoteObject(Systemd.SERVICE_NAME, objectPath, TargetInterface.class);

        return new Target(manager, iface, name);
    }

    @Override
    public TargetInterface getInterface() {
        return (TargetInterface) super.getInterface();
    }

}
