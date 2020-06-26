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

import org.freedesktop.dbus.types.UInt64;

public class IOIops extends IOPath {

    private final BigInteger iops;

    public IOIops(final Object[] array) {
        super(array[0]);

        this.iops = ((UInt64) array[1]).value();
    }

    public static List<IOIops> list(final Vector<Object[]> vector) {
        List<IOIops> list = new ArrayList<>(vector.size());

        for (Object[] array : vector) {
            IOIops iops = new IOIops(array);

            list.add(iops);
        }

        return list;
    }

    public BigInteger getIops() {
        return iops;
    }

    @Override
    public String toString() {
        return String.format("IOIops [filePath=%s, iops=%s]", filePath, iops);
    }

}
