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

package de.thjom.java.systemd.features;

import java.math.BigInteger;

import de.thjom.java.systemd.InterfaceAdapter;
import de.thjom.java.systemd.Properties;

public interface TasksAccounting {

    static class Property extends InterfaceAdapter.AdapterProperty {

        public static final String TASKS_ACCOUNTING = "TasksAccounting";
        public static final String TASKS_CURRENT = "TasksCurrent";
        public static final String TASKS_MAX = "TasksMax";

        private Property() {
            super();
        }

        public static final String[] getAllNames() {
            return getAllNames(Property.class);
        }

    }

    Properties getProperties();

    default boolean isTasksAccounting() {
        return getProperties().getBoolean(Property.TASKS_ACCOUNTING);
    }

    default BigInteger getTasksCurrent() {
        return getProperties().getBigInteger(Property.TASKS_CURRENT);
    }

    default BigInteger getTasksMax() {
        return getProperties().getBigInteger(Property.TASKS_MAX);
    }

}
