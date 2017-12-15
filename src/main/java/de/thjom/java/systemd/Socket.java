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

import de.thjom.java.systemd.interfaces.SocketInterface;
import de.thjom.java.systemd.types.AddressFamilyRestriction;
import de.thjom.java.systemd.types.AppArmorProfile;
import de.thjom.java.systemd.types.DeviceAllowControl;
import de.thjom.java.systemd.types.EnvironmentFile;
import de.thjom.java.systemd.types.ExecutionInfo;
import de.thjom.java.systemd.types.IOBandwidth;
import de.thjom.java.systemd.types.IODeviceWeight;
import de.thjom.java.systemd.types.ListenInfo;
import de.thjom.java.systemd.types.SELinuxContext;
import de.thjom.java.systemd.types.SmackProcessLabel;
import de.thjom.java.systemd.types.SystemCallFilter;

public class Socket extends Unit implements Limitable, IoAccountable, IpAccountable, MemoryAccountable {

    public static final String SERVICE_NAME = Systemd.SERVICE_NAME + ".Socket";
    public static final String UNIT_SUFFIX = ".socket";

    public static class Property extends InterfaceAdapter.AdapterProperty {

        public static final String ACCEPT = "Accept";
        public static final String AMBIENT_CAPABILITIES = "AmbientCapabilities";
        public static final String APP_ARMOR_PROFILE = "AppArmorProfile";
        public static final String BACKLOG = "Backlog";
        public static final String BIND_IPV6_ONLY = "BindIPv6Only";
        public static final String BIND_TO_DEVICE = "BindToDevice";
        public static final String BLOCK_IO_ACCOUNTING = "BlockIOAccounting";
        public static final String BLOCK_IO_DEVICE_WEIGHT = "BlockIODeviceWeight";
        public static final String BLOCK_IO_READ_BANDWIDTH = "BlockIOReadBandwidth";
        public static final String BLOCK_IO_WEIGHT = "BlockIOWeight";
        public static final String BLOCK_IO_WRITE_BANDWIDTH = "BlockIOWriteBandwidth";
        public static final String BROADCAST = "Broadcast";
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
        public static final String CONTROL_PID = "ControlPID";
        public static final String DEFER_ACCEPT_USEC = "DeferAcceptUSec";
        public static final String DELEGATE = "Delegate";
        public static final String DEVICE_ALLOW = "DeviceAllow";
        public static final String DEVICE_POLICY = "DevicePolicy";
        public static final String DIRECTORY_MODE = "DirectoryMode";
        public static final String ENVIRONMENT = "Environment";
        public static final String ENVIRONMENT_FILES = "EnvironmentFiles";
        public static final String EXEC_START_POST = "ExecStartPost";
        public static final String EXEC_START_PRE = "ExecStartPre";
        public static final String EXEC_STOP_POST = "ExecStopPost";
        public static final String EXEC_STOP_PRE = "ExecStopPre";
        public static final String FILE_DESCRIPTOR_NAME = "FileDescriptorName";
        public static final String FREE_BIND = "FreeBind";
        public static final String GROUP = "Group";
        public static final String IO_SCHEDULING = "IOScheduling";
        public static final String IO_SCHEDULING_CLASS = "IOSchedulingClass";
        public static final String IO_SCHEDULING_PRIORITY = "IOSchedulingPriority";
        public static final String IP_TOS = "IPTOS";
        public static final String IP_TTL = "IPTTL";
        public static final String IGNORE_SIGPIPE = "IgnoreSIGPIPE";
        public static final String INACCESSIBLE_DIRECTORIES = "InaccessibleDirectories";
        public static final String KEEP_ALIVE = "KeepAlive";
        public static final String KEEP_ALIVE_INTERVAL_USEC = "KeepAliveIntervalUSec";
        public static final String KEEP_ALIVE_PROBES = "KeepAliveProbes";
        public static final String KEEP_ALIVE_TIME_USEC = "KeepAliveTimeUSec";
        public static final String KILL_MODE = "KillMode";
        public static final String KILL_SIGNAL = "KillSignal";
        public static final String LISTEN = "Listen";
        public static final String MARK = "Mark";
        public static final String MAX_CONNECTIONS = "MaxConnections";
        public static final String MAX_CONNECTIONS_PER_SOURCE = "MaxConnectionsPerSource";
        public static final String MEMORY_ACCOUNTING = "MemoryAccounting";
        public static final String MEMORY_CURRENT = "MemoryCurrent";
        public static final String MEMORY_LIMIT = "MemoryLimit";
        public static final String MESSAGE_QUEUE_MAX_MESSAGES = "MessageQueueMaxMessages";
        public static final String MESSAGE_QUEUE_MESSAGE_SIZE = "MessageQueueMessageSize";
        public static final String MOUNT_FLAGS = "MountFlags";
        public static final String NACCEPTED = "NAccepted";
        public static final String NCONNECTIONS = "NConnections";
        public static final String NICE = "Nice";
        public static final String NO_DELAY = "NoDelay";
        public static final String NO_NEW_PRIVILEGES = "NoNewPrivileges";
        public static final String NON_BLOCKING = "NonBlocking";
        public static final String OOM_SCORE_ADJUST = "OOMScoreAdjust";
        public static final String PAM_NAME = "PAMName";
        public static final String PASS_CREDENTIALS = "PassCredentials";
        public static final String PASS_ENVIRONMENT = "PassEnvironment";
        public static final String PASS_SECURITY = "PassSecurity";
        public static final String PERSONALITY = "Personality";
        public static final String PIPE_SIZE = "PipeSize";
        public static final String PRIORITY = "Priority";
        public static final String PRIVATE_DEVICES = "PrivateDevices";
        public static final String PRIVATE_NETWORK = "PrivateNetwork";
        public static final String PRIVATE_TMP = "PrivateTmp";
        public static final String PROTECT_HOME = "ProtectHome";
        public static final String PROTECT_SYSTEM = "ProtectSystem";
        public static final String READ_ONLY_DIRECTORIES = "ReadOnlyDirectories";
        public static final String READ_WRITE_DIRECTORIES = "ReadWriteDirectories";
        public static final String RECEIVE_BUFFER = "ReceiveBuffer";
        public static final String REMOVE_ON_STOP = "RemoveOnStop";
        public static final String RESTRICT_ADDRESS_FAMILIES = "RestrictAddressFamilies";
        public static final String RESULT = "Result";
        public static final String REUSE_PORT = "ReusePort";
        public static final String ROOT_DIRECTORY = "RootDirectory";
        public static final String RUNTIME_DIRECTORY = "RuntimeDirectory";
        public static final String RUNTIME_DIRECTORY_MODE = "RuntimeDirectoryMode";
        public static final String SELINUX_CONTEXT = "SELinuxContext";
        public static final String SAME_PROCESS_GROUP = "SameProcessGroup";
        public static final String SECURE_BITS = "SecureBits";
        public static final String SEND_BUFFER = "SendBuffer";
        public static final String SEND_SIGHUP = "SendSIGHUP";
        public static final String SEND_SIGKILL = "SendSIGKILL";
        public static final String SLICE = "Slice";
        public static final String SMACK_LABEL = "SmackLabel";
        public static final String SMACK_LABEL_IPIN = "SmackLabelIPIn";
        public static final String SMACK_LABEL_IPOUT = "SmackLabelIPOut";
        public static final String SMACK_PROCESS_LABEL = "SmackProcessLabel";
        public static final String SOCKET_GROUP = "SocketGroup";
        public static final String SOCKET_MODE = "SocketMode";
        public static final String SOCKET_PROTOCOL = "SocketProtocol";
        public static final String SOCKET_USER = "SocketUser";
        public static final String STANDARD_ERROR = "StandardError";
        public static final String STANDARD_INPUT = "StandardInput";
        public static final String STANDARD_OUTPUT = "StandardOutput";
        public static final String STARTUP_BLOCK_IO_WEIGHT = "StartupBlockIOWeight";
        public static final String STARTUP_CPU_SHARES = "StartupCPUShares";
        public static final String SUPPLEMENTARY_GROUPS = "SupplementaryGroups";
        public static final String SYMLINKS = "Symlinks";
        public static final String SYSLOG_FACILITY = "SyslogFacility";
        public static final String SYSLOG_IDENTIFIER = "SyslogIdentifier";
        public static final String SYSLOG_LEVEL = "SyslogLevel";
        public static final String SYSLOG_LEVEL_PREFIX = "SyslogLevelPrefix";
        public static final String SYSLOG_PRIORITY = "SyslogPriority";
        public static final String SYSTEM_CALL_ARCHITECTURES = "SystemCallArchitectures";
        public static final String SYSTEM_CALL_ERROR_NUMBER = "SystemCallErrorNumber";
        public static final String SYSTEM_CALL_FILTER = "SystemCallFilter";
        public static final String TRIGGER_LIMIT_BURST = "TriggerLimitBurst";
        public static final String TRIGGER_LIMIT_INTERVAL_USEC = "TriggerLimitIntervalUSec";
        public static final String TTY_PATH = "TTYPath";
        public static final String TTY_RESET = "TTYReset";
        public static final String TTY_V_HANGUP = "TTYVHangup";
        public static final String TTY_VT_DISALLOCATE = "TTYVTDisallocate";
        public static final String TASKS_ACCOUNTING = "TasksAccounting";
        public static final String TASKS_CURRENT = "TasksCurrent";
        public static final String TASKS_MAX = "TasksMax";
        public static final String TIMEOUT_USEC = "TimeoutUSec";
        public static final String TIMER_SLACK_NSEC = "TimerSlackNSec";
        public static final String TRANSPARENT = "Transparent";
        public static final String UMASK = "UMask";
        public static final String USER = "User";
        public static final String UTMP_IDENTIFIER = "UtmpIdentifier";
        public static final String UTMP_MODE = "UtmpMode";
        public static final String WORKING_DIRECTORY = "WorkingDirectory";
        public static final String WRITABLE = "Writable";

        private Property() {
            super();
        }

        public static final String[] getAllNames() {
            return getAllNames(
                    Property.class,
                    Limitable.Property.class,
                    IoAccountable.Property.class,
                    IpAccountable.Property.class,
                    MemoryAccountable.Property.class
            );
        }

    }

    private Socket(final Manager manager, final SocketInterface iface, final String name) throws DBusException {
        super(manager, iface, name);

        this.properties = Properties.create(dbus, iface.getObjectPath(), SERVICE_NAME);
    }

    static Socket create(final Manager manager, String name) throws DBusException {
        name = Unit.normalizeName(name, UNIT_SUFFIX);

        String objectPath = Unit.OBJECT_PATH + Systemd.escapePath(name);
        SocketInterface iface = manager.dbus.getRemoteObject(Systemd.SERVICE_NAME, objectPath, SocketInterface.class);

        return new Socket(manager, iface, name);
    }

    @Override
    public SocketInterface getInterface() {
        return (SocketInterface) super.getInterface();
    }

    public boolean isAccept() {
        return properties.getBoolean(Property.ACCEPT);
    }

    public BigInteger getAmbientCapabilities() {
        return properties.getBigInteger(Property.AMBIENT_CAPABILITIES);
    }

    public AppArmorProfile getAppArmorProfile() {
        Object[] array = (Object[]) properties.getVariant(Property.APP_ARMOR_PROFILE).getValue();

        return new AppArmorProfile(array);
    }

    public long getBacklog() {
        return properties.getLong(Property.BACKLOG);
    }

    public String getBindIPv6Only() {
        return properties.getString(Property.BIND_IPV6_ONLY);
    }

    public String getBindToDevice() {
        return properties.getString(Property.BIND_TO_DEVICE);
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

    public boolean isBroadcast() {
        return properties.getBoolean(Property.BROADCAST);
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

    public long getControlPID() {
        return properties.getLong(Property.CONTROL_PID);
    }

    public BigInteger getDeferAcceptUSec() {
        return properties.getBigInteger(Property.DEFER_ACCEPT_USEC);
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

    public long getDirectoryMode() {
        return properties.getLong(Property.DIRECTORY_MODE);
    }

    public Vector<String> getEnvironment() {
        return properties.getVector(Property.ENVIRONMENT);
    }

    public List<EnvironmentFile> getEnvironmentFiles() {
        return EnvironmentFile.list(properties.getVector(Property.ENVIRONMENT_FILES));
    }

    public List<ExecutionInfo> getExecStartPost() {
        return ExecutionInfo.list(properties.getVector(Property.EXEC_START_POST));
    }

    public List<ExecutionInfo> getExecStartPre() {
        return ExecutionInfo.list(properties.getVector(Property.EXEC_START_PRE));
    }

    public List<ExecutionInfo> getExecStopPost() {
        return ExecutionInfo.list(properties.getVector(Property.EXEC_STOP_POST));
    }

    public List<ExecutionInfo> getExecStopPre() {
        return ExecutionInfo.list(properties.getVector(Property.EXEC_STOP_PRE));
    }

    public String getFileDescriptorName() {
        return properties.getString(Property.FILE_DESCRIPTOR_NAME);
    }

    public boolean isFreeBind() {
        return properties.getBoolean(Property.FREE_BIND);
    }

    public String getGroup() {
        return properties.getString(Property.GROUP);
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

    public int getIPTOS() {
        return properties.getInteger(Property.IP_TOS);
    }

    public int getIPTTL() {
        return properties.getInteger(Property.IP_TTL);
    }

    public boolean isIgnoreSIGPIPE() {
        return properties.getBoolean(Property.IGNORE_SIGPIPE);
    }

    public Vector<String> getInaccessibleDirectories() {
        return properties.getVector(Property.INACCESSIBLE_DIRECTORIES);
    }

    public boolean isKeepAlive() {
        return properties.getBoolean(Property.KEEP_ALIVE);
    }

    public BigInteger getKeepAliveIntervalUSec() {
        return properties.getBigInteger(Property.KEEP_ALIVE_INTERVAL_USEC);
    }

    public long getKeepAliveProbes() {
        return properties.getLong(Property.KEEP_ALIVE_PROBES);
    }

    public BigInteger getKeepAliveTimeUSec() {
        return properties.getBigInteger(Property.KEEP_ALIVE_TIME_USEC);
    }

    public String getKillMode() {
        return properties.getString(Property.KILL_MODE);
    }

    public int getKillSignal() {
        return properties.getInteger(Property.KILL_SIGNAL);
    }

    public List<ListenInfo> getListen() {
        return ListenInfo.list(properties.getVector(Property.LISTEN));
    }

    public int getMark() {
        return properties.getInteger(Property.MARK);
    }

    public long getMaxConnections() {
        return properties.getLong(Property.MAX_CONNECTIONS);
    }

    public long getMaxConnectionsPerSource() {
        return properties.getLong(Property.MAX_CONNECTIONS_PER_SOURCE);
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

    public long getMessageQueueMaxMessages() {
        return properties.getLong(Property.MESSAGE_QUEUE_MAX_MESSAGES);
    }

    public long getMessageQueueMessageSize() {
        return properties.getLong(Property.MESSAGE_QUEUE_MESSAGE_SIZE);
    }

    public BigInteger getMountFlags() {
        return properties.getBigInteger(Property.MOUNT_FLAGS);
    }

    public long getNAccepted() {
        return properties.getLong(Property.NACCEPTED);
    }

    public long getNConnections() {
        return properties.getLong(Property.NCONNECTIONS);
    }

    public int getNice() {
        return properties.getInteger(Property.NICE);
    }

    public boolean isNoDelay() {
        return properties.getBoolean(Property.NO_DELAY);
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

    public boolean isPassCredentials() {
        return properties.getBoolean(Property.PASS_CREDENTIALS);
    }

    public Vector<String> getPassEnvironment() {
        return properties.getVector(Property.PASS_ENVIRONMENT);
    }

    public boolean isPassSecurity() {
        return properties.getBoolean(Property.PASS_SECURITY);
    }

    public String getPersonality() {
        return properties.getString(Property.PERSONALITY);
    }

    public BigInteger getPipeSize() {
        return properties.getBigInteger(Property.PIPE_SIZE);
    }

    public int getPriority() {
        return properties.getInteger(Property.PRIORITY);
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

    public BigInteger getReceiveBuffer() {
        return properties.getBigInteger(Property.RECEIVE_BUFFER);
    }

    public boolean isRemoveOnStop() {
        return properties.getBoolean(Property.REMOVE_ON_STOP);
    }

    public AddressFamilyRestriction getRestrictAddressFamilies() {
        Object[] array = (Object[]) properties.getVariant(Property.RESTRICT_ADDRESS_FAMILIES).getValue();

        return new AddressFamilyRestriction(array);
    }

    public String getResult() {
        return properties.getString(Property.RESULT);
    }

    public boolean getReusePort() {
        return properties.getBoolean(Property.REUSE_PORT);
    }

    public String getRootDirectory() {
        return properties.getString(Property.ROOT_DIRECTORY);
    }

    public Vector<String> getRuntimeDirectory() {
        return properties.getVector(Property.RUNTIME_DIRECTORY);
    }

    public long getRuntimeDirectoryMode() {
        return properties.getLong(Property.RUNTIME_DIRECTORY_MODE);
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

    public BigInteger getSendBuffer() {
        return properties.getBigInteger(Property.SEND_BUFFER);
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

    public String getSmackLabel() {
        return properties.getString(Property.SMACK_LABEL);
    }

    public String getSmackLabelIPIn() {
        return properties.getString(Property.SMACK_LABEL_IPIN);
    }

    public String getSmackLabelIPOut() {
        return properties.getString(Property.SMACK_LABEL_IPOUT);
    }

    public SmackProcessLabel getSmackProcessLabel() {
        Object[] array = (Object[]) properties.getVariant(Property.SMACK_PROCESS_LABEL).getValue();

        return new SmackProcessLabel(array);
    }

    public String getSocketGroup() {
        return properties.getString(Property.SOCKET_GROUP);
    }

    public long getSocketMode() {
        return properties.getLong(Property.SOCKET_MODE);
    }

    public int getSocketProtocol() {
        return properties.getInteger(Property.SOCKET_PROTOCOL);
    }

    public String getSocketUser() {
        return properties.getString(Property.SOCKET_USER);
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

    public Vector<String> getSymlinks() {
        return properties.getVector(Property.SYMLINKS);
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

    public long getTriggerLimitBurst() {
        return properties.getLong(Property.TRIGGER_LIMIT_BURST);
    }

    public BigInteger getTriggerLimitIntervalUSec() {
        return properties.getBigInteger(Property.TRIGGER_LIMIT_INTERVAL_USEC);
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

    public BigInteger getTimeoutUSec() {
        return properties.getBigInteger(Property.TIMEOUT_USEC);
    }

    public BigInteger getTimerSlackNSec() {
        return properties.getBigInteger(Property.TIMER_SLACK_NSEC);
    }

    public boolean isTransparent() {
        return properties.getBoolean(Property.TRANSPARENT);
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

    public String getUtmpMode() {
        return properties.getString(Property.UTMP_MODE);
    }

    public String getWorkingDirectory() {
        return properties.getString(Property.WORKING_DIRECTORY);
    }

    public boolean isWritable() {
        return properties.getBoolean(Property.WRITABLE);
    }

}
