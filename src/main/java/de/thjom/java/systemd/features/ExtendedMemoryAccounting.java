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

import java.util.List;

import de.thjom.java.systemd.InterfaceAdapter;

public interface ExtendedMemoryAccounting extends MemoryAccounting {

    static class Property extends InterfaceAdapter.AdapterProperty {

        public static final String MEMORY_DENY_WRITE_EXECUTE = "MemoryDenyWriteExecute";

        private Property() {
            super();
        }

        public static final List<String> getAllNames() {
            return getAllNames(Property.class);
        }

    }

    default boolean isMemoryDenyWriteExecute() {
        return getProperties().getBoolean(Property.MEMORY_DENY_WRITE_EXECUTE);
    }

}
