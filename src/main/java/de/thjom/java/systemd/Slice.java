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

import java.math.BigInteger;
import java.util.List;

import org.freedesktop.dbus.exceptions.DBusException;

import de.thjom.java.systemd.features.IoAccounting;
import de.thjom.java.systemd.features.IpAccounting;
import de.thjom.java.systemd.features.MemoryAccounting;
import de.thjom.java.systemd.interfaces.SliceInterface;
import de.thjom.java.systemd.types.DeviceAllowControl;
import de.thjom.java.systemd.types.IOBandwidth;
import de.thjom.java.systemd.types.IODeviceWeight;
import de.thjom.java.systemd.types.UnitProcessType;

public class Slice extends Unit implements IoAccounting, IpAccounting, MemoryAccounting {

    public static final String SERVICE_NAME = Systemd.SERVICE_NAME + ".Slice";
    public static final String UNIT_SUFFIX = ".slice";

    public static class Property extends InterfaceAdapter.AdapterProperty {

        public static final String BLOCK_IO_ACCOUNTING = "BlockIOAccounting";
        public static final String BLOCK_IO_DEVICE_WEIGHT = "BlockIODeviceWeight";
        public static final String BLOCK_IO_READ_BANDWIDTH = "BlockIOReadBandwidth";
        public static final String BLOCK_IO_WEIGHT = "BlockIOWeight";
        public static final String BLOCK_IO_WRITE_BANDWIDTH = "BlockIOWriteBandwidth";
        public static final String CPU_ACCOUNTING = "CPUAccounting";
        public static final String CPU_SHARES = "CPUShares";
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
                    IoAccounting.Property.class,
                    IpAccounting.Property.class,
                    MemoryAccounting.Property.class
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

    public List<UnitProcessType> getProcesses() {
        return getInterface().getProcesses();
    }

    public boolean isBlockIOAccounting() {
        return properties.getBoolean(Property.BLOCK_IO_ACCOUNTING);
    }

    public List<IODeviceWeight> getBlockIODeviceWeight() {
        return IODeviceWeight.list(properties.getVector(Property.BLOCK_IO_DEVICE_WEIGHT));
    }

    public List<IOBandwidth> getBlockIOReadBandwidth() {
        return IOBandwidth.list(properties.getVector(Property.BLOCK_IO_READ_BANDWIDTH));
    }

    public BigInteger getBlockIOWeight() {
        return properties.getBigInteger(Property.BLOCK_IO_WEIGHT);
    }

    public List<IOBandwidth> getBlockIOWriteBandwidth() {
        return IOBandwidth.list(properties.getVector(Property.BLOCK_IO_WRITE_BANDWIDTH));
    }

    public boolean isCPUAccounting() {
        return properties.getBoolean(Property.CPU_ACCOUNTING);
    }

    public BigInteger getCPUShares() {
        return properties.getBigInteger(Property.CPU_SHARES);
    }

    public String getControlGroup() {
        return properties.getString(Property.CONTROL_GROUP);
    }

    public List<DeviceAllowControl> getDeviceAllow() {
        return DeviceAllowControl.list(properties.getVector(Property.DEVICE_ALLOW));
    }

    public String getDevicePolicy() {
        return properties.getString(Property.DEVICE_POLICY);
    }

    @Override
    public boolean isMemoryDenyWriteExecute() {
        throw new UnsupportedOperationException(Property.ERROR_PROPERTY_MISSING);
    }

    public String getSlice() {
        return properties.getString(Property.SLICE);
    }

}
