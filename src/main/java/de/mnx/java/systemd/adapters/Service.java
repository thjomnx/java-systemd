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

package de.mnx.java.systemd.adapters;

import static de.mnx.java.systemd.Systemd.SYSTEMD_DBUS_NAME;

import org.freedesktop.dbus.DBusConnection;
import org.freedesktop.dbus.DBusInterface;

public class Service extends Unit {

    public static final String SERVICE_NAME = SYSTEMD_DBUS_NAME + ".Service";

    public Service(final DBusConnection dbus, final DBusInterface iface) {
        super(dbus, iface);

        initProperties(SERVICE_NAME);
    }

    public long getMainPID() {
        return properties.getLong("MainPID");
    }

    public SELinuxContext getSELinuxContext() {
        Object[] array = (Object[]) properties.getVariant("SELinuxContext").getValue();

        return new SELinuxContext((boolean) array[0], (String) array[1]);
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

    // TODO Rename variables to their real meaning
    // http://systemd-devel.freedesktop.narkive.com/6xnzKsRp/patch-export-selinuxcontext-on-the-bus-as-a-structure
    public static class SELinuxContext {

        private final boolean prefixed;
        private final String userData;

        public SELinuxContext(final boolean prefix, final String userData) {
            this.prefixed = prefix;
            this.userData = userData;
        }

        public boolean isPrefixed() {
            return prefixed;
        }

        public String getUserData() {
            return userData;
        }

        @Override
        public String toString() {
            return "[prefixed=" + prefixed + ", userData=" + userData + "]";
        }

    }

    /**
     *
.AppArmorProfile                 property  (bs)           false ""                                 const
.BlockIOAccounting               property  b              false                                    -
.BlockIODeviceWeight             property  a(st)          0                                        -
.BlockIOReadBandwidth            property  a(st)          0                                        -
.BlockIOWeight                   property  t              18446744073709551615                     -
.BlockIOWriteBandwidth           property  a(st)          0                                        -
.BusName                         property  s              ""                                       const
.CPUAccounting                   property  b              false                                    -
.CPUAffinity                     property  ay             0                                        const
.CPUQuotaPerSecUSec              property  t              18446744073709551615                     -
.CPUSchedulingPolicy             property  i              0                                        const
.CPUSchedulingPriority           property  i              0                                        const
.CPUSchedulingResetOnFork        property  b              false                                    const
.CPUShares                       property  t              18446744073709551615                     -
.CPUUsageNSec                    property  t              18446744073709551615                     -
.Capabilities                    property  s              ""                                       const
.CapabilityBoundingSet           property  t              18446744073709551615                     const
.ControlGroup                    property  s              "/system.slice/cronie.service"           -
.ControlPID                      property  u              0                                        emits-change
.Delegate                        property  b              false                                    -
.DeviceAllow                     property  a(ss)          0                                        -
.DevicePolicy                    property  s              "auto"                                   -
.Environment                     property  as             0                                        const
.EnvironmentFiles                property  a(sb)          0                                        const
.ExecMainCode                    property  i              0                                        emits-change
.ExecMainExitTimestamp           property  t              0                                        emits-change
.ExecMainExitTimestampMonotonic  property  t              0                                        emits-change
.ExecMainPID                     property  u              369                                      emits-change
.ExecMainStartTimestamp          property  t              1454158017072144                         emits-change
.ExecMainStartTimestampMonotonic property  t              7581134                                  emits-change
.ExecMainStatus                  property  i              0                                        emits-change
.ExecReload                      property  a(sasbttttuii) 1 "/usr/bin/kill" 3 "/usr/bin/kill" "... emits-invalidation
.ExecStart                       property  a(sasbttttuii) 1 "/usr/bin/crond" 2 "/usr/bin/crond"... emits-invalidation
.ExecStartPost                   property  a(sasbttttuii) 0                                        emits-invalidation
.ExecStartPre                    property  a(sasbttttuii) 0                                        emits-invalidation
.ExecStop                        property  a(sasbttttuii) 0                                        emits-invalidation
.ExecStopPost                    property  a(sasbttttuii) 0                                        emits-invalidation
.FailureAction                   property  s              "none"                                   const
.FileDescriptorStoreMax          property  u              0                                        const
.Group                           property  s              ""                                       const
.GuessMainPID                    property  b              true                                     const
.IOScheduling                    property  i              0                                        const
.IgnoreSIGPIPE                   property  b              true                                     const
.InaccessibleDirectories         property  as             0                                        const
.KillMode                        property  s              "process"                                const
.KillSignal                      property  i              15                                       const
.LimitAS                         property  t              18446744073709551615                     const
.LimitCORE                       property  t              18446744073709551615                     const
.LimitCPU                        property  t              18446744073709551615                     const
.LimitDATA                       property  t              18446744073709551615                     const
.LimitFSIZE                      property  t              18446744073709551615                     const
.LimitLOCKS                      property  t              18446744073709551615                     const
.LimitMEMLOCK                    property  t              65536                                    const
.LimitMSGQUEUE                   property  t              819200                                   const
.LimitNICE                       property  t              0                                        const
.LimitNOFILE                     property  t              4096                                     const
.LimitNPROC                      property  t              48013                                    const
.LimitRSS                        property  t              18446744073709551615                     const
.LimitRTPRIO                     property  t              0                                        const
.LimitRTTIME                     property  t              18446744073709551615                     const
.LimitSIGPENDING                 property  t              48013                                    const
.LimitSTACK                      property  t              18446744073709551615                     const
  .MainPID                         property  u              369                                      emits-change
.MemoryAccounting                property  b              false                                    -
.MemoryCurrent                   property  t              18446744073709551615                     -
.MemoryLimit                     property  t              18446744073709551615                     -
.MountFlags                      property  t              0                                        const
.NFileDescriptorStore            property  u              0                                        -
.Nice                            property  i              0                                        const
.NoNewPrivileges                 property  b              false                                    const
.NonBlocking                     property  b              false                                    const
.NotifyAccess                    property  s              "none"                                   const
.OOMScoreAdjust                  property  i              0                                        const
.PAMName                         property  s              ""                                       const
.PIDFile                         property  s              ""                                       const
.PassEnvironment                 property  as             0                                        const
.PermissionsStartOnly            property  b              false                                    const
.Personality                     property  s              ""                                       const
.PrivateDevices                  property  b              false                                    const
.PrivateNetwork                  property  b              false                                    const
.PrivateTmp                      property  b              false                                    const
.ProtectHome                     property  s              "no"                                     const
.ProtectSystem                   property  s              "no"                                     const
.ReadOnlyDirectories             property  as             0                                        const
.ReadWriteDirectories            property  as             0                                        const
.RebootArgument                  property  s              ""                                       const
.RemainAfterExit                 property  b              false                                    const
.Restart                         property  s              "always"                                 const
.RestartUSec                     property  t              100000                                   const
.RestrictAddressFamilies         property  (bas)          false 0                                  const
.Result                          property  s              "success"                                emits-change
.RootDirectory                   property  s              ""                                       const
.RootDirectoryStartOnly          property  b              false                                    const
.RuntimeDirectory                property  as             0                                        const
.RuntimeDirectoryMode            property  u              493                                      const
  .SELinuxContext                  property  (bs)           false ""                                 const
.SameProcessGroup                property  b              false                                    const
.SecureBits                      property  i              0                                        const
.SendSIGHUP                      property  b              false                                    const
.SendSIGKILL                     property  b              true                                     const
.Slice                           property  s              "system.slice"                           -
.SmackProcessLabel               property  (bs)           false ""                                 const
  .StandardError                   property  s              "inherit"                                const
  .StandardInput                   property  s              "null"                                   const
  .StandardOutput                  property  s              "journal"                                const
.StartLimitAction                property  s              "none"                                   const
.StartLimitBurst                 property  u              5                                        const
.StartLimitInterval              property  t              10000000                                 const
.StartupBlockIOWeight            property  t              18446744073709551615                     -
.StartupCPUShares                property  t              18446744073709551615                     -
.StatusErrno                     property  i              0                                        emits-change
.StatusText                      property  s              ""                                       emits-change
.SupplementaryGroups             property  as             0                                        const
.SyslogFacility                  property  i              3                                        const
.SyslogIdentifier                property  s              ""                                       const
.SyslogLevel                     property  i              6                                        const
.SyslogLevelPrefix               property  b              true                                     const
.SyslogPriority                  property  i              30                                       const
.SystemCallArchitectures         property  as             0                                        const
.SystemCallErrorNumber           property  i              0                                        const
.SystemCallFilter                property  (bas)          false 0                                  const
.TTYPath                         property  s              ""                                       const
.TTYReset                        property  b              false                                    const
.TTYVHangup                      property  b              false                                    const
.TTYVTDisallocate                property  b              false                                    const
.TasksAccounting                 property  b              true                                     -
.TasksCurrent                    property  t              1                                        -
.TasksMax                        property  t              512                                      -
.TimeoutStartUSec                property  t              90000000                                 const
.TimeoutStopUSec                 property  t              90000000                                 const
.TimerSlackNSec                  property  t              50000                                    const
.Type                            property  s              "simple"                                 const
.UMask                           property  u              18                                       const
.USBFunctionDescriptors          property  s              ""                                       emits-change
.USBFunctionStrings              property  s              ""                                       emits-change
.User                            property  s              ""                                       const
.UtmpIdentifier                  property  s              ""                                       const
.UtmpMode                        property  s              "init"                                   const
.WatchdogTimestamp               property  t              1454158017072175                         -
.WatchdogTimestampMonotonic      property  t              7581165                                  -
.WatchdogUSec                    property  t              0                                        const
.WorkingDirectory                property  s              ""                                       const
     */

}
