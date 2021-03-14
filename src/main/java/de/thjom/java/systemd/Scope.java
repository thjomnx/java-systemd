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

import de.thjom.java.systemd.features.CpuAccounting;
import de.thjom.java.systemd.features.IoAccounting;
import de.thjom.java.systemd.features.IpAccounting;
import de.thjom.java.systemd.features.MemoryAccounting;
import de.thjom.java.systemd.features.ResourceControl;
import de.thjom.java.systemd.features.TasksAccounting;
import de.thjom.java.systemd.interfaces.ScopeInterface;
import de.thjom.java.systemd.types.DeviceAllowControl;
import de.thjom.java.systemd.types.UnitProcessType;

public class Scope extends Unit implements CpuAccounting, IoAccounting, IpAccounting, MemoryAccounting, ResourceControl, TasksAccounting {

    public static final String SERVICE_NAME = Systemd.SERVICE_NAME + ".Scope";
    public static final String UNIT_SUFFIX = ".scope";

    public static class Property extends InterfaceAdapter.AdapterProperty {

        public static final String CONTROL_GROUP = "ControlGroup";
        public static final String CONTROLLER = "Controller";
        public static final String DEVICE_ALLOW = "DeviceAllow";
        public static final String DEVICE_POLICY = "DevicePolicy";
        public static final String FINAL_KILL_SIGNAL = "FinalKillSignal";
        public static final String KILL_MODE = "KillMode";
        public static final String KILL_SIGNAL = "KillSignal";
        public static final String RESTART_KILL_SIGNAL = "RestartKillSignal";
        public static final String RESULT = "Result";
        public static final String RUNTIME_MAX_USEC = "RuntimeMaxUSec";
        public static final String SEND_SIGHUP = "SendSIGHUP";
        public static final String SEND_SIGKILL = "SendSIGKILL";
        public static final String SLICE = "Slice";
        public static final String TIMEOUT_STOP_USEC = "TimeoutStopUSec";
        public static final String WATCHDOG_SIGNAL = "WatchdogSignal";

        private Property() {
            super();
        }

        public static List<String> getAllNames() {
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

    private Scope(final Manager manager, final ScopeInterface iface, final String name) throws DBusException {
        super(manager, iface, name);

        this.properties = Properties.create(dbus, iface.getObjectPath(), SERVICE_NAME);
    }

    static Scope create(final Manager manager, String name) throws DBusException {
        name = Unit.normalizeName(name, UNIT_SUFFIX);

        String objectPath = Unit.OBJECT_PATH + Systemd.escapePath(name);
        ScopeInterface iface = manager.dbus.getRemoteObject(Systemd.SERVICE_NAME, objectPath, ScopeInterface.class);

        return new Scope(manager, iface, name);
    }

    @Override
    public ScopeInterface getInterface() {
        return (ScopeInterface) super.getInterface();
    }

    public void abandon() {
        getInterface().abandon();
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

    public String getController() {
        return properties.getString(Property.CONTROLLER);
    }

    public List<DeviceAllowControl> getDeviceAllow() {
        return DeviceAllowControl.list(properties.getList(Property.DEVICE_ALLOW));
    }

    public String getDevicePolicy() {
        return properties.getString(Property.DEVICE_POLICY);
    }

    public int getFinalKillSignal() {
        return properties.getInteger(Property.FINAL_KILL_SIGNAL);
    }

    public String getKillMode() {
        return properties.getString(Property.KILL_MODE);
    }

    public int getKillSignal() {
        return properties.getInteger(Property.KILL_SIGNAL);
    }

    public int getRestartKillSignal() {
        return properties.getInteger(Property.RESTART_KILL_SIGNAL);
    }

    public String getResult() {
        return properties.getString(Property.RESULT);
    }

    public BigInteger getRuntimeMaxUSec() {
        return properties.getBigInteger(Property.RUNTIME_MAX_USEC);
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

    public BigInteger getTimeoutStopUSec() {
        return properties.getBigInteger(Property.TIMEOUT_STOP_USEC);
    }

    public int getWatchdogSignal() {
        return properties.getInteger(Property.WATCHDOG_SIGNAL);
    }

}
