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

import org.freedesktop.dbus.types.UInt64;

public class BindPath {

    private final String source;
    private final String destination;
    private final boolean ignoreErrorNoEntity;
    private final BigInteger recursive;

    public BindPath(final Object[] array) {
        this.source = String.valueOf(array[0]);
        this.destination = String.valueOf(array[1]);
        this.ignoreErrorNoEntity = (boolean) array[2];
        this.recursive = ((UInt64) array[3]).value();
    }

    public static List<BindPath> list(final List<Object[]> arrays) {
        List<BindPath> infos = new ArrayList<>(arrays.size());

        for (Object[] array : arrays) {
            BindPath info = new BindPath(array);

            infos.add(info);
        }

        return infos;
    }

    public String getSource() {
        return source;
    }

    public String getDestination() {
        return destination;
    }

    public boolean isIgnoreErrorNoEntity() {
        return ignoreErrorNoEntity;
    }

    public BigInteger getRecursive() {
        return recursive;
    }

    @Override
    public String toString() {
        return new StringBuilder()
                .append("BindPath [source=")
                .append(source)
                .append(", destination=")
                .append(destination)
                .append(", ignoreErrorNoEntity=")
                .append(ignoreErrorNoEntity)
                .append(", recursive=")
                .append(recursive)
                .append("]")
                .toString();
    }

}
