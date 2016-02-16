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

import org.freedesktop.DBus.Introspectable;
import org.freedesktop.dbus.DBusConnection;
import org.freedesktop.dbus.exceptions.DBusException;

import de.mnx.java.systemd.interfaces.SliceInterface;

public class Slice extends Unit {

    public static final String SERVICE_NAME = Systemd.SERVICE_NAME + ".Slice";
    public static final String UNIT_SUFFIX = ".slice";

    private final Properties properties;

    private Slice(final DBusConnection dbus, final SliceInterface iface) throws DBusException {
        super(dbus, iface);

        this.properties = Properties.create(dbus, iface.getObjectPath(), SERVICE_NAME);
    }

    static Slice create(final DBusConnection dbus, final String objectPath) throws DBusException {
        SliceInterface iface = dbus.getRemoteObject(Systemd.SERVICE_NAME, objectPath, SliceInterface.class);

        return new Slice(dbus, iface);
    }

    @Override
    public SliceInterface getInterface() {
        return (SliceInterface) super.getInterface();
    }

    public String introspect() throws DBusException {
        Introspectable intro = dbus.getRemoteObject(Systemd.SERVICE_NAME, getInterface().getObjectPath(), Introspectable.class);

        return intro.Introspect();
    }

    public String getSlice() {
        return properties.getString("Slice");
    }

/*
  readonly s Slice = '-.slice';
readonly s ControlGroup = '/system.slice';
readonly b CPUAccounting = false;
readonly t CPUShares = 1024;
readonly b BlockIOAccounting = false;
readonly t BlockIOWeight = 1000;
readonly a(st) BlockIODeviceWeight = [];
readonly a(st) BlockIOReadBandwidth=;
readonly a(st) BlockIOWriteBandwidth=;
readonly b MemoryAccounting = false;
readonly t MemoryLimit = 18446744073709551615;
readonly s DevicePolicy = 'auto';
readonly a(ss) DeviceAllow = [];
 */

}
