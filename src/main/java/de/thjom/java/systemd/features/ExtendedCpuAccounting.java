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

import de.thjom.java.systemd.InterfaceAdapter;

public interface ExtendedCpuAccounting extends CpuAccounting {

    static class Property extends InterfaceAdapter.AdapterProperty {

        public static final String CPU_AFFINITY = "CPUAffinity";
        public static final String CPU_SCHEDULING_POLICY = "CPUSchedulingPolicy";
        public static final String CPU_SCHEDULING_PRIORITY = "CPUSchedulingPriority";
        public static final String CPU_SCHEDULING_RESET_ON_FORK = "CPUSchedulingResetOnFork";

        private Property() {
            super();
        }

        public static final String[] getAllNames() {
            return getAllNames(Property.class);
        }

    }

    default byte[] getCPUAffinity() {
        return (byte[]) getProperties().getVariant(Property.CPU_AFFINITY).getValue();
    }

    default int getCPUSchedulingPolicy() {
        return getProperties().getInteger(Property.CPU_SCHEDULING_POLICY);
    }

    default int getCPUSchedulingPriority() {
        return getProperties().getInteger(Property.CPU_SCHEDULING_PRIORITY);
    }

    default boolean isCPUSchedulingResetOnFork() {
        return getProperties().getBoolean(Property.CPU_SCHEDULING_RESET_ON_FORK);
    }

}
