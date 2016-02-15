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

package de.mnx.java.systemd.types;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

import org.freedesktop.dbus.UInt64;

public class BlockIOBandwidth extends BlockIOPath {

    private final BigInteger bandwidth;

    public BlockIOBandwidth(final Object[] array) {
        super(array[0]);

        this.bandwidth = ((UInt64) array[1]).value();
    }

    public static List<BlockIOBandwidth> list(final Vector<Object[]> vector) {
        List<BlockIOBandwidth> bandwidths = new ArrayList<>(vector.size());

        for (Object[] array : vector) {
            BlockIOBandwidth bandwidth = new BlockIOBandwidth(array);

            bandwidths.add(bandwidth);
        }

        return bandwidths;
    }

    public BigInteger getBandwidth() {
        return bandwidth;
    }

    @Override
    public String toString() {
        return String.format("BlockIOBandwidth [filePath=%s, bandwidth=%s]", filePath, bandwidth);
    }

}
