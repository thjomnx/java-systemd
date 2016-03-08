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

public class SmackProcessLabel {

    private final boolean prefixed;
    private final String label;

    public SmackProcessLabel(final Object[] array) {
        this.prefixed = (boolean) array[0];
        this.label = String.valueOf(array[1]);
    }

    public boolean isPrefixed() {
        return prefixed;
    }

    public String getLabel() {
        return label;
    }

    public String toConfigString() {
        return String.format("%s%s", prefixed ? "-" : "", label);
    }

    @Override
    public String toString() {
        return String.format("SmackProcessLabel [prefixed=%s, label=%s]", prefixed, label);
    }

}
