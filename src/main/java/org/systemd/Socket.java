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

import org.freedesktop.dbus.DBusConnection;
import org.freedesktop.dbus.exceptions.DBusException;
import org.systemd.interfaces.SocketInterface;

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
