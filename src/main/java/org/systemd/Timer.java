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

package org.systemd;

import org.freedesktop.dbus.DBusConnection;
import org.freedesktop.dbus.exceptions.DBusException;
import org.systemd.interfaces.TimerInterface;

public class Timer extends Unit {

    public static final String SERVICE_NAME = Systemd.SERVICE_NAME + ".Timer";
    public static final String UNIT_SUFFIX = ".timer";

    private final Properties properties;

    private Timer(final DBusConnection dbus, final TimerInterface iface, final String name) throws DBusException {
        super(dbus, iface, name);

        this.properties = Properties.create(dbus, iface.getObjectPath(), SERVICE_NAME);
    }

    static Timer create(final DBusConnection dbus, String name) throws DBusException {
        name = Unit.normalizeName(name, UNIT_SUFFIX);

        String objectPath = Unit.OBJECT_PATH + Systemd.escapePath(name);
        TimerInterface iface = dbus.getRemoteObject(Systemd.SERVICE_NAME, objectPath, TimerInterface.class);

        return new Timer(dbus, iface, name);
    }

    @Override
    public TimerInterface getInterface() {
        return (TimerInterface) super.getInterface();
    }

    public String getUnit() {
        return properties.getString("Unit");
    }

    /*
    readonly a(stt) TimersMonotonic = [('OnUnitActiveUSec', 86400000000, 173700033104), ('OnBootUSec', 900000000, 900000000)];
    readonly a(sst) TimersCalendar = [];
     */

    public long getNextElapseUSecRealtime() {
        return properties.getLong("NextElapseUSecRealtime");
    }

    public long getNextElapseUSecMonotonic() {
        return properties.getLong("NextElapseUSecMonotonic");
    }

    public String getResult() {
        return properties.getString("Result");
    }

}