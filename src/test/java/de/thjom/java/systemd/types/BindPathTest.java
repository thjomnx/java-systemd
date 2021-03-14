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

import java.util.ArrayList;
import java.util.List;

import org.freedesktop.dbus.types.UInt64;
import org.testng.Assert;
import org.testng.annotations.Test;

public class BindPathTest {

    @Test(description = "Tests parameterized constructor.")
    public void testConstructor() {
        BindPath instance = new BindPath(new Object[] {
                "foo",
                "bar",
                true,
                new UInt64("1234")
        });

        Assert.assertNotNull(instance);
        Assert.assertEquals(instance.getSource(), "foo");
        Assert.assertEquals(instance.getDestination(), "bar");
        Assert.assertTrue(instance.isIgnoreErrorNoEntity());
        Assert.assertEquals(instance.getRecursive().intValue(), 1234);
    }

    @Test(description = "Tests constructor failure cases due to malformed arguments.", expectedExceptions = ArrayIndexOutOfBoundsException.class)
    public void testConstructorFailures() {
        new BindPath(new Object[0]);
    }

    @Test(description = "Tests processing of multiple data rows.")
    public void testBulkProcessing() {
        List<Object[]> source = new ArrayList<>();

        List<BindPath> list = BindPath.list(source);

        Assert.assertNotNull(list);
        Assert.assertEquals(list.size(), 0);

        // Next test
        source.add(new Object[] {
                "foo0",
                "bar0",
                true,
                new UInt64("1234")
        });
        source.add(new Object[] {
                "foo1",
                "bar1",
                false,
                new UInt64("2345")
        });

        list = BindPath.list(source);

        Assert.assertNotNull(list);
        Assert.assertEquals(list.size(), 2);

        BindPath item = list.get(0);

        Assert.assertNotNull(item);
        Assert.assertEquals(item.getSource(), "foo0");
        Assert.assertEquals(item.getDestination(), "bar0");
        Assert.assertTrue(item.isIgnoreErrorNoEntity());
        Assert.assertEquals(item.getRecursive().intValue(), 1234);

        item = list.get(1);

        Assert.assertNotNull(item);
        Assert.assertEquals(item.getSource(), "foo1");
        Assert.assertEquals(item.getDestination(), "bar1");
        Assert.assertFalse(item.isIgnoreErrorNoEntity());
        Assert.assertEquals(item.getRecursive().intValue(), 2345);
    }

    @Test(description = "Tests processing failure cases on multiple data rows.", expectedExceptions = ArrayIndexOutOfBoundsException.class)
    public void testBulkProcessingFailureEmptyArray() {
        List<Object[]> list = new ArrayList<>();
        list.add(new Object[0]);

        BindPath.list(list);
    }

    @Test(description = "Tests processing failure cases on multiple data rows.", expectedExceptions = ClassCastException.class)
    public void testBulkProcessingFailureMalformedArray() {
        List<Object[]> list = new ArrayList<>();
        list.add(new Object[] {
                "foo",
                null,
                false,
                1234
        });

        BindPath.list(list);
    }

}
