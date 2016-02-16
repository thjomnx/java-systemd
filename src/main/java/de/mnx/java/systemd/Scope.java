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

import de.mnx.java.systemd.interfaces.ScopeInterface;

public class Scope extends Unit {

    public static final String SERVICE_NAME = Systemd.SERVICE_NAME + ".Scope";
    public static final String UNIT_SUFFIX = ".scope";

    private final Properties properties;

    private Scope(final DBusConnection dbus, final ScopeInterface iface) throws DBusException {
        super(dbus, iface);

        this.properties = Properties.create(dbus, iface.getObjectPath(), SERVICE_NAME);
    }

    static Scope create(final DBusConnection dbus, final String objectPath) throws DBusException {
        ScopeInterface iface = dbus.getRemoteObject(Systemd.SERVICE_NAME, objectPath, ScopeInterface.class);

        return new Scope(dbus, iface);
    }

    @Override
    public ScopeInterface getInterface() {
        return (ScopeInterface) super.getInterface();
    }

    public String introspect() throws DBusException {
        Introspectable intro = dbus.getRemoteObject(Systemd.SERVICE_NAME, getInterface().getObjectPath(), Introspectable.class);

        return intro.Introspect();
    }

    public String getSlice() {
        return properties.getString("Slice");
    }

/*
  readonly s Slice = 'user-1000.slice';
readonly s ControlGroup = '/user.slice/user-1000.slice/session-1.scope';
readonly t TimeoutStopUSec = 500000;
readonly s KillMode = 'none';
readonly i KillSignal = 15;
readonly b SendSIGKILL = true;
readonly b SendSIGHUP = true;
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
readonly s Result = 'success';
readonly s Controller = '';
 */

}
