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

import java.util.List;

public class SystemCallFilter {

    private final boolean blacklist;
    private final List<String> sysCalls;

    @SuppressWarnings("unchecked")
    public SystemCallFilter(final Object[] array) {
        this.blacklist = (boolean) array[0];
        this.sysCalls = (List<String>) array[1];
    }

    public boolean isBlacklist() {
        return blacklist;
    }

    public List<String> getSysCalls() {
        return sysCalls;
    }

    @Override
    public String toString() {
        return String.format("SystemCallFilter [blacklist=%s, sysCalls=%s]", blacklist, sysCalls);
    }

}
