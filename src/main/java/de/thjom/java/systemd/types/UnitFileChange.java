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
import org.freedesktop.dbus.Struct;

public class UnitFileChange extends Struct {

    @Position(0)
    private final String changeType;

    @Position(1)
    private final String changePath;

    @Position(2)
    private final String changeSource;

    public UnitFileChange(final String changeType, final String changePath, final String changeSource) {
        super();

        this.changeType = changeType;
        this.changePath = changePath;
        this.changeSource = changeSource;
    }

    public String getChangeType() {
        return changeType;
    }

    public String getChangePath() {
        return changePath;
    }

    public String getChangeSource() {
        return changeSource;
    }

    public String toFormattedString() {
        return String.format("%s %s %s", changeType, changePath, changeSource);
    }

}
