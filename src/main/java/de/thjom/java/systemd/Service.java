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
import de.thjom.java.systemd.features.TasksAccounting;
import de.thjom.java.systemd.features.Ulimit;
import de.thjom.java.systemd.interfaces.ServiceInterface;
import de.thjom.java.systemd.types.DeviceAllowControl;
import de.thjom.java.systemd.types.EnvironmentFile;
import de.thjom.java.systemd.types.ExecutionInfo;
import de.thjom.java.systemd.types.SELinuxContext;
import de.thjom.java.systemd.types.SmackProcessLabel;
import de.thjom.java.systemd.types.SystemCallFilter;
import de.thjom.java.systemd.types.UnitProcessType;

public class Service extends Unit implements ExtendedCpuAccounting, DynamicUserAccounting, IoAccounting, IpAccounting, ExtendedMemoryAccounting, TasksAccounting, Ulimit {

    public static final String SERVICE_NAME = Systemd.SERVICE_NAME + ".Service";
    public static final String UNIT_SUFFIX = ".service";

    public static class Property extends InterfaceAdapter.AdapterProperty {

        public static final String BUS_NAME = "BusName";
        public static final String CAPABILITY_BOUNDING_SET = "CapabilityBoundingSet";
        public static final String CONTROL_PID = "ControlPID";
        public static final String DELEGATE = "Delegate";
        public static final String DELEGATE_CONTROLLERS = "DelegateControllers";
        public static final String DEVICE_ALLOW = "DeviceAllow";
        public static final String DEVICE_POLICY = "DevicePolicy";
        public static final String ENVIRONMENT = "Environment";
        public static final String ENVIRONMENT_FILES = "EnvironmentFiles";
        public static final String EXEC_MAIN_CODE = "ExecMainCode";
        public static final String EXEC_MAIN_EXIT_TIMESTAMP = "ExecMainExitTimestamp";
        public static final String EXEC_MAIN_EXIT_TIMESTAMP_MONOTONIC = "ExecMainExitTimestampMonotonic";
        public static final String EXEC_MAIN_PID = "ExecMainPID";
        public static final String EXEC_MAIN_START_TIMESTAMP = "ExecMainStartTimestamp";
        public static final String EXEC_MAIN_START_TIMESTAMP_MONOTONIC = "ExecMainStartTimestampMonotonic";
        public static final String EXEC_MAIN_STATUS = "ExecMainStatus";
        public static final String EXEC_RELOAD = "ExecReload";
        public static final String EXEC_START = "ExecStart";
        public static final String EXEC_START_POST = "ExecStartPost";
        public static final String EXEC_START_PRE = "ExecStartPre";
        public static final String EXEC_STOP = "ExecStop";
        public static final String EXEC_STOP_POST = "ExecStopPost";
        public static final String FILE_DESCRIPTOR_STORE_MAX = "FileDescriptorStoreMax";
        public static final String GROUP = "Group";
        public static final String GUESS_MAIN_PID = "GuessMainPID";
        public static final String IO_SCHEDULING_CLASS = "IOSchedulingClass";
        public static final String IO_SCHEDULING_PRIORITY = "IOSchedulingPriority";
        public static final String IGNORE_SIGPIPE = "IgnoreSIGPIPE";
        public static final String INACCESSIBLE_PATHS = "InaccessiblePaths";
        public static final String KILL_MODE = "KillMode";
        public static final String KILL_SIGNAL = "KillSignal";
        public static final String MAIN_PID = "MainPID";
        public static final String MOUNT_FLAGS = "MountFlags";
        public static final String NFILE_DESCRIPTOR_STORE = "NFileDescriptorStore";
        public static final String NICE = "Nice";
        public static final String NO_NEW_PRIVILEGES = "NoNewPrivileges";
        public static final String NON_BLOCKING = "NonBlocking";
        public static final String NOTIFY_ACCESS = "NotifyAccess";
        public static final String N_RESTARTS = "NRestarts";
        public static final String OOM_SCORE_ADJUST = "OOMScoreAdjust";
        public static final String PAM_NAME = "PAMName";
        public static final String PID_FILE = "PIDFile";
        public static final String PERMISSIONS_START_ONLY = "PermissionsStartOnly";
        public static final String READ_ONLY_PATHS = "ReadOnlyPaths";
        public static final String READ_WRITE_PATHS = "ReadWritePaths";
        public static final String REMAIN_AFTER_EXIT = "RemainAfterExit";
        public static final String RESTART = "Restart";
        public static final String RESTART_USEC = "RestartUSec";
        public static final String RESULT = "Result";
        public static final String ROOT_DIRECTORY = "RootDirectory";
        public static final String ROOT_DIRECTORY_START_ONLY = "RootDirectoryStartOnly";
        public static final String RUNTIME_MAX_USEC = "RuntimeMaxUSec";
        public static final String SELINUX_CONTEXT = "SELinuxContext";
        public static final String SAME_PROCESS_GROUP = "SameProcessGroup";
        public static final String SECURE_BITS = "SecureBits";
        public static final String SEND_SIGHUP = "SendSIGHUP";
        public static final String SEND_SIGKILL = "SendSIGKILL";
        public static final String SLICE = "Slice";
        public static final String SMACK_PROCESS_LABEL = "SmackProcessLabel";
        public static final String STATUS_ERRNO = "StatusErrno";
        public static final String STATUS_TEXT = "StatusText";
        public static final String SUPPLEMENTARY_GROUPS = "SupplementaryGroups";
        public static final String SYSLOG_IDENTIFIER = "SyslogIdentifier";
        public static final String SYSLOG_LEVEL_PREFIX = "SyslogLevelPrefix";
        public static final String SYSLOG_PRIORITY = "SyslogPriority";
        public static final String SYSTEM_CALL_FILTER = "SystemCallFilter";
        public static final String TTY_PATH = "TTYPath";
        public static final String TTY_RESET = "TTYReset";
        public static final String TTY_V_HANGUP = "TTYVHangup";
        public static final String TTY_VT_DISALLOCATE = "TTYVTDisallocate";
        public static final String TASKS_ACCOUNTING = "TasksAccounting";
        public static final String TASKS_CURRENT = "TasksCurrent";
        public static final String TASKS_MAX = "TasksMax";
        public static final String TIMEOUT_START_USEC = "TimeoutStartUSec";
        public static final String TIMEOUT_STOP_USEC = "TimeoutStopUSec";
        public static final String TIMER_SLACK_NSEC = "TimerSlackNSec";
        public static final String TYPE = "Type";
        public static final String UMASK = "UMask";
        public static final String USB_FUNCTION_DESCRIPTORS = "USBFunctionDescriptors";
        public static final String USB_FUNCTION_STRINGS = "USBFunctionStrings";
        public static final String WATCHDOG_TIMESTAMP = "WatchdogTimestamp";
        public static final String WATCHDOG_TIMESTAMP_MONOTONIC = "WatchdogTimestampMonotonic";
        public static final String WATCHDOG_USEC = "WatchdogUSec";
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
                    TasksAccounting.Property.class,
                    Ulimit.Property.class
            );
        }

    }

    private Service(final Manager manager, final ServiceInterface iface, final String name) throws DBusException {
        super(manager, iface, name);

        this.properties = Properties.create(dbus, iface.getObjectPath(), SERVICE_NAME);
    }

    static Service create(final Manager manager, String name) throws DBusException {
        name = Unit.normalizeName(name, UNIT_SUFFIX);

        String objectPath = Unit.OBJECT_PATH + Systemd.escapePath(name);
        ServiceInterface iface = manager.dbus.getRemoteObject(Systemd.SERVICE_NAME, objectPath, ServiceInterface.class);

        return new Service(manager, iface, name);
    }

    @Override
    public ServiceInterface getInterface() {
        return (ServiceInterface) super.getInterface();
    }

    public void attachProcesses(final String cgroupPath, final long[] pids) {
        getInterface().attachProcesses(cgroupPath, pids);
    }

    public List<UnitProcessType> getProcesses() {
        return getInterface().getProcesses();
    }

    public String getBusName() {
        return properties.getString(Property.BUS_NAME);
    }

    public BigInteger getCapabilityBoundingSet() {
        return properties.getBigInteger(Property.CAPABILITY_BOUNDING_SET);
    }

    public long getControlPID() {
        return properties.getLong(Property.CONTROL_PID);
    }

    public boolean isDelegate() {
        return properties.getBoolean(Property.DELEGATE);
    }

    public Vector<String> getDelegateControllers() {
        return properties.getVector(Property.DELEGATE_CONTROLLERS);
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

    public int getExecMainCode() {
        return properties.getInteger(Property.EXEC_MAIN_CODE);
    }

    public long getExecMainExitTimestamp() {
        return properties.getLong(Property.EXEC_MAIN_EXIT_TIMESTAMP);
    }

    public long getExecMainExitTimestampMonotonic() {
        return properties.getLong(Property.EXEC_MAIN_EXIT_TIMESTAMP_MONOTONIC);
    }

    public long getExecMainPID() {
        return properties.getLong(Property.EXEC_MAIN_PID);
    }

    public long getExecMainStartTimestamp() {
        return properties.getLong(Property.EXEC_MAIN_START_TIMESTAMP);
    }

    public long getExecMainStartTimestampMonotonic() {
        return properties.getLong(Property.EXEC_MAIN_START_TIMESTAMP_MONOTONIC);
    }

    public int getExecMainStatus() {
        return properties.getInteger(Property.EXEC_MAIN_STATUS);
    }

    public List<ExecutionInfo> getExecReload() {
        return ExecutionInfo.list(properties.getVector(Property.EXEC_RELOAD));
    }

    public List<ExecutionInfo> getExecStart() {
        return ExecutionInfo.list(properties.getVector(Property.EXEC_START));
    }

    public List<ExecutionInfo> getExecStartPost() {
        return ExecutionInfo.list(properties.getVector(Property.EXEC_START_POST));
    }

    public List<ExecutionInfo> getExecStartPre() {
        return ExecutionInfo.list(properties.getVector(Property.EXEC_START_PRE));
    }

    public List<ExecutionInfo> getExecStop() {
        return ExecutionInfo.list(properties.getVector(Property.EXEC_STOP));
    }

    public List<ExecutionInfo> getExecStopPost() {
        return ExecutionInfo.list(properties.getVector(Property.EXEC_STOP_POST));
    }

    public long getFileDescriptorStoreMax() {
        return properties.getLong(Property.FILE_DESCRIPTOR_STORE_MAX);
    }

    public boolean isGuessMainPID() {
        return properties.getBoolean(Property.GUESS_MAIN_PID);
    }

    public int getIOSchedulingClass() {
        return properties.getInteger(Property.IO_SCHEDULING_CLASS);
    }

    public int getIOSchedulingPriority() {
        return properties.getInteger(Property.IO_SCHEDULING_PRIORITY);
    }

    public boolean isIgnoreSIGPIPE() {
        return properties.getBoolean(Property.IGNORE_SIGPIPE);
    }

    public Vector<String> getInaccessiblePaths() {
        return properties.getVector(Property.INACCESSIBLE_PATHS);
    }

    public String getKillMode() {
        return properties.getString(Property.KILL_MODE);
    }

    public int getKillSignal() {
        return properties.getInteger(Property.KILL_SIGNAL);
    }

    public int getMainPID() {
        return properties.getInteger(Property.MAIN_PID);
    }

    public BigInteger getMountFlags() {
        return properties.getBigInteger(Property.MOUNT_FLAGS);
    }

    public long getNFileDescriptorStore() {
        return properties.getLong(Property.NFILE_DESCRIPTOR_STORE);
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

    public String getNotifyAccess() {
        return properties.getString(Property.NOTIFY_ACCESS);
    }

    public long getNRestarts() {
        return properties.getLong(Property.N_RESTARTS);
    }

    public int getOOMScoreAdjust() {
        return properties.getInteger(Property.OOM_SCORE_ADJUST);
    }

    public String getPAMName() {
        return properties.getString(Property.PAM_NAME);
    }

    public String getPIDFile() {
        return properties.getString(Property.PID_FILE);
    }

    public boolean isPermissionsStartOnly() {
        return properties.getBoolean(Property.PERMISSIONS_START_ONLY);
    }

    public Vector<String> getReadOnlyPaths() {
        return properties.getVector(Property.READ_ONLY_PATHS);
    }

    public Vector<String> getReadWritePaths() {
        return properties.getVector(Property.READ_WRITE_PATHS);
    }

    public boolean isRemainAfterExit() {
        return properties.getBoolean(Property.REMAIN_AFTER_EXIT);
    }

    public String getRestart() {
        return properties.getString(Property.RESTART);
    }

    public BigInteger getRestartUSec() {
        return properties.getBigInteger(Property.RESTART_USEC);
    }

    public String getResult() {
        return properties.getString(Property.RESULT);
    }

    public String getRootDirectory() {
        return properties.getString(Property.ROOT_DIRECTORY);
    }

    public boolean isRootDirectoryStartOnly() {
        return properties.getBoolean(Property.ROOT_DIRECTORY_START_ONLY);
    }

    public BigInteger getRuntimeMaxUSec() {
        return properties.getBigInteger(Property.RUNTIME_MAX_USEC);
    }

    public SELinuxContext getSELinuxContext() {
        Object[] array = (Object[]) properties.getVariant(Property.SELINUX_CONTEXT).getValue();

        return new SELinuxContext(array);
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

    public SmackProcessLabel getSmackProcessLabel() {
        Object[] array = (Object[]) properties.getVariant(Property.SMACK_PROCESS_LABEL).getValue();

        return new SmackProcessLabel(array);
    }

    public int getStatusErrno() {
        return properties.getInteger(Property.STATUS_ERRNO);
    }

    public String getStatusText() {
        return properties.getString(Property.STATUS_TEXT);
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

    public BigInteger getTimeoutStartUSec() {
        return properties.getBigInteger(Property.TIMEOUT_START_USEC);
    }

    public BigInteger getTimeoutStopUSec() {
        return properties.getBigInteger(Property.TIMEOUT_STOP_USEC);
    }

    public BigInteger getTimerSlackNSec() {
        return properties.getBigInteger(Property.TIMER_SLACK_NSEC);
    }

    public String getType() {
        return properties.getString(Property.TYPE);
    }

    public long getUMask() {
        return properties.getLong(Property.UMASK);
    }

    public String getUSBFunctionDescriptors() {
        return properties.getString(Property.USB_FUNCTION_DESCRIPTORS);
    }

    public String getUSBFunctionStrings() {
        return properties.getString(Property.USB_FUNCTION_STRINGS);
    }

    public long getWatchdogTimestamp() {
        return properties.getLong(Property.WATCHDOG_TIMESTAMP);
    }

    public long getWatchdogTimestampMonotonic() {
        return properties.getLong(Property.WATCHDOG_TIMESTAMP_MONOTONIC);
    }

    public BigInteger getWatchdogUSec() {
        return properties.getBigInteger(Property.WATCHDOG_USEC);
    }

    public String getWorkingDirectory() {
        return properties.getString(Property.WORKING_DIRECTORY);
    }

}
