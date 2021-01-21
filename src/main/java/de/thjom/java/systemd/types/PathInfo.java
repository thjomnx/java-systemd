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

public class PathInfo {

    private final String watchCondition;
    private final String watchedPath;

    public PathInfo(final Object[] array) {
        this.watchCondition = String.valueOf(array[0]);
        this.watchedPath = String.valueOf(array[1]);
    }

    public static List<PathInfo> list(final List<Object[]> arrays) {
        List<PathInfo> infos = new ArrayList<>(arrays.size());

        for (Object[] array : arrays) {
            PathInfo info = new PathInfo(array);

            infos.add(info);
        }

        return infos;
    }

    public String getWatchCondition() {
        return watchCondition;
    }

    public String getWatchedPath() {
        return watchedPath;
    }

    @Override
    public String toString() {
        return String.format("PathInfo [watchCondition=%s, watchedPath=%s]", watchCondition, watchedPath);
    }

}
