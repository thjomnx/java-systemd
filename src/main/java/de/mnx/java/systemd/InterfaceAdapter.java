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

import org.freedesktop.dbus.DBusInterface;

public abstract class InterfaceAdapter implements DBusInterface {

    private final DBusInterface iface;

    protected InterfaceAdapter(final DBusInterface iface) {
        this.iface = iface;
    }

    @Override
    public String getObjectPath() {
        return iface.getObjectPath();
    }

    @Override
    public boolean isRemote() {
        return iface.isRemote();
    }

    protected DBusInterface getInterface() {
        return iface;
    }

}
