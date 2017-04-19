/*
 * Java-systemd implementation
 * Copyright (c) 2016 Markus Enax
 *
 * This program is free software; you can redistribute it and/or modify it under
 * the terms of either the GNU Lesser General Public License Version 2 or the
 * Academic Free Licence Version 3.0.
 *
 * Full licence texts are included in the COPYING file with this program.
 */

package de.thjom.java.systemd;

import java.util.List;

import org.freedesktop.dbus.exceptions.DBusException;

import de.thjom.java.systemd.interfaces.TimerInterface;
import de.thjom.java.systemd.types.TimersCalendar;
import de.thjom.java.systemd.types.TimersMonotonic;

public class Timer extends Unit {

    public static final String SERVICE_NAME = Systemd.SERVICE_NAME + ".Timer";
    public static final String UNIT_SUFFIX = ".timer";

    public static class Property extends InterfaceAdapter.Property {

        public static final String ACCURACY_USEC = "AccuracyUSec";
        public static final String LAST_TRIGGER_USEC = "LastTriggerUSec";
        public static final String LAST_TRIGGER_USEC_MONOTONIC = "LastTriggerUSecMonotonic";
        public static final String NEXT_ELAPSE_USEC_MONOTONIC = "NextElapseUSecMonotonic";
        public static final String NEXT_ELAPSE_USEC_REALTIME = "NextElapseUSecRealtime";
        public static final String PERSISTENT = "Persistent";
        public static final String RANDOMIZED_DELAY_USEC = "RandomizedDelayUSec";
        public static final String REMAIN_AFTER_ELAPSE = "RemainAfterElapse";
        public static final String RESULT = "Result";
        public static final String UNIT = "Unit";
        public static final String TIMERS_CALENDAR = "TimersCalendar";
        public static final String TIMERS_MONOTONIC = "TimersMonotonic";
        public static final String WAKE_SYSTEM = "WakeSystem";

        private Property() {
            super();
        }

        public static final String[] getAllNames() {
            return getAllNames(Property.class);
        }

    }

    private Timer(final Manager manager, final TimerInterface iface, final String name) throws DBusException {
        super(manager, iface, name);

        this.properties = Properties.create(dbus, iface.getObjectPath(), SERVICE_NAME);
    }

    static Timer create(final Manager manager, String name) throws DBusException {
        name = Unit.normalizeName(name, UNIT_SUFFIX);

        String objectPath = Unit.OBJECT_PATH + Systemd.escapePath(name);
        TimerInterface iface = manager.dbus.getRemoteObject(Systemd.SERVICE_NAME, objectPath, TimerInterface.class);

        return new Timer(manager, iface, name);
    }

    @Override
    public TimerInterface getInterface() {
        return (TimerInterface) super.getInterface();
    }

    public long getAccuracyUSec() {
        return properties.getLong(Property.ACCURACY_USEC);
    }

    public long getLastTriggerUSec() {
        return properties.getLong(Property.LAST_TRIGGER_USEC);
    }

    public long getLastTriggerUSecMonotonic() {
        return properties.getLong(Property.LAST_TRIGGER_USEC_MONOTONIC);
    }

    public long getNextElapseUSecMonotonic() {
        return properties.getLong(Property.NEXT_ELAPSE_USEC_MONOTONIC);
    }

    public long getNextElapseUSecRealtime() {
        return properties.getLong(Property.NEXT_ELAPSE_USEC_REALTIME);
    }

    public boolean isPersistent() {
        return properties.getBoolean(Property.PERSISTENT);
    }

    public long getRandomizedDelayUSec() {
        return properties.getLong(Property.RANDOMIZED_DELAY_USEC);
    }

    public boolean isRemainAfterElapse() {
        return properties.getBoolean(Property.REMAIN_AFTER_ELAPSE);
    }

    public String getResult() {
        return properties.getString(Property.RESULT);
    }

    public String getUnit() {
        return properties.getString(Property.UNIT);
    }

    public List<TimersCalendar> getTimersCalendar() {
        return TimersCalendar.list(properties.getVector(Property.TIMERS_CALENDAR));
    }

    public List<TimersMonotonic> getTimersMonotonic() {
        return TimersMonotonic.list(properties.getVector(Property.TIMERS_MONOTONIC));
    }

    public boolean isWakeSystem() {
        return properties.getBoolean(Property.WAKE_SYSTEM);
    }

}
