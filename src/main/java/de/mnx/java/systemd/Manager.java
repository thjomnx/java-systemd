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

package de.mnx.java.systemd;

import java.util.List;

import org.freedesktop.dbus.DBusInterface;

import de.mnx.java.systemd.interfaces.ManagerInterface;
import de.mnx.java.systemd.types.UnitFileStatus;

public class Manager extends InterfaceAdapter {

    Manager(final ManagerInterface iface) {
        super(iface);
    }

    @Override
    public ManagerInterface getInterface() {
        return (ManagerInterface) super.getInterface();
    }

    public List<UnitFileStatus> listUnitFiles() {
        return getInterface().listUnitFiles();
    }

    public Unit getUnit(final String name) {
        DBusInterface iface = getInterface().getUnit(name);

        return new Unit(iface);
    }

    public String dump() {
        return getInterface().dump();
    }

}
