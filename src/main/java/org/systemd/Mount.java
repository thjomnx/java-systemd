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
import org.systemd.interfaces.MountInterface;

public class Mount extends Unit {

    public static final String SERVICE_NAME = Systemd.SERVICE_NAME + ".Mount";
    public static final String UNIT_SUFFIX = ".mount";

    private final Properties properties;

    private Mount(final DBusConnection dbus, final MountInterface iface, final String name) throws DBusException {
        super(dbus, iface, name);

        this.properties = Properties.create(dbus, iface.getObjectPath(), SERVICE_NAME);
    }

    static Mount create(final DBusConnection dbus, String name) throws DBusException {
        name = Unit.normalizeName(name, UNIT_SUFFIX);

        String objectPath = Unit.OBJECT_PATH + Systemd.escapePath(name);
        MountInterface iface = dbus.getRemoteObject(Systemd.SERVICE_NAME, objectPath, MountInterface.class);

        return new Mount(dbus, iface, name);
    }

    @Override
    public MountInterface getInterface() {
        return (MountInterface) super.getInterface();
    }

/*
.AmbientCapabilities           property  t              0                                        const
.AppArmorProfile               property  (bs)           false ""                                 const
.BlockIOAccounting             property  b              false                                    -
.BlockIODeviceWeight           property  a(st)          0                                        -
.BlockIOReadBandwidth          property  a(st)          0                                        -
.BlockIOWeight                 property  t              18446744073709551615                     -
.BlockIOWriteBandwidth         property  a(st)          0                                        -
.CPUAccounting                 property  b              false                                    -
.CPUAffinity                   property  ay             0                                        const
.CPUQuotaPerSecUSec            property  t              18446744073709551615                     -
.CPUSchedulingPolicy           property  i              0                                        const
.CPUSchedulingPriority         property  i              0                                        const
.CPUSchedulingResetOnFork      property  b              false                                    const
.CPUShares                     property  t              18446744073709551615                     -
.CPUUsageNSec                  property  t              18446744073709551615                     -
.Capabilities                  property  s              ""                                       const
.CapabilityBoundingSet         property  t              18446744073709551615                     const
.ControlGroup                  property  s              "/system.slice/tmp.mount"                -
.ControlPID                    property  u              0                                        emits-change
.Delegate                      property  b              false                                    -
.DeviceAllow                   property  a(ss)          0                                        -
.DevicePolicy                  property  s              "auto"                                   -
.DirectoryMode                 property  u              493                                      const
.Environment                   property  as             0                                        const
.EnvironmentFiles              property  a(sb)          0                                        const
.ExecMount                     property  a(sasbttttuii) 1 "/usr/bin/mount" 7 "/usr/bin/mount"... emits-invalidation
.ExecRemount                   property  a(sasbttttuii) 0                                        emits-invalidation
.ExecUnmount                   property  a(sasbttttuii) 0                                        emits-invalidation
.Group                         property  s              ""                                       const
.IOScheduling                  property  i              0                                        const
.IgnoreSIGPIPE                 property  b              true                                     const
.InaccessibleDirectories       property  as             0                                        const
.KillMode                      property  s              "control-group"                          const
.KillSignal                    property  i              15                                       const
.LimitAS                       property  t              18446744073709551615                     const
.LimitASSoft                   property  t              18446744073709551615                     const
.LimitCORE                     property  t              18446744073709551615                     const
.LimitCORESoft                 property  t              18446744073709551615                     const
.LimitCPU                      property  t              18446744073709551615                     const
.LimitCPUSoft                  property  t              18446744073709551615                     const
.LimitDATA                     property  t              18446744073709551615                     const
.LimitDATASoft                 property  t              18446744073709551615                     const
.LimitFSIZE                    property  t              18446744073709551615                     const
.LimitFSIZESoft                property  t              18446744073709551615                     const
.LimitLOCKS                    property  t              18446744073709551615                     const
.LimitLOCKSSoft                property  t              18446744073709551615                     const
.LimitMEMLOCK                  property  t              65536                                    const
.LimitMEMLOCKSoft              property  t              65536                                    const
.LimitMSGQUEUE                 property  t              819200                                   const
.LimitMSGQUEUESoft             property  t              819200                                   const
.LimitNICE                     property  t              0                                        const
.LimitNICESoft                 property  t              0                                        const
.LimitNOFILE                   property  t              4096                                     const
.LimitNOFILESoft               property  t              1024                                     const
.LimitNPROC                    property  t              64140                                    const
.LimitNPROCSoft                property  t              64140                                    const
.LimitRSS                      property  t              18446744073709551615                     const
.LimitRSSSoft                  property  t              18446744073709551615                     const
.LimitRTPRIO                   property  t              0                                        const
.LimitRTPRIOSoft               property  t              0                                        const
.LimitRTTIME                   property  t              18446744073709551615                     const
.LimitRTTIMESoft               property  t              18446744073709551615                     const
.LimitSIGPENDING               property  t              64140                                    const
.LimitSIGPENDINGSoft           property  t              64140                                    const
.LimitSTACK                    property  t              18446744073709551615                     const
.LimitSTACKSoft                property  t              8388608                                  const
.MemoryAccounting              property  b              false                                    -
.MemoryCurrent                 property  t              18446744073709551615                     -
.MemoryLimit                   property  t              18446744073709551615                     -
.MountFlags                    property  t              0                                        const
.Nice                          property  i              0                                        const
.NoNewPrivileges               property  b              false                                    const
.NonBlocking                   property  b              false                                    const
.OOMScoreAdjust                property  i              0                                        const
.Options                       property  s              "rw"                                     emits-change
.PAMName                       property  s              ""                                       const
.PassEnvironment               property  as             0                                        const
.Personality                   property  s              ""                                       const
.PrivateDevices                property  b              false                                    const
.PrivateNetwork                property  b              false                                    const
.PrivateTmp                    property  b              false                                    const
.ProtectHome                   property  s              "no"                                     const
.ProtectSystem                 property  s              "no"                                     const
.ReadOnlyDirectories           property  as             0                                        const
.ReadWriteDirectories          property  as             0                                        const
.RestrictAddressFamilies       property  (bas)          false 0                                  const
.Result                        property  s              "success"                                emits-change
.RootDirectory                 property  s              ""                                       const
.RuntimeDirectory              property  as             0                                        const
.RuntimeDirectoryMode          property  u              493                                      const
.SELinuxContext                property  (bs)           false ""                                 const
.SameProcessGroup              property  b              true                                     const
.SecureBits                    property  i              0                                        const
.SendSIGHUP                    property  b              false                                    const
.SendSIGKILL                   property  b              true                                     const
.Slice                         property  s              "system.slice"                           -
.SloppyOptions                 property  b              false                                    const
.SmackProcessLabel             property  (bs)           false ""                                 const
.StandardError                 property  s              "inherit"                                const
.StandardInput                 property  s              "null"                                   const
.StandardOutput                property  s              "journal"                                const
.StartupBlockIOWeight          property  t              18446744073709551615                     -
.StartupCPUShares              property  t              18446744073709551615                     -
.SupplementaryGroups           property  as             0                                        const
.SyslogFacility                property  i              3                                        const
.SyslogIdentifier              property  s              ""                                       const
.SyslogLevel                   property  i              6                                        const
.SyslogLevelPrefix             property  b              true                                     const
.SyslogPriority                property  i              30                                       const
.SystemCallArchitectures       property  as             0                                        const
.SystemCallErrorNumber         property  i              0                                        const
.SystemCallFilter              property  (bas)          false 0                                  const
.TTYPath                       property  s              ""                                       const
.TTYReset                      property  b              false                                    const
.TTYVHangup                    property  b              false                                    const
.TTYVTDisallocate              property  b              false                                    const
.TasksAccounting               property  b              true                                     -
.TasksCurrent                  property  t              0                                        -
.TasksMax                      property  t              512                                      -
.TimeoutUSec                   property  t              90000000                                 const
.TimerSlackNSec                property  t              50000                                    const
.Type                          property  s              "tmpfs"                                  emits-change
.UMask                         property  u              18                                       const
.User                          property  s              ""                                       const
.UtmpIdentifier                property  s              ""                                       const
.UtmpMode                      property  s              "init"                                   const
.What                          property  s              "tmpfs"                                  emits-change
.Where                         property  s              "/tmp"                                   const
.WorkingDirectory              property  s              ""                                       const
 */

}
