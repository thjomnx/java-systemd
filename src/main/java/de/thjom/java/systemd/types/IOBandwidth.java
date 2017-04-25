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

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

import org.freedesktop.dbus.UInt64;

public class IOBandwidth extends IOPath {

    private final BigInteger bandwidth;

    public IOBandwidth(final Object[] array) {
        super(array[0]);

        this.bandwidth = ((UInt64) array[1]).value();
    }

    public static List<IOBandwidth> list(final Vector<Object[]> vector) {
        List<IOBandwidth> bandwidths = new ArrayList<>(vector.size());

        for (Object[] array : vector) {
            IOBandwidth bandwidth = new IOBandwidth(array);

            bandwidths.add(bandwidth);
        }

        return bandwidths;
    }

    public BigInteger getBandwidth() {
        return bandwidth;
    }

    @Override
    public String toString() {
        return String.format("IOBandwidth [filePath=%s, bandwidth=%s]", filePath, bandwidth);
    }

}
