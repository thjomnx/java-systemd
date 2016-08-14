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

package de.thjom.java.systemd;

import java.util.List;

import org.freedesktop.dbus.DBusConnection;
import org.freedesktop.dbus.exceptions.DBusException;

import de.thjom.java.systemd.interfaces.TimerInterface;
import de.thjom.java.systemd.types.TimersCalendar;
import de.thjom.java.systemd.types.TimersMonotonic;

public class Timer extends Unit {

    public static final String SERVICE_NAME = Systemd.SERVICE_NAME + ".Timer";
    public static final String UNIT_SUFFIX = ".timer";

    public static class Property extends InterfaceAdapter.Property {

        public static final String UNIT = "Unit";
        public static final String TIMERS_MONOTONIC = "TimersMonotonic";
        public static final String TIMERS_CALENDAR = "TimersCalendar";
        public static final String NEXT_ELAPSE_USEC_REALTIME = "NextElapseUSecRealtime";
        public static final String NEXT_ELAPSE_USEC_MONOTONIC = "NextElapseUSecMonotonic";
        public static final String RESULT = "Result";

        private Property() {
            super();
        }

        public static final String[] getAllNames() {
            return getAllNames(Property.class);
        }

    }

    private Timer(final DBusConnection dbus, final TimerInterface iface, final String name, final Manager manager) throws DBusException {
        super(dbus, iface, name, manager);

        this.properties = Properties.create(dbus, iface.getObjectPath(), SERVICE_NAME);
    }

    static Timer create(final DBusConnection dbus, String name, final Manager manager) throws DBusException {
        name = Unit.normalizeName(name, UNIT_SUFFIX);

        String objectPath = Unit.OBJECT_PATH + Systemd.escapePath(name);
        TimerInterface iface = dbus.getRemoteObject(Systemd.SERVICE_NAME, objectPath, TimerInterface.class);

        return new Timer(dbus, iface, name, manager);
    }

    @Override
    public TimerInterface getInterface() {
        return (TimerInterface) super.getInterface();
    }

    public String getUnit() {
        return properties.getString(Property.UNIT);
    }

    public List<TimersMonotonic> getTimersMonotonic() {
        return TimersMonotonic.list(properties.getVector(Property.TIMERS_MONOTONIC));
    }

    public List<TimersCalendar> getTimersCalendar() {
        return TimersCalendar.list(properties.getVector(Property.TIMERS_CALENDAR));
    }

    public long getNextElapseUSecRealtime() {
        return properties.getLong(Property.NEXT_ELAPSE_USEC_REALTIME);
    }

    public long getNextElapseUSecMonotonic() {
        return properties.getLong(Property.NEXT_ELAPSE_USEC_MONOTONIC);
    }

    public String getResult() {
        return properties.getString(Property.RESULT);
    }

}
