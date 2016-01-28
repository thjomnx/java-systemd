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

import static de.mnx.java.systemd.Systemd.SYSTEMD_DBUS_NAME;
import static de.mnx.java.systemd.Systemd.SYSTEMD_OBJECT_PATH;

import java.util.List;
import java.util.Vector;

import org.freedesktop.dbus.DBusConnection;
import org.freedesktop.dbus.exceptions.DBusException;

import de.mnx.java.systemd.Systemd;
import de.mnx.java.systemd.interfaces.ManagerInterface;
import de.mnx.java.systemd.interfaces.ServiceInterface;
import de.mnx.java.systemd.types.UnitFileStatus;

public class Manager extends InterfaceAdapter {

    public static final String SERVICE_NAME = SYSTEMD_DBUS_NAME + ".Manager";

    public static final String PROPERTY_VERSION = "Version";
    public static final String PROPERTY_ARCHITECTURE = "Architecture";
    public static final String PROPERTY_ENVIRONMENT = "Environment";
    public static final String PROPERTY_SHOW_STATUS = "ShowStatus";
    public static final String PROPERTY_SYSTEM_STATE = "SystemState";

    private Manager(final DBusConnection dbus, final ManagerInterface iface) {
        super(dbus, iface);

        initProperties(SERVICE_NAME);
    }

    public static Manager create(final DBusConnection dbus) throws DBusException {
        ManagerInterface iface = dbus.getRemoteObject(SYSTEMD_DBUS_NAME, SYSTEMD_OBJECT_PATH, ManagerInterface.class);

        return new Manager(dbus, iface);
    }

    @Override
    public ManagerInterface getInterface() {
        return (ManagerInterface) super.getInterface();
    }

    public List<UnitFileStatus> listUnitFiles() {
        return getInterface().listUnitFiles();
    }

    public Service getService(final String name) throws DBusException {
        String service = name.endsWith(".service") ? name : name + ".service";
        String objectPath = SYSTEMD_OBJECT_PATH + "/unit/" + Systemd.escapePath(service);

        ServiceInterface iface = dbus.getRemoteObject(SYSTEMD_DBUS_NAME, objectPath, ServiceInterface.class);

        return new Service(dbus, iface);
    }

    public String dump() {
        return getInterface().dump();
    }

    public String getVersion() {
        return properties.getString(PROPERTY_VERSION);
    }

    public String getArchitecture() {
        return properties.getString(PROPERTY_ARCHITECTURE);
    }

    public Vector<String> getEnvironment() {
        return properties.getVector(PROPERTY_ENVIRONMENT);
    }

    public boolean getStatus() {
        return properties.getBoolean(PROPERTY_SHOW_STATUS);
    }

    public String getSystemState() {
        return properties.getString(PROPERTY_SYSTEM_STATE);
    }

}
