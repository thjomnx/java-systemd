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

public class UnitFileInstallChange extends Tuple {

    private boolean carriesInstallInfo;

    private UnitFileChange unitFileChange;

    public UnitFileInstallChange(final boolean carriesInstallInfo, final UnitFileChange unitFileChange) {
        super();

        this.carriesInstallInfo = carriesInstallInfo;
        this.unitFileChange = unitFileChange;
    }

    public String getSummary() {
        return String.format("%b %s", carriesInstallInfo, unitFileChange);
    }

    public boolean isCarriesInstallInfo() {
        return carriesInstallInfo;
    }

    public UnitFileChange getUnitFileChange() {
        return unitFileChange;
    }

}
