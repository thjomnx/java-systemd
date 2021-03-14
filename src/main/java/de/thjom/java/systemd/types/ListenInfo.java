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

public class ListenInfo {

    private final String socketType;
    private final String filePath;

    public ListenInfo(final Object[] array) {
        this.socketType = String.valueOf(array[0]);
        this.filePath = String.valueOf(array[1]);
    }

    public static List<ListenInfo> list(final List<Object[]> arrays) {
        List<ListenInfo> infos = new ArrayList<>(arrays.size());

        for (Object[] array : arrays) {
            ListenInfo info = new ListenInfo(array);

            infos.add(info);
        }

        return infos;
    }

    public String getSocketType() {
        return socketType;
    }

    public String getFilePath() {
        return filePath;
    }

    @Override
    public String toString() {
        return String.format("ListenInfo [socketType=%s, filePath=%s]", socketType, filePath);
    }

}
