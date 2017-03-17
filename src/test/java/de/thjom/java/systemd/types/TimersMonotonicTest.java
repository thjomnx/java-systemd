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

import java.util.List;
import java.util.Vector;

import org.freedesktop.dbus.UInt64;
import org.testng.Assert;
import org.testng.annotations.Test;

public class TimersMonotonicTest {

    @Test(description="Tests parameterized constructor.")
    public void testConstructor() {
        TimersMonotonic instance = new TimersMonotonic(new Object[] { "foo", new UInt64("1234"), new UInt64("2345") });

        Assert.assertNotNull(instance);
        Assert.assertEquals(instance.getTimerBase(), "foo");
        Assert.assertEquals(instance.getOffsetUSec(), (long) 1234);
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
        Vector<Object[]> vec = new Vector<>();

        List<TimersMonotonic> list = TimersMonotonic.list(vec);

        Assert.assertNotNull(list);
        Assert.assertEquals(list.size(), 0);

        // Next test
        vec.add(new Object[] { "foo1", new UInt64("1234"), new UInt64("2345") });
        vec.add(new Object[] { "foo2", new UInt64("4321"), new UInt64("5432") });

        list = TimersMonotonic.list(vec);

        Assert.assertNotNull(list);
        Assert.assertEquals(list.size(), 2);

        TimersMonotonic item = list.get(0);

        Assert.assertNotNull(item);
        Assert.assertEquals(item.getTimerBase(), "foo1");
        Assert.assertEquals(item.getOffsetUSec(), (long) 1234);
        Assert.assertEquals(item.getNextElapsePoint(), (long) 2345);

        item = list.get(1);

        Assert.assertNotNull(item);
        Assert.assertEquals(item.getTimerBase(), "foo2");
        Assert.assertEquals(item.getOffsetUSec(), (long) 4321);
        Assert.assertEquals(item.getNextElapsePoint(), (long) 5432);
    }

    @Test(description="Tests processing failure cases on multiple data rows.")
    public void testBulkProcessingFailures() {
        Vector<Object[]> vec = new Vector<>();
        vec.add(new Object[0]);

        Exception exc = null;

        try {
            TimersMonotonic.list(vec);
        }
        catch (Exception e) {
            exc = e;
        }

        Assert.assertEquals(exc.getClass(), ArrayIndexOutOfBoundsException.class);

        // Next test
        vec.clear();
        vec.add(new Object[] { "foo", "bar", 1 });

        exc = null;

        try {
            TimersMonotonic.list(vec);
        }
        catch (Exception e) {
            exc = e;
        }

        Assert.assertEquals(exc.getClass(), ClassCastException.class);
    }

}
