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

import org.testng.Assert;
import org.testng.annotations.Test;

public class MountImageTest {

    @Test(description = "Tests parameterized constructor.")
    public void testConstructor() {
        List<Object[]> imageOptions = new ArrayList<>();
        imageOptions.add(new Object[] { "arg0", "arg1" });

        MountImage instance = new MountImage(new Object[] {
                "foo",
                "bar",
                true,
                imageOptions
        }
        );

        Assert.assertNotNull(instance);
        Assert.assertEquals(instance.getSource(), "foo");
        Assert.assertEquals(instance.getDestination(), "bar");
        Assert.assertTrue(instance.isIgnoreENOENT());
        Assert.assertEquals(instance.getMountOptions().get(0).getPartitionDesignator(), "arg0");
        Assert.assertEquals(instance.getMountOptions().get(0).getOptions(), "arg1");
    }

    @Test(description = "Tests constructor failure cases due to malformed arguments.", expectedExceptions = ArrayIndexOutOfBoundsException.class)
    public void testConstructorFailures() {
        new MountImage(new Object[0]);
    }

    @Test(description = "Tests processing of multiple data rows.")
    public void testBulkProcessing() {
        List<Object[]> source = new ArrayList<>();

        List<MountImage> list = MountImage.list(source);

        Assert.assertNotNull(list);
        Assert.assertEquals(list.size(), 0);

        // Next test
        List<Object[]> imageOptions0 = new ArrayList<>();
        imageOptions0.add(new Object[] { "arg00", "arg01" });

        List<Object[]> imageOptions1 = new ArrayList<>();
        imageOptions1.add(new Object[] { "arg10", "arg11" });

        source.add(new Object[] {
                        "foo0",
                        "bar0",
                        true,
                        imageOptions0
                }
        );
        source.add(new Object[] {
                        "foo1",
                        "bar1",
                        false,
                        imageOptions1
                }
        );

        list = MountImage.list(source);

        Assert.assertNotNull(list);
        Assert.assertEquals(list.size(), 2);

        MountImage item = list.get(0);

        Assert.assertNotNull(item);
        Assert.assertNotNull(item);
        Assert.assertEquals(item.getSource(), "foo0");
        Assert.assertEquals(item.getDestination(), "bar0");
        Assert.assertTrue(item.isIgnoreENOENT());
        Assert.assertEquals(item.getMountOptions().get(0).getPartitionDesignator(), "arg00");
        Assert.assertEquals(item.getMountOptions().get(0).getOptions(), "arg01");

        item = list.get(1);

        Assert.assertNotNull(item);
        Assert.assertNotNull(item);
        Assert.assertNotNull(item);
        Assert.assertEquals(item.getSource(), "foo1");
        Assert.assertEquals(item.getDestination(), "bar1");
        Assert.assertFalse(item.isIgnoreENOENT());
        Assert.assertEquals(item.getMountOptions().get(0).getPartitionDesignator(), "arg10");
        Assert.assertEquals(item.getMountOptions().get(0).getOptions(), "arg11");
    }

    @Test(description = "Tests processing failure cases on multiple data rows.", expectedExceptions = ArrayIndexOutOfBoundsException.class)
    public void testBulkProcessingFailureEmptyArray() {
        List<Object[]> list = new ArrayList<>();
        list.add(new Object[0]);

        MountImage.list(list);
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

        MountImage.list(list);
    }

}