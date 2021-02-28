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

import de.thjom.java.systemd.features.DynamicUserAccounting;
import de.thjom.java.systemd.features.ExtendedCpuAccounting;
import de.thjom.java.systemd.features.ExtendedMemoryAccounting;
import de.thjom.java.systemd.features.IoAccounting;
import de.thjom.java.systemd.features.IpAccounting;
import de.thjom.java.systemd.features.ResourceControl;
import de.thjom.java.systemd.features.TasksAccounting;
import de.thjom.java.systemd.features.Ulimit;
import de.thjom.java.systemd.interfaces.SocketInterface;
import de.thjom.java.systemd.types.DeviceAllowControl;
import de.thjom.java.systemd.types.EnvironmentFile;
import de.thjom.java.systemd.types.ExecutionInfo;
import de.thjom.java.systemd.types.ListenInfo;
import de.thjom.java.systemd.types.SystemCallFilter;
import de.thjom.java.systemd.types.UnitProcessType;

public class Socket extends Unit implements ExtendedCpuAccounting, DynamicUserAccounting, IoAccounting, IpAccounting, ExtendedMemoryAccounting, ResourceControl, TasksAccounting, Ulimit {

    public static final String SERVICE_NAME = Systemd.SERVICE_NAME + ".Socket";
    public static final String UNIT_SUFFIX = ".socket";

    public static class Property extends InterfaceAdapter.AdapterProperty {

        public static final String ACCEPT = "Accept";
        public static final String BACKLOG = "Backlog";
        public static final String BIND_IPV6_ONLY = "BindIPv6Only";
        public static final String BIND_TO_DEVICE = "BindToDevice";
        public static final String BROADCAST = "Broadcast";
        public static final String CAPABILITY_BOUNDING_SET = "CapabilityBoundingSet";
        public static final String CONTROL_PID = "ControlPID";
        public static final String DEFER_ACCEPT_USEC = "DeferAcceptUSec";
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
        public static final String FINAL_KILL_SIGNAL = "FinalKillSignal";
        public static final String FLUSH_PENDING = "FlushPending";
        public static final String FREE_BIND = "FreeBind";
        public static final String IO_SCHEDULING_CLASS = "IOSchedulingClass";
        public static final String IO_SCHEDULING_PRIORITY = "IOSchedulingPriority";
        public static final String IP_TOS = "IPTOS";
        public static final String IP_TTL = "IPTTL";
        public static final String IGNORE_SIGPIPE = "IgnoreSIGPIPE";
        public static final String INACCESSIBLE_PATHS = "InaccessiblePaths";
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
        public static final String MESSAGE_QUEUE_MAX_MESSAGES = "MessageQueueMaxMessages";
        public static final String MESSAGE_QUEUE_MESSAGE_SIZE = "MessageQueueMessageSize";
        public static final String MOUNT_FLAGS = "MountFlags";
        public static final String NACCEPTED = "NAccepted";
        public static final String NCONNECTIONS = "NConnections";
        public static final String NICE = "Nice";
        public static final String NO_DELAY = "NoDelay";
        public static final String NO_NEW_PRIVILEGES = "NoNewPrivileges";
        public static final String NON_BLOCKING = "NonBlocking";
        public static final String NREFUSED = "NRefused";
        public static final String OOM_SCORE_ADJUST = "OOMScoreAdjust";
        public static final String PAM_NAME = "PAMName";
        public static final String PASS_CREDENTIALS = "PassCredentials";
        public static final String PASS_PACKET_INFO = "PassPacketInfo";
        public static final String PASS_SECURITY = "PassSecurity";
        public static final String PIPE_SIZE = "PipeSize";
        public static final String PRIORITY = "Priority";
        public static final String READ_ONLY_PATHS = "ReadOnlyPaths";
        public static final String READ_WRITE_PATHS = "ReadWritePaths";
        public static final String RECEIVE_BUFFER = "ReceiveBuffer";
        public static final String REMOVE_ON_STOP = "RemoveOnStop";
        public static final String RESTART_KILL_SIGNAL = "RestartKillSignal";
        public static final String RESULT = "Result";
        public static final String REUSE_PORT = "ReusePort";
        public static final String ROOT_DIRECTORY = "RootDirectory";
        public static final String SAME_PROCESS_GROUP = "SameProcessGroup";
        public static final String SECURE_BITS = "SecureBits";
        public static final String SEND_BUFFER = "SendBuffer";
        public static final String SEND_SIGHUP = "SendSIGHUP";
        public static final String SEND_SIGKILL = "SendSIGKILL";
        public static final String SLICE = "Slice";
        public static final String SMACK_LABEL = "SmackLabel";
        public static final String SMACK_LABEL_IPIN = "SmackLabelIPIn";
        public static final String SMACK_LABEL_IPOUT = "SmackLabelIPOut";
        public static final String SOCKET_GROUP = "SocketGroup";
        public static final String SOCKET_MODE = "SocketMode";
        public static final String SOCKET_PROTOCOL = "SocketProtocol";
        public static final String SOCKET_USER = "SocketUser";
        public static final String SUPPLEMENTARY_GROUPS = "SupplementaryGroups";
        public static final String SYMLINKS = "Symlinks";
        public static final String SYSLOG_IDENTIFIER = "SyslogIdentifier";
        public static final String SYSLOG_LEVEL_PREFIX = "SyslogLevelPrefix";
        public static final String SYSLOG_PRIORITY = "SyslogPriority";
        public static final String SYSTEM_CALL_FILTER = "SystemCallFilter";
        public static final String TCP_CONGESTION = "TCPCongestion";
        public static final String TRIGGER_LIMIT_BURST = "TriggerLimitBurst";
        public static final String TRIGGER_LIMIT_INTERVAL_USEC = "TriggerLimitIntervalUSec";
        public static final String TTY_PATH = "TTYPath";
        public static final String TTY_RESET = "TTYReset";
        public static final String TTY_V_HANGUP = "TTYVHangup";
        public static final String TTY_VT_DISALLOCATE = "TTYVTDisallocate";
        public static final String TIMEOUT_USEC = "TimeoutUSec";
        public static final String TIMER_SLACK_NSEC = "TimerSlackNSec";
        public static final String TIMESTAMPING = "Timestamping";
        public static final String TRANSPARENT = "Transparent";
        public static final String UMASK = "UMask";
        public static final String WATCHDOG_SIGNAL = "WatchdogSignal";
        public static final String WORKING_DIRECTORY = "WorkingDirectory";
        public static final String WRITABLE = "Writable";

        private Property() {
            super();
        }

        public static final List<String> getAllNames() {
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

    public void attachProcesses(final String cgroupPath, final long[] pids) {
        getInterface().attachProcesses(cgroupPath, pids);
    }

    public List<UnitProcessType> getProcesses() {
        return getInterface().getProcesses();
    }

    public boolean isAccept() {
        return properties.getBoolean(Property.ACCEPT);
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

    public boolean isBroadcast() {
        return properties.getBoolean(Property.BROADCAST);
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

    public List<DeviceAllowControl> getDeviceAllow() {
        return DeviceAllowControl.list(properties.getList(Property.DEVICE_ALLOW));
    }

    public String getDevicePolicy() {
        return properties.getString(Property.DEVICE_POLICY);
    }

    public long getDirectoryMode() {
        return properties.getLong(Property.DIRECTORY_MODE);
    }

    public List<String> getEnvironment() {
        return properties.getList(Property.ENVIRONMENT);
    }

    public List<EnvironmentFile> getEnvironmentFiles() {
        return EnvironmentFile.list(properties.getList(Property.ENVIRONMENT_FILES));
    }

    public List<ExecutionInfo> getExecStartPost() {
        return ExecutionInfo.list(properties.getList(Property.EXEC_START_POST));
    }

    public List<ExecutionInfo> getExecStartPre() {
        return ExecutionInfo.list(properties.getList(Property.EXEC_START_PRE));
    }

    public List<ExecutionInfo> getExecStopPost() {
        return ExecutionInfo.list(properties.getList(Property.EXEC_STOP_POST));
    }

    public List<ExecutionInfo> getExecStopPre() {
        return ExecutionInfo.list(properties.getList(Property.EXEC_STOP_PRE));
    }

    public String getFileDescriptorName() {
        return properties.getString(Property.FILE_DESCRIPTOR_NAME);
    }

    public int getFinalKillSignal() {
        return properties.getInteger(Property.FINAL_KILL_SIGNAL);
    }

    public boolean isFlushPending() {
        return properties.getBoolean(Property.FLUSH_PENDING);
    }

    public boolean isFreeBind() {
        return properties.getBoolean(Property.FREE_BIND);
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

    public List<String> getInaccessiblePaths() {
        return properties.getList(Property.INACCESSIBLE_PATHS);
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
        return ListenInfo.list(properties.getList(Property.LISTEN));
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

    public long getNRefused() {
        return properties.getLong(Property.NREFUSED);
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

    public boolean isPassPacketInfo() {
        return properties.getBoolean(Property.PASS_PACKET_INFO);
    }

    public boolean isPassSecurity() {
        return properties.getBoolean(Property.PASS_SECURITY);
    }

    public BigInteger getPipeSize() {
        return properties.getBigInteger(Property.PIPE_SIZE);
    }

    public int getPriority() {
        return properties.getInteger(Property.PRIORITY);
    }

    public List<String> getReadOnlyPaths() {
        return properties.getList(Property.READ_ONLY_PATHS);
    }

    public List<String> getReadWritePaths() {
        return properties.getList(Property.READ_WRITE_PATHS);
    }

    public BigInteger getReceiveBuffer() {
        return properties.getBigInteger(Property.RECEIVE_BUFFER);
    }

    public boolean isRemoveOnStop() {
        return properties.getBoolean(Property.REMOVE_ON_STOP);
    }

    public int getRestartKillSignal() {
        return properties.getInteger(Property.RESTART_KILL_SIGNAL);
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

    public List<String> getSupplementaryGroups() {
        return properties.getList(Property.SUPPLEMENTARY_GROUPS);
    }

    public List<String> getSymlinks() {
        return properties.getList(Property.SYMLINKS);
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

    public String getTCPCongestion() {
        return properties.getString(Property.TCP_CONGESTION);
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

    public BigInteger getTimeoutUSec() {
        return properties.getBigInteger(Property.TIMEOUT_USEC);
    }

    public BigInteger getTimerSlackNSec() {
        return properties.getBigInteger(Property.TIMER_SLACK_NSEC);
    }

    public String getTimestamping() {
        return properties.getString(Property.TIMESTAMPING);
    }

    public boolean isTransparent() {
        return properties.getBoolean(Property.TRANSPARENT);
    }

    public long getUMask() {
        return properties.getLong(Property.UMASK);
    }

    public int getWatchdogSignal() {
        return properties.getInteger(Property.WATCHDOG_SIGNAL);
    }

    public String getWorkingDirectory() {
        return properties.getString(Property.WORKING_DIRECTORY);
    }

    public boolean isWritable() {
        return properties.getBoolean(Property.WRITABLE);
    }

}
