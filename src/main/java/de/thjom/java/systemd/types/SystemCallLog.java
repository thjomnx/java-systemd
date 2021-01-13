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

import java.util.Vector;

public class SystemCallLog {

    private final boolean allowlist;
    private final Vector<String> sysCalls;

    @SuppressWarnings("unchecked")
    public SystemCallLog(final Object[] array) {
        this.allowlist = (boolean) array[0];
        this.sysCalls = (Vector<String>) array[1];
    }

    public boolean isAllowlist() {
        return allowlist;
    }

    public Vector<String> getSysCalls() {
        return sysCalls;
    }

    @Override
    public String toString() {
        return String.format("SystemCallLog [allowlist=%s, sysCalls=%s]", allowlist, sysCalls);
    }

}
