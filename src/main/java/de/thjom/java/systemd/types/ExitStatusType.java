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

public class ExitStatusType {

    private final List<Integer> customSignals;
    private final List<Integer> commonSignals;

    @SuppressWarnings("unchecked")
    public ExitStatusType(final Object[] array) {
        this.customSignals = (List<Integer>) array[0];
        this.commonSignals = (List<Integer>) array[1];
    }

    public static ExitStatusType of(final Object[] array) {
        return new ExitStatusType(array);
    }

    public List<Integer> getCustomSignals() {
        return customSignals;
    }

    public List<Integer> getCommonSignals() {
        return commonSignals;
    }

    @Override
    public String toString() {
        return String.format("ExitStatusType [customSignals=%s, commonSignals=%s]", customSignals, commonSignals);
    }

}
