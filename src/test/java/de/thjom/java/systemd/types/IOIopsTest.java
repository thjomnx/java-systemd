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

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

import org.freedesktop.dbus.types.UInt64;
import org.testng.Assert;
import org.testng.annotations.Test;

public class IOIopsTest {

    @Test(description="Tests parameterized constructor.")
    public void testConstructor() {
        IOIops instance = new IOIops(new Object[] { "foo", new UInt64("23") });

        Assert.assertNotNull(instance);
        Assert.assertEquals(instance.getFilePath(), "foo");
        Assert.assertEquals(instance.getIops(), new BigInteger("23"));
    }

    @Test(description="Tests constructor failure cases due to malformed arguments.")
    public void testConstructorFailures() {
        Exception exc = null;

        try {
            new IOIops(new Object[0]);
        }
        catch (Exception e) {
            exc = e;
        }

        Assert.assertEquals(exc.getClass(), ArrayIndexOutOfBoundsException.class);
    }

    @Test(description="Tests processing of multiple data rows.")
    public void testBulkProcessing() {
        List<Object[]> source = new ArrayList<>();

        List<IOIops> list = IOIops.list(source);

        Assert.assertNotNull(list);
        Assert.assertEquals(list.size(), 0);

        // Next test
        source.add(new Object[] { "foo", new UInt64("23") });
        source.add(new Object[] { "bar", new UInt64("42") });

        list = IOIops.list(source);

        Assert.assertNotNull(source);
        Assert.assertEquals(source.size(), 2);

        IOIops item = list.get(0);

        Assert.assertNotNull(item);
        Assert.assertEquals(item.getFilePath(), "foo");
        Assert.assertEquals(item.getIops(), new BigInteger("23"));

        item = list.get(1);

        Assert.assertNotNull(item);
        Assert.assertEquals(item.getFilePath(), "bar");
        Assert.assertEquals(item.getIops(), new BigInteger("42"));
    }

    @Test(description="Tests processing failure cases on multiple data rows.")
    public void testBulkProcessingFailures() {
        List<Object[]> list = new ArrayList<>();
        list.add(new Object[0]);

        Exception exc = null;

        try {
            IOIops.list(list);
        }
        catch (Exception e) {
            exc = e;
        }

        Assert.assertEquals(exc.getClass(), ArrayIndexOutOfBoundsException.class);

        // Next test
        list.clear();
        list.add(new Object[] { "foo", (int) 1 });

        exc = null;

        try {
            IOIops.list(list);
        }
        catch (Exception e) {
            exc = e;
        }

        Assert.assertEquals(exc.getClass(), ClassCastException.class);
    }

}
