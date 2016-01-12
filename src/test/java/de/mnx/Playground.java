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

import java.util.Vector;

import org.freedesktop.dbus.exceptions.DBusException;

import de.mnx.java.systemd.Systemd;

public class Playground {

    public static void main(String[] args) {
//        try {
//            System.out.println(Systemd.bus().introspect());
//        }
//        catch (final DBusException e) {
//            e.printStackTrace();
//        }

//        System.exit(0);

        try {
            System.out.println(Systemd.bus().getVersion());
            System.out.println(Systemd.bus().getArchitecture());

            Vector<String> environment = Systemd.bus().getEnvironment();

            for (String var : environment) {
                System.out.println(var);
            }

            System.out.println(Systemd.bus().getStatus());
            System.out.println(Systemd.bus().getSystemState());
        }
        catch (final DBusException e) {
            e.printStackTrace();
        }

//        System.exit(0);
//
//        try {
//            Manager manager = Systemd.bus().manager();
//
//            UnitFileStatus[] unitFiles = manager.listUnitFiles();
//
//            for (UnitFileStatus unitFile : unitFiles) {
//                System.out.println(unitFile.getFile() + " :: " + unitFile.getStatus());
//            }
//        }
//        catch (final DBusException e) {
//            e.printStackTrace();
//        }
//
//        System.exit(0);
//
//        try {
//            Manager manager = Systemd.bus().manager();
//
//            System.out.println(manager.dump());
//        }
//        catch (final DBusException e) {
//            e.printStackTrace();
//        }
//
//        System.exit(0);
    }

}
