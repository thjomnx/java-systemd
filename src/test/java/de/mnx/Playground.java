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

package de.mnx;

import org.freedesktop.dbus.exceptions.DBusException;

import de.mnx.java.systemd.Systemd;
import de.mnx.java.systemd.adapters.Manager;
import de.mnx.java.systemd.adapters.Service;

public class Playground {

    private Playground() {
        // Do nothing (static class)
    }

    public static void introspect(final Systemd systemd) throws DBusException {
        System.out.println(systemd.introspect());
    }

    public static void properties(final Manager manager) throws DBusException  {
        System.out.println("Architecture: " + manager.getArchitecture());

        System.out.println("ConfirmSpawn: " + manager.isConfirmSpawn());
        System.out.println("ControlGroup: " + manager.getControlGroup());
        System.out.println("DefaultBlockIOAccounting: " + manager.isDefaultBlockIOAccounting());
        System.out.println("DefaultCPUAccounting: " + manager.isDefaultCPUAccounting());
        System.out.println("DefaultMemoryAccounting: " + manager.isDefaultMemoryAccounting());
        System.out.println("DefaultStandardError: " + manager.getDefaultStandardError());
        System.out.println("DefaultStandardOutput: " + manager.getDefaultStandardOutput());
        System.out.println("DefaultTasksAccounting: " + manager.isDefaultTasksAccounting());
        System.out.println("Environment: " + manager.getEnvironment());
        System.out.println("ExitCode: " + manager.getExitCode());
        System.out.println("Features: " + manager.getFeatures());
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
    }

    public static void methods(final Manager manager) throws DBusException  {
//        System.out.println();
//
//        for (UnitFileType unitFile : manager.listUnitFiles()) {
//            System.out.println(unitFile.getSummary());
//        }

//        System.out.println();
//
//        for (UnitType unit : manager.listUnits()) {
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
        System.out.println("WantedBy: " + cronie.getWantedBy());
        System.out.println("LoadState: " + cronie.getLoadState());
        System.out.println("ActiveState: " + cronie.getActiveState());

        System.out.println();

        System.out.println("'cronie' properties:");
        System.out.println("ControlGroup: " + cronie.getControlGroup());
        System.out.println("Environment: " + cronie.getEnvironment());
        System.out.println("ExecReload: " + cronie.getExecReload());
        System.out.println("ExecStart: " + cronie.getExecStart());
        System.out.println("ExecStartPost: " + cronie.getExecStartPost());
        System.out.println("ExecStartPre: " + cronie.getExecStartPre());
        System.out.println("ExecStop: " + cronie.getExecStop());
        System.out.println("ExecStopPost: " + cronie.getExecStopPost());
        System.out.println("MainPID: " + cronie.getMainPID());
        System.out.println("Result: " + cronie.getResult());
        System.out.println("SELinuxContext: " + cronie.getSELinuxContext());
        System.out.println("StandardError: " + cronie.getStandardError());
        System.out.println("StandardInput: " + cronie.getStandardInput());
        System.out.println("StandardOutput: " + cronie.getStandardOutput());
        System.out.println("Type: " + cronie.getType());

        System.out.println();

//        System.out.println(manager.dump());
    }

    public static void main(String[] args) {
        Systemd systemd = new Systemd();

        try {
            systemd.connect();

//            introspect(systemd);
            properties(systemd.getManager());
            methods(systemd.getManager());
        }
        catch (final Exception e) {
            e.printStackTrace();
        }
        finally {
            systemd.disconnect();

            System.exit(0);
        }
    }

}
