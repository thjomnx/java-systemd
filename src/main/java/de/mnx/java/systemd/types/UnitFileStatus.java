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

package de.mnx.java.systemd.types;

import java.nio.file.Path;
import java.nio.file.Paths;

import org.freedesktop.dbus.Position;
import org.freedesktop.dbus.Struct;

public class UnitFileStatus extends Struct {

    public enum Status {
        ENABLED,
        DISABLED,
        STATIC,
        INDIRECT;
    }

    @Position(0)
    private final Path path;

    @Position(1)
    private final Status status;

    public UnitFileStatus(final String path, final String status) {
        this.path = Paths.get(path);
        this.status = Status.valueOf(status.toUpperCase());
    }

    public Path getPath() {
        return path;
    }

    public Status getStatus() {
        return status;
    }

}
