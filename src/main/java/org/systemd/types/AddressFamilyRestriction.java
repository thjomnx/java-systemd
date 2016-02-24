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

import java.util.Vector;

public class AddressFamilyRestriction {

    private final boolean blacklist;
    private final Vector<String> addressFamilies;

    @SuppressWarnings("unchecked")
    public AddressFamilyRestriction(final Object[] array) {
        this.blacklist = (boolean) array[0];
        this.addressFamilies = (Vector<String>) array[1];
    }

    public boolean isBlacklist() {
        return blacklist;
    }

    public Vector<String> getAddressFamilies() {
        return addressFamilies;
    }

    @Override
    public String toString() {
        return String.format("AddressFamilyRestriction [blacklist=%s, addressFamilies=%s]", blacklist, addressFamilies);
    }

}
