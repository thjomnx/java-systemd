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

public class MountImage {

    private final String source;
    private final String destination;
    private final boolean ignoreENOENT;
    private final List<ImageOptions> mountOptions;

    public MountImage(final Object[] array) {
        this.source = String.valueOf(array[0]);
        this.destination = String.valueOf(array[1]);
        this.ignoreENOENT = (boolean) array[2];

        @SuppressWarnings("unchecked")
        Vector<Object[]> options = (Vector<Object[]>) array[3];

        this.mountOptions = ImageOptions.list(options);
    }

    public static List<MountImage> list(final Vector<Object[]> vector) {
        List<MountImage> infos = new ArrayList<>(vector.size());

        for (Object[] array : vector) {
            MountImage info = new MountImage(array);

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

    public boolean isIgnoreENOENT() {
        return ignoreENOENT;
    }

    public List<ImageOptions> getMountOptions() {
        return mountOptions;
    }

    @Override
    public String toString() {
        return String.format("MountImage [source=%s, destination=%s, ignoreENOENT=%b, mountOptions=%s]", source, destination, ignoreENOENT, mountOptions);
    }

}
