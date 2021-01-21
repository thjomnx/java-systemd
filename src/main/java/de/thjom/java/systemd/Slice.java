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

import de.thjom.java.systemd.features.CpuAccounting;
import de.thjom.java.systemd.features.IoAccounting;
import de.thjom.java.systemd.features.IpAccounting;
import de.thjom.java.systemd.features.MemoryAccounting;
import de.thjom.java.systemd.features.ResourceControl;
import de.thjom.java.systemd.features.TasksAccounting;
import de.thjom.java.systemd.interfaces.SliceInterface;
import de.thjom.java.systemd.types.DeviceAllowControl;
import de.thjom.java.systemd.types.UnitProcessType;

public class Slice extends Unit implements CpuAccounting, IoAccounting, IpAccounting, MemoryAccounting, ResourceControl, TasksAccounting {

    public static final String SERVICE_NAME = Systemd.SERVICE_NAME + ".Slice";
    public static final String UNIT_SUFFIX = ".slice";

    public static class Property extends InterfaceAdapter.AdapterProperty {

        public static final String CONTROL_GROUP = "ControlGroup";
        public static final String DEVICE_ALLOW = "DeviceAllow";
        public static final String DEVICE_POLICY = "DevicePolicy";
        public static final String SLICE = "Slice";

        private Property() {
            super();
        }

        public static final String[] getAllNames() {
            return getAllNames(
                    Property.class,
                    CpuAccounting.Property.class,
                    IoAccounting.Property.class,
                    IpAccounting.Property.class,
                    MemoryAccounting.Property.class,
                    ResourceControl.Property.class,
                    TasksAccounting.Property.class
            );
        }

    }

    private Slice(final Manager manager, final SliceInterface iface, final String name) throws DBusException {
        super(manager, iface, name);

        this.properties = Properties.create(dbus, iface.getObjectPath(), SERVICE_NAME);
    }

    static Slice create(final Manager manager, String name) throws DBusException {
        name = Unit.normalizeName(name, UNIT_SUFFIX);

        String objectPath = Unit.OBJECT_PATH + Systemd.escapePath(name);
        SliceInterface iface = manager.dbus.getRemoteObject(Systemd.SERVICE_NAME, objectPath, SliceInterface.class);

        return new Slice(manager, iface, name);
    }

    @Override
    public SliceInterface getInterface() {
        return (SliceInterface) super.getInterface();
    }

    public void attachProcesses(final String cgroupPath, final long[] pids) {
        getInterface().attachProcesses(cgroupPath, pids);
    }

    public List<UnitProcessType> getProcesses() {
        return getInterface().getProcesses();
    }

    public String getControlGroup() {
        return properties.getString(Property.CONTROL_GROUP);
    }

    public List<DeviceAllowControl> getDeviceAllow() {
        return DeviceAllowControl.list(properties.getList(Property.DEVICE_ALLOW));
    }

    public String getDevicePolicy() {
        return properties.getString(Property.DEVICE_POLICY);
    }

    public String getSlice() {
        return properties.getString(Property.SLICE);
    }

}
