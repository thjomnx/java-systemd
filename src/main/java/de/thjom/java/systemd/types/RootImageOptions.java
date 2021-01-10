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

import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

public class RootImageOptions {

    private final String partitionDesignator;
    private final String options;

    public RootImageOptions(final Object[] array) {
        this.partitionDesignator = String.valueOf(array[0]);
        this.options = String.valueOf(array[1]);
    }

    public static List<RootImageOptions> list(final Vector<Object[]> vector) {
        List<RootImageOptions> infos = new ArrayList<>(vector.size());

        for (Object[] array : vector) {
            RootImageOptions info = new RootImageOptions(array);

            infos.add(info);
        }

        return infos;
    }

    public String getPartitionDesignator() {
        return partitionDesignator;
    }

    public String getOptions() {
        return options;
    }

    @Override
    public String toString() {
        return String.format("RootImageOptions [partitionDesignator=%s, options=%s]", partitionDesignator, options);
    }

}
