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
import de.mnx.java.systemd.types.UnitFileStatus;

public class DBusTesting {

    public static void main(String[] args) {
        try {
            System.out.println(Systemd.bus().getVersion());
            System.out.println(Systemd.bus().getArchitecture());

            UnitFileStatus[] unitFiles = Systemd.bus().listUnitFiles();

            for (UnitFileStatus unitFile : unitFiles) {
                System.out.println(unitFile.getFile() + " :: " + unitFile.getStatus());
            }
        }
        catch (final DBusException e) {
            e.printStackTrace();
        }

        System.exit(0);
    }

}
