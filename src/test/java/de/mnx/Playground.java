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
import org.freedesktop.dbus.exceptions.DBusExecutionException;

import de.mnx.java.systemd.Manager;
import de.mnx.java.systemd.Systemd;
import de.mnx.java.systemd.Unit;

public class Playground {

    public Playground() {
        // Do nothing
    }

    public void introspect() throws DBusException {
        System.out.println(Systemd.bus().introspect());
    }

    public void properties() throws DBusException  {
        System.out.println(Systemd.bus().getVersion());
        System.out.println(Systemd.bus().getArchitecture());

        System.out.println(Systemd.bus().getEnvironment());

        System.out.println(Systemd.bus().getStatus());
        System.out.println(Systemd.bus().getSystemState());
    }

    public void methods() throws DBusException  {
        Manager manager = Systemd.bus().manager();

        Unit unit = manager.getUnit("cronie.service");
        System.out.println(unit.getObjectPath());

//        List<UnitFileStatus> list = manager.listUnitFiles();
//
//        for (UnitFileStatus item : list) {
//            System.out.println(item.getPath() + " : " + item.getStatus().toString().toLowerCase());
//        }

//        System.out.println(manager.dump());
    }

    public static void main(String[] args) {
        Playground playground = new Playground();

        try {
//            playground.introspect();
            playground.properties();
            playground.methods();
        }
        catch (final DBusException | DBusExecutionException e) {
            e.printStackTrace();
        }
        finally {
            System.exit(0);
        }
    }

}
