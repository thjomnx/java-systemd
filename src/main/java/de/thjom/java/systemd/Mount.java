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
import de.thjom.java.systemd.features.IoAccounting;
import de.thjom.java.systemd.features.IpAccounting;
import de.thjom.java.systemd.features.MemoryAccounting;
import de.thjom.java.systemd.features.TasksAccounting;
import de.thjom.java.systemd.features.Ulimit;
import de.thjom.java.systemd.interfaces.MountInterface;
import de.thjom.java.systemd.types.AddressFamilyRestriction;
import de.thjom.java.systemd.types.AppArmorProfile;
import de.thjom.java.systemd.types.DeviceAllowControl;
import de.thjom.java.systemd.types.EnvironmentFile;
import de.thjom.java.systemd.types.ExecutionInfo;
import de.thjom.java.systemd.types.IOBandwidth;
import de.thjom.java.systemd.types.IODeviceWeight;
import de.thjom.java.systemd.types.SELinuxContext;
import de.thjom.java.systemd.types.SmackProcessLabel;
import de.thjom.java.systemd.types.SystemCallFilter;
import de.thjom.java.systemd.types.UnitProcessType;

public class Mount extends Unit implements DynamicUserAccounting, IoAccounting, IpAccounting, MemoryAccounting, TasksAccounting, Ulimit {

    public static final String SERVICE_NAME = Systemd.SERVICE_NAME + ".Mount";
    public static final String UNIT_SUFFIX = ".mount";

    public static class Property extends InterfaceAdapter.AdapterProperty {

        public static final String AMBIENT_CAPABILITIES = "AmbientCapabilities";
        public static final String APP_ARMOR_PROFILE = "AppArmorProfile";
        public static final String BLOCK_IO_ACCOUNTING = "BlockIOAccounting";
        public static final String BLOCK_IO_DEVICE_WEIGHT = "BlockIODeviceWeight";
        public static final String BLOCK_IO_READ_BANDWIDTH = "BlockIOReadBandwidth";
        public static final String BLOCK_IO_WEIGHT = "BlockIOWeight";
        public static final String BLOCK_IO_WRITE_BANDWIDTH = "BlockIOWriteBandwidth";
        public static final String CPU_ACCOUNTING = "CPUAccounting";
        public static final String CPU_AFFINITY = "CPUAffinity";
        public static final String CPU_QUOTA_PER_SEC_USEC = "CPUQuotaPerSecUSec";
        public static final String CPU_SCHEDULING_POLICY = "CPUSchedulingPolicy";
        public static final String CPU_SCHEDULING_PRIORITY = "CPUSchedulingPriority";
        public static final String CPU_SCHEDULING_RESET_ON_FORK = "CPUSchedulingResetOnFork";
        public static final String CPU_SHARES = "CPUShares";
        public static final String CPU_USAGE_NSEC = "CPUUsageNSec";
        public static final String CAPABILITY_BOUNDING_SET = "CapabilityBoundingSet";
        public static final String CONTROL_GROUP = "ControlGroup";
        public static final String CONTROL_PID = "ControlPID";
        public static final String DELEGATE = "Delegate";
        public static final String DELEGATE_CONTROLLERS = "DelegateControllers";
        public static final String DEVICE_ALLOW = "DeviceAllow";
        public static final String DEVICE_POLICY = "DevicePolicy";
        public static final String DIRECTORY_MODE = "DirectoryMode";
        public static final String ENVIRONMENT = "Environment";
        public static final String ENVIRONMENT_FILES = "EnvironmentFiles";
        public static final String EXEC_MOUNT = "ExecMount";
        public static final String EXEC_REMOUNT = "ExecRemount";
        public static final String EXEC_UNMOUNT = "ExecUnmount";
        public static final String FORCE_UNMOUNT = "ForceUnmount";
        public static final String GROUP = "Group";
        public static final String IO_SCHEDULING_CLASS = "IOSchedulingClass";
        public static final String IO_SCHEDULING_PRIORITY = "IOSchedulingPriority";
        public static final String IGNORE_SIGPIPE = "IgnoreSIGPIPE";
        public static final String INACCESSIBLE_PATHS = "InaccessiblePaths";
        public static final String KILL_MODE = "KillMode";
        public static final String KILL_SIGNAL = "KillSignal";
        public static final String LAZY_UNMOUNT = "LazyUnmount";
        public static final String MOUNT_FLAGS = "MountFlags";
        public static final String NICE = "Nice";
        public static final String NO_NEW_PRIVILEGES = "NoNewPrivileges";
        public static final String NON_BLOCKING = "NonBlocking";
        public static final String OOM_SCORE_ADJUST = "OOMScoreAdjust";
        public static final String OPTIONS = "Options";
        public static final String PAMNAME = "PAMName";
        public static final String PASS_ENVIRONMENT = "PassEnvironment";
        public static final String PERSONALITY = "Personality";
        public static final String PRIVATE_DEVICES = "PrivateDevices";
        public static final String READ_ONLY_PATHS = "ReadOnlyPaths";
        public static final String READ_WRITE_PATHS = "ReadWritePaths";
        public static final String RESTRICT_ADDRESS_FAMILIES = "RestrictAddressFamilies";
        public static final String RESULT = "Result";
        public static final String ROOT_DIRECTORY = "RootDirectory";
        public static final String SELINUX_CONTEXT = "SELinuxContext";
        public static final String SAME_PROCESS_GROUP = "SameProcessGroup";
        public static final String SECURE_BITS = "SecureBits";
        public static final String SEND_SIGHUP = "SendSIGHUP";
        public static final String SEND_SIGKILL = "SendSIGKILL";
        public static final String SLICE = "Slice";
        public static final String SLOPPY_OPTIONS = "SloppyOptions";
        public static final String SMACK_PROCESS_LABEL = "SmackProcessLabel";
        public static final String STANDARD_ERROR = "StandardError";
        public static final String STANDARD_INPUT = "StandardInput";
        public static final String STANDARD_OUTPUT = "StandardOutput";
        public static final String STARTUP_BLOCK_IO_WEIGHT = "StartupBlockIOWeight";
        public static final String STARTUP_CPU_SHARES = "StartupCPUShares";
        public static final String SUPPLEMENTARY_GROUPS = "SupplementaryGroups";
        public static final String SYSLOG_FACILITY = "SyslogFacility";
        public static final String SYSLOG_IDENTIFIER = "SyslogIdentifier";
        public static final String SYSLOG_LEVEL = "SyslogLevel";
        public static final String SYSLOG_LEVEL_PREFIX = "SyslogLevelPrefix";
        public static final String SYSLOG_PRIORITY = "SyslogPriority";
        public static final String SYSTEM_CALL_ARCHITECTURES = "SystemCallArchitectures";
        public static final String SYSTEM_CALL_ERROR_NUMBER = "SystemCallErrorNumber";
        public static final String SYSTEM_CALL_FILTER = "SystemCallFilter";
        public static final String TTY_PATH = "TTYPath";
        public static final String TTY_RESET = "TTYReset";
        public static final String TTY_V_HANGUP = "TTYVHangup";
        public static final String TTY_VT_DISALLOCATE = "TTYVTDisallocate";
        public static final String TASKS_ACCOUNTING = "TasksAccounting";
        public static final String TASKS_CURRENT = "TasksCurrent";
        public static final String TASKS_MAX = "TasksMax";
        public static final String TIMEOUT_USEC = "TimeoutUSec";
        public static final String TIMER_SLACK_NSEC = "TimerSlackNSec";
        public static final String TYPE = "Type";
        public static final String UMASK = "UMask";
        public static final String USER = "User";
        public static final String UTMP_IDENTIFIER = "UtmpIdentifier";
        public static final String UTMP_MODE = "UtmpMode";
        public static final String WHAT = "What";
        public static final String WHERE = "Where";
        public static final String WORKING_DIRECTORY = "WorkingDirectory";

        private Property() {
            super();
        }

        public static final String[] getAllNames() {
            return getAllNames(
                    Property.class,
                    DynamicUserAccounting.Property.class,
                    IoAccounting.Property.class,
                    IpAccounting.Property.class,
                    MemoryAccounting.Property.class,
                    TasksAccounting.Property.class,
                    Ulimit.Property.class
            );
        }

    }

    private Mount(final Manager manager, final MountInterface iface, final String name) throws DBusException {
        super(manager, iface, name);

        this.properties = Properties.create(dbus, iface.getObjectPath(), SERVICE_NAME);
    }

    static Mount create(final Manager manager, String name) throws DBusException {
        name = Unit.normalizeName(name, UNIT_SUFFIX);

        String objectPath = Unit.OBJECT_PATH + Systemd.escapePath(name);
        MountInterface iface = manager.dbus.getRemoteObject(Systemd.SERVICE_NAME, objectPath, MountInterface.class);

        return new Mount(manager, iface, name);
    }

    @Override
    public MountInterface getInterface() {
        return (MountInterface) super.getInterface();
    }

    public List<UnitProcessType> getProcesses() {
        return getInterface().getProcesses();
    }

    public BigInteger getAmbientCapabilities() {
        return properties.getBigInteger(Property.AMBIENT_CAPABILITIES);
    }

    public AppArmorProfile getAppArmorProfile() {
        Object[] array = (Object[]) properties.getVariant(Property.APP_ARMOR_PROFILE).getValue();

        return new AppArmorProfile(array);
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

    public byte[] getCPUAffinity() {
        return (byte[]) properties.getVariant(Property.CPU_AFFINITY).getValue();
    }

    public BigInteger getCPUQuotaPerSecUSec() {
        return properties.getBigInteger(Property.CPU_QUOTA_PER_SEC_USEC);
    }

    public int getCPUSchedulingPolicy() {
        return properties.getInteger(Property.CPU_SCHEDULING_POLICY);
    }

    public int getCPUSchedulingPriority() {
        return properties.getInteger(Property.CPU_SCHEDULING_PRIORITY);
    }

    public boolean isCPUSchedulingResetOnFork() {
        return properties.getBoolean(Property.CPU_SCHEDULING_RESET_ON_FORK);
    }

    public BigInteger getCPUShares() {
        return properties.getBigInteger(Property.CPU_SHARES);
    }

    public BigInteger getCPUUsageNSec() {
        return properties.getBigInteger(Property.CPU_USAGE_NSEC);
    }

    public BigInteger getCapabilityBoundingSet() {
        return properties.getBigInteger(Property.CAPABILITY_BOUNDING_SET);
    }

    public String getControlGroup() {
        return properties.getString(Property.CONTROL_GROUP);
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

    public long getDirectoryMode() {
        return properties.getLong(Property.DIRECTORY_MODE);
    }

    public Vector<String> getEnvironment() {
        return properties.getVector(Property.ENVIRONMENT);
    }

    public List<EnvironmentFile> getEnvironmentFiles() {
        return EnvironmentFile.list(properties.getVector(Property.ENVIRONMENT_FILES));
    }

    public List<ExecutionInfo> getExecMount() {
        return ExecutionInfo.list(properties.getVector(Property.EXEC_MOUNT));
    }

    public List<ExecutionInfo> getExecRemount() {
        return ExecutionInfo.list(properties.getVector(Property.EXEC_REMOUNT));
    }

    public List<ExecutionInfo> getExecUnmount() {
        return ExecutionInfo.list(properties.getVector(Property.EXEC_UNMOUNT));
    }

    public boolean isForceUnmount() {
        return properties.getBoolean(Property.FORCE_UNMOUNT);
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

    public boolean isLazyUnmount() {
        return properties.getBoolean(Property.LAZY_UNMOUNT);
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
        return properties.getString(Property.PAMNAME);
    }

    public Vector<String> getPassEnvironment() {
        return properties.getVector(Property.PASS_ENVIRONMENT);
    }

    public String getPersonality() {
        return properties.getString(Property.PERSONALITY);
    }

    public boolean isPrivateDevices() {
        return properties.getBoolean(Property.PRIVATE_DEVICES);
    }

    public Vector<String> getReadOnlyPaths() {
        return properties.getVector(Property.READ_ONLY_PATHS);
    }

    public Vector<String> getReadWritePaths() {
        return properties.getVector(Property.READ_WRITE_PATHS);
    }

    public AddressFamilyRestriction getRestrictAddressFamilies() {
        Object[] array = (Object[]) properties.getVariant(Property.RESTRICT_ADDRESS_FAMILIES).getValue();

        return new AddressFamilyRestriction(array);
    }

    public String getResult() {
        return properties.getString(Property.RESULT);
    }

    public String getRootDirectory() {
        return properties.getString(Property.ROOT_DIRECTORY);
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

    public boolean isSloppyOptions() {
        return properties.getBoolean(Property.SLOPPY_OPTIONS);
    }

    public SmackProcessLabel getSmackProcessLabel() {
        Object[] array = (Object[]) properties.getVariant(Property.SMACK_PROCESS_LABEL).getValue();

        return new SmackProcessLabel(array);
    }

    public String getStandardError() {
        return properties.getString(Property.STANDARD_ERROR);
    }

    public String getStandardInput() {
        return properties.getString(Property.STANDARD_INPUT);
    }

    public String getStandardOutput() {
        return properties.getString(Property.STANDARD_OUTPUT);
    }

    public BigInteger getStartupBlockIOWeight() {
        return properties.getBigInteger(Property.STARTUP_BLOCK_IO_WEIGHT);
    }

    public BigInteger getStartupCPUShares() {
        return properties.getBigInteger(Property.STARTUP_CPU_SHARES);
    }

    public Vector<String> getSupplementaryGroups() {
        return properties.getVector(Property.SUPPLEMENTARY_GROUPS);
    }

    public int getSyslogFacility() {
        return properties.getInteger(Property.SYSLOG_FACILITY);
    }

    public String getSyslogIdentifier() {
        return properties.getString(Property.SYSLOG_IDENTIFIER);
    }

    public int getSyslogLevel() {
        return properties.getInteger(Property.SYSLOG_LEVEL);
    }

    public boolean isSyslogLevelPrefix() {
        return properties.getBoolean(Property.SYSLOG_LEVEL_PREFIX);
    }

    public int getSyslogPriority() {
        return properties.getInteger(Property.SYSLOG_PRIORITY);
    }

    public Vector<String> getSystemCallArchitectures() {
        return properties.getVector(Property.SYSTEM_CALL_ARCHITECTURES);
    }

    public int getSystemCallErrorNumber() {
        return properties.getInteger(Property.SYSTEM_CALL_ERROR_NUMBER);
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

    public BigInteger getTimerSlackNSec() {
        return properties.getBigInteger(Property.TIMER_SLACK_NSEC);
    }

    public String getType() {
        return properties.getString(Property.TYPE);
    }

    public long getUMask() {
        return properties.getLong(Property.UMASK);
    }

    public String getUtmpIdentifier() {
        return properties.getString(Property.UTMP_IDENTIFIER);
    }

    public String getUtmpMode() {
        return properties.getString(Property.UTMP_MODE);
    }

    public String getWhat() {
        return properties.getString(Property.WHAT);
    }

    public String getWhere() {
        return properties.getString(Property.WHERE);
    }

    public String getWorkingDirectory() {
        return properties.getString(Property.WORKING_DIRECTORY);
    }

}
