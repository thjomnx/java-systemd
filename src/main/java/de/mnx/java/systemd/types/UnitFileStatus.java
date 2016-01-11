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

import org.freedesktop.dbus.Position;
import org.freedesktop.dbus.Struct;

public class UnitFileStatus extends Struct {

    @Position(0)
    private final String file;

    public String getFile() {
        return file;
    }

    @Position(1)
    private final String status;

    public String getStatus() {
        return status;
    }

    public UnitFileStatus(String file, String status) {
        this.file = file;
        this.status = status;
    }

}
