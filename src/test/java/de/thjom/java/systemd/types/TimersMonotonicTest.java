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

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

import org.freedesktop.dbus.types.UInt64;
import org.testng.Assert;
import org.testng.annotations.Test;

public class TimersMonotonicTest {

    @Test(description="Tests parameterized constructor.")
    public void testConstructor() {
        TimersMonotonic instance = new TimersMonotonic(new Object[] { "foo", new UInt64("1234"), new UInt64("2345") });

        Assert.assertNotNull(instance);
        Assert.assertEquals(instance.getTimerBase(), "foo");
        Assert.assertEquals(instance.getOffsetUSec(), BigInteger.valueOf((long) 1234));
        Assert.assertEquals(instance.getNextElapsePoint(), (long) 2345);
    }

    @Test(description="Tests constructor failure cases due to malformed arguments.")
    public void testConstructorFailures() {
        Exception exc = null;

        try {
            new TimersMonotonic(new Object[0]);
        }
        catch (Exception e) {
            exc = e;
        }

        Assert.assertEquals(exc.getClass(), ArrayIndexOutOfBoundsException.class);
    }

    @Test(description="Tests processing of multiple data rows.")
    public void testBulkProcessing() {
        List<Object[]> source = new ArrayList<>();

        List<TimersMonotonic> list = TimersMonotonic.list(source);

        Assert.assertNotNull(list);
        Assert.assertEquals(list.size(), 0);

        // Next test
        source.add(new Object[] { "foo1", new UInt64("1234"), new UInt64("2345") });
        source.add(new Object[] { "foo2", new UInt64("4321"), new UInt64("5432") });

        list = TimersMonotonic.list(source);

        Assert.assertNotNull(list);
        Assert.assertEquals(list.size(), 2);

        TimersMonotonic item = list.get(0);

        Assert.assertNotNull(item);
        Assert.assertEquals(item.getTimerBase(), "foo1");
        Assert.assertEquals(item.getOffsetUSec(), BigInteger.valueOf((long) 1234));
        Assert.assertEquals(item.getNextElapsePoint(), (long) 2345);

        item = list.get(1);

        Assert.assertNotNull(item);
        Assert.assertEquals(item.getTimerBase(), "foo2");
        Assert.assertEquals(item.getOffsetUSec(), BigInteger.valueOf((long) 4321));
        Assert.assertEquals(item.getNextElapsePoint(), (long) 5432);
    }

    @Test(description="Tests processing failure cases on multiple data rows.")
    public void testBulkProcessingFailures() {
        List<Object[]> list = new ArrayList<>();
        list.add(new Object[0]);

        Exception exc = null;

        try {
            TimersMonotonic.list(list);
        }
        catch (Exception e) {
            exc = e;
        }

        Assert.assertEquals(exc.getClass(), ArrayIndexOutOfBoundsException.class);

        // Next test
        list.clear();
        list.add(new Object[] { "foo", "bar", 1 });

        exc = null;

        try {
            TimersMonotonic.list(list);
        }
        catch (Exception e) {
            exc = e;
        }

        Assert.assertEquals(exc.getClass(), ClassCastException.class);
    }

}
