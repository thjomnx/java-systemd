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

public class SystemCallFilter {

    private final boolean field1;
    private final Vector<String> field2;

    @SuppressWarnings("unchecked")
    public SystemCallFilter(final Object[] array) {
        this.field1 = (boolean) array[0];
        this.field2 = (Vector<String>) array[1];
    }

    public boolean isField1() {
        return field1;
    }

    public Vector<String> getField2() {
        return field2;
    }

    @Override
    public String toString() {
        return String.format("SystemCallFilter [field1=%s, field2=%s]", field1, field2);
    }

}
