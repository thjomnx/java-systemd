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

package org.systemd.types;

import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

public class TimersMonotonic {

    private final String timerBase;
    private final long offsetUSec;
    private final long nextElapsePoint;

    public TimersMonotonic(final Object[] array) {
        this.timerBase = String.valueOf(array[0]);
        this.offsetUSec = ((Number) array[1]).longValue();
        this.nextElapsePoint = ((Number) array[2]).longValue();
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

    public long getOffsetUSec() {
        return offsetUSec;
    }

    public long getNextElapsePoint() {
        return nextElapsePoint;
    }

}
