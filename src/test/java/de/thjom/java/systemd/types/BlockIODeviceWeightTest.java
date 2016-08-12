/*
 * Java-systemd implementation
 * Copyright (c) 2016 Markus Enax
 *
 * This program is free software; you can redistribute it and/or modify it
 * under the terms of either the GNU Lesser General Public License Version 2 or the
 * Academic Free Licence Version 2.1.
 *
 * Full licence texts are included in the COPYING file with this program.
 */

package de.thjom.java.systemd.types;

import java.math.BigInteger;
import java.util.List;
import java.util.Vector;

import org.freedesktop.dbus.UInt64;
import org.testng.Assert;
import org.testng.annotations.Test;

public class BlockIODeviceWeightTest {

    @Test(description="Tests parameterized constructor.")
    public void testConstructor() {
        BlockIODeviceWeight instance = new BlockIODeviceWeight(new Object[] { "foo", new UInt64("23") });

        Assert.assertNotNull(instance);
        Assert.assertEquals(instance.getFilePath(), "foo");
        Assert.assertEquals(instance.getWeight(), new BigInteger("23"));
    }

    @Test(description="Tests constructor failure cases due to malformed arguments.")
    public void testConstructorFailures() {
        Exception exc = null;

        try {
            new BlockIODeviceWeight(new Object[0]);
        }
        catch (Exception e) {
            exc = e;
        }

        Assert.assertEquals(exc.getClass(), ArrayIndexOutOfBoundsException.class);
    }

    @Test(description="Tests processing of multiple data rows.")
    public void testBulkProcessing() {
        Vector<Object[]> vec = new Vector<>();
        vec.add(new Object[] { "foo", new UInt64("23") });
        vec.add(new Object[] { "bar", new UInt64("42") });

        List<BlockIODeviceWeight> list = BlockIODeviceWeight.list(vec);

        Assert.assertNotNull(list);
        Assert.assertEquals(list.size(), 2);

        BlockIODeviceWeight item = list.get(0);

        Assert.assertNotNull(item);
        Assert.assertEquals(item.getFilePath(), "foo");
        Assert.assertEquals(item.getWeight(), new BigInteger("23"));

        item = list.get(1);

        Assert.assertNotNull(item);
        Assert.assertEquals(item.getFilePath(), "bar");
        Assert.assertEquals(item.getWeight(), new BigInteger("42"));
    }

    @Test(description="Tests processing failure cases on multiple data rows.")
    public void testBulkProcessingFailures() {
        Vector<Object[]> vec = new Vector<>();
        vec.add(new Object[] { "foo", (int) 1 });

        Exception exc = null;

        try {
            BlockIODeviceWeight.list(vec);
        }
        catch (Exception e) {
            exc = e;
        }

        Assert.assertEquals(exc.getClass(), ClassCastException.class);
    }

}
