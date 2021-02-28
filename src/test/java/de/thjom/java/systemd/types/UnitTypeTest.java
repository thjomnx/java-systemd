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

import org.freedesktop.dbus.DBusPath;
import org.freedesktop.dbus.types.UInt32;
import org.testng.Assert;
import org.testng.annotations.Test;

import de.thjom.java.systemd.Systemd;
import de.thjom.java.systemd.Unit;

public class UnitTypeTest {

    @Test(description="Tests the constructor and getters.")
    public void testStructure() {
        String unitName = "dummy.service";

        UnitType type = new UnitType(unitName, "Dummy unit",
                                     "loaded", "active", "running",
                                     "Follow",
                                     new DBusPath(Unit.OBJECT_PATH + Systemd.escapePath(unitName)),
                                     new UInt32(42L), "dummy type", new DBusPath("dummy_path"));

        Assert.assertEquals(type.getUnitName(), unitName);
        Assert.assertEquals(type.getUnitDescription(), "Dummy unit");
        Assert.assertEquals(type.getLoadState(), "loaded");
        Assert.assertEquals(type.getActiveState(), "active");
        Assert.assertEquals(type.getSubState(), "running");
        Assert.assertEquals(type.getFollowingUnit(), "Follow");
        Assert.assertEquals(type.getUnitObjectPath().getPath(), Unit.OBJECT_PATH + Systemd.escapePath(unitName));
        Assert.assertEquals(type.getJobId(), 42);
        Assert.assertEquals(type.getJobType(), "dummy type");
        Assert.assertEquals(type.getJobObjectPath().getPath(), "dummy_path");
    }

    @Test(description="Tests the formatted string aggregator.")
    public void testFormattedString() {
        String unitName = "dummy.service";

        UnitType type = new UnitType(unitName, "Dummy unit",
                "loaded", "active", "running",
                "Follow",
                new DBusPath(Unit.OBJECT_PATH + Systemd.escapePath(unitName)),
                new UInt32(42L), "dummy type", new DBusPath("dummy_path"));

        Assert.assertFalse(type.toFormattedString().isEmpty());
    }

    @Test(description="Tests the implemention of the 'Comparable' interface.")
    public void testComparability() {
        String unitName1 = "dummy1.service";

        UnitType type1 = new UnitType(unitName1, "Dummy unit",
                "loaded", "active", "running",
                "Follow",
                new DBusPath(Unit.OBJECT_PATH + Systemd.escapePath(unitName1)),
                new UInt32(42L), "dummy type", new DBusPath("dummy_path"));

        String unitName2 = "dummy2.service";

        UnitType type2 = new UnitType(unitName2, "Dummy unit",
                "loaded", "active", "running",
                "Follow",
                new DBusPath(Unit.OBJECT_PATH + Systemd.escapePath(unitName2)),
                new UInt32(42L), "dummy type", new DBusPath("dummy_path"));

        // Test list sorting
        List<UnitType> list = new ArrayList<>();
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
