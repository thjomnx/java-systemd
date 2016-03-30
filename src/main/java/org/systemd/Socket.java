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
import org.systemd.types.AppArmorProfile;
import org.systemd.types.BlockIOBandwidth;
import org.systemd.types.BlockIODeviceWeight;
import org.systemd.types.DeviceAllowControl;
import org.systemd.types.EnvironmentFile;
import org.systemd.types.ExecutionInfo;
import org.systemd.types.ListenInfo;

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

    public String[] getInaccessibleDirectories() {
        return (String[]) properties.getVariant("InaccessibleDirectories").getValue();
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

/*
  .Accept                         property  b              false                                    const
  .AmbientCapabilities            property  t              0                                        const
  .AppArmorProfile                property  (bs)           false ""                                 const
  .Backlog                        property  u              128                                      const
  .BindIPv6Only                   property  s              "default"                                const
  .BindToDevice                   property  s              ""                                       const
  .BlockIOAccounting              property  b              false                                    -
  .BlockIODeviceWeight            property  a(st)          0                                        -
  .BlockIOReadBandwidth           property  a(st)          0                                        -
  .BlockIOWeight                  property  t              18446744073709551615                     -
  .BlockIOWriteBandwidth          property  a(st)          0                                        -
  .Broadcast                      property  b              false                                    const
  .CPUAccounting                  property  b              false                                    -
  .CPUAffinity                    property  ay             0                                        const
  .CPUQuotaPerSecUSec             property  t              18446744073709551615                     -
  .CPUSchedulingPolicy            property  i              0                                        const
  .CPUSchedulingPriority          property  i              0                                        const
  .CPUSchedulingResetOnFork       property  b              false                                    const
  .CPUShares                      property  t              18446744073709551615                     -
  .CPUUsageNSec                   property  t              18446744073709551615                     -
  .Capabilities                   property  s              ""                                       const
  .CapabilityBoundingSet          property  t              18446744073709551615                     const
  .ControlGroup                   property  s              ""                                       -
  .ControlPID                     property  u              0                                        emits-change
  .DeferAcceptUSec                property  t              0                                        const
  .Delegate                       property  b              false                                    -
  .DeviceAllow                    property  a(ss)          0                                        -
  .DevicePolicy                   property  s              "auto"                                   -
  .DirectoryMode                  property  u              493                                      const
  .Environment                    property  as             0                                        const
  .EnvironmentFiles               property  a(sb)          0                                        const
  .ExecStartPost                  property  a(sasbttttuii) 0                                        emits-invalidation
  .ExecStartPre                   property  a(sasbttttuii) 0                                        emits-invalidation
  .ExecStopPost                   property  a(sasbttttuii) 0                                        emits-invalidation
  .ExecStopPre                    property  a(sasbttttuii) 0                                        emits-invalidation
  .FileDescriptorName             property  s              "dbus.socket"                            -
  .FreeBind                       property  b              false                                    const
  .Group                          property  s              ""                                       const
  .IOScheduling                   property  i              0                                        const
  .IPTOS                          property  i              -1                                       const
  .IPTTL                          property  i              -1                                       const
  .IgnoreSIGPIPE                  property  b              true                                     const
  .InaccessibleDirectories        property  as             0                                        const
  .KeepAlive                      property  b              false                                    const
  .KeepAliveIntervalUSec          property  t              0                                        const
  .KeepAliveProbes                property  u              0                                        const
  .KeepAliveTimeUSec              property  t              0                                        const
  .KillMode                       property  s              "control-group"                          const
  .KillSignal                     property  i              15                                       const
  .LimitAS                        property  t              18446744073709551615                     const
  .LimitASSoft                    property  t              18446744073709551615                     const
  .LimitCORE                      property  t              18446744073709551615                     const
  .LimitCORESoft                  property  t              18446744073709551615                     const
  .LimitCPU                       property  t              18446744073709551615                     const
  .LimitCPUSoft                   property  t              18446744073709551615                     const
  .LimitDATA                      property  t              18446744073709551615                     const
  .LimitDATASoft                  property  t              18446744073709551615                     const
  .LimitFSIZE                     property  t              18446744073709551615                     const
  .LimitFSIZESoft                 property  t              18446744073709551615                     const
  .LimitLOCKS                     property  t              18446744073709551615                     const
  .LimitLOCKSSoft                 property  t              18446744073709551615                     const
  .LimitMEMLOCK                   property  t              65536                                    const
  .LimitMEMLOCKSoft               property  t              65536                                    const
  .LimitMSGQUEUE                  property  t              819200                                   const
  .LimitMSGQUEUESoft              property  t              819200                                   const
  .LimitNICE                      property  t              0                                        const
  .LimitNICESoft                  property  t              0                                        const
  .LimitNOFILE                    property  t              4096                                     const
  .LimitNOFILESoft                property  t              1024                                     const
  .LimitNPROC                     property  t              64140                                    const
  .LimitNPROCSoft                 property  t              64140                                    const
  .LimitRSS                       property  t              18446744073709551615                     const
  .LimitRSSSoft                   property  t              18446744073709551615                     const
  .LimitRTPRIO                    property  t              0                                        const
  .LimitRTPRIOSoft                property  t              0                                        const
  .LimitRTTIME                    property  t              18446744073709551615                     const
  .LimitRTTIMESoft                property  t              18446744073709551615                     const
  .LimitSIGPENDING                property  t              64140                                    const
  .LimitSIGPENDINGSoft            property  t              64140                                    const
  .LimitSTACK                     property  t              18446744073709551615                     const
  .LimitSTACKSoft                 property  t              8388608                                  const
  .Listen                         property  a(ss)          1 "Stream" "/run/dbus/system_bus_socket" const
.Mark                           property  i              -1                                       const
.MaxConnections                 property  u              64                                       const
.MemoryAccounting               property  b              false                                    -
.MemoryCurrent                  property  t              18446744073709551615                     -
.MemoryLimit                    property  t              18446744073709551615                     -
.MessageQueueMaxMessages        property  x              0                                        const
.MessageQueueMessageSize        property  x              0                                        const
.MountFlags                     property  t              0                                        const
.NAccepted                      property  u              0                                        -
.NConnections                   property  u              0                                        -
.Nice                           property  i              0                                        const
.NoDelay                        property  b              false                                    const
.NoNewPrivileges                property  b              false                                    const
.NonBlocking                    property  b              false                                    const
.OOMScoreAdjust                 property  i              0                                        const
.PAMName                        property  s              ""                                       const
.PassCredentials                property  b              false                                    const
.PassEnvironment                property  as             0                                        const
.PassSecurity                   property  b              false                                    const
.Personality                    property  s              ""                                       const
.PipeSize                       property  t              0                                        const
.Priority                       property  i              -1                                       const
.PrivateDevices                 property  b              false                                    const
.PrivateNetwork                 property  b              false                                    const
.PrivateTmp                     property  b              false                                    const
.ProtectHome                    property  s              "no"                                     const
.ProtectSystem                  property  s              "no"                                     const
.ReadOnlyDirectories            property  as             0                                        const
.ReadWriteDirectories           property  as             0                                        const
.ReceiveBuffer                  property  t              0                                        const
.RemoveOnStop                   property  b              false                                    const
.RestrictAddressFamilies        property  (bas)          false 0                                  const
.Result                         property  s              "success"                                emits-change
.ReusePort                      property  b              false                                    const
.RootDirectory                  property  s              ""                                       const
.RuntimeDirectory               property  as             0                                        const
.RuntimeDirectoryMode           property  u              493                                      const
.SELinuxContext                 property  (bs)           false ""                                 const
.SameProcessGroup               property  b              false                                    const
.SecureBits                     property  i              0                                        const
.SendBuffer                     property  t              0                                        const
.SendSIGHUP                     property  b              false                                    const
.SendSIGKILL                    property  b              true                                     const
.Slice                          property  s              ""                                       -
.SmackLabel                     property  s              ""                                       const
.SmackLabelIPIn                 property  s              ""                                       const
.SmackLabelIPOut                property  s              ""                                       const
.SmackProcessLabel              property  (bs)           false ""                                 const
.SocketGroup                    property  s              ""                                       const
.SocketMode                     property  u              438                                      const
.SocketProtocol                 property  i              0                                        const
.SocketUser                     property  s              ""                                       const
.StandardError                  property  s              "inherit"                                const
.StandardInput                  property  s              "null"                                   const
.StandardOutput                 property  s              "journal"                                const
.StartupBlockIOWeight           property  t              18446744073709551615                     -
.StartupCPUShares               property  t              18446744073709551615                     -
.SupplementaryGroups            property  as             0                                        const
.Symlinks                       property  as             0                                        const
.SyslogFacility                 property  i              3                                        const
.SyslogIdentifier               property  s              ""                                       const
.SyslogLevel                    property  i              6                                        const
.SyslogLevelPrefix              property  b              true                                     const
.SyslogPriority                 property  i              30                                       const
.SystemCallArchitectures        property  as             0                                        const
.SystemCallErrorNumber          property  i              0                                        const
.SystemCallFilter               property  (bas)          false 0                                  const
.TTYPath                        property  s              ""                                       const
.TTYReset                       property  b              false                                    const
.TTYVHangup                     property  b              false                                    const
.TTYVTDisallocate               property  b              false                                    const
.TasksAccounting                property  b              true                                     -
.TasksCurrent                   property  t              18446744073709551615                     -
.TasksMax                       property  t              512                                      -
.TimeoutUSec                    property  t              90000000                                 const
.TimerSlackNSec                 property  t              50000                                    const
.Transparent                    property  b              false                                    const
.UMask                          property  u              18                                       const
.User                           property  s              ""                                       const
.UtmpIdentifier                 property  s              ""                                       const
.UtmpMode                       property  s              "init"                                   const
.WorkingDirectory               property  s              ""                                       const
.Writable                       property  b              false                                    const
 */

}
