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

public class ImageOptionsTest {

    @Test(description = "Tests parameterized constructor.")
    public void testConstructor() {
        ImageOptions instance = new ImageOptions(new Object[] {
                "foo",
                "bar"
        }
        );

        Assert.assertNotNull(instance);
        Assert.assertEquals(instance.getPartitionDesignator(), "foo");
        Assert.assertEquals(instance.getOptions(), "bar");
    }

    @Test(description = "Tests constructor failure cases due to malformed arguments.", expectedExceptions = ArrayIndexOutOfBoundsException.class)
    public void testConstructorFailures() {
        new ImageOptions(new Object[0]);
    }

    @Test(description = "Tests processing of multiple data rows.")
    public void testBulkProcessing() {
        List<Object[]> source = new ArrayList<>();

        List<ImageOptions> list = ImageOptions.list(source);

        Assert.assertNotNull(list);
        Assert.assertEquals(list.size(), 0);

        // Next test
        source.add(new Object[] {
                        "foo",
                        "bar"
                }
        );
        source.add(new Object[] {
                        "foo",
                        "bar"
                }
        );

        list = ImageOptions.list(source);

        Assert.assertNotNull(list);
        Assert.assertEquals(list.size(), 2);

        ImageOptions item = list.get(0);

        Assert.assertNotNull(item);
        Assert.assertEquals(item.getPartitionDesignator(), "foo");
        Assert.assertEquals(item.getOptions(), "bar");

        item = list.get(1);

        Assert.assertNotNull(item);
        Assert.assertEquals(item.getPartitionDesignator(), "foo");
        Assert.assertEquals(item.getOptions(), "bar");
    }

    @Test(description = "Tests processing failure cases on multiple data rows.", expectedExceptions = ArrayIndexOutOfBoundsException.class)
    public void testBulkProcessingFailureEmptyArray() {
        List<Object[]> list = new ArrayList<>();
        list.add(new Object[0]);

        ImageOptions.list(list);
    }

}