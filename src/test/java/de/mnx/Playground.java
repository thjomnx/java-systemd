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
import de.mnx.java.systemd.types.UnitFileStatus;
import de.mnx.java.systemd.types.UnitStatus;

public class Playground {

    private Playground() {
        // Do nothing (static class)
    }

    public static void introspect(final Systemd systemd) throws DBusException {
        System.out.println(systemd.introspect());
    }

    public static void properties(final Manager manager) throws DBusException  {
        System.out.println("Version: " + manager.getVersion());
        System.out.println("Architecture: " + manager.getArchitecture());

        System.out.println("Environment: " + manager.getEnvironment());

        System.out.println("Status: " + manager.getStatus());
        System.out.println("SystemState: " + manager.getSystemState());
    }

    public static void methods(final Manager manager) throws DBusException  {
        for (UnitFileStatus item : manager.listUnitFiles()) {
            System.out.println(item.getPath() + " : " + item.getStatus().toString().toLowerCase());
        }

        for (UnitStatus unit : manager.listUnits()) {
            System.out.println(unit.getUnitName() + ":");
            System.out.println("  Description: " + unit.getUnitDescription());
            System.out.println("  LoadState: " + unit.getLoadState());
            System.out.println("  ActiveState: " + unit.getActiveState());
            System.out.println("  SubState: " + unit.getSubState());
            System.out.println("  FollowingUnit: " + unit.getFollowingUnit());
            System.out.println("  ObjectPath: " + unit.getUnitObjectPath());
            System.out.println("  JobID: " + unit.getJobId());
            System.out.println("  JobType: " + unit.getJobType());
            System.out.println("  JobObjectPath: " + unit.getJobObjectPath());

            if (unit.getUnitName().endsWith(".service") && unit.getActiveState().equals("active") && unit.getSubState().equals("running")) {
                Service service = manager.getService(unit.getUnitName());
                System.out.println("  Runtime statistics:");
                System.out.println("    MainPID: " + service.getProcessId());
            }

            System.out.println();
        }

//        System.out.println(manager.dump());
    }

    public static void main(String[] args) {
        Systemd systemd = new Systemd();

        try {
            systemd.connect();

//            introspect(systemd);
//            properties(systemd.getManager());
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
