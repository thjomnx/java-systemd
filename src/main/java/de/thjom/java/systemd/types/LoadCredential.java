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

public class LoadCredential {

    private final String name;
    private final String source;

    public LoadCredential(final Object[] array) {
        this.name = String.valueOf(array[0]);
        this.source = String.valueOf(array[1]);
    }

    public static List<LoadCredential> list(final Vector<Object[]> vector) {
        List<LoadCredential> infos = new ArrayList<>(vector.size());

        for (Object[] array : vector) {
            LoadCredential info = new LoadCredential(array);

            infos.add(info);
        }

        return infos;
    }

    public String getName() {
        return name;
    }

    public String getSource() {
        return source;
    }

    @Override
    public String toString() {
        return String.format("LoadCredential [name=%s, source=%s]", name, source);
    }

}
