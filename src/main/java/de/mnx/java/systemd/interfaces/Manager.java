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

import static de.mnx.java.systemd.Systemd.SYSTEMD_MANAGER_NAME;

import org.freedesktop.dbus.DBusInterface;
import org.freedesktop.dbus.DBusInterfaceName;
import org.freedesktop.dbus.DBusMemberName;

import de.mnx.java.systemd.types.UnitFileStatus;

@DBusInterfaceName(value = SYSTEMD_MANAGER_NAME)
public interface Manager extends DBusInterface {

    @DBusMemberName(value = "Dump")
    String dump();

    @DBusMemberName(value = "ListUnitFiles")
    UnitFileStatus[] listUnitFiles();

}
