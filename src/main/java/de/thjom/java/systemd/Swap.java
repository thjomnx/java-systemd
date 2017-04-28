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

import de.thjom.java.systemd.interfaces.SwapInterface;
import de.thjom.java.systemd.types.DeviceAllowControl;
import de.thjom.java.systemd.types.EnvironmentFile;
import de.thjom.java.systemd.types.ExecutionInfo;
import de.thjom.java.systemd.types.IOBandwidth;
import de.thjom.java.systemd.types.IODeviceWeight;
import de.thjom.java.systemd.types.IOIops;
import de.thjom.java.systemd.types.SystemCallFilter;

public class Swap extends Unit {

    public static final String SERVICE_NAME = Systemd.SERVICE_NAME + ".Swap";
    public static final String UNIT_SUFFIX = ".swap";

    public static class Property extends InterfaceAdapter.Property {

        public static final String BLOCK_IO_ACCOUNTING = "BlockIOAccounting";
        public static final String BLOCK_IO_DEVICE_WEIGHT = "BlockIODeviceWeight";
        public static final String BLOCK_IO_READ_BANDWIDTH = "BlockIOReadBandwidth";
        public static final String BLOCK_IO_WEIGHT = "BlockIOWeight";
        public static final String BLOCK_IO_WRITE_BANDWIDTH = "BlockIOWriteBandwidth";
        public static final String CAPABILITIES = "Capabilities";
        public static final String CAPABILITY_BOUNDING_SET = "CapabilityBoundingSet";
        public static final String CONTROL_GROUP = "ControlGroup";
        public static final String CONTROL_PID = "ControlPID";
        public static final String CPU_ACCOUNTING = "CPUAccounting";
        public static final String CPU_AFFINITY = "CPUAffinity";
        public static final String CPU_SCHEDULING_POLICY = "CPUSchedulingPolicy";
        public static final String CPU_SCHEDULING_PRIORITY = "CPUSchedulingPriority";
        public static final String CPU_SCHEDULING_RESET_ON_FORK = "CPUSchedulingResetOnFork";
        public static final String CPU_SHARES = "CPUShares";
        public static final String CPU_WEIGHT = "CPUWeight";
        public static final String DEVICE_ALLOW = "DeviceAllow";
        public static final String DEVICE_POLICY = "DevicePolicy";
        public static final String DYNAMIC_USER = "DynamicUser";
        public static final String ENVIRONMENT = "Environment";
        public static final String ENVIRONMENT_FILES = "EnvironmentFiles";
        public static final String EXEC_ACTIVATE = "ExecActivate";
        public static final String EXEC_DEACTIVATE = "ExecDeactivate";
        public static final String GID = "GID";
        public static final String GROUP = "Group";
        public static final String IGNORE_SIGPIPE = "IgnoreSIGPIPE";
        public static final String INACCESSIBLE_DIRECTORIES = "InaccessibleDirectories";
        public static final String INACCESSIBLE_PATHS = "InaccessiblePaths";
        public static final String IO_ACCOUNTING = "IOAccounting";
        public static final String IO_DEVICE_WEIGHT = "IODeviceWeight";
        public static final String IO_READ_BANDWIDTH_MAX = "IOReadBandwidthMax";
        public static final String IO_READ_IOPS_MAX = "IOReadIOPSMax";
        public static final String IO_SCHEDULING = "IOScheduling";
        public static final String IO_WEIGHT = "IOWeight";
        public static final String IO_WRITE_BANDWIDTH_MAX = "IOWriteBandwidthMax";
        public static final String IO_WRITE_IOPS_MAX = "IOWriteIOPSMax";
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
        public static final String MEMORY_ACCOUNTING = "MemoryAccounting";
        public static final String MEMORY_DENY_WRITE_EXECUTE = "MemoryDenyWriteExecute";
        public static final String MEMORY_HIGH = "MemoryHigh";
        public static final String MEMORY_LIMIT = "MemoryLimit";
        public static final String MEMORY_LOW = "MemoryLow";
        public static final String MEMORY_MAX = "MemoryMax";
        public static final String MEMORY_SWAP_MAX = "MemorySwapMax";
        public static final String MOUNT_FLAGS = "MountFlags";
        public static final String NICE = "Nice";
        public static final String NON_BLOCKING = "NonBlocking";
        public static final String NO_NEW_PRIVILEGES = "NoNewPrivileges";
        public static final String OOM_SCORE_ADJUST = "OOMScoreAdjust";
        public static final String PAM_NAME = "PAMName";
        public static final String PRIORITY = "Priority";
        public static final String PRIVATE_NETWORK = "PrivateNetwork";
        public static final String PRIVATE_TMP = "PrivateTmp";
        public static final String PRIVATE_USERS = "PrivateUsers";
        public static final String PROTECT_CONTROL_GROUPS = "ProtectControlGroups";
        public static final String PROTECT_KERNEL_MODULES = "ProtectKernelModules";
        public static final String PROTECT_KERNEL_TUNABLES = "ProtectKernelTunables";
        public static final String READ_ONLY_DIRECTORIES = "ReadOnlyDirectories";
        public static final String READ_ONLY_PATHS = "ReadOnlyPaths";
        public static final String READ_WRITE_DIRECTORIES = "ReadWriteDirectories";
        public static final String READ_WRITE_PATHS = "ReadWritePaths";
        public static final String REMOVE_IPC = "RemoveIPC";
        public static final String RESTRICT_REALTIME = "RestrictRealtime";
        public static final String RESULT = "Result";
        public static final String ROOT_DIRECTORY = "RootDirectory";
        public static final String SAME_PROCESS_GROUP = "SameProcessGroup";
        public static final String SECURE_BITS = "SecureBits";
        public static final String SEND_SIGHUP = "SendSIGHUP";
        public static final String SEND_SIGKILL = "SendSIGKILL";
        public static final String SLICE = "Slice";
        public static final String STANDARD_ERROR = "StandardError";
        public static final String STANDARD_ERROR_FILE_DESCRIPTOR_NAME = "StandardErrorFileDescriptorName";
        public static final String STANDARD_INPUT = "StandardInput";
        public static final String STANDARD_INPUT_FILE_DESCRIPTOR_NAME = "StandardInputFileDescriptorName";
        public static final String STANDARD_OUTPUT = "StandardOutput";
        public static final String STANDARD_OUTPUT_FILE_DESCRIPTOR_NAME = "StandardOutputFileDescriptorName";
        public static final String STARTUP_CPU_WEIGHT = "StartupCPUWeight";
        public static final String STARTUP_IO_WEIGHT = "StartupIOWeight";
        public static final String SUPPLEMENTARY_GROUPS = "SupplementaryGroups";
        public static final String SYSLOG_IDENTIFIER = "SyslogIdentifier";
        public static final String SYSLOG_LEVEL_PREFIX = "SyslogLevelPrefix";
        public static final String SYSLOG_PRIORITY = "SyslogPriority";
        public static final String SYSTEM_CALL_FILTER = "SystemCallFilter";
        public static final String TCP_WRAP_NAME = "TCPWrapName";
        public static final String TIMEOUT_USEC = "TimeoutUSec";
        public static final String TIMER_SLACK_NS = "TimerSlackNS";
        public static final String TTY_PATH = "TTYPath";
        public static final String TTY_RESET = "TTYReset";
        public static final String TTY_V_HANGUP = "TTYVHangup";
        public static final String TTY_VT_DISALLOCATE = "TTYVTDisallocate";
        public static final String UID = "UID";
        public static final String UMASK = "UMask";
        public static final String USER = "User";
        public static final String UTMP_IDENTIFIER = "UtmpIdentifier";
        public static final String WHAT = "What";
        public static final String WORKING_DIRECTORY = "WorkingDirectory";

        private Property() {
            super();
        }

        public static final String[] getAllNames() {
            return getAllNames(Property.class);
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

    public BigInteger getCPUWeight() {
        return properties.getBigInteger(Property.CPU_WEIGHT);
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

    public List<DeviceAllowControl> getDeviceAllow() {
        return DeviceAllowControl.list(properties.getVector(Property.DEVICE_ALLOW));
    }

    public String getDevicePolicy() {
        return properties.getString(Property.DEVICE_POLICY);
    }

    public boolean isDynamicUser() {
        return properties.getBoolean(Property.DYNAMIC_USER);
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

    public int getGID() {
        return properties.getInteger(Property.GID);
    }

    public String getGroup() {
        return properties.getString(Property.GROUP);
    }

    public boolean isIgnoreSIGPIPE() {
        return properties.getBoolean(Property.IGNORE_SIGPIPE);
    }

    public Vector<String> getInaccessibleDirectories() {
        return properties.getVector(Property.INACCESSIBLE_DIRECTORIES);
    }

    public Vector<String> getInaccessiblePaths() {
        return properties.getVector(Property.INACCESSIBLE_PATHS);
    }

    public boolean isIOAccounting() {
        return properties.getBoolean(Property.IO_ACCOUNTING);
    }

    public List<IODeviceWeight> getIODeviceWeight() {
        return IODeviceWeight.list(properties.getVector(Property.IO_DEVICE_WEIGHT));
    }

    public List<IOBandwidth> getIOReadBandwidthMax() {
        return IOBandwidth.list(properties.getVector(Property.IO_READ_BANDWIDTH_MAX));
    }

    public List<IOIops> getIOReadIOPSMax() {
        return IOIops.list(properties.getVector(Property.IO_READ_IOPS_MAX));
    }

    public int getIOScheduling() {
        return properties.getInteger(Property.IO_SCHEDULING);
    }

    public BigInteger getIOWeight() {
        return properties.getBigInteger(Property.IO_WEIGHT);
    }

    public List<IOBandwidth> getIOWriteBandwidthMax() {
        return IOBandwidth.list(properties.getVector(Property.IO_WRITE_BANDWIDTH_MAX));
    }

    public List<IOIops> getIOWriteIOPSMax() {
        return IOIops.list(properties.getVector(Property.IO_WRITE_IOPS_MAX));
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

    public boolean isMemoryAccounting() {
        return properties.getBoolean(Property.MEMORY_ACCOUNTING);
    }

    public boolean isMemoryDenyWriteExecute() {
        return properties.getBoolean(Property.MEMORY_DENY_WRITE_EXECUTE);
    }

    public BigInteger getMemoryHigh() {
        return properties.getBigInteger(Property.MEMORY_HIGH);
    }

    public BigInteger getMemoryLimit() {
        return properties.getBigInteger(Property.MEMORY_LIMIT);
    }

    public BigInteger getMemoryLow() {
        return properties.getBigInteger(Property.MEMORY_LOW);
    }

    public BigInteger getMemoryMax() {
        return properties.getBigInteger(Property.MEMORY_MAX);
    }

    public BigInteger getMemorySwapMax() {
        return properties.getBigInteger(Property.MEMORY_SWAP_MAX);
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

    public String getPAMName() {
        return properties.getString(Property.PAM_NAME);
    }

    public int getPriority() {
        return properties.getInteger(Property.PRIORITY);
    }

    public boolean isPrivateNetwork() {
        return properties.getBoolean(Property.PRIVATE_NETWORK);
    }

    public boolean isPrivateTmp() {
        return properties.getBoolean(Property.PRIVATE_TMP);
    }

    public boolean isPrivateUsers() {
        return properties.getBoolean(Property.PRIVATE_USERS);
    }

    public boolean isProtectControlGroups() {
        return properties.getBoolean(Property.PROTECT_CONTROL_GROUPS);
    }

    public boolean isProtectKernelModules() {
        return properties.getBoolean(Property.PROTECT_KERNEL_MODULES);
    }

    public boolean isProtectKernelTunables() {
        return properties.getBoolean(Property.PROTECT_KERNEL_TUNABLES);
    }

    public Vector<String> getReadOnlyDirectories() {
        return properties.getVector(Property.READ_ONLY_DIRECTORIES);
    }

    public Vector<String> getReadOnlyPaths() {
        return properties.getVector(Property.READ_ONLY_PATHS);
    }

    public Vector<String> getReadWriteDirectories() {
        return properties.getVector(Property.READ_WRITE_DIRECTORIES);
    }

    public Vector<String> getReadWritePaths() {
        return properties.getVector(Property.READ_WRITE_PATHS);
    }

    public boolean isRemoveIPC() {
        return properties.getBoolean(Property.REMOVE_IPC);
    }

    public boolean isRestrictRealtime() {
        return properties.getBoolean(Property.RESTRICT_REALTIME);
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

    public String getStandardError() {
        return properties.getString(Property.STANDARD_ERROR);
    }

    public String getStandardErrorFileDescriptorName() {
        return properties.getString(Property.STANDARD_ERROR_FILE_DESCRIPTOR_NAME);
    }

    public String getStandardInput() {
        return properties.getString(Property.STANDARD_INPUT);
    }

    public String getStandardInputFileDescriptorName() {
        return properties.getString(Property.STANDARD_INPUT_FILE_DESCRIPTOR_NAME);
    }

    public String getStandardOutput() {
        return properties.getString(Property.STANDARD_OUTPUT);
    }

    public String getStandardOutputFileDescriptorName() {
        return properties.getString(Property.STANDARD_OUTPUT_FILE_DESCRIPTOR_NAME);
    }

    public BigInteger getStartupCPUWeight() {
        return properties.getBigInteger(Property.STARTUP_CPU_WEIGHT);
    }

    public BigInteger getStartupIOWeight() {
        return properties.getBigInteger(Property.STARTUP_IO_WEIGHT);
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

    public String getTCPWrapName() {
        return properties.getString(Property.TCP_WRAP_NAME);
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

    public long getTimerSlackNS() {
        return properties.getLong(Property.TIMER_SLACK_NS);
    }

    public int getUID() {
        return properties.getInteger(Property.UID);
    }

    public long getUMask() {
        return properties.getLong(Property.UMASK);
    }

    public String getUser() {
        return properties.getString(Property.USER);
    }

    public String getUtmpIdentifier() {
        return properties.getString(Property.UTMP_IDENTIFIER);
    }

    public String getWhat() {
        return properties.getString(Property.WHAT);
    }

    public String getWorkingDirectory() {
        return properties.getString(Property.WORKING_DIRECTORY);
    }

}
