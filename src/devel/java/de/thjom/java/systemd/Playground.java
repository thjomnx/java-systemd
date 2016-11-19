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

package de.thjom.java.systemd;

import java.util.Arrays;

import org.freedesktop.dbus.exceptions.DBusException;

import de.thjom.java.systemd.Systemd.InstanceType;

public class Playground {

    private Playground() {
        // Do nothing (static class)
    }

    public static void introspect(final Manager manager) throws DBusException {
        System.out.println(manager.introspect());
        System.out.println(manager.getAutomount("proc-sys-fs-binfmt_misc").introspect());
//        System.out.println(manager.getDevice("").introspect());
        System.out.println(manager.getMount("tmp").introspect());
        System.out.println(manager.getPath("systemd-ask-password-wall").introspect());
//        System.out.println(manager.getScope("").introspect());
        System.out.println(manager.getService("cronie").introspect());
        System.out.println(manager.getSlice("system").introspect());
//        System.out.println(manager.getSnapshot("").introspect());
        System.out.println(manager.getSocket("dbus").introspect());
//        System.out.println(manager.getSwap("").introspect());
        System.out.println(manager.getTarget("basic").introspect());
        System.out.println(manager.getTimer("shadow").introspect());
    }

    public static void properties(final Manager manager) throws DBusException  {
        System.out.println("'Manager' properties:");

        System.out.println("Architecture: " + manager.getArchitecture());
        System.out.println("ConfirmSpawn: " + manager.isConfirmSpawn());
        System.out.println("ControlGroup: " + manager.getControlGroup());
        System.out.println("DefaultBlockIOAccounting: " + manager.isDefaultBlockIOAccounting());
        System.out.println("DefaultCPUAccounting: " + manager.isDefaultCPUAccounting());
        System.out.println("DefaultLimitAS: " + manager.getDefaultLimitAS());
        System.out.println("DefaultLimitCORE: " + manager.getDefaultLimitCORE());
        System.out.println("DefaultLimitCPU: " + manager.getDefaultLimitCPU());
        System.out.println("DefaultLimitDATA: " + manager.getDefaultLimitDATA());
        System.out.println("DefaultLimitFSIZE: " + manager.getDefaultLimitFSIZE());
        System.out.println("DefaultLimitLOCKS: " + manager.getDefaultLimitLOCKS());
        System.out.println("DefaultLimitMEMLOCK: " + manager.getDefaultLimitMEMLOCK());
        System.out.println("DefaultLimitMSGQUEUE: " + manager.getDefaultLimitMSGQUEUE());
        System.out.println("DefaultLimitNICE: " + manager.getDefaultLimitNICE());
        System.out.println("DefaultLimitNOFILE: " + manager.getDefaultLimitNOFILE());
        System.out.println("DefaultLimitNPROC: " + manager.getDefaultLimitNPROC());
        System.out.println("DefaultLimitRSS: " + manager.getDefaultLimitRSS());
        System.out.println("DefaultLimitRTPRIO: " + manager.getDefaultLimitRTPRIO());
        System.out.println("DefaultLimitRTTIME: " + manager.getDefaultLimitRTTIME());
        System.out.println("DefaultLimitSIGPENDING: " + manager.getDefaultLimitSIGPENDING());
        System.out.println("DefaultLimitSTACK: " + manager.getDefaultLimitSTACK());
        System.out.println("DefaultMemoryAccounting: " + manager.isDefaultMemoryAccounting());
        System.out.println("DefaultRestartUSec: " + manager.getDefaultRestartUSec());
        System.out.println("DefaultStandardError: " + manager.getDefaultStandardError());
        System.out.println("DefaultStandardOutput: " + manager.getDefaultStandardOutput());
        System.out.println("DefaultStartLimitBurst: " + manager.getDefaultStartLimitBurst());
        System.out.println("DefaultStartLimitInterval: " + manager.getDefaultStartLimitInterval());
        System.out.println("DefaultTasksAccounting: " + manager.isDefaultTasksAccounting());
        System.out.println("DefaultTasksMax: " + manager.getDefaultTasksMax());
        System.out.println("DefaultTimeoutStartUSec: " + manager.getDefaultTimeoutStartUSec());
        System.out.println("DefaultTimeoutStopUSec: " + manager.getDefaultTimeoutStopUSec());
        System.out.println("DefaultTimerAccuracyUSec: " + manager.getDefaultTimerAccuracyUSec());
        System.out.println("Environment: " + manager.getEnvironment());
        System.out.println("ExitCode: " + manager.getExitCode());
        System.out.println("Features: " + manager.getFeatures());
        System.out.println("FinishTimestamp: " + manager.getFinishTimestamp());
        System.out.println("FinishTimestampMonotonic: " + manager.getFinishTimestampMonotonic());
        System.out.println("FirmwareTimestamp: " + manager.getFirmwareTimestamp());
        System.out.println("FirmwareTimestampMonotonic: " + manager.getFirmwareTimestampMonotonic());
        System.out.println("GeneratorsFinishTimestamp: " + manager.getGeneratorsFinishTimestamp());
        System.out.println("GeneratorsFinishTimestampMonotonic: " + manager.getGeneratorsFinishTimestampMonotonic());
        System.out.println("GeneratorsStartTimestamp: " + manager.getGeneratorsStartTimestamp());
        System.out.println("GeneratorsStartTimestampMonotonic: " + manager.getGeneratorsStartTimestampMonotonic());
        System.out.println("InitRDTimestamp: " + manager.getInitRDTimestamp() + " (" + Systemd.timestampToDate(manager.getInitRDTimestamp()) + ")");
        System.out.println("InitRDTimestampMonotonic: " + manager.getInitRDTimestampMonotonic());
        System.out.println("KernelTimestamp: " + manager.getKernelTimestamp() + " (" + Systemd.timestampToDate(manager.getKernelTimestamp()) + ")");
        System.out.println("KernelTimestampMonotonic: " + manager.getKernelTimestampMonotonic());
        System.out.println("LoaderTimestamp: " + manager.getLoaderTimestamp());
        System.out.println("LoaderTimestampMonotonic: " + manager.getLoaderTimestampMonotonic());
        System.out.println("LogLevel: " + manager.getLogLevel());
        System.out.println("LogTarget: " + manager.getLogTarget());
        System.out.println("NFailedJobs: " + manager.getNFailedJobs());
        System.out.println("NFailedUnits: " + manager.getNFailedUnits());
        System.out.println("NInstalledJobs: " + manager.getNInstalledJobs());
        System.out.println("NJobs: " + manager.getNJobs());
        System.out.println("NNames: " + manager.getNNames());
        System.out.println("Progress: " + manager.getProgress());
        System.out.println("RuntimeWatchdogUSec: " + manager.getRuntimeWatchdogUSec());
        System.out.println("SecurityFinishTimestamp: " + manager.getSecurityFinishTimestamp() + " (" + Systemd.timestampToDate(manager.getSecurityFinishTimestamp()) + ")");
        System.out.println("SecurityFinishTimestampMonotonic: " + manager.getSecurityFinishTimestampMonotonic());
        System.out.println("SecurityStartTimestamp: " + manager.getSecurityStartTimestamp() + " (" + Systemd.timestampToDate(manager.getSecurityStartTimestamp()) + ")");
        System.out.println("SecurityStartTimestampMonotonic: " + manager.getSecurityStartTimestampMonotonic());
        System.out.println("ShowStatus: " + manager.isShowStatus());
        System.out.println("SystemState: " + manager.getSystemState());
        System.out.println("Tainted: " + manager.getTainted());
        System.out.println("TimerSlackNSec: " + manager.getTimerSlackNSec());
        System.out.println("UnitPath: " + manager.getUnitPath());
        System.out.println("UnitsLoadFinishTimestamp: " + manager.getUnitsLoadFinishTimestamp());
        System.out.println("UnitsLoadFinishTimestampMonotonic: " + manager.getUnitsLoadFinishTimestampMonotonic());
        System.out.println("UnitsLoadStartTimestamp: " + manager.getUnitsLoadStartTimestamp());
        System.out.println("UnitsLoadStartTimestampMonotonic: " + manager.getUnitsLoadStartTimestampMonotonic());
        System.out.println("UserspaceTimestamp: " + manager.getUserspaceTimestamp() + " (" + Systemd.timestampToDate(manager.getUserspaceTimestamp()) + ")");
        System.out.println("UserspaceTimestampMonotonic: " + manager.getUserspaceTimestampMonotonic());
        System.out.println("Version: " + manager.getVersion());
        System.out.println("Virtualization: " + manager.getVirtualization());

        System.out.println();
        System.out.println("'Manager' properties (via 'Property' interface):");

        for (String propertyName : Manager.Property.getAllNames()) {
            Object value = manager.getProperties().getVariant(propertyName).getValue();

            System.out.println("manager::" + propertyName + ": " + value);
        }
    }

    public static void methods(final Manager manager) throws DBusException  {
//        System.out.println();
//
//        List<UnitFileType> unitFiles = manager.listUnitFiles();
//        Collections.sort(unitFiles);
//
//        for (UnitFileType unitFile : unitFiles) {
//            System.out.println(unitFile.getSummary());
//        }

//        System.out.println();
//
//        List<UnitType> units = manager.listUnits();
//        Collections.sort(units);
//
//        for (UnitType unit : units) {
//            System.out.println(unit.getSummary());
//
//            if (unit.getUnitName().endsWith(".service") && unit.getSubState().equals("running")) {
//                Service service = manager.getService(unit.getUnitName());
//                System.out.println(" Runtime statistics:");
//                System.out.println("  MainPID: " + service.getMainPID());
//            }
//
//            System.out.println();
//        }

        System.out.println();

        Service polkit = manager.getService("polkit");
        System.out.println("'polkit' properties:");
        System.out.println("Environment: " + polkit.getEnvironment());
        System.out.println("MainPID: " + polkit.getMainPID());
        System.out.println("Type: " + polkit.getType());

        System.out.println();

        Service cronie = manager.getService("cronie");
        System.out.println("'cronie' properties (unit interface):");
        System.out.println("ActiveEnterTimestamp: " + cronie.getActiveEnterTimestamp());
        System.out.println("ActiveEnterTimestampMonotonic: " + cronie.getActiveEnterTimestampMonotonic());
        System.out.println("ActiveExitTimestamp: " + cronie.getActiveExitTimestamp());
        System.out.println("ActiveExitTimestampMonotonic: " + cronie.getActiveExitTimestampMonotonic());
        System.out.println("ActiveState: " + cronie.getActiveState());
        System.out.println("After: " + cronie.getAfter());
        System.out.println("AllowIsolate: " + cronie.isAllowIsolate());
        System.out.println("AssertResult: " + cronie.isAssertResult());
        System.out.println("AssertTimestamp: " + cronie.getAssertTimestamp());
        System.out.println("AssertTimestampMonotonic: " + cronie.getAssertTimestampMonotonic());
        System.out.println("Asserts: " + cronie.getAsserts());
        System.out.println("Before: " + cronie.getBefore());
        System.out.println("BindsTo: " + cronie.getBindsTo());
        System.out.println("BoundBy: " + cronie.getBoundBy());
        System.out.println("CanIsolate: " + cronie.isCanIsolate());
        System.out.println("CanReload: " + cronie.isCanReload());
        System.out.println("CanStart: " + cronie.isCanStart());
        System.out.println("CanStop: " + cronie.isCanStop());
        System.out.println("ConditionResult: " + cronie.getConditionResult());
        System.out.println("ConditionTimestamp: " + cronie.getConditionTimestamp());
        System.out.println("ConditionTimestampMonotonic: " + cronie.getConditionTimestampMonotonic());
        System.out.println("Conditions: " + cronie.getConditions());
        System.out.println("ConflictedBy: " + cronie.getConflictedBy());
        System.out.println("Conflicts: " + cronie.getConflicts());
        System.out.println("ConsistsOf: " + cronie.getConsistsOf());
        System.out.println("DefaultDependencies: " + cronie.isDefaultDependencies());
        System.out.println("Description: " + cronie.getDescription());
        System.out.println("Documentation: " + cronie.getDocumentation());
        System.out.println("DropInPaths: " + cronie.getDropInPaths());
        System.out.println("Following: " + cronie.getFollowing());
        System.out.println("FragmentPath: " + cronie.getFragmentPath());
        System.out.println("Id: " + cronie.getId());
        System.out.println("IgnoreOnIsolate: " + cronie.isIgnoreOnIsolate());
        System.out.println("InactiveEnterTimestamp: " + cronie.getInactiveEnterTimestamp());
        System.out.println("InactiveEnterTimestampMonotonic: " + cronie.getInactiveEnterTimestampMonotonic());
        System.out.println("InactiveExitTimestamp: " + cronie.getInactiveExitTimestamp());
        System.out.println("InactiveExitTimestampMonotonic: " + cronie.getInactiveExitTimestampMonotonic());
        System.out.println("Job: " + cronie.getJob());
        System.out.println("JobTimeoutAction: " + cronie.getJobTimeoutAction());
        System.out.println("JobTimeoutRebootArgument: " + cronie.getJobTimeoutRebootArgument());
        System.out.println("JobTimeoutUSec: " + cronie.getJobTimeoutUSec());
        System.out.println("JoinsNamespaceOf: " + cronie.getJoinsNamespaceOf());
        System.out.println("LoadError: " + cronie.getLoadError());
        System.out.println("LoadState: " + cronie.getLoadState());
        System.out.println("Names: " + cronie.getNames());
        System.out.println("NeedDaemonReload: " + cronie.isNeedDaemonReload());
        System.out.println("OnFailure: " + cronie.getOnFailure());
        System.out.println("OnFailureJobMode: " + cronie.getOnFailureJobMode());
        System.out.println("PartOf: " + cronie.getPartOf());
        System.out.println("PropagatesReloadTo: " + cronie.getPropagatesReloadTo());
        System.out.println("RefuseManualStart: " + cronie.isRefuseManualStart());
        System.out.println("RefuseManualStop: " + cronie.isRefuseManualStop());
        System.out.println("ReloadPropagatedFrom: " + cronie.getReloadPropagatedFrom());
        System.out.println("RequiredBy: " + cronie.getRequiredBy());
        System.out.println("Requires: " + cronie.getRequires());
        System.out.println("RequiresMountsFor: " + cronie.getRequiresMountsFor());
        System.out.println("Requisite: " + cronie.getRequisite());
        System.out.println("RequisiteOf: " + cronie.getRequisiteOf());
        System.out.println("SourcePath: " + cronie.getSourcePath());
        System.out.println("StopWhenUnneeded: " + cronie.isStopWhenUnneeded());
        System.out.println("SubState: " + cronie.getSubState());
        System.out.println("Transient: " + cronie.isTransient());
        System.out.println("TriggeredBy: " + cronie.getTriggeredBy());
        System.out.println("Triggers: " + cronie.getTriggers());

        System.out.println("WantedBy: " + cronie.getWantedBy());
        System.out.println("Wants: " + cronie.getWants());

        System.out.println();

        System.out.println("'cronie' properties (service interface):");
        System.out.println("AppArmorProfile: " + cronie.getAppArmorProfile());
        System.out.println("BlockIOAccounting: " + cronie.isBlockIOAccounting());
        System.out.println("BlockIODeviceWeight: " + cronie.getBlockIODeviceWeight());
        System.out.println("BlockIOReadBandwidth: " + cronie.getBlockIOReadBandwidth());
        System.out.println("BlockIOWeight: " + cronie.getBlockIOWeight());
        System.out.println("BlockIOWriteBandwidth: " + cronie.getBlockIOWriteBandwidth());
        System.out.println("BusName: " + cronie.getBusName());
        System.out.println("CPUAccounting: " + cronie.isCPUAccounting());
        System.out.println("CPUAffinity: " + Arrays.toString(cronie.getCPUAffinity()));
        System.out.println("CPUQuotaPerSecUSec: " + cronie.getCPUQuotaPerSecUSec());
        System.out.println("CPUSchedulingPolicy: " + cronie.getCPUSchedulingPolicy());
        System.out.println("CPUSchedulingPriority: " + cronie.getCPUSchedulingPriority());
        System.out.println("CPUSchedulingResetOnFork: " + cronie.isCPUSchedulingResetOnFork());
        System.out.println("CPUShares: " + cronie.getCPUShares());
        System.out.println("CPUUsageNSec: " + cronie.getCPUUsageNSec());
        System.out.println("Capabilities: " + cronie.getCapabilities());
        System.out.println("CapabilityBoundingSet: " + cronie.getCapabilityBoundingSet());
        System.out.println("ControlGroup: " + cronie.getControlGroup());
        System.out.println("ControlPID: " + cronie.getControlPID());
        System.out.println("Delegate: " + cronie.isDelegate());
        System.out.println("DeviceAllow: " + cronie.getDeviceAllow());
        System.out.println("DevicePolicy: " + cronie.getDevicePolicy());
        System.out.println("Environment: " + cronie.getEnvironment());
        System.out.println("EnvironmentFiles: " + cronie.getEnvironmentFiles());
        System.out.println("ExecMainCode: " + cronie.getExecMainCode());
        System.out.println("ExecMainExitTimestamp: " + cronie.getExecMainExitTimestamp());
        System.out.println("ExecMainExitTimestampMonotonic: " + cronie.getExecMainExitTimestampMonotonic());
        System.out.println("ExecMainPID: " + cronie.getExecMainPID());
        System.out.println("ExecMainStartTimestamp: " + cronie.getExecMainStartTimestamp());
        System.out.println("ExecMainStartTimestampMonotonic: " + cronie.getExecMainStartTimestampMonotonic());
        System.out.println("ExecMainStatus: " + cronie.getExecMainStatus());
        System.out.println("ExecReload: " + cronie.getExecReload());
        System.out.println("ExecStart: " + cronie.getExecStart());
        System.out.println("ExecStartPost: " + cronie.getExecStartPost());
        System.out.println("ExecStartPre: " + cronie.getExecStartPre());
        System.out.println("ExecStop: " + cronie.getExecStop());
        System.out.println("ExecStopPost: " + cronie.getExecStopPost());
        System.out.println("FailureAction: " + cronie.getFailureAction());
        System.out.println("FileDescriptorStoreMax: " + cronie.getFileDescriptorStoreMax());
        System.out.println("Group: " + cronie.getGroup());
        System.out.println("GuessMainPID: " + cronie.isGuessMainPID());
        System.out.println("IOScheduling: " + cronie.getIOScheduling());
        System.out.println("IgnoreSIGPIPE: " + cronie.isIgnoreSIGPIPE());
        System.out.println("InaccessibleDirectories: " + cronie.getInaccessibleDirectories());
        System.out.println("KillMode: " + cronie.getKillMode());
        System.out.println("KillSignal: " + cronie.getKillSignal());
        System.out.println("LimitAS: " + cronie.getLimitAS());
        System.out.println("LimitCORE: " + cronie.getLimitCORE());
        System.out.println("LimitCPU: " + cronie.getLimitCPU());
        System.out.println("LimitDATA: " + cronie.getLimitDATA());
        System.out.println("LimitFSIZE: " + cronie.getLimitFSIZE());
        System.out.println("LimitLOCKS: " + cronie.getLimitLOCKS());
        System.out.println("LimitMEMLOCK: " + cronie.getLimitMEMLOCK());
        System.out.println("LimitMSGQUEUE: " + cronie.getLimitMSGQUEUE());
        System.out.println("LimitNICE: " + cronie.getLimitNICE());
        System.out.println("LimitNOFILE: " + cronie.getLimitNOFILE());
        System.out.println("LimitNPROC: " + cronie.getLimitNPROC());
        System.out.println("LimitRSS: " + cronie.getLimitRSS());
        System.out.println("LimitRTPRIO: " + cronie.getLimitRTPRIO());
        System.out.println("LimitRTTIME: " + cronie.getLimitRTTIME());
        System.out.println("LimitSIGPENDING: " + cronie.getLimitSIGPENDING());
        System.out.println("LimitSTACK: " + cronie.getLimitSTACK());
        System.out.println("MainPID: " + cronie.getMainPID());
        System.out.println("MemoryAccounting: " + cronie.isMemoryAccounting());
        System.out.println("MemoryCurrent: " + cronie.getMemoryCurrent());
        System.out.println("MemoryLimit: " + cronie.getMemoryLimit());
        System.out.println("MountFlags: " + cronie.getMountFlags());
        System.out.println("NFileDescriptorStore: " + cronie.getNFileDescriptorStore());
        System.out.println("Nice: " + cronie.getNice());
        System.out.println("NoNewPrivileges: " + cronie.isNoNewPrivileges());
        System.out.println("NonBlocking: " + cronie.isNonBlocking());
        System.out.println("NotifyAccess: " + cronie.getNotifyAccess());
        System.out.println("OOMScoreAdjust: " + cronie.getOOMScoreAdjust());
        System.out.println("PAMName: " + cronie.getPAMName());
        System.out.println("PIDFile: " + cronie.getPIDFile());
        System.out.println("PassEnvironment: " + cronie.getPassEnvironment());
        System.out.println("PermissionsStartOnly: " + cronie.isPermissionsStartOnly());
        System.out.println("Personality: " + cronie.getPersonality());
        System.out.println("PrivateDevices: " + cronie.isPrivateDevices());
        System.out.println("PrivateNetwork: " + cronie.isPrivateNetwork());
        System.out.println("PrivateTmp: " + cronie.isPrivateTmp());
        System.out.println("ProtectHome: " + cronie.getProtectHome());
        System.out.println("ProtectSystem: " + cronie.getProtectSystem());
        System.out.println("ReadOnlyDirectories: " + cronie.getReadOnlyDirectories());
        System.out.println("ReadWriteDirectories: " + cronie.getReadWriteDirectories());
        System.out.println("RebootArgument: " + cronie.getRebootArgument());
        System.out.println("RemainAfterExit: " + cronie.isRemainAfterExit());
        System.out.println("Restart: " + cronie.getRestart());
        System.out.println("RestartUSec: " + cronie.getRestartUSec());
        System.out.println("RestrictAddressFamilies: " + cronie.getRestrictAddressFamilies());
        System.out.println("Result: " + cronie.getResult());
        System.out.println("RootDirectory: " + cronie.getRootDirectory());
        System.out.println("RootDirectoryStartOnly: " + cronie.isRootDirectoryStartOnly());
        System.out.println("RuntimeDirectory: " + cronie.getRuntimeDirectory());
        System.out.println("RuntimeDirectoryMode: " + cronie.getRuntimeDirectoryMode());
        System.out.println("SELinuxContext: " + cronie.getSELinuxContext());
        System.out.println("SameProcessGroup: " + cronie.isSameProcessGroup());
        System.out.println("SecureBits: " + cronie.getSecureBits());
        System.out.println("SendSIGHUP: " + cronie.isSendSIGHUP());
        System.out.println("SendSIGKILL: " + cronie.isSendSIGKILL());
        System.out.println("Slice: " + cronie.getSlice());
        System.out.println("SmackProcessLabel: " + cronie.getSmackProcessLabel());
        System.out.println("StandardError: " + cronie.getStandardError());
        System.out.println("StandardInput: " + cronie.getStandardInput());
        System.out.println("StandardOutput: " + cronie.getStandardOutput());
        System.out.println("StartLimitAction: " + cronie.getStartLimitAction());
        System.out.println("StartLimitBurst: " + cronie.getStartLimitBurst());
        System.out.println("StartLimitInterval: " + cronie.getStartLimitInterval());
        System.out.println("StartupBlockIOWeight: " + cronie.getStartupBlockIOWeight());
        System.out.println("StartupCPUShares: " + cronie.getStartupCPUShares());
        System.out.println("StatusErrno: " + cronie.getStatusErrno());
        System.out.println("StatusText: " + cronie.getStatusText());
        System.out.println("SupplementaryGroups: " + cronie.getSupplementaryGroups());
        System.out.println("SyslogFacility: " + cronie.getSyslogFacility());
        System.out.println("SyslogIdentifier: " + cronie.getSyslogIdentifier());
        System.out.println("SyslogLevel: " + cronie.getSyslogLevel());
        System.out.println("SyslogLevelPrefix: " + cronie.isSyslogLevelPrefix());
        System.out.println("SyslogPriority: " + cronie.getSyslogPriority());
        System.out.println("SystemCallArchitectures: " + cronie.getSystemCallArchitectures());
        System.out.println("SystemCallErrorNumber: " + cronie.getSystemCallErrorNumber());
        System.out.println("TTYPath: " + cronie.getTTYPath());
        System.out.println("TTYReset: " + cronie.isTTYReset());
        System.out.println("TTYVHangup: " + cronie.isTTYVHangup());
        System.out.println("TTYVTDisallocate: " + cronie.isTTYVTDisallocate());
        System.out.println("TasksAccounting: " + cronie.isTasksAccounting());
        System.out.println("TasksCurrent: " + cronie.getTasksCurrent());
        System.out.println("TasksMax: " + cronie.getTasksMax());
        System.out.println("TimeoutStartUSec: " + cronie.getTimeoutStartUSec());
        System.out.println("TimeoutStopUSec: " + cronie.getTimeoutStopUSec());
        System.out.println("TimerSlackNSec: " + cronie.getTimerSlackNSec());
        System.out.println("Type: " + cronie.getType());
        System.out.println("UMask: " + cronie.getUMask());
        System.out.println("USBFunctionDescriptors: " + cronie.getUSBFunctionDescriptors());
        System.out.println("USBFunctionStrings: " + cronie.getUSBFunctionStrings());
        System.out.println("User: " + cronie.getUser());
        System.out.println("UtmpIdentifier: " + cronie.getUtmpIdentifier());
        System.out.println("UtmpMode: " + cronie.getUtmpMode());
        System.out.println("WatchdogTimestamp: " + cronie.getWatchdogTimestamp());
        System.out.println("WatchdogTimestampMonotonic: " + cronie.getWatchdogTimestampMonotonic());
        System.out.println("WatchdogUSec: " + cronie.getWatchdogUSec());
        System.out.println("WorkingDirectory: " + cronie.getWorkingDirectory());

        System.out.println();

        System.out.println("'cronie' methods (unit interface, work only with privileges):");
//        System.out.println("Start: " + cronie.start(Mode.FAIL));
//        System.out.println("Stop: " + cronie.stop(Mode.FAIL));

        System.out.println();

        Target basic = manager.getTarget("basic");
        System.out.println("'basic' properties (target interface, none present for now):");
        System.out.println("ObjectPath: " + basic.getObjectPath());

        System.out.println();

        Device sda1 = manager.getDevice("dev-sda1");
        System.out.println("'sda1' properties (device interface):");
        System.out.println("SysFSPath: " + sda1.getSysFSPath());

        System.out.println();

        Service autofs = manager.getService("autofs");
        System.out.println("'autofs' properties (service interface):");
        System.out.println("EnvironmentFiles: " + autofs.getEnvironmentFiles());

        System.out.println();
    }

    public static void main(String[] args) {
        try {
            Systemd systemd = Systemd.get(InstanceType.USER);

//            introspect(systemd.getManager());
            properties(systemd.getManager());
            methods(systemd.getManager());

//            for (UnitType ut : systemd.getManager().listUnits()) {
//                Unit unit = systemd.getManager().getUnit(ut.getUnitName());
//
//                if (unit instanceof Service) {
//                    System.out.format("%s: %s\n", unit, ((Service) unit).getRestrictAddressFamilies());
//                    System.out.format("%s: %s\n", unit, ((Service) unit).getSystemCallFilter());
//                    System.out.format("%s: %s\n", unit, ((Service) unit).getSmackProcessLabel());
//                }
//            }

//            System.out.println(manager.dump());

//            System.out.println(systemd.getManager().dump());

//            Manager manager = systemd.getManager();

//            manager.stopUnit("postgresql.service", Mode.REPLACE.getValue());
//            manager.startUnit("postgresql.service", Mode.REPLACE.getValue());

//            Service pgsql = manager.getService("postgresql");
//            pgsql.stop(Mode.REPLACE);
//            pgsql.start(Mode.REPLACE);
        }
        catch (final Exception e) {
            e.printStackTrace();
        }
        finally {
            try {
                Systemd.disconnect();
            }
            catch (DBusException e) {
                e.printStackTrace();
            }
        }
    }

}
