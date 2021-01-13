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
import java.util.Arrays;
import java.util.List;
import java.util.Vector;

public class SetCredential {

    private final String id;
    private final byte[] data;

    public SetCredential(final Object[] array) {
        this.id = String.valueOf(array[0]);

        @SuppressWarnings("unchecked")
        List<Byte> list = (List<Byte>) array[1];
        byte[] bytes = new byte[list.size()];

        for (int i = 0; i < list.size(); i++) {
            bytes[i] = list.get(i);
        }

        this.data = bytes;
    }

    public static List<SetCredential> list(final Vector<Object[]> vector) {
        List<SetCredential> infos = new ArrayList<>(vector.size());

        for (Object[] array : vector) {
            SetCredential info = new SetCredential(array);

            infos.add(info);
        }

        return infos;
    }

    public String getId() {
        return id;
    }

    public byte[] getData() {
        return Arrays.copyOf(data, data.length);
    }

    @Override
    public String toString() {
        return String.format("SetCredential [id=%s, data=%s]", id, Arrays.toString(data));
    }

}
