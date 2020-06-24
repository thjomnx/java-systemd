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

package de.thjom.java.systemd.types;

import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

import org.freedesktop.dbus.types.UInt64;

public class TimersCalendar {

    private final String timerBase;
    private final String calendar;
    private final long nextElapsePoint;

    public TimersCalendar(final Object[] array) {
        this.timerBase = String.valueOf(array[0]);
        this.calendar = String.valueOf(array[1]);
        this.nextElapsePoint = ((UInt64) array[2]).longValue();
    }

    public static List<TimersCalendar> list(final Vector<Object[]> vector) {
        List<TimersCalendar> timers = new ArrayList<>(vector.size());

        for (Object[] array : vector) {
            TimersCalendar timer = new TimersCalendar(array);

            timers.add(timer);
        }

        return timers;
    }

    public String getTimerBase() {
        return timerBase;
    }

    public String getCalendar() {
        return calendar;
    }

    public long getNextElapsePoint() {
        return nextElapsePoint;
    }

    @Override
    public String toString() {
        return String.format("TimersCalendar [timerBase=%s, calendar=%s, nextElapsePoint=%s]", timerBase, calendar, nextElapsePoint);
    }

}
