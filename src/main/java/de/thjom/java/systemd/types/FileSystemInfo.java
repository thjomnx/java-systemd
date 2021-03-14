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
import java.util.Collection;
import java.util.List;

public class FileSystemInfo {

    private final String path;
    private final String options;

    public FileSystemInfo(final Object[] array) {
        this.path = String.valueOf(array[0]);
        this.options = String.valueOf(array[1]);
    }

    public static List<FileSystemInfo> list(final Collection<Object[]> arrays) {
        List<FileSystemInfo> infos = new ArrayList<>(arrays.size());

        for (Object[] array : arrays) {
            FileSystemInfo info = new FileSystemInfo(array);

            infos.add(info);
        }

        return infos;
    }

    public String getPath() {
        return path;
    }

    public String getOptions() {
        return options;
    }

    @Override
    public String toString() {
        return String.format("FileSystemInfo [path=%s, options=%s]", path, options);
    }

}
