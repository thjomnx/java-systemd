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

public class ExtendedExecutionInfoTest {

    @Test(description = "Tests parameterized constructor.")
    public void testConstructor() {
        List<String> list = new ArrayList<>();
        list.add("arg1");
        list.add("arg2");

        ExtendedExecutionInfo instance = new ExtendedExecutionInfo(new Object[] {
                "foo",
                list,
                list,
                new UInt64("1234"),
                new UInt64("2345"),
                new UInt64("3456"),
                new UInt64("4567"),
                new UInt32("99"),
                23,
                42
        }
        );

        Assert.assertNotNull(instance);
        Assert.assertEquals(instance.getBinaryPath(), "foo");
        Assert.assertEquals(instance.getArguments(), list);
        Assert.assertEquals(instance.getCommandFlags(), list);
        Assert.assertEquals(instance.getLastStartTimestamp(), 1234L);
        Assert.assertEquals(instance.getLastStartTimestampMonotonic(), 2345L);
        Assert.assertEquals(instance.getLastFinishTimestamp(), 3456L);
        Assert.assertEquals(instance.getLastFinishTimestampMonotonic(), 4567L);
        Assert.assertEquals(instance.getProcessId(), 99);
        Assert.assertEquals(instance.getLastExitCode(), 23);
        Assert.assertEquals(instance.getLastExitStatus(), 42);
    }

    @Test(description = "Tests constructor failure cases due to malformed arguments.", expectedExceptions = ArrayIndexOutOfBoundsException.class)
    public void testConstructorFailures() {
        new ExtendedExecutionInfo(new Object[0]);
    }

    @Test(description = "Tests processing of multiple data rows.")
    public void testBulkProcessing() {
        List<Object[]> source = new ArrayList<>();

        List<ExtendedExecutionInfo> list = ExtendedExecutionInfo.list(source);

        Assert.assertNotNull(list);
        Assert.assertEquals(list.size(), 0);

        // Next test
        source.add(new Object[] {
                        "foo",
                        new ArrayList<String>(),
                        new ArrayList<String>(),
                        new UInt64("1234"),
                        new UInt64("2345"),
                        new UInt64("3456"),
                        new UInt64("4567"),
                        new UInt32("99"),
                        23,
                        42
                }
        );
        source.add(new Object[] {
                        "bar",
                        new ArrayList<String>(),
                        new ArrayList<String>(),
                        new UInt64("4321"),
                        new UInt64("5432"),
                        new UInt64("6543"),
                        new UInt64("7654"),
                        new UInt32("999"),
                        42,
                        23
                }
        );

        list = ExtendedExecutionInfo.list(source);

        Assert.assertNotNull(list);
        Assert.assertEquals(list.size(), 2);

        ExtendedExecutionInfo item = list.get(0);

        Assert.assertNotNull(item);
        Assert.assertEquals(item.getBinaryPath(), "foo");
        Assert.assertEquals(item.getArguments(), Collections.emptyList());
        Assert.assertEquals(item.getCommandFlags(), Collections.emptyList());
        Assert.assertEquals(item.getLastStartTimestamp(), 1234L);
        Assert.assertEquals(item.getLastStartTimestampMonotonic(), 2345L);
        Assert.assertEquals(item.getLastFinishTimestamp(), 3456L);
        Assert.assertEquals(item.getLastFinishTimestampMonotonic(), 4567L);
        Assert.assertEquals(item.getProcessId(), 99);
        Assert.assertEquals(item.getLastExitCode(), 23);
        Assert.assertEquals(item.getLastExitStatus(), 42);

        item = list.get(1);

        Assert.assertNotNull(item);
        Assert.assertEquals(item.getBinaryPath(), "bar");
        Assert.assertEquals(item.getArguments(), Collections.emptyList());
        Assert.assertEquals(item.getCommandFlags(), Collections.emptyList());
        Assert.assertEquals(item.getLastStartTimestamp(), 4321L);
        Assert.assertEquals(item.getLastStartTimestampMonotonic(), 5432L);
        Assert.assertEquals(item.getLastFinishTimestamp(), 6543L);
        Assert.assertEquals(item.getLastFinishTimestampMonotonic(), 7654L);
        Assert.assertEquals(item.getProcessId(), 999);
        Assert.assertEquals(item.getLastExitCode(), 42);
        Assert.assertEquals(item.getLastExitStatus(), 23);
    }

    @Test(description = "Tests processing failure cases on multiple data rows.", expectedExceptions = ArrayIndexOutOfBoundsException.class)
    public void testBulkProcessingFailureEmptyArray() {
        List<Object[]> list = new ArrayList<>();
        list.add(new Object[0]);

        ExtendedExecutionInfo.list(list);
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

        ExtendedExecutionInfo.list(list);
    }

}