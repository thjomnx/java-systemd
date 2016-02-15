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

public class BlockIODeviceWeight extends BlockIOPath {

    private final BigInteger weight;

    public BlockIODeviceWeight(final Object[] array) {
        super(array[0]);

        this.weight = ((UInt64) array[1]).value();
    }

    public static List<BlockIODeviceWeight> list(final Vector<Object[]> vector) {
        List<BlockIODeviceWeight> weights = new ArrayList<>(vector.size());

        for (Object[] array : vector) {
            BlockIODeviceWeight weight = new BlockIODeviceWeight(array);

            weights.add(weight);
        }

        return weights;
    }

    public BigInteger getWeight() {
        return weight;
    }

    @Override
    public String toString() {
        return String.format("BlockIODeviceWeight [filePath=%s, weight=%s]", filePath, weight);
    }

}
