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

public class EnvironmentFileTest {

    @Test(description="Tests parameterized constructor.")
    public void testConstructor() {
        EnvironmentFile instance = new EnvironmentFile(new Object[] { "foo", true });

        Assert.assertNotNull(instance);
        Assert.assertEquals(instance.getFilePath(), "foo");
        Assert.assertTrue(instance.isPrefixed());
    }

    @Test(description="Tests constructor failure cases due to malformed arguments.")
    public void testConstructorFailures() {
        Exception exc = null;

        try {
            new EnvironmentFile(new Object[0]);
        }
        catch (Exception e) {
            exc = e;
        }

        Assert.assertEquals(exc.getClass(), ArrayIndexOutOfBoundsException.class);
    }

    @Test(description="Tests processing of multiple data rows.")
    public void testBulkProcessing() {
        List<Object[]> source = new ArrayList<>();

        List<EnvironmentFile> list = EnvironmentFile.list(source);

        Assert.assertNotNull(list);
        Assert.assertEquals(list.size(), 0);

        // Next test
        source.add(new Object[] { "foo", true });
        source.add(new Object[] { "bar", false });

        list = EnvironmentFile.list(source);

        Assert.assertNotNull(list);
        Assert.assertEquals(list.size(), 2);

        EnvironmentFile item = list.get(0);

        Assert.assertNotNull(item);
        Assert.assertEquals(item.getFilePath(), "foo");
        Assert.assertTrue(item.isPrefixed());

        item = list.get(1);

        Assert.assertNotNull(item);
        Assert.assertEquals(item.getFilePath(), "bar");
        Assert.assertFalse(item.isPrefixed());
    }

    @Test(description="Tests processing failure cases on multiple data rows.")
    public void testBulkProcessingFailures() {
        List<Object[]> list = new ArrayList<>();
        list.add(new Object[0]);

        Exception exc = null;

        try {
            EnvironmentFile.list(list);
        }
        catch (Exception e) {
            exc = e;
        }

        Assert.assertEquals(exc.getClass(), ArrayIndexOutOfBoundsException.class);

        // Next test
        list.clear();
        list.add(new Object[] { "foo", 1 });

        exc = null;

        try {
            EnvironmentFile.list(list);
        }
        catch (Exception e) {
            exc = e;
        }

        Assert.assertEquals(exc.getClass(), ClassCastException.class);
    }

}
