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

import java.math.BigInteger;
import java.util.List;

import org.freedesktop.dbus.DBusConnection;
import org.freedesktop.dbus.exceptions.DBusException;
import org.systemd.interfaces.ScopeInterface;
import org.systemd.types.BlockIOBandwidth;
import org.systemd.types.BlockIODeviceWeight;
import org.systemd.types.DeviceAllowControl;

public class Scope extends Unit {

    public static final String SERVICE_NAME = Systemd.SERVICE_NAME + ".Scope";
    public static final String UNIT_SUFFIX = ".scope";

    public static class Property extends InterfaceAdapter.Property {

        public static final String BLOCK_IOACCOUNTING = "BlockIOAccounting";
        public static final String BLOCK_IODEVICE_WEIGHT = "BlockIODeviceWeight";
        public static final String BLOCK_IOREAD_BANDWIDTH = "BlockIOReadBandwidth";
        public static final String BLOCK_IOWEIGHT = "BlockIOWeight";
        public static final String BLOCK_IOWRITE_BANDWIDTH = "BlockIOWriteBandwidth";
        public static final String CPUACCOUNTING = "CPUAccounting";
        public static final String CPUSHARES = "CPUShares";
        public static final String CONTROL_GROUP = "ControlGroup";
        public static final String CONTROLLER = "Controller";
        public static final String DEVICE_ALLOW = "DeviceAllow";
        public static final String DEVICE_POLICY = "DevicePolicy";
        public static final String KILL_MODE = "KillMode";
        public static final String KILL_SIGNAL = "KillSignal";
        public static final String MEMORY_ACCOUNTING = "MemoryAccounting";
        public static final String MEMORY_LIMIT = "MemoryLimit";
        public static final String RESULT = "Result";
        public static final String SEND_SIGHUP = "SendSIGHUP";
        public static final String SEND_SIGKILL = "SendSIGKILL";
        public static final String SLICE = "Slice";
        public static final String TIMEOUT_STOP_USEC = "TimeoutStopUSec";

        private Property() {
            super();
        }

        public static final String[] getAllNames() {
            return getAllNames(Property.class);
        }

    }

    private final Properties properties;

    private Scope(final DBusConnection dbus, final ScopeInterface iface, final String name) throws DBusException {
        super(dbus, iface, name);

        this.properties = Properties.create(dbus, iface.getObjectPath(), SERVICE_NAME);
    }

    static Scope create(final DBusConnection dbus, String name) throws DBusException {
        name = Unit.normalizeName(name, UNIT_SUFFIX);

        String objectPath = Unit.OBJECT_PATH + Systemd.escapePath(name);
        ScopeInterface iface = dbus.getRemoteObject(Systemd.SERVICE_NAME, objectPath, ScopeInterface.class);

        return new Scope(dbus, iface, name);
    }

    @Override
    public ScopeInterface getInterface() {
        return (ScopeInterface) super.getInterface();
    }

    public boolean isBlockIOAccounting() {
        return properties.getBoolean(Property.BLOCK_IOACCOUNTING);
    }

    public List<BlockIODeviceWeight> getBlockIODeviceWeight() {
        return BlockIODeviceWeight.list(properties.getVector(Property.BLOCK_IODEVICE_WEIGHT));
    }

    public List<BlockIOBandwidth> getBlockIOReadBandwidth() {
        return BlockIOBandwidth.list(properties.getVector(Property.BLOCK_IOREAD_BANDWIDTH));
    }

    public BigInteger getBlockIOWeight() {
        return properties.getBigInteger(Property.BLOCK_IOWEIGHT);
    }

    public List<BlockIOBandwidth> getBlockIOWriteBandwidth() {
        return BlockIOBandwidth.list(properties.getVector(Property.BLOCK_IOWRITE_BANDWIDTH));
    }

    public boolean isCPUAccounting() {
        return properties.getBoolean(Property.CPUACCOUNTING);
    }

    public BigInteger getCPUShares() {
        return properties.getBigInteger(Property.CPUSHARES);
    }

    public String getControlGroup() {
        return properties.getString(Property.CONTROL_GROUP);
    }

    public String getController() {
        return properties.getString(Property.CONTROLLER);
    }

    public List<DeviceAllowControl> getDeviceAllow() {
        return DeviceAllowControl.list(properties.getVector(Property.DEVICE_ALLOW));
    }

    public String getDevicePolicy() {
        return properties.getString(Property.DEVICE_POLICY);
    }

    public String getKillMode() {
        return properties.getString(Property.KILL_MODE);
    }

    public int getKillSignal() {
        return properties.getInteger(Property.KILL_SIGNAL);
    }

    public boolean isMemoryAccounting() {
        return properties.getBoolean(Property.MEMORY_ACCOUNTING);
    }

    public BigInteger getMemoryLimit() {
        return properties.getBigInteger(Property.MEMORY_LIMIT);
    }

    public String getResult() {
        return properties.getString(Property.RESULT);
    }

    public boolean isSendSIGHUP() {
        return properties.getBoolean(Property.SEND_SIGHUP);
    }

    public boolean isSendSIGKILL() {
        return properties.getBoolean(Property.SEND_SIGKILL);
    }

    public String getSlice() {
        return properties.getString(Property.SLICE);
    }

    public long getTimeoutStopUSec() {
        return properties.getLong(Property.TIMEOUT_STOP_USEC);
    }

}
