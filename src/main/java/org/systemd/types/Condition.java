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

public class Condition {

    private final String type;
    private final boolean trigger;
    private final boolean reversed;
    private final String value;
    private final int status;

    public Condition(final Object[] array) {
        this.type = String.valueOf(array[0]);
        this.trigger = (boolean) array[1];
        this.reversed = (boolean) array[2];
        this.value = String.valueOf(array[3]);
        this.status = (int) array[4];
    }

    public static List<Condition> list(final Vector<Object[]> vector) {
        List<Condition> conds = new ArrayList<>(vector.size());

        for (Object[] array : vector) {
            Condition cond = new Condition(array);

            conds.add(cond);
        }

        return conds;
    }

    public String getType() {
        return type;
    }

    public boolean isTrigger() {
        return trigger;
    }

    public boolean isReversed() {
        return reversed;
    }

    public String getValue() {
        return value;
    }

    public int getStatus() {
        return status;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("ConditionInfo [type=");
        builder.append(type);
        builder.append(", trigger=");
        builder.append(trigger);
        builder.append(", reversed=");
        builder.append(reversed);
        builder.append(", value=");
        builder.append(value);
        builder.append(", status=");
        builder.append(status);
        builder.append("]");

        return builder.toString();
    }

}
