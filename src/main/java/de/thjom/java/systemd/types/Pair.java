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

import org.freedesktop.dbus.Tuple;
import org.freedesktop.dbus.annotations.Position;

public final class Pair<A, B> extends Tuple {

    @Position(0)
    public final A a;
    @Position(1)
    public final B b;

    public Pair(A a, B b) {
        this.a = a;
        this.b = b;
    }
}