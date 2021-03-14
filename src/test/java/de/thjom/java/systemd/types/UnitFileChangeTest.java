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

import org.testng.Assert;
import org.testng.annotations.Test;

public class UnitFileChangeTest {

    @Test(description="Tests the constructor and getters.")
    public void testStructure() {
        UnitFileChange type = new UnitFileChange("type0", "path0", "source0");

        Assert.assertEquals(type.getChangeType(), "type0");
        Assert.assertEquals(type.getChangePath(), "path0");
        Assert.assertEquals(type.getChangeSource(), "source0");
    }

    @Test(description="Tests the formatted string aggregator.")
    public void testFormattedString() {
        UnitFileChange type = new UnitFileChange("type0", "path0", "source0");

        Assert.assertEquals(type.toFormattedString(), "type0 path0 source0");
    }

}
