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

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

import org.freedesktop.dbus.types.UInt64;

public class TimersMonotonic {

    private final String timerBase;
    private final BigInteger offsetUSec;
    private final long nextElapsePoint;

    public TimersMonotonic(final Object[] array) {
        this.timerBase = String.valueOf(array[0]);
        this.offsetUSec = ((UInt64) array[1]).value();
        this.nextElapsePoint = ((UInt64) array[2]).longValue();
    }

    public static List<TimersMonotonic> list(final Vector<Object[]> vector) {
        List<TimersMonotonic> timers = new ArrayList<>(vector.size());

        for (Object[] array : vector) {
            TimersMonotonic timer = new TimersMonotonic(array);

            timers.add(timer);
        }

        return timers;
    }

    public String getTimerBase() {
        return timerBase;
    }

    public BigInteger getOffsetUSec() {
        return offsetUSec;
    }

    public long getNextElapsePoint() {
        return nextElapsePoint;
    }

    @Override
    public String toString() {
        return String.format("TimersMonotonic [timerBase=%s, offsetUSec=%s, nextElapsePoint=%s]", timerBase, offsetUSec, nextElapsePoint);
    }

}
