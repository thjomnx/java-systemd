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

import org.freedesktop.dbus.annotations.Position;

public class UnitFileType extends UnitBase implements Comparable<UnitFileType> {

    @Position(0)
    private final String path;

    @Position(1)
    private final String status;

    public UnitFileType(final String path, final String status) {
        super(path);

        this.path = path;
        this.status = status;
    }

    public String getSummary() {
        return String.format("%s %s", path, status);
    }

    public String getPath() {
        return path;
    }

    public String getStatus() {
        return status;
    }

    @Override
    public int compareTo(final UnitFileType other) {
        if (other == null) {
            return Integer.MAX_VALUE;
        }
        else {
            return path.compareTo(other.path);
        }
    }

}
