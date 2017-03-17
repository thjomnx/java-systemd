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

import org.testng.Assert;
import org.testng.annotations.Test;

public class ListenInfoTest {

    @Test(description="Tests parameterized constructor.")
    public void testConstructor() {
        ListenInfo instance = new ListenInfo(new Object[] { "foo", "bar" });

        Assert.assertNotNull(instance);
        Assert.assertEquals(instance.getSocketType(), "foo");
        Assert.assertEquals(instance.getFilePath(), "bar");
    }

    @Test(description="Tests constructor failure cases due to malformed arguments.")
    public void testConstructorFailures() {
        Exception exc = null;

        try {
            new ListenInfo(new Object[0]);
        }
        catch (Exception e) {
            exc = e;
        }

        Assert.assertEquals(exc.getClass(), ArrayIndexOutOfBoundsException.class);
    }

    @Test(description="Tests processing of multiple data rows.")
    public void testBulkProcessing() {
        Vector<Object[]> vec = new Vector<>();

        List<ListenInfo> list = ListenInfo.list(vec);

        Assert.assertNotNull(list);
        Assert.assertEquals(list.size(), 0);

        // Next test
        vec.add(new Object[] { "foo1", "bar1" });
        vec.add(new Object[] { "foo2", "bar2" });

        list = ListenInfo.list(vec);

        Assert.assertNotNull(list);
        Assert.assertEquals(list.size(), 2);

        ListenInfo item = list.get(0);

        Assert.assertNotNull(item);
        Assert.assertEquals(item.getSocketType(), "foo1");
        Assert.assertEquals(item.getFilePath(), "bar1");

        item = list.get(1);

        Assert.assertNotNull(item);
        Assert.assertEquals(item.getSocketType(), "foo2");
        Assert.assertEquals(item.getFilePath(), "bar2");
    }

    @Test(description="Tests processing failure cases on multiple data rows.")
    public void testBulkProcessingFailures() {
        Vector<Object[]> vec = new Vector<>();
        vec.add(new Object[0]);

        Exception exc = null;

        try {
            ListenInfo.list(vec);
        }
        catch (Exception e) {
            exc = e;
        }

        Assert.assertEquals(exc.getClass(), ArrayIndexOutOfBoundsException.class);
    }

}
