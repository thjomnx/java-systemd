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
import java.util.Collections;
import java.util.List;

import org.freedesktop.dbus.types.UInt32;
import org.freedesktop.dbus.types.UInt64;
import org.testng.Assert;
import org.testng.annotations.Test;

public class ExecutionInfoTest {

    @Test(description="Tests parameterized constructor.")
    public void testConstructor() {
        List<String> list = new ArrayList<>();
        list.add("arg1");
        list.add("arg2");

        ExecutionInfo instance = new ExecutionInfo(new Object[] {
                "foo",
                list,
                true,
                new UInt64("1234"),
                new UInt64("2345"),
                new UInt64("3456"),
                new UInt64("4567"),
                new UInt32("99"),
                (int) 23,
                (int) 42
                }
        );

        Assert.assertNotNull(instance);
        Assert.assertEquals(instance.getBinaryPath(), "foo");
        Assert.assertEquals(instance.getArguments(), list);
        Assert.assertTrue(instance.isFailOnUncleanExit());
        Assert.assertEquals(instance.getLastStartTimestamp(), (long) 1234);
        Assert.assertEquals(instance.getLastStartTimestampMonotonic(), (long) 2345);
        Assert.assertEquals(instance.getLastFinishTimestamp(), (long) 3456);
        Assert.assertEquals(instance.getLastFinishTimestampMonotonic(), (long) 4567);
        Assert.assertEquals(instance.getProcessId(), 99);
        Assert.assertEquals(instance.getLastExitCode(), 23);
        Assert.assertEquals(instance.getLastExitStatus(), 42);
    }

    @Test(description="Tests constructor failure cases due to malformed arguments.")
    public void testConstructorFailures() {
        Exception exc = null;

        try {
            new ExecutionInfo(new Object[0]);
        }
        catch (Exception e) {
            exc = e;
        }

        Assert.assertEquals(exc.getClass(), ArrayIndexOutOfBoundsException.class);
    }

    @Test(description="Tests processing of multiple data rows.")
    public void testBulkProcessing() {
        List<Object[]> source = new ArrayList<>();

        List<ExecutionInfo> list = ExecutionInfo.list(source);

        Assert.assertNotNull(list);
        Assert.assertEquals(list.size(), 0);

        // Next test
        source.add(new Object[] {
                "foo",
                new ArrayList<String>(),
                true,
                new UInt64("1234"),
                new UInt64("2345"),
                new UInt64("3456"),
                new UInt64("4567"),
                new UInt32("99"),
                (int) 23,
                (int) 42
                }
        );
        source.add(new Object[] {
                "bar",
                new ArrayList<String>(),
                false,
                new UInt64("4321"),
                new UInt64("5432"),
                new UInt64("6543"),
                new UInt64("7654"),
                new UInt32("999"),
                (int) 42,
                (int) 23
                }
        );

        list = ExecutionInfo.list(source);

        Assert.assertNotNull(list);
        Assert.assertEquals(list.size(), 2);

        ExecutionInfo item = list.get(0);

        Assert.assertNotNull(item);
        Assert.assertEquals(item.getBinaryPath(), "foo");
        Assert.assertEquals(item.getArguments(), Collections.emptyList());
        Assert.assertTrue(item.isFailOnUncleanExit());
        Assert.assertEquals(item.getLastStartTimestamp(), (long) 1234);
        Assert.assertEquals(item.getLastStartTimestampMonotonic(), (long) 2345);
        Assert.assertEquals(item.getLastFinishTimestamp(), (long) 3456);
        Assert.assertEquals(item.getLastFinishTimestampMonotonic(), (long) 4567);
        Assert.assertEquals(item.getProcessId(), 99);
        Assert.assertEquals(item.getLastExitCode(), 23);
        Assert.assertEquals(item.getLastExitStatus(), 42);

        item = list.get(1);

        Assert.assertNotNull(item);
        Assert.assertEquals(item.getBinaryPath(), "bar");
        Assert.assertEquals(item.getArguments(), Collections.emptyList());
        Assert.assertFalse(item.isFailOnUncleanExit());
        Assert.assertEquals(item.getLastStartTimestamp(), (long) 4321);
        Assert.assertEquals(item.getLastStartTimestampMonotonic(), (long) 5432);
        Assert.assertEquals(item.getLastFinishTimestamp(), (long) 6543);
        Assert.assertEquals(item.getLastFinishTimestampMonotonic(), (long) 7654);
        Assert.assertEquals(item.getProcessId(), 999);
        Assert.assertEquals(item.getLastExitCode(), 42);
        Assert.assertEquals(item.getLastExitStatus(), 23);
    }

    @Test(description="Tests processing failure cases on multiple data rows.")
    public void testBulkProcessingFailures() {
        List<Object[]> list = new ArrayList<>();
        list.add(new Object[0]);

        Exception exc = null;

        try {
            ExecutionInfo.list(list);
        }
        catch (Exception e) {
            exc = e;
        }

        Assert.assertEquals(exc.getClass(), ArrayIndexOutOfBoundsException.class);

        // Next test
        list.clear();
        list.add(new Object[] {
                "foo",
                null,
                true,
                1234,
                2345,
                3456,
                4567,
                99,
                23,
                42
                }
        );

        exc = null;

        try {
            ExecutionInfo.list(list);
        }
        catch (Exception e) {
            exc = e;
        }

        Assert.assertEquals(exc.getClass(), ClassCastException.class);
    }

}
