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

import org.freedesktop.dbus.DBusConnection;
import org.freedesktop.dbus.DBusInterface;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class InterfaceAdapter implements DBusInterface {

    protected final Logger log = LoggerFactory.getLogger(getClass());

    protected final DBusConnection dbus;

    protected InterfaceAdapter(final DBusConnection dbus) {
        this.dbus = dbus;
    }

    @Override
    public String getObjectPath() {
        return getInterface().getObjectPath();
    }

    @Override
    public boolean isRemote() {
        return getInterface().isRemote();
    }

    protected abstract DBusInterface getInterface();

}
