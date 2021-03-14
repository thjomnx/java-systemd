/*
 * Java-systemd implementation
 * Copyright (c) 2016 Markus Enax
 *
 * This program is free software; you can redistribute it and/or modify it
 * under the terms of either the GNU Lesser General Public License Version 2 or the
 * Academic Free Licence Version 3.0.
 *
 * Full licence texts are included in the COPYING file with this program.
 */

package de.thjom.java.systemd.types;

import org.testng.Assert;
import org.testng.annotations.Test;

public class UnitFileInstallChangeTest {

    private static final UnitFileChange UNIT_FILE_CHANGE = new UnitFileChange("type0", "path0", "source0");

    @Test(description="Tests parameterized constructor.")
    public void testConstructor() {
        UnitFileInstallChange instance = new UnitFileInstallChange(true, UNIT_FILE_CHANGE);

        Assert.assertTrue(instance.isCarriesInstallInfo());
        Assert.assertEquals(instance.getUnitFileChange(), UNIT_FILE_CHANGE);
    }

    @Test(description="Tests the formatted string aggregator.")
    public void testFormattedString() {
        UnitFileInstallChange instance = new UnitFileInstallChange(true, UNIT_FILE_CHANGE);

        Assert.assertEquals(instance.toFormattedString(), "true " + UNIT_FILE_CHANGE.toFormattedString());
    }

}
