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

package org.systemd.interfaces;

import java.util.List;

import org.freedesktop.dbus.DBusInterface;
import org.freedesktop.dbus.DBusInterfaceName;
import org.freedesktop.dbus.DBusMemberName;
import org.freedesktop.dbus.DBusSignal;
import org.freedesktop.dbus.exceptions.DBusException;
import org.systemd.types.UnitFileType;
import org.systemd.types.UnitType;

@DBusInterfaceName(value = org.systemd.Manager.SERVICE_NAME)
public interface ManagerInterface extends DBusInterface {

    @DBusMemberName(value = "ListUnitFiles")
    List<UnitFileType> listUnitFiles();

    @DBusMemberName(value = "ListUnits")
    List<UnitType> listUnits();

    @DBusMemberName(value = "GetUnit")
    DBusInterface getUnit(final String name);

    @DBusMemberName(value = "Dump")
    String dump();

    public class Reloading extends DBusSignal {

        // TODO Find meaning of boolean field
        public Reloading(final String objectPath, final boolean field1) throws DBusException {
            super(objectPath, field1);
        }

    }

    public class UnitFilesChanged extends DBusSignal {

        public UnitFilesChanged(final String objectPath) throws DBusException {
            super(objectPath);
        }

    }

}
