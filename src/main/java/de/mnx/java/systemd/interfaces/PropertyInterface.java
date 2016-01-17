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

import org.freedesktop.dbus.DBusInterface;
import org.freedesktop.dbus.DBusInterfaceName;
import org.freedesktop.dbus.DBusMemberName;
import org.freedesktop.dbus.Variant;

@DBusInterfaceName(value = de.mnx.java.systemd.adapters.InterfaceAdapter.Properties.SERVICE_NAME)
public interface PropertyInterface extends DBusInterface {

    @DBusMemberName(value = "Get")
    Variant<?> getProperty(String iface, String property);

}
