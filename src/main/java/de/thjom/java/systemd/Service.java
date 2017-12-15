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

import de.thjom.java.systemd.interfaces.ServiceInterface;
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

public class Service extends Unit implements IpAccountable, MemoryAccountable {

    public static final String SERVICE_NAME = Systemd.SERVICE_NAME + ".Service";
    public static final String UNIT_SUFFIX = ".service";

    public static class Property extends InterfaceAdapter.AdapterProperty {

        public static final String APP_ARMOR_PROFILE = "AppArmorProfile";
        public static final String BLOCK_IO_ACCOUNTING = "BlockIOAccounting";
        public static final String BLOCK_IO_DEVICE_WEIGHT = "BlockIODeviceWeight";
        public static final String BLOCK_IO_READ_BANDWIDTH = "BlockIOReadBandwidth";
        public static final String BLOCK_IO_WEIGHT = "BlockIOWeight";
        public static final String BLOCK_IO_WRITE_BANDWIDTH = "BlockIOWriteBandwidth";
        public static final String BUS_NAME = "BusName";
        public static final String CPU_ACCOUNTING = "CPUAccounting";
        public static final String CPU_AFFINITY = "CPUAffinity";
        public static final String CPU_QUOTA_PER_SEC_USEC = "CPUQuotaPerSecUSec";
        public static final String CPU_SCHEDULING_POLICY = "CPUSchedulingPolicy";
        public static final String CPU_SCHEDULING_PRIORITY = "CPUSchedulingPriority";
        public static final String CPU_SCHEDULING_RESET_ON_FORK = "CPUSchedulingResetOnFork";
        public static final String CPU_SHARES = "CPUShares";
        public static final String CPU_USAGE_NSEC = "CPUUsageNSec";
        public static final String CAPABILITIES = "Capabilities";
        public static final String CAPABILITY_BOUNDING_SET = "CapabilityBoundingSet";
        public static final String CONTROL_GROUP = "ControlGroup";
        public static final String CONTROL_PID = "ControlPID";
        public static final String DELEGATE = "Delegate";
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
        public static final String FAILURE_ACTION = "FailureAction";
        public static final String FILE_DESCRIPTOR_STORE_MAX = "FileDescriptorStoreMax";
        public static final String GROUP = "Group";
        public static final String GUESS_MAIN_PID = "GuessMainPID";
        public static final String IO_SCHEDULING = "IOScheduling";
        public static final String IO_SCHEDULING_CLASS = "IOSchedulingClass";
        public static final String IO_SCHEDULING_PRIORITY = "IOSchedulingPriority";
        public static final String IGNORE_SIGPIPE = "IgnoreSIGPIPE";
        public static final String INACCESSIBLE_DIRECTORIES = "InaccessibleDirectories";
        public static final String KILL_MODE = "KillMode";
        public static final String KILL_SIGNAL = "KillSignal";
        public static final String LIMIT_AS = "LimitAS";
        public static final String LIMIT_CORE = "LimitCORE";
        public static final String LIMIT_CPU = "LimitCPU";
        public static final String LIMIT_DATA = "LimitDATA";
        public static final String LIMIT_FSIZE = "LimitFSIZE";
        public static final String LIMIT_LOCKS = "LimitLOCKS";
        public static final String LIMIT_MEMLOCK = "LimitMEMLOCK";
        public static final String LIMIT_MSGQUEUE = "LimitMSGQUEUE";
        public static final String LIMIT_NICE = "LimitNICE";
        public static final String LIMIT_NOFILE = "LimitNOFILE";
        public static final String LIMIT_NPROC = "LimitNPROC";
        public static final String LIMIT_RSS = "LimitRSS";
        public static final String LIMIT_RTPRIO = "LimitRTPRIO";
        public static final String LIMIT_RTTIME = "LimitRTTIME";
        public static final String LIMIT_SIGPENDING = "LimitSIGPENDING";
        public static final String LIMIT_STACK = "LimitSTACK";
        public static final String MAIN_PID = "MainPID";
        public static final String MEMORY_ACCOUNTING = "MemoryAccounting";
        public static final String MEMORY_CURRENT = "MemoryCurrent";
        public static final String MEMORY_LIMIT = "MemoryLimit";
        public static final String MOUNT_FLAGS = "MountFlags";
        public static final String NFILE_DESCRIPTOR_STORE = "NFileDescriptorStore";
        public static final String NICE = "Nice";
        public static final String NO_NEW_PRIVILEGES = "NoNewPrivileges";
        public static final String NON_BLOCKING = "NonBlocking";
        public static final String NOTIFY_ACCESS = "NotifyAccess";
        public static final String OOM_SCORE_ADJUST = "OOMScoreAdjust";
        public static final String PAM_NAME = "PAMName";
        public static final String PID_FILE = "PIDFile";
        public static final String PASS_ENVIRONMENT = "PassEnvironment";
        public static final String PERMISSIONS_START_ONLY = "PermissionsStartOnly";
        public static final String PERSONALITY = "Personality";
        public static final String PRIVATE_DEVICES = "PrivateDevices";
        public static final String PRIVATE_NETWORK = "PrivateNetwork";
        public static final String PRIVATE_TMP = "PrivateTmp";
        public static final String PROTECT_HOME = "ProtectHome";
        public static final String PROTECT_SYSTEM = "ProtectSystem";
        public static final String READ_ONLY_DIRECTORIES = "ReadOnlyDirectories";
        public static final String READ_WRITE_DIRECTORIES = "ReadWriteDirectories";
        public static final String REBOOT_ARGUMENT = "RebootArgument";
        public static final String REMAIN_AFTER_EXIT = "RemainAfterExit";
        public static final String RESTART = "Restart";
        public static final String RESTART_USEC = "RestartUSec";
        public static final String RESTRICT_ADDRESS_FAMILIES = "RestrictAddressFamilies";
        public static final String RESULT = "Result";
        public static final String ROOT_DIRECTORY = "RootDirectory";
        public static final String ROOT_DIRECTORY_START_ONLY = "RootDirectoryStartOnly";
        public static final String RUNTIME_DIRECTORY = "RuntimeDirectory";
        public static final String RUNTIME_DIRECTORY_MODE = "RuntimeDirectoryMode";
        public static final String RUNTIME_MAX_USEC = "RuntimeMaxUSec";
        public static final String SELINUX_CONTEXT = "SELinuxContext";
        public static final String SAME_PROCESS_GROUP = "SameProcessGroup";
        public static final String SECURE_BITS = "SecureBits";
        public static final String SEND_SIGHUP = "SendSIGHUP";
        public static final String SEND_SIGKILL = "SendSIGKILL";
        public static final String SLICE = "Slice";
        public static final String SMACK_PROCESS_LABEL = "SmackProcessLabel";
        public static final String STANDARD_ERROR = "StandardError";
        public static final String STANDARD_INPUT = "StandardInput";
        public static final String STANDARD_OUTPUT = "StandardOutput";
        public static final String START_LIMIT_ACTION = "StartLimitAction";
        public static final String START_LIMIT_BURST = "StartLimitBurst";
        public static final String START_LIMIT_INTERVAL = "StartLimitInterval";
        public static final String STARTUP_BLOCK_IO_WEIGHT = "StartupBlockIOWeight";
        public static final String STARTUP_CPU_SHARES = "StartupCPUShares";
        public static final String STATUS_ERRNO = "StatusErrno";
        public static final String STATUS_TEXT = "StatusText";
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
        public static final String TIMEOUT_START_USEC = "TimeoutStartUSec";
        public static final String TIMEOUT_STOP_USEC = "TimeoutStopUSec";
        public static final String TIMER_SLACK_NSEC = "TimerSlackNSec";
        public static final String TYPE = "Type";
        public static final String UMASK = "UMask";
        public static final String USB_FUNCTION_DESCRIPTORS = "USBFunctionDescriptors";
        public static final String USB_FUNCTION_STRINGS = "USBFunctionStrings";
        public static final String USER = "User";
        public static final String UTMP_IDENTIFIER = "UtmpIdentifier";
        public static final String UTMP_MODE = "UtmpMode";
        public static final String WATCHDOG_TIMESTAMP = "WatchdogTimestamp";
        public static final String WATCHDOG_TIMESTAMP_MONOTONIC = "WatchdogTimestampMonotonic";
        public static final String WATCHDOG_USEC = "WatchdogUSec";
        public static final String WORKING_DIRECTORY = "WorkingDirectory";

        private Property() {
            super();
        }

        public static final String[] getAllNames() {
            return getAllNames(Property.class, IpAccountable.Property.class, MemoryAccountable.Property.class);
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

    public String getBusName() {
        return properties.getString(Property.BUS_NAME);
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

    public String getCapabilities() {
        return properties.getString(Property.CAPABILITIES);
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

    public String getFailureAction() {
        return properties.getString(Property.FAILURE_ACTION);
    }

    public long getFileDescriptorStoreMax() {
        return properties.getLong(Property.FILE_DESCRIPTOR_STORE_MAX);
    }

    public String getGroup() {
        return properties.getString(Property.GROUP);
    }

    public boolean isGuessMainPID() {
        return properties.getBoolean(Property.GUESS_MAIN_PID);
    }

    public int getIOScheduling() {
        return properties.getInteger(Property.IO_SCHEDULING);
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

    public Vector<String> getInaccessibleDirectories() {
        return properties.getVector(Property.INACCESSIBLE_DIRECTORIES);
    }

    public String getKillMode() {
        return properties.getString(Property.KILL_MODE);
    }

    public int getKillSignal() {
        return properties.getInteger(Property.KILL_SIGNAL);
    }

    public BigInteger getLimitAS() {
        return properties.getBigInteger(Property.LIMIT_AS);
    }

    public BigInteger getLimitCORE() {
        return properties.getBigInteger(Property.LIMIT_CORE);
    }

    public BigInteger getLimitCPU() {
        return properties.getBigInteger(Property.LIMIT_CPU);
    }

    public BigInteger getLimitDATA() {
        return properties.getBigInteger(Property.LIMIT_DATA);
    }

    public BigInteger getLimitFSIZE() {
        return properties.getBigInteger(Property.LIMIT_FSIZE);
    }

    public BigInteger getLimitLOCKS() {
        return properties.getBigInteger(Property.LIMIT_LOCKS);
    }

    public BigInteger getLimitMEMLOCK() {
        return properties.getBigInteger(Property.LIMIT_MEMLOCK);
    }

    public BigInteger getLimitMSGQUEUE() {
        return properties.getBigInteger(Property.LIMIT_MSGQUEUE);
    }

    public BigInteger getLimitNICE() {
        return properties.getBigInteger(Property.LIMIT_NICE);
    }

    public BigInteger getLimitNOFILE() {
        return properties.getBigInteger(Property.LIMIT_NOFILE);
    }

    public BigInteger getLimitNPROC() {
        return properties.getBigInteger(Property.LIMIT_NPROC);
    }

    public BigInteger getLimitRSS() {
        return properties.getBigInteger(Property.LIMIT_RSS);
    }

    public BigInteger getLimitRTPRIO() {
        return properties.getBigInteger(Property.LIMIT_RTPRIO);
    }

    public BigInteger getLimitRTTIME() {
        return properties.getBigInteger(Property.LIMIT_RTTIME);
    }

    public BigInteger getLimitSIGPENDING() {
        return properties.getBigInteger(Property.LIMIT_SIGPENDING);
    }

    public BigInteger getLimitSTACK() {
        return properties.getBigInteger(Property.LIMIT_STACK);
    }

    public int getMainPID() {
        return properties.getInteger(Property.MAIN_PID);
    }

    public boolean isMemoryAccounting() {
        return properties.getBoolean(Property.MEMORY_ACCOUNTING);
    }

    public BigInteger getMemoryCurrent() {
        return properties.getBigInteger(Property.MEMORY_CURRENT);
    }

    public BigInteger getMemoryLimit() {
        return properties.getBigInteger(Property.MEMORY_LIMIT);
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

    public int getOOMScoreAdjust() {
        return properties.getInteger(Property.OOM_SCORE_ADJUST);
    }

    public String getPAMName() {
        return properties.getString(Property.PAM_NAME);
    }

    public String getPIDFile() {
        return properties.getString(Property.PID_FILE);
    }

    public Vector<String> getPassEnvironment() {
        return properties.getVector(Property.PASS_ENVIRONMENT);
    }

    public boolean isPermissionsStartOnly() {
        return properties.getBoolean(Property.PERMISSIONS_START_ONLY);
    }

    public String getPersonality() {
        return properties.getString(Property.PERSONALITY);
    }

    public boolean isPrivateDevices() {
        return properties.getBoolean(Property.PRIVATE_DEVICES);
    }

    public boolean isPrivateNetwork() {
        return properties.getBoolean(Property.PRIVATE_NETWORK);
    }

    public boolean isPrivateTmp() {
        return properties.getBoolean(Property.PRIVATE_TMP);
    }

    public String getProtectHome() {
        return properties.getString(Property.PROTECT_HOME);
    }

    public String getProtectSystem() {
        return properties.getString(Property.PROTECT_SYSTEM);
    }

    public Vector<String> getReadOnlyDirectories() {
        return properties.getVector(Property.READ_ONLY_DIRECTORIES);
    }

    public Vector<String> getReadWriteDirectories() {
        return properties.getVector(Property.READ_WRITE_DIRECTORIES);
    }

    public String getRebootArgument() {
        return properties.getString(Property.REBOOT_ARGUMENT);
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

    public boolean isRootDirectoryStartOnly() {
        return properties.getBoolean(Property.ROOT_DIRECTORY_START_ONLY);
    }

    public Vector<String> getRuntimeDirectory() {
        return properties.getVector(Property.RUNTIME_DIRECTORY);
    }

    public long getRuntimeDirectoryMode() {
        return properties.getLong(Property.RUNTIME_DIRECTORY_MODE);
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

    public String getStandardError() {
        return properties.getString(Property.STANDARD_ERROR);
    }

    public String getStandardInput() {
        return properties.getString(Property.STANDARD_INPUT);
    }

    public String getStandardOutput() {
        return properties.getString(Property.STANDARD_OUTPUT);
    }

    public String getStartLimitAction() {
        return properties.getString(Property.START_LIMIT_ACTION);
    }

    public long getStartLimitBurst() {
        return properties.getLong(Property.START_LIMIT_BURST);
    }

    public BigInteger getStartLimitInterval() {
        return properties.getBigInteger(Property.START_LIMIT_INTERVAL);
    }

    public BigInteger getStartupBlockIOWeight() {
        return properties.getBigInteger(Property.STARTUP_BLOCK_IO_WEIGHT);
    }

    public BigInteger getStartupCPUShares() {
        return properties.getBigInteger(Property.STARTUP_CPU_SHARES);
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

    public boolean isTasksAccounting() {
        return properties.getBoolean(Property.TASKS_ACCOUNTING);
    }

    public BigInteger getTasksCurrent() {
        return properties.getBigInteger(Property.TASKS_CURRENT);
    }

    public BigInteger getTasksMax() {
        return properties.getBigInteger(Property.TASKS_MAX);
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

    public String getUser() {
        return properties.getString(Property.USER);
    }

    public String getUtmpIdentifier() {
        return properties.getString(Property.UTMP_IDENTIFIER);
    }

    public String getUtmpMode() {
        return properties.getString(Property.UTMP_MODE);
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
