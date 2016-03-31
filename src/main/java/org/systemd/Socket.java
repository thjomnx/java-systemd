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
import java.util.Vector;

import org.freedesktop.dbus.DBusConnection;
import org.freedesktop.dbus.exceptions.DBusException;
import org.systemd.interfaces.SocketInterface;
import org.systemd.types.AddressFamilyRestriction;
import org.systemd.types.AppArmorProfile;
import org.systemd.types.BlockIOBandwidth;
import org.systemd.types.BlockIODeviceWeight;
import org.systemd.types.DeviceAllowControl;
import org.systemd.types.EnvironmentFile;
import org.systemd.types.ExecutionInfo;
import org.systemd.types.ListenInfo;
import org.systemd.types.SELinuxContext;
import org.systemd.types.SmackProcessLabel;
import org.systemd.types.SystemCallFilter;

public class Socket extends Unit {

    public static final String SERVICE_NAME = Systemd.SERVICE_NAME + ".Socket";
    public static final String UNIT_SUFFIX = ".socket";

    private final Properties properties;

    private Socket(final DBusConnection dbus, final SocketInterface iface, final String name) throws DBusException {
        super(dbus, iface, name);

        this.properties = Properties.create(dbus, iface.getObjectPath(), SERVICE_NAME);
    }

    static Socket create(final DBusConnection dbus, String name) throws DBusException {
        name = Unit.normalizeName(name, UNIT_SUFFIX);

        String objectPath = Unit.OBJECT_PATH + Systemd.escapePath(name);
        SocketInterface iface = dbus.getRemoteObject(Systemd.SERVICE_NAME, objectPath, SocketInterface.class);

        return new Socket(dbus, iface, name);
    }

    @Override
    public SocketInterface getInterface() {
        return (SocketInterface) super.getInterface();
    }

    public boolean isAccept() {
        return properties.getBoolean("Accept");
    }

    public BigInteger getAmbientCapabilities() {
        return properties.getBigInteger("AmbientCapabilities");
    }

    public AppArmorProfile getAppArmorProfile() {
        Object[] array = (Object[]) properties.getVariant("AppArmorProfile").getValue();

        return new AppArmorProfile(array);
    }

    public long getBacklog() {
        return properties.getLong("Backlog");
    }

    public String getBindIPv6Only() {
        return properties.getString("BindIPv6Only");
    }

    public String getBindToDevice() {
        return properties.getString("BindToDevice");
    }

    public boolean isBlockIOAccounting() {
        return properties.getBoolean("BlockIOAccounting");
    }

    public List<BlockIODeviceWeight> getBlockIODeviceWeight() {
        return BlockIODeviceWeight.list(properties.getVector("BlockIODeviceWeight"));
    }

    public List<BlockIOBandwidth> getBlockIOReadBandwidth() {
        return BlockIOBandwidth.list(properties.getVector("BlockIOReadBandwidth"));
    }

    public BigInteger getBlockIOWeight() {
        return properties.getBigInteger("BlockIOWeight");
    }

    public List<BlockIOBandwidth> getBlockIOWriteBandwidth() {
        return BlockIOBandwidth.list(properties.getVector("BlockIOWriteBandwidth"));
    }

    public boolean isBroadcast() {
        return properties.getBoolean("Broadcast");
    }

    public boolean isCPUAccounting() {
        return properties.getBoolean("CPUAccounting");
    }

    public byte[] getCPUAffinity() {
        return (byte[]) properties.getVariant("CPUAffinity").getValue();
    }

    public BigInteger getCPUQuotaPerSecUSec() {
        return properties.getBigInteger("CPUQuotaPerSecUSec");
    }

    public int getCPUSchedulingPolicy() {
        return properties.getInteger("CPUSchedulingPolicy");
    }

    public int getCPUSchedulingPriority() {
        return properties.getInteger("CPUSchedulingPriority");
    }

    public boolean isCPUSchedulingResetOnFork() {
        return properties.getBoolean("CPUSchedulingResetOnFork");
    }

    public BigInteger getCPUShares() {
        return properties.getBigInteger("CPUShares");
    }

    public BigInteger getCPUUsageNSec() {
        return properties.getBigInteger("CPUUsageNSec");
    }

    public String getCapabilities() {
        return properties.getString("Capabilities");
    }

    public BigInteger getCapabilityBoundingSet() {
        return properties.getBigInteger("CapabilityBoundingSet");
    }

    public long getControlPID() {
        return properties.getLong("ControlPID");
    }

    public long getDeferAcceptUSec() {
        return properties.getLong("DeferAcceptUSec");
    }

    public boolean isDelegate() {
        return properties.getBoolean("Delegate");
    }

    public List<DeviceAllowControl> getDeviceAllow() {
        return DeviceAllowControl.list(properties.getVector("DeviceAllow"));
    }

    public String getDevicePolicy() {
        return properties.getString("DevicePolicy");
    }

    public long getDirectoryMode() {
        return properties.getLong("DirectoryMode");
    }

    public Vector<String> getEnvironment() {
        return properties.getVector("Environment");
    }

    public List<EnvironmentFile> getEnvironmentFiles() {
        return EnvironmentFile.list(properties.getVector("EnvironmentFiles"));
    }

    public List<ExecutionInfo> getExecStartPost() {
        return ExecutionInfo.list(properties.getVector("ExecStartPost"));
    }

    public List<ExecutionInfo> getExecStartPre() {
        return ExecutionInfo.list(properties.getVector("ExecStartPre"));
    }

    public List<ExecutionInfo> getExecStopPost() {
        return ExecutionInfo.list(properties.getVector("ExecStopPost"));
    }

    public List<ExecutionInfo> getExecStopPre() {
        return ExecutionInfo.list(properties.getVector("ExecStopPre"));
    }

    public String getFileDescriptorName() {
        return properties.getString("FileDescriptorName");
    }

    public boolean isFreeBind() {
        return properties.getBoolean("FreeBind");
    }

    public String getGroup() {
        return properties.getString("Group");
    }

    public int getIOScheduling() {
        return properties.getInteger("IOScheduling");
    }

    public int getIPTOS() {
        return properties.getInteger("IPTOS");
    }

    public int getIPTTL() {
        return properties.getInteger("IPTTL");
    }

    public boolean isIgnoreSIGPIPE() {
        return properties.getBoolean("IgnoreSIGPIPE");
    }

    public Vector<String> getInaccessibleDirectories() {
        return properties.getVector("InaccessibleDirectories");
    }

    public boolean isKeepAlive() {
        return properties.getBoolean("KeepAlive");
    }

    public long getKeepAliveIntervalUSec() {
        return properties.getLong("KeepAliveIntervalUSec");
    }

    public long getKeepAliveProbes() {
        return properties.getLong("KeepAliveProbes");
    }

    public long getKeepAliveTimeUSec() {
        return properties.getLong("KeepAliveTimeUSec");
    }

    public String getKillMode() {
        return properties.getString("KillMode");
    }

    public int getKillSignal() {
        return properties.getInteger("KillSignal");
    }

    public BigInteger getLimitAS() {
        return properties.getBigInteger("LimitAS");
    }

    public BigInteger getLimitASSoft() {
        return properties.getBigInteger("LimitASSoft");
    }

    public BigInteger getLimitCORE() {
        return properties.getBigInteger("LimitCORE");
    }

    public BigInteger getLimitCORESoft() {
        return properties.getBigInteger("LimitCORESoft");
    }

    public BigInteger getLimitCPU() {
        return properties.getBigInteger("LimitCPU");
    }

    public BigInteger getLimitCPUSoft() {
        return properties.getBigInteger("LimitCPUSoft");
    }

    public BigInteger getLimitDATA() {
        return properties.getBigInteger("LimitDATA");
    }

    public BigInteger getLimitDATASoft() {
        return properties.getBigInteger("LimitDATASoft");
    }

    public BigInteger getLimitFSIZE() {
        return properties.getBigInteger("LimitFSIZE");
    }

    public BigInteger getLimitFSIZESoft() {
        return properties.getBigInteger("LimitFSIZESoft");
    }

    public BigInteger getLimitLOCKS() {
        return properties.getBigInteger("LimitLOCKS");
    }

    public BigInteger getLimitLOCKSSoft() {
        return properties.getBigInteger("LimitLOCKSSoft");
    }

    public BigInteger getLimitMEMLOCK() {
        return properties.getBigInteger("LimitMEMLOCK");
    }

    public BigInteger getLimitMEMLOCKSoft() {
        return properties.getBigInteger("LimitMEMLOCKSoft");
    }

    public BigInteger getLimitMSGQUEUE() {
        return properties.getBigInteger("LimitMSGQUEUE");
    }

    public BigInteger getLimitMSGQUEUESoft() {
        return properties.getBigInteger("LimitMSGQUEUESoft");
    }

    public BigInteger getLimitNICE() {
        return properties.getBigInteger("LimitNICE");
    }

    public BigInteger getLimitNICESoft() {
        return properties.getBigInteger("LimitNICESoft");
    }

    public BigInteger getLimitNOFILE() {
        return properties.getBigInteger("LimitNOFILE");
    }

    public BigInteger getLimitNOFILESoft() {
        return properties.getBigInteger("LimitNOFILESoft");
    }

    public BigInteger getLimitNPROC() {
        return properties.getBigInteger("LimitNPROC");
    }

    public BigInteger getLimitNPROCSoft() {
        return properties.getBigInteger("LimitNPROCSoft");
    }

    public BigInteger getLimitRSS() {
        return properties.getBigInteger("LimitRSS");
    }

    public BigInteger getLimitRSSSoft() {
        return properties.getBigInteger("LimitRSSSoft");
    }

    public BigInteger getLimitRTPRIO() {
        return properties.getBigInteger("LimitRTPRIO");
    }

    public BigInteger getLimitRTPRIOSoft() {
        return properties.getBigInteger("LimitRTPRIOSoft");
    }

    public BigInteger getLimitRTTIME() {
        return properties.getBigInteger("LimitRTTIME");
    }

    public BigInteger getLimitRTTIMESoft() {
        return properties.getBigInteger("LimitRTTIMESoft");
    }

    public BigInteger getLimitSIGPENDING() {
        return properties.getBigInteger("LimitSIGPENDING");
    }

    public BigInteger getLimitSIGPENDINGSoft() {
        return properties.getBigInteger("LimitSIGPENDINGSoft");
    }

    public BigInteger getLimitSTACK() {
        return properties.getBigInteger("LimitSTACK");
    }

    public BigInteger getLimitSTACKSoft() {
        return properties.getBigInteger("LimitSTACKSoft");
    }

    public List<ListenInfo> getListen() {
        return ListenInfo.list(properties.getVector("Listen"));
    }

    public int getMark() {
        return properties.getInteger("Mark");
    }

    public long getMaxConnections() {
        return properties.getLong("MaxConnections");
    }

    public boolean isMemoryAccounting() {
        return properties.getBoolean("MemoryAccounting");
    }

    public BigInteger getMemoryCurrent() {
        return properties.getBigInteger("MemoryCurrent");
    }

    public BigInteger getMemoryLimit() {
        return properties.getBigInteger("MemoryLimit");
    }

    public long getMessageQueueMaxMessages() {
        return properties.getLong("MessageQueueMaxMessages");
    }

    public long getMessageQueueMessageSize() {
        return properties.getLong("MessageQueueMessageSize");
    }

    public BigInteger getMountFlags() {
        return properties.getBigInteger("MountFlags");
    }

    public long getNAccepted() {
        return properties.getLong("NAccepted");
    }

    public long getNConnections() {
        return properties.getLong("NConnections");
    }

    public int getNice() {
        return properties.getInteger("Nice");
    }

    public boolean isNoDelay() {
        return properties.getBoolean("NoDelay");
    }

    public boolean isNoNewPrivileges() {
        return properties.getBoolean("NoNewPrivileges");
    }

    public boolean isNonBlocking() {
        return properties.getBoolean("NonBlocking");
    }

    public int getOOMScoreAdjust() {
        return properties.getInteger("OOMScoreAdjust");
    }

    public String getPAMName() {
        return properties.getString("PAMName");
    }

    public boolean isPassCredentials() {
        return properties.getBoolean("PassCredentials");
    }

    public Vector<String> getPassEnvironment() {
        return properties.getVector("PassEnvironment");
    }

    public boolean isPassSecurity() {
        return properties.getBoolean("PassSecurity");
    }

    public String getPersonality() {
        return properties.getString("Personality");
    }

    public BigInteger getPipeSize() {
        return properties.getBigInteger("PipeSize");
    }

    public int getPriority() {
        return properties.getInteger("Priority");
    }

    public boolean isPrivateDevices() {
        return properties.getBoolean("PrivateDevices");
    }

    public boolean isPrivateNetwork() {
        return properties.getBoolean("PrivateNetwork");
    }

    public boolean isPrivateTmp() {
        return properties.getBoolean("PrivateTmp");
    }

    public String getProtectHome() {
        return properties.getString("ProtectHome");
    }

    public String getProtectSystem() {
        return properties.getString("ProtectSystem");
    }

    public Vector<String> getReadOnlyDirectories() {
        return properties.getVector("ReadOnlyDirectories");
    }

    public Vector<String> getReadWriteDirectories() {
        return properties.getVector("ReadWriteDirectories");
    }

    public BigInteger getReceiveBuffer() {
        return properties.getBigInteger("ReceiveBuffer");
    }

    public boolean isRemoveOnStop() {
        return properties.getBoolean("RemoveOnStop");
    }

    public AddressFamilyRestriction getRestrictAddressFamilies() {
        Object[] array = (Object[]) properties.getVariant("RestrictAddressFamilies").getValue();

        return new AddressFamilyRestriction(array);
    }

    public String getResult() {
        return properties.getString("Result");
    }

    public boolean getReusePort() {
        return properties.getBoolean("ReusePort");
    }

    public String getRootDirectory() {
        return properties.getString("RootDirectory");
    }

    public Vector<String> getRuntimeDirectory() {
        return properties.getVector("RuntimeDirectory");
    }

    public long getRuntimeDirectoryMode() {
        return properties.getLong("RuntimeDirectoryMode");
    }

    public SELinuxContext getSELinuxContext() {
        Object[] array = (Object[]) properties.getVariant("SELinuxContext").getValue();

        return new SELinuxContext(array);
    }

    public boolean isSameProcessGroup() {
        return properties.getBoolean("SameProcessGroup");
    }

    public int getSecureBits() {
        return properties.getInteger("SecureBits");
    }

    public BigInteger getSendBuffer() {
        return properties.getBigInteger("SendBuffer");
    }

    public boolean isSendSIGHUP() {
        return properties.getBoolean("SendSIGHUP");
    }

    public boolean isSendSIGKILL() {
        return properties.getBoolean("SendSIGKILL");
    }

    public String getSlice() {
        return properties.getString("Slice");
    }

    public String getSmackLabel() {
        return properties.getString("SmackLabel");
    }

    public String getSmackLabelIPIn() {
        return properties.getString("SmackLabelIPIn");
    }

    public String getSmackLabelIPOut() {
        return properties.getString("SmackLabelIPOut");
    }

    public SmackProcessLabel getSmackProcessLabel() {
        Object[] array = (Object[]) properties.getVariant("SmackProcessLabel").getValue();

        return new SmackProcessLabel(array);
    }

    public String getSocketGroup() {
        return properties.getString("SocketGroup");
    }

    public long getSocketMode() {
        return properties.getLong("SocketMode");
    }

    public int getSocketProtocol() {
        return properties.getInteger("SocketProtocol");
    }

    public String getSocketUser() {
        return properties.getString("SocketUser");
    }

    public String getStandardError() {
        return properties.getString("StandardError");
    }

    public String getStandardInput() {
        return properties.getString("StandardInput");
    }

    public String getStandardOutput() {
        return properties.getString("StandardOutput");
    }

    public BigInteger getStartupBlockIOWeight() {
        return properties.getBigInteger("StartupBlockIOWeight");
    }

    public BigInteger getStartupCPUShares() {
        return properties.getBigInteger("StartupCPUShares");
    }

    public Vector<String> getSupplementaryGroups() {
        return properties.getVector("SupplementaryGroups");
    }

    public Vector<String> getSymlinks() {
        return properties.getVector("Symlinks");
    }

    public int getSyslogFacility() {
        return properties.getInteger("SyslogFacility");
    }

    public String getSyslogIdentifier() {
        return properties.getString("SyslogIdentifier");
    }

    public int getSyslogLevel() {
        return properties.getInteger("SyslogLevel");
    }

    public boolean isSyslogLevelPrefix() {
        return properties.getBoolean("SyslogLevelPrefix");
    }

    public int getSyslogPriority() {
        return properties.getInteger("SyslogPriority");
    }

    public Vector<String> getSystemCallArchitectures() {
        return properties.getVector("SystemCallArchitectures");
    }

    public int getSystemCallErrorNumber() {
        return properties.getInteger("SystemCallErrorNumber");
    }

    public SystemCallFilter getSystemCallFilter() {
        Object[] array = (Object[]) properties.getVariant("SystemCallFilter").getValue();

        return new SystemCallFilter(array);
    }

    public String getTTYPath() {
        return properties.getString("TTYPath");
    }

    public boolean isTTYReset() {
        return properties.getBoolean("TTYReset");
    }

    public boolean isTTYVHangup() {
        return properties.getBoolean("TTYVHangup");
    }

    public boolean isTTYVTDisallocate() {
        return properties.getBoolean("TTYVTDisallocate");
    }

    public boolean isTasksAccounting() {
        return properties.getBoolean("TasksAccounting");
    }

    public BigInteger getTasksCurrent() {
        return properties.getBigInteger("TasksCurrent");
    }

    public BigInteger getTasksMax() {
        return properties.getBigInteger("TasksMax");
    }

    public long getTimeoutUSec() {
        return properties.getLong("TimeoutUSec");
    }

    public long getTimerSlackNSec() {
        return properties.getLong("TimerSlackNSec");
    }

    public boolean isTransparent() {
        return properties.getBoolean("Transparent");
    }

    public long getUMask() {
        return properties.getLong("UMask");
    }

    public String getUser() {
        return properties.getString("User");
    }

    public String getUtmpIdentifier() {
        return properties.getString("UtmpIdentifier");
    }

    public String getUtmpMode() {
        return properties.getString("UtmpMode");
    }

    public String getWorkingDirectory() {
        return properties.getString("WorkingDirectory");
    }

    public boolean isWritable() {
        return properties.getBoolean("Writable");
    }

}
