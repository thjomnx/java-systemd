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

        System.out.println("ConfirmSpawn: " + manager.getConfirmSpawn());
        System.out.println("ControlGroup: " + manager.getControlGroup());
        System.out.println("DefaultBlockIOAccounting: " + manager.getDefaultBlockIOAccounting());
        System.out.println("DefaultCPUAccounting: " + manager.getDefaultCPUAccounting());
        System.out.println("DefaultStandardError: " + manager.getDefaultStandardError());
        System.out.println("DefaultStandardOutput: " + manager.getDefaultStandardOutput());
        System.out.println("Environment: " + manager.getEnvironment());
        System.out.println("ExitCode: " + manager.getExitCode());
        System.out.println("Features: " + manager.getFeatures());
        System.out.println("InitRDTimestamp: " + manager.getInitRDTimestamp() + " (" + Systemd.timestampToDate(manager.getInitRDTimestamp()) + ")");
        System.out.println("InitRDTimestampMonotonic: " + manager.getInitRDTimestampMonotonic());
        System.out.println("KernelTimestamp: " + manager.getKernelTimestamp() + " (" + Systemd.timestampToDate(manager.getKernelTimestamp()) + ")");
        System.out.println("KernelTimestampMonotonic: " + manager.getKernelTimestampMonotonic());
        System.out.println("LogLevel: " + manager.getLogLevel());
        System.out.println("LogTarget: " + manager.getLogTarget());
        System.out.println("NFailedJobs: " + manager.getNumFailedJobs());
        System.out.println("NFailedUnits: " + manager.getNumFailedUnits());
        System.out.println("NInstalledJobs: " + manager.getNumInstalledJobs());
        System.out.println("NJobs: " + manager.getNumJobs());
        System.out.println("Progress: " + manager.getProgress());
        System.out.println("SecurityFinishTimestamp: " + manager.getSecurityFinishTimestamp() + " (" + Systemd.timestampToDate(manager.getSecurityFinishTimestamp()) + ")");
        System.out.println("SecurityFinishTimestampMonotonic: " + manager.getSecurityFinishTimestampMonotonic());
        System.out.println("SecurityStartTimestamp: " + manager.getSecurityStartTimestamp() + " (" + Systemd.timestampToDate(manager.getSecurityStartTimestamp()) + ")");
        System.out.println("SecurityStartTimestampMonotonic: " + manager.getSecurityStartTimestampMonotonic());
        System.out.println("Status: " + manager.getStatus());
        System.out.println("SystemState: " + manager.getSystemState());
        System.out.println("Tainted: " + manager.getTainted());
        System.out.println("UnitPath: " + manager.getUnitPath());
        System.out.println("UserspaceTimestamp: " + manager.getUserspaceTimestamp() + " (" + Systemd.timestampToDate(manager.getUserspaceTimestamp()) + ")");
        System.out.println("UserspaceTimestampMonotonic: " + manager.getUserspaceTimestampMonotonic());
        System.out.println("Version: " + manager.getVersion());
        System.out.println("Virtualization: " + manager.getVirtualization());
    }

    public static void methods(final Manager manager) throws DBusException  {
//        for (UnitFileType unitFile : manager.listUnitFiles()) {
//            System.out.println(unitFile.getSummary());
//        }

//        for (UnitType unit : manager.listUnits()) {
//            System.out.println(unit.getSummary());
//
//            if (unit.getUnitName().endsWith(".service") && unit.getSubState().equals("running")) {
//                Service service = manager.getService(unit.getUnitName());
//                System.out.println(" Runtime statistics:");
//                System.out.println("  MainPID: " + service.getProcessId());
//            }
//
//            System.out.println();
//        }

        Service polkit = manager.getService("polkit");
        System.out.println("\n'polkit' properties:");
        System.out.println("MainPID: " + polkit.getMainPID());
        System.out.println("SELinuxContext: " + polkit.getSELinuxContext());
        System.out.println("StandardError: " + polkit.getStandardError());
        System.out.println("StandardInput: " + polkit.getStandardInput());
        System.out.println("StandardOutput: " + polkit.getStandardOutput());

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
