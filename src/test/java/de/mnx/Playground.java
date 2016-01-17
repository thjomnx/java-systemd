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

import java.util.List;

import org.freedesktop.dbus.exceptions.DBusException;
import org.freedesktop.dbus.exceptions.DBusExecutionException;

import de.mnx.java.systemd.Systemd;
import de.mnx.java.systemd.adapters.Manager;
import de.mnx.java.systemd.adapters.Service;
import de.mnx.java.systemd.types.UnitFileStatus;

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
//        Service cronie = manager.getService("cronie");
//        System.out.println("Object path: " + cronie.getObjectPath());
//        System.out.println("MainPDI: " + cronie.getProcessId());

        List<UnitFileStatus> list = manager.listUnitFiles();

        for (UnitFileStatus item : list) {
            System.out.println(item.getPath() + " : " + item.getStatus().toString().toLowerCase());
        }

        for (UnitFileStatus item : list) {
            if (item.getStatus().equals(UnitFileStatus.Status.ENABLED)) {
                String path = item.getPath().getFileName().toString();

                if (path.endsWith(".service")) {
                    String serviceName = path.substring(0, path.indexOf(".service"));

                    try {
                        Service service = manager.getService(serviceName);
                        System.out.println("Object path: " + service.getObjectPath());
                        System.out.println("MainPDI: " + service.getProcessId());
                    }
                    catch (final DBusExecutionException e) {
                        System.err.println(e.getMessage());
                    }
                }
            }
        }

//        System.out.println(manager.dump());
    }

    public static void main(String[] args) {
        Systemd systemd = new Systemd();

        try {
            systemd.connect();

            introspect(systemd);
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
