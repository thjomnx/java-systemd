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
import java.util.Vector;

import org.freedesktop.dbus.exceptions.DBusException;

import de.thjom.java.systemd.features.DynamicUserAccounting;
import de.thjom.java.systemd.features.ExtendedCpuAccounting;
import de.thjom.java.systemd.features.ExtendedMemoryAccounting;
import de.thjom.java.systemd.features.IoAccounting;
import de.thjom.java.systemd.features.IpAccounting;
import de.thjom.java.systemd.features.ResourceControl;
import de.thjom.java.systemd.features.TasksAccounting;
import de.thjom.java.systemd.features.Ulimit;
import de.thjom.java.systemd.interfaces.SwapInterface;
import de.thjom.java.systemd.types.DeviceAllowControl;
import de.thjom.java.systemd.types.EnvironmentFile;
import de.thjom.java.systemd.types.ExecutionInfo;
import de.thjom.java.systemd.types.SystemCallFilter;
import de.thjom.java.systemd.types.UnitProcessType;

public class Swap extends Unit implements ExtendedCpuAccounting, DynamicUserAccounting, IoAccounting, IpAccounting, ExtendedMemoryAccounting, ResourceControl, TasksAccounting, Ulimit {

    public static final String SERVICE_NAME = Systemd.SERVICE_NAME + ".Swap";
    public static final String UNIT_SUFFIX = ".swap";

    public static class Property extends InterfaceAdapter.AdapterProperty {

        public static final String CAPABILITY_BOUNDING_SET = "CapabilityBoundingSet";
        public static final String CONTROL_PID = "ControlPID";
        public static final String DEVICE_ALLOW = "DeviceAllow";
        public static final String DEVICE_POLICY = "DevicePolicy";
        public static final String DYNAMIC_USER = "DynamicUser";
        public static final String ENVIRONMENT = "Environment";
        public static final String ENVIRONMENT_FILES = "EnvironmentFiles";
        public static final String EXEC_ACTIVATE = "ExecActivate";
        public static final String EXEC_DEACTIVATE = "ExecDeactivate";
        public static final String FINAL_KILL_SIGNAL = "FinalKillSignal";
        public static final String IGNORE_SIGPIPE = "IgnoreSIGPIPE";
        public static final String INACCESSIBLE_PATHS = "InaccessiblePaths";
        public static final String IO_SCHEDULING_CLASS = "IOSchedulingClass";
        public static final String IO_SCHEDULING_PRIORITY = "IOSchedulingPriority";
        public static final String KILL_MODE = "KillMode";
        public static final String KILL_SIGNAL = "KillSignal";
        public static final String MOUNT_FLAGS = "MountFlags";
        public static final String NICE = "Nice";
        public static final String NON_BLOCKING = "NonBlocking";
        public static final String NO_NEW_PRIVILEGES = "NoNewPrivileges";
        public static final String OOM_SCORE_ADJUST = "OOMScoreAdjust";
        public static final String OPTIONS = "Options";
        public static final String PAM_NAME = "PAMName";
        public static final String PRIORITY = "Priority";
        public static final String READ_ONLY_PATHS = "ReadOnlyPaths";
        public static final String READ_WRITE_PATHS = "ReadWritePaths";
        public static final String RESTART_KILL_SIGNAL = "RestartKillSignal";
        public static final String RESULT = "Result";
        public static final String ROOT_DIRECTORY = "RootDirectory";
        public static final String SAME_PROCESS_GROUP = "SameProcessGroup";
        public static final String SECURE_BITS = "SecureBits";
        public static final String SEND_SIGHUP = "SendSIGHUP";
        public static final String SEND_SIGKILL = "SendSIGKILL";
        public static final String SLICE = "Slice";
        public static final String SUPPLEMENTARY_GROUPS = "SupplementaryGroups";
        public static final String SYSLOG_IDENTIFIER = "SyslogIdentifier";
        public static final String SYSLOG_LEVEL_PREFIX = "SyslogLevelPrefix";
        public static final String SYSLOG_PRIORITY = "SyslogPriority";
        public static final String SYSTEM_CALL_FILTER = "SystemCallFilter";
        public static final String TIMEOUT_USEC = "TimeoutUSec";
        public static final String TIMER_SLACK_NSEC = "TimerSlackNSec";
        public static final String TTY_PATH = "TTYPath";
        public static final String TTY_RESET = "TTYReset";
        public static final String TTY_V_HANGUP = "TTYVHangup";
        public static final String TTY_VT_DISALLOCATE = "TTYVTDisallocate";
        public static final String UMASK = "UMask";
        public static final String WATCHDOG_SIGNAL = "WatchdogSignal";
        public static final String WHAT = "What";
        public static final String WORKING_DIRECTORY = "WorkingDirectory";

        private Property() {
            super();
        }

        public static final String[] getAllNames() {
            return getAllNames(
                    Property.class,
                    ExtendedCpuAccounting.Property.class,
                    DynamicUserAccounting.Property.class,
                    IoAccounting.Property.class,
                    IpAccounting.Property.class,
                    ExtendedMemoryAccounting.Property.class,
                    ResourceControl.Property.class,
                    TasksAccounting.Property.class,
                    Ulimit.Property.class
            );
        }

    }

    private Swap(final Manager manager, final SwapInterface iface, final String name) throws DBusException {
        super(manager, iface, name);

        this.properties = Properties.create(dbus, iface.getObjectPath(), SERVICE_NAME);
    }

    static Swap create(final Manager manager, String name) throws DBusException {
        name = Unit.normalizeName(name, UNIT_SUFFIX);

        String objectPath = Unit.OBJECT_PATH + Systemd.escapePath(name);
        SwapInterface iface = manager.dbus.getRemoteObject(Systemd.SERVICE_NAME, objectPath, SwapInterface.class);

        return new Swap(manager, iface, name);
    }

    @Override
    public SwapInterface getInterface() {
        return (SwapInterface) super.getInterface();
    }

    public void attachProcesses(final String cgroupPath, final long[] pids) {
        getInterface().attachProcesses(cgroupPath, pids);
    }

    public List<UnitProcessType> getProcesses() {
        return getInterface().getProcesses();
    }

    public BigInteger getCapabilityBoundingSet() {
        return properties.getBigInteger(Property.CAPABILITY_BOUNDING_SET);
    }

    public long getControlPID() {
        return properties.getLong(Property.CONTROL_PID);
    }

    public List<DeviceAllowControl> getDeviceAllow() {
        return DeviceAllowControl.list(properties.getVector(Property.DEVICE_ALLOW));
    }

    public String getDevicePolicy() {
        return properties.getString(Property.DEVICE_POLICY);
    }

    public Vector<String> getEnvironment() {
        return properties.getVector(Property.ENVIRONMENT);
    }

    public List<EnvironmentFile> getEnvironmentFiles() {
        return EnvironmentFile.list(properties.getVector(Property.ENVIRONMENT_FILES));
    }

    public List<ExecutionInfo> getExecActivate() {
        return ExecutionInfo.list(properties.getVector(Property.EXEC_ACTIVATE));
    }

    public List<ExecutionInfo> getExecDeactivate() {
        return ExecutionInfo.list(properties.getVector(Property.EXEC_DEACTIVATE));
    }

    public int getFinalKillSignal() {
        return properties.getInteger(Property.FINAL_KILL_SIGNAL);
    }

    public boolean isIgnoreSIGPIPE() {
        return properties.getBoolean(Property.IGNORE_SIGPIPE);
    }

    public Vector<String> getInaccessiblePaths() {
        return properties.getVector(Property.INACCESSIBLE_PATHS);
    }

    public int getIOSchedulingClass() {
        return properties.getInteger(Property.IO_SCHEDULING_CLASS);
    }

    public int getIOSchedulingPriority() {
        return properties.getInteger(Property.IO_SCHEDULING_PRIORITY);
    }

    public String getKillMode() {
        return properties.getString(Property.KILL_MODE);
    }

    public int getKillSignal() {
        return properties.getInteger(Property.KILL_SIGNAL);
    }

    public BigInteger getMountFlags() {
        return properties.getBigInteger(Property.MOUNT_FLAGS);
    }

    public int getNice() {
        return properties.getInteger(Property.NICE);
    }

    public boolean isNoNewPrivileges() {
        return properties.getBoolean(Property.NO_NEW_PRIVILEGES);
    }

    public boolean isNonBlocking() {
        return properties.getBoolean(Property.NON_BLOCKING);
    }

    public int getOOMScoreAdjust() {
        return properties.getInteger(Property.OOM_SCORE_ADJUST);
    }

    public String getOptions() {
        return properties.getString(Property.OPTIONS);
    }

    public String getPAMName() {
        return properties.getString(Property.PAM_NAME);
    }

    public int getPriority() {
        return properties.getInteger(Property.PRIORITY);
    }

    public Vector<String> getReadOnlyPaths() {
        return properties.getVector(Property.READ_ONLY_PATHS);
    }

    public Vector<String> getReadWritePaths() {
        return properties.getVector(Property.READ_WRITE_PATHS);
    }

    public int getRestartKillSignal() {
        return properties.getInteger(Property.RESTART_KILL_SIGNAL);
    }

    public String getResult() {
        return properties.getString(Property.RESULT);
    }

    public String getRootDirectory() {
        return properties.getString(Property.ROOT_DIRECTORY);
    }

    public boolean isSameProcessGroup() {
        return properties.getBoolean(Property.SAME_PROCESS_GROUP);
    }

    public int getSecureBits() {
        return properties.getInteger(Property.SECURE_BITS);
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

    public Vector<String> getSupplementaryGroups() {
        return properties.getVector(Property.SUPPLEMENTARY_GROUPS);
    }

    public String getSyslogIdentifier() {
        return properties.getString(Property.SYSLOG_IDENTIFIER);
    }

    public boolean isSyslogLevelPrefix() {
        return properties.getBoolean(Property.SYSLOG_LEVEL_PREFIX);
    }

    public int getSyslogPriority() {
        return properties.getInteger(Property.SYSLOG_PRIORITY);
    }

    public SystemCallFilter getSystemCallFilter() {
        Object[] array = (Object[]) properties.getVariant(Property.SYSTEM_CALL_FILTER).getValue();

        return new SystemCallFilter(array);
    }

    public String getTTYPath() {
        return properties.getString(Property.TTY_PATH);
    }

    public boolean isTTYReset() {
        return properties.getBoolean(Property.TTY_RESET);
    }

    public boolean isTTYVHangup() {
        return properties.getBoolean(Property.TTY_V_HANGUP);
    }

    public boolean isTTYVTDisallocate() {
        return properties.getBoolean(Property.TTY_VT_DISALLOCATE);
    }

    public BigInteger getTimeoutUSec() {
        return properties.getBigInteger(Property.TIMEOUT_USEC);
    }

    public long getTimerSlackNSec() {
        return properties.getLong(Property.TIMER_SLACK_NSEC);
    }

    public long getUMask() {
        return properties.getLong(Property.UMASK);
    }

    public int getWatchdogSignal() {
        return properties.getInteger(Property.WATCHDOG_SIGNAL);
    }

    public String getWhat() {
        return properties.getString(Property.WHAT);
    }

    public String getWorkingDirectory() {
        return properties.getString(Property.WORKING_DIRECTORY);
    }

}
