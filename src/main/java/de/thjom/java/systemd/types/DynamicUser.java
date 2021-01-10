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

import org.freedesktop.dbus.types.UInt32;

public class DynamicUser {

    private final long uid;
    private final String name;

    public DynamicUser(final Object[] array) {
        this.uid = ((UInt32) array[0]).longValue();
        this.name = String.valueOf(array[1]);
    }

    public static List<DynamicUser> list(final Vector<Object[]> vector) {
        List<DynamicUser> infos = new ArrayList<>(vector.size());

        for (Object[] array : vector) {
            DynamicUser info = new DynamicUser(array);

            infos.add(info);
        }

        return infos;
    }

    public long getUid() {
        return uid;
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return String.format("DynamicUser [uid=%s, name=%s]", uid, name);
    }

}
