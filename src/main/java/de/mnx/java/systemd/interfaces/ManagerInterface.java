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

package de.mnx.java.systemd.interfaces;

import java.util.List;

import org.freedesktop.dbus.DBusInterface;
import org.freedesktop.dbus.DBusInterfaceName;
import org.freedesktop.dbus.DBusMemberName;

import de.mnx.java.systemd.types.UnitFileStatus;
import de.mnx.java.systemd.types.UnitStatus;

@DBusInterfaceName(value = de.mnx.java.systemd.adapters.Manager.SERVICE_NAME)
public interface ManagerInterface extends DBusInterface {

    @DBusMemberName(value = "ListUnitFiles")
    List<UnitFileStatus> listUnitFiles();

    @DBusMemberName(value = "ListUnits")
    List<UnitStatus> listUnits();

    @DBusMemberName(value = "GetUnit")
    DBusInterface getUnit(final String name);

    @DBusMemberName(value = "Dump")
    String dump();

}
