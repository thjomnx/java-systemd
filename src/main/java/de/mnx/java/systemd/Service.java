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

package de.mnx.java.systemd;

import static de.mnx.java.systemd.Systemd.SYSTEMD_DBUS_NAME;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

import org.freedesktop.DBus.Introspectable;
import org.freedesktop.dbus.DBusConnection;
import org.freedesktop.dbus.UInt32;
import org.freedesktop.dbus.UInt64;
import org.freedesktop.dbus.exceptions.DBusException;

import de.mnx.java.systemd.interfaces.ServiceInterface;

public class Service extends Unit {

    public static final String SERVICE_NAME = SYSTEMD_DBUS_NAME + ".Service";
    public static final String UNIT_SUFFIX = ".service";

    private final Properties properties;

    private Service(final DBusConnection dbus, final ServiceInterface iface) throws DBusException {
        super(dbus, iface);

        this.properties = Properties.create(dbus, iface.getObjectPath(), SERVICE_NAME);
    }

    static Service create(final DBusConnection dbus, final String objectPath) throws DBusException {
        ServiceInterface iface = dbus.getRemoteObject(SYSTEMD_DBUS_NAME, objectPath, ServiceInterface.class);

        return new Service(dbus, iface);
    }

    @Override
    public ServiceInterface getInterface() {
        return (ServiceInterface) super.getInterface();
    }

    public String introspect() throws DBusException {
        Introspectable intro = dbus.getRemoteObject(SYSTEMD_DBUS_NAME, getInterface().getObjectPath(), Introspectable.class);

        return intro.Introspect();
    }

    public String getControlGroup() {
        return properties.getString("ControlGroup");
    }

    public String getDevicePolicy() {
        return properties.getString("DevicePolicy");
    }

    public Vector<String> getEnvironment() {
        return properties.getVector("Environment");
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
        return ExecutionInfo.transform(properties.getVector("ExecReload"));
    }

    public List<ExecutionInfo> getExecStart() {
        return ExecutionInfo.transform(properties.getVector("ExecStart"));
    }

    public List<ExecutionInfo> getExecStartPost() {
        return ExecutionInfo.transform(properties.getVector("ExecStartPost"));
    }

    public List<ExecutionInfo> getExecStartPre() {
        return ExecutionInfo.transform(properties.getVector("ExecStartPre"));
    }

    public List<ExecutionInfo> getExecStop() {
        return ExecutionInfo.transform(properties.getVector("ExecStop"));
    }

    public List<ExecutionInfo> getExecStopPost() {
        return ExecutionInfo.transform(properties.getVector("ExecStopPost"));
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

    public String getResult() {
        return properties.getString("Result");
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

    public static class ExecutionInfo {

        private final String binaryPath;
        private final Vector<String> arguments;
        private final boolean failOnUncleanExit;
        private final long lastStartTimestamp;
        private final long lastStartTimestampMonotonic;
        private final long lastFinishTimestamp;
        private final long lastFinishTimestampMonotonic;
        private final int processId;
        private final int lastExitCode;
        private final int lastExitStatus;

        @SuppressWarnings("unchecked")
        public ExecutionInfo(final Object[] array) {
            this.binaryPath = String.valueOf(array[0]);
            this.arguments = (Vector<String>) array[1];
            this.failOnUncleanExit = (boolean) array[2];
            this.lastStartTimestamp = ((UInt64) array[3]).longValue();
            this.lastStartTimestampMonotonic = ((UInt64) array[4]).longValue();
            this.lastFinishTimestamp = ((UInt64) array[5]).longValue();
            this.lastFinishTimestampMonotonic = ((UInt64) array[6]).longValue();
            this.processId = ((UInt32) array[7]).intValue();
            this.lastExitCode = (int) array[8];
            this.lastExitStatus = (int) array[9];
        }

        private static List<ExecutionInfo> transform(final Vector<Object[]> vector) {
            List<ExecutionInfo> execs = new ArrayList<>(vector.size());

            for (Object[] array : vector) {
                ExecutionInfo exec = new ExecutionInfo(array);

                execs.add(exec);
            }

            return execs;
        }

        public String getBinaryPath() {
            return binaryPath;
        }

        public Vector<String> getArguments() {
            return arguments;
        }

        public boolean isFailOnUncleanExit() {
            return failOnUncleanExit;
        }

        public long getLastStartTimestamp() {
            return lastStartTimestamp;
        }

        public long getLastStartTimestampMonotonic() {
            return lastStartTimestampMonotonic;
        }

        public long getLastFinishTimestamp() {
            return lastFinishTimestamp;
        }

        public long getLastFinishTimestampMonotonic() {
            return lastFinishTimestampMonotonic;
        }

        public int getProcessId() {
            return processId;
        }

        public int getLastExitCode() {
            return lastExitCode;
        }

        public int getLastExitStatus() {
            return lastExitStatus;
        }

        @Override
        public String toString() {
            StringBuilder builder = new StringBuilder();
            builder.append("ExecutionInfo [binaryPath=");
            builder.append(binaryPath);
            builder.append(", arguments=");
            builder.append(arguments);
            builder.append(", failOnUncleanExit=");
            builder.append(failOnUncleanExit);
            builder.append(", lastStartTimestamp=");
            builder.append(lastStartTimestamp);
            builder.append(", lastStartTimestampMonotonic=");
            builder.append(lastStartTimestampMonotonic);
            builder.append(", lastFinishTimestamp=");
            builder.append(lastFinishTimestamp);
            builder.append(", lastFinishTimestampMonotonic=");
            builder.append(lastFinishTimestampMonotonic);
            builder.append(", processId=");
            builder.append(processId);
            builder.append(", lastExitCode=");
            builder.append(lastExitCode);
            builder.append(", lastExitStatus=");
            builder.append(lastExitStatus);
            builder.append("]");

            return builder.toString();
        }

    }

    // TODO Rename 'prefixed' variable to match its real purpose
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
            return String.format("SELinuxContext [prefixed=%s, userData=%s]", prefixed, userData);
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
