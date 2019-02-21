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

import org.freedesktop.dbus.types.UInt32;
import org.testng.Assert;
import org.testng.annotations.Test;

public class UnitProcessTypeTest {

    @Test(description="Tests the constructor and getters.")
    public void testStructure() {
        UnitProcessType type = new UnitProcessType("/user.slice", new UInt32(23L), "/sbin/init");

        Assert.assertEquals(type.getCgroupPath(), "/user.slice");
        Assert.assertEquals(type.getPid(), 23);
        Assert.assertEquals(type.getCommandLine(), "/sbin/init");
    }

    @Test(description="Tests the summary string aggregator.")
    public void testSummary() {
        UnitProcessType type = new UnitProcessType("/user.slice", new UInt32(23L), "/sbin/init");

        Assert.assertEquals(type.getSummary(), "/user.slice 23 /sbin/init");
    }

    @Test(description="Tests the implemention of the 'Comparable' interface.")
    public void testComparability() {
        UnitProcessType type1 = new UnitProcessType("/user.slice", new UInt32(23L), "/sbin/init");
        UnitProcessType type2 = new UnitProcessType("/user.slice/user-1000.slice", new UInt32(42L), "/dev/null");

        // Test list sorting
        List<UnitProcessType> list = new ArrayList<>();
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
