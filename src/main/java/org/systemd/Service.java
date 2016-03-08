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
import org.systemd.interfaces.ServiceInterface;
import org.systemd.types.AddressFamilyRestriction;
import org.systemd.types.AppArmorProfile;
import org.systemd.types.BlockIOBandwidth;
import org.systemd.types.BlockIODeviceWeight;
import org.systemd.types.DeviceAllowControl;
import org.systemd.types.EnvironmentFile;
import org.systemd.types.ExecutionInfo;
import org.systemd.types.SELinuxContext;
import org.systemd.types.SmackProcessLabel;
import org.systemd.types.SystemCallFilter;

public class Service extends Unit {

    public static final String SERVICE_NAME = Systemd.SERVICE_NAME + ".Service";
    public static final String UNIT_SUFFIX = ".service";

    private final Properties properties;

    private Service(final DBusConnection dbus, final ServiceInterface iface, final String name) throws DBusException {
        super(dbus, iface, name);

        this.properties = Properties.create(dbus, iface.getObjectPath(), SERVICE_NAME);
    }

    static Service create(final DBusConnection dbus, String name) throws DBusException {
        name = Unit.normalizeName(name, UNIT_SUFFIX);

        String objectPath = Unit.OBJECT_PATH + Systemd.escapePath(name);
        ServiceInterface iface = dbus.getRemoteObject(Systemd.SERVICE_NAME, objectPath, ServiceInterface.class);

        return new Service(dbus, iface, name);
    }

    @Override
    public ServiceInterface getInterface() {
        return (ServiceInterface) super.getInterface();
    }

    public AppArmorProfile getAppArmorProfile() {
        Object[] array = (Object[]) properties.getVariant("AppArmorProfile").getValue();

        return new AppArmorProfile(array);
    }

    public boolean getBlockIOAccounting() {
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

    public String getBusName() {
        return properties.getString("BusName");
    }

    public boolean getCPUAccounting() {
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

    public String getControlGroup() {
        return properties.getString("ControlGroup");
    }

    public long getControlPID() {
        return properties.getLong("ControlPID");
    }

    public boolean doesDelegate() {
        return properties.getBoolean("Delegate");
    }

    public List<DeviceAllowControl> getDeviceAllow() {
        return DeviceAllowControl.list(properties.getVector("DeviceAllow"));
    }

    public String getDevicePolicy() {
        return properties.getString("DevicePolicy");
    }

    public Vector<String> getEnvironment() {
        return properties.getVector("Environment");
    }

    public List<EnvironmentFile> getEnvironmentFiles() {
        return EnvironmentFile.list(properties.getVector("EnvironmentFiles"));
    }

    public int getExecMainCode() {
        return properties.getInteger("ExecMainCode");
    }

    public long getExecMainExitTimestamp() {
        return properties.getLong("ExecMainExitTimestamp");
    }

    public long getExecMainExitTimestampMonotonic() {
        return properties.getLong("ExecMainExitTimestampMonotonic");
    }

    public long getExecMainPID() {
        return properties.getLong("ExecMainPID");
    }

    public long getExecMainStartTimestamp() {
        return properties.getLong("ExecMainStartTimestamp");
    }

    public long getExecMainStartTimestampMonotonic() {
        return properties.getLong("ExecMainStartTimestampMonotonic");
    }

    public int getExecMainStatus() {
        return properties.getInteger("ExecMainStatus");
    }

    public List<ExecutionInfo> getExecReload() {
        return ExecutionInfo.list(properties.getVector("ExecReload"));
    }

    public List<ExecutionInfo> getExecStart() {
        return ExecutionInfo.list(properties.getVector("ExecStart"));
    }

    public List<ExecutionInfo> getExecStartPost() {
        return ExecutionInfo.list(properties.getVector("ExecStartPost"));
    }

    public List<ExecutionInfo> getExecStartPre() {
        return ExecutionInfo.list(properties.getVector("ExecStartPre"));
    }

    public List<ExecutionInfo> getExecStop() {
        return ExecutionInfo.list(properties.getVector("ExecStop"));
    }

    public List<ExecutionInfo> getExecStopPost() {
        return ExecutionInfo.list(properties.getVector("ExecStopPost"));
    }

    public String getFailureAction() {
        return properties.getString("FailureAction");
    }

    public long getFileDescriptorStoreMax() {
        return properties.getLong("FileDescriptorStoreMax");
    }

    public String getGroup() {
        return properties.getString("Group");
    }

    public boolean isGuessMainPID() {
        return properties.getBoolean("GuessMainPID");
    }

    public int getIOScheduling() {
        return properties.getInteger("IOScheduling");
    }

    public boolean isIgnoreSIGPIPE() {
        return properties.getBoolean("IgnoreSIGPIPE");
    }

    public Vector<String> getInaccessibleDirectories() {
        return properties.getVector("InaccessibleDirectories");
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

    public BigInteger getLimitCORE() {
        return properties.getBigInteger("LimitCORE");
    }

    public BigInteger getLimitCPU() {
        return properties.getBigInteger("LimitCPU");
    }

    public BigInteger getLimitDATA() {
        return properties.getBigInteger("LimitDATA");
    }

    public BigInteger getLimitFSIZE() {
        return properties.getBigInteger("LimitFSIZE");
    }

    public BigInteger getLimitLOCKS() {
        return properties.getBigInteger("LimitLOCKS");
    }

    public BigInteger getLimitMEMLOCK() {
        return properties.getBigInteger("LimitMEMLOCK");
    }

    public BigInteger getLimitMSGQUEUE() {
        return properties.getBigInteger("LimitMSGQUEUE");
    }

    public BigInteger getLimitNICE() {
        return properties.getBigInteger("LimitNICE");
    }

    public BigInteger getLimitNOFILE() {
        return properties.getBigInteger("LimitNOFILE");
    }

    public BigInteger getLimitNPROC() {
        return properties.getBigInteger("LimitNPROC");
    }

    public BigInteger getLimitRSS() {
        return properties.getBigInteger("LimitRSS");
    }

    public BigInteger getLimitRTPRIO() {
        return properties.getBigInteger("LimitRTPRIO");
    }

    public BigInteger getLimitRTTIME() {
        return properties.getBigInteger("LimitRTTIME");
    }

    public BigInteger getLimitSIGPENDING() {
        return properties.getBigInteger("LimitSIGPENDING");
    }

    public BigInteger getLimitSTACK() {
        return properties.getBigInteger("LimitSTACK");
    }

    public int getMainPID() {
        return properties.getInteger("MainPID");
    }

    public boolean hasMemoryAccounting() {
        return properties.getBoolean("MemoryAccounting");
    }

    public BigInteger getMemoryCurrent() {
        return properties.getBigInteger("MemoryCurrent");
    }

    public BigInteger getMemoryLimit() {
        return properties.getBigInteger("MemoryLimit");
    }

    public BigInteger getMountFlags() {
        return properties.getBigInteger("MountFlags");
    }

    public long getNFileDescriptorStore() {
        return properties.getLong("NFileDescriptorStore");
    }

    public int getNice() {
        return properties.getInteger("Nice");
    }

    public boolean canNoNewPrivileges() {
        return properties.getBoolean("NoNewPrivileges");
    }

    public boolean isNonBlocking() {
        return properties.getBoolean("NonBlocking");
    }

    public String getNotifyAccess() {
        return properties.getString("NotifyAccess");
    }

    public int getOOMScoreAdjust() {
        return properties.getInteger("OOMScoreAdjust");
    }

    public String getPAMName() {
        return properties.getString("PAMName");
    }

    public String getPIDFile() {
        return properties.getString("PIDFile");
    }

    public Vector<String> getPassEnvironment() {
        return properties.getVector("PassEnvironment");
    }

    public boolean isPermissionsStartOnly() {
        return properties.getBoolean("PermissionsStartOnly");
    }

    public String getPersonality() {
        return properties.getString("Personality");
    }

    public boolean hasPrivateDevices() {
        return properties.getBoolean("PrivateDevices");
    }

    public boolean hasPrivateNetwork() {
        return properties.getBoolean("PrivateNetwork");
    }

    public boolean hasPrivateTmp() {
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

    public String getRebootArgument() {
        return properties.getString("RebootArgument");
    }

    public boolean isRemainAfterExit() {
        return properties.getBoolean("RemainAfterExit");
    }

    public String getRestart() {
        return properties.getString("Restart");
    }

    public long getRestartUSec() {
        return properties.getLong("RestartUSec");
    }

    public AddressFamilyRestriction getRestrictAddressFamilies() {
        Object[] array = (Object[]) properties.getVariant("RestrictAddressFamilies").getValue();

        return new AddressFamilyRestriction(array);
    }

    public String getResult() {
        return properties.getString("Result");
    }

    public String getRootDirectory() {
        return properties.getString("RootDirectory");
    }

    public boolean isRootDirectoryStartOnly() {
        return properties.getBoolean("RootDirectoryStartOnly");
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

    public boolean isSendSIGHUP() {
        return properties.getBoolean("SendSIGHUP");
    }

    public boolean isSendSIGKILL() {
        return properties.getBoolean("SendSIGKILL");
    }

    public String getSlice() {
        return properties.getString("Slice");
    }

    public SmackProcessLabel getSmackProcessLabel() {
        Object[] array = (Object[]) properties.getVariant("SmackProcessLabel").getValue();

        return new SmackProcessLabel(array);
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

    public String getStartLimitAction() {
        return properties.getString("StartLimitAction");
    }

    public long getStartLimitBurst() {
        return properties.getLong("StartLimitBurst");
    }

    public BigInteger getStartLimitInterval() {
        return properties.getBigInteger("StartLimitInterval");
    }

    public BigInteger getStartupBlockIOWeight() {
        return properties.getBigInteger("StartupBlockIOWeight");
    }

    public BigInteger getStartupCPUShares() {
        return properties.getBigInteger("StartupCPUShares");
    }

    public int getStatusErrno() {
        return properties.getInteger("StatusErrno");
    }

    public String getStatusText() {
        return properties.getString("StatusText");
    }

    public Vector<String> getSupplementaryGroups() {
        return properties.getVector("SupplementaryGroups");
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

    public boolean hasSyslogLevelPrefix() {
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

    public boolean hasTasksAccounting() {
        return properties.getBoolean("TasksAccounting");
    }

    public long getTasksCurrent() {
        return properties.getLong("TasksCurrent");
    }

    public long getTasksMax() {
        return properties.getLong("TasksMax");
    }

    public long getTimeoutStartUSec() {
        return properties.getLong("TimeoutStartUSec");
    }

    public long getTimeoutStopUSec() {
        return properties.getLong("TimeoutStopUSec");
    }

    public long getTimerSlackNSec() {
        return properties.getLong("TimerSlackNSec");
    }

    public String getType() {
        return properties.getString("Type");
    }

    public long getUMask() {
        return properties.getLong("UMask");
    }

    public String getUSBFunctionDescriptors() {
        return properties.getString("USBFunctionDescriptors");
    }

    public String getUSBFunctionStrings() {
        return properties.getString("USBFunctionStrings");
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

    public long getWatchdogTimestamp() {
        return properties.getLong("WatchdogTimestamp");
    }

    public long getWatchdogTimestampMonotonic() {
        return properties.getLong("WatchdogTimestampMonotonic");
    }

    public long getWatchdogUSec() {
        return properties.getLong("WatchdogUSec");
    }

    public String getWorkingDirectory() {
        return properties.getString("WorkingDirectory");
    }

}
