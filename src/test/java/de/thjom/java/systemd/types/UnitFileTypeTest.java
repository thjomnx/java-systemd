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

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.testng.Assert;
import org.testng.annotations.Test;

public class UnitFileTypeTest {

    @Test(description="Tests the constructor and getters.")
    public void testStructure() {
        UnitFileType type = new UnitFileType("/usr/lib/systemd/system/avahi-daemon.service", "loaded");

        Assert.assertEquals(type.getPath(), "/usr/lib/systemd/system/avahi-daemon.service");
        Assert.assertEquals(type.getStatus(), "loaded");
    }

    @Test(description="Tests the summary string aggregator.")
    public void testSummary() {
        UnitFileType type = new UnitFileType("/usr/lib/systemd/system/avahi-daemon.service", "loaded");

        Assert.assertEquals(type.getSummary(), "/usr/lib/systemd/system/avahi-daemon.service loaded");
    }

    @Test(description="Tests the implemention of the 'Comparable' interface.")
    public void testComparability() {
        UnitFileType type1 = new UnitFileType("/usr/lib/systemd/system/avahi-daemon.service", "loaded");
        UnitFileType type2 = new UnitFileType("/usr/lib/systemd/system/polkit.service", "loaded");

        // Test list sorting
        List<UnitFileType> list = new ArrayList<>();
        list.add(type2);
        list.add(type1);

        Assert.assertSame(list.get(0), type2);
        Assert.assertSame(list.get(1), type1);

        Collections.sort(list);

        Assert.assertSame(list.get(0), type1);
        Assert.assertSame(list.get(1), type2);

        // Test null check
        Assert.assertEquals(type1.compareTo(null), Integer.MAX_VALUE);
    }

}
