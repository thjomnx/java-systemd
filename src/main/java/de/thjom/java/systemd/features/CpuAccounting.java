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

public interface CpuAccounting extends Feature {

    static class Property extends InterfaceAdapter.AdapterProperty {

        public static final String CPU_ACCOUNTING = "CPUAccounting";
        public static final String CPU_QUOTA_PER_SEC_USEC = "CPUQuotaPerSecUSec";
        public static final String CPU_SHARES = "CPUShares";
        public static final String CPU_USAGE_NSEC = "CPUUsageNSec";
        public static final String CPU_WEIGHT = "CPUWeight";
        public static final String STARTUP_CPU_SHARES = "StartupCPUShares";
        public static final String STARTUP_CPU_WEIGHT = "StartupCPUWeight";

        private Property() {
            super();
        }

        public static final String[] getAllNames() {
            return getAllNames(Property.class);
        }

    }

    default boolean isCPUAccounting() {
        return getProperties().getBoolean(Property.CPU_ACCOUNTING);
    }

    default BigInteger getCPUQuotaPerSecUSec() {
        return getProperties().getBigInteger(Property.CPU_QUOTA_PER_SEC_USEC);
    }

    default BigInteger getCPUShares() {
        return getProperties().getBigInteger(Property.CPU_SHARES);
    }

    default BigInteger getCPUUsageNSec() {
        return getProperties().getBigInteger(Property.CPU_USAGE_NSEC);
    }

    default BigInteger getCPUWeight() {
        return getProperties().getBigInteger(Property.CPU_WEIGHT);
    }

    default BigInteger getStartupCPUShares() {
        return getProperties().getBigInteger(Property.STARTUP_CPU_SHARES);
    }

    default BigInteger getStartupCPUWeight() {
        return getProperties().getBigInteger(Property.STARTUP_CPU_WEIGHT);
    }

}
