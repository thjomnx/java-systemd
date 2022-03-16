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

import org.freedesktop.dbus.Struct;
import org.freedesktop.dbus.annotations.Position;

public final class StructForUnitEnableAndDisable extends Struct {
    @Position(0)
    public final String a;
    @Position(1)
    public final String b;
    @Position(2)
    public final String c;

    public StructForUnitEnableAndDisable(String a, String b, String c) {
        this.a = a;
        this.b = b;
        this.c = c;
    }
}