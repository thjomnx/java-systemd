/*
 * Java-systemd implementation
 * Copyright (c) 2016 Markus Enax
 *
 * This program is free software; you can redistribute it and/or modify it under
 * the terms of either the GNU Lesser General Public License Version 2 or the
 * Academic Free Licence Version 2.1.
 *
 * Full licence texts are included in the COPYING file with this program.
 */

package de.thjom.java.systemd.types;

import org.freedesktop.dbus.Path;
import org.freedesktop.dbus.UInt32;

public class Job {

    private final long id;
    private final Path objectPath;

    public Job(final Object[] array) {
        this.id = ((UInt32) array[0]).longValue();
        this.objectPath = (Path) array[1];
    }

    public long getId() {
        return id;
    }

    public Path getObjectPath() {
        return objectPath;
    }

    @Override
    public String toString() {
        return String.format("JobInfo [id=%s, objectPath=%s]", id, objectPath);
    }

}
