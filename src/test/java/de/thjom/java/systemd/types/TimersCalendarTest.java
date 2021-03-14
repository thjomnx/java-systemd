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

public class TimersCalendarTest {

    @Test(description="Tests parameterized constructor.")
    public void testConstructor() {
        TimersCalendar instance = new TimersCalendar(new Object[] { "foo", "bar", new UInt64("1234") });

        Assert.assertNotNull(instance);
        Assert.assertEquals(instance.getTimerBase(), "foo");
        Assert.assertEquals(instance.getCalendar(), "bar");
        Assert.assertEquals(instance.getNextElapsePoint(), (long) 1234);
    }

    @Test(description="Tests constructor failure cases due to malformed arguments.")
    public void testConstructorFailures() {
        Exception exc = null;

        try {
            new TimersCalendar(new Object[0]);
        }
        catch (Exception e) {
            exc = e;
        }

        Assert.assertEquals(exc.getClass(), ArrayIndexOutOfBoundsException.class);
    }

    @Test(description="Tests processing of multiple data rows.")
    public void testBulkProcessing() {
        List<Object[]> source = new ArrayList<>();

        List<TimersCalendar> list = TimersCalendar.list(source);

        Assert.assertNotNull(list);
        Assert.assertEquals(list.size(), 0);

        // Next test
        source.add(new Object[] { "foo1", "bar1", new UInt64("1234") });
        source.add(new Object[] { "foo2", "bar2", new UInt64("5678") });

        list = TimersCalendar.list(source);

        Assert.assertNotNull(list);
        Assert.assertEquals(list.size(), 2);

        TimersCalendar item = list.get(0);

        Assert.assertNotNull(item);
        Assert.assertEquals(item.getTimerBase(), "foo1");
        Assert.assertEquals(item.getCalendar(), "bar1");
        Assert.assertEquals(item.getNextElapsePoint(), (long) 1234);

        item = list.get(1);

        Assert.assertNotNull(item);
        Assert.assertEquals(item.getTimerBase(), "foo2");
        Assert.assertEquals(item.getCalendar(), "bar2");
        Assert.assertEquals(item.getNextElapsePoint(), (long) 5678);
    }

    @Test(description="Tests processing failure cases on multiple data rows.")
    public void testBulkProcessingFailures() {
        List<Object[]> list = new ArrayList<>();
        list.add(new Object[0]);

        Exception exc = null;

        try {
            TimersCalendar.list(list);
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
            TimersCalendar.list(list);
        }
        catch (Exception e) {
            exc = e;
        }

        Assert.assertEquals(exc.getClass(), ClassCastException.class);
    }

}
