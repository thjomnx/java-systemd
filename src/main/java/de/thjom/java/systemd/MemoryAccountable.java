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

import java.math.BigInteger;

public interface MemoryAccountable {

    static class Property extends InterfaceAdapter.AdapterProperty {

        public static final String MEMORY_HIGH = "MemoryHigh";
        public static final String MEMORY_LOW = "MemoryLow";
        public static final String MEMORY_MAX = "MemoryMax";
        public static final String MEMORY_SWAP_MAX = "MemorySwapMax";

        private Property() {
            super();
        }

        public static final String[] getAllNames() {
            return getAllNames(Property.class);
        }

    }

    Properties getProperties();

    default BigInteger getMemoryHigh() {
        return getProperties().getBigInteger(Property.MEMORY_HIGH);
    }

    default BigInteger getMemoryLow() {
        return getProperties().getBigInteger(Property.MEMORY_LOW);
    }

    default BigInteger getMemoryMax() {
        return getProperties().getBigInteger(Property.MEMORY_MAX);
    }

    default BigInteger getMemorySwapMax() {
        return getProperties().getBigInteger(Property.MEMORY_SWAP_MAX);
    }

}
