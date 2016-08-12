/*
 * Java-systemd implementation
 * Copyright (c) 2016 Markus Enax
 *
 * This program is free software; you can redistribute it and/or modify it
 * under the terms of either the GNU Lesser General Public License Version 2 or the
 * Academic Free Licence Version 2.1.
 *
 * Full licence texts are included in the COPYING file with this program.
 */

package de.thjom.java.systemd.types;

import java.util.List;
import java.util.Vector;

import org.testng.Assert;
import org.testng.annotations.Test;

public class ConditionTest {

    @Test(description="Tests parameterized constructor.")
    public void testConstructor() {
        Condition instance = new Condition(new Object[] { "foo", true, false, "bar", 23 });

        Assert.assertNotNull(instance);
        Assert.assertEquals(instance.getType(), "foo");
        Assert.assertTrue(instance.isTrigger());
        Assert.assertFalse(instance.isReversed());
        Assert.assertEquals(instance.getValue(), "bar");
        Assert.assertEquals(instance.getStatus(), 23);
    }

    @Test(description="Tests constructor failure cases due to malformed arguments.")
    public void testConstructorFailures() {
        Exception exc = null;

        try {
            new Condition(new Object[0]);
        }
        catch (Exception e) {
            exc = e;
        }

        Assert.assertEquals(exc.getClass(), ArrayIndexOutOfBoundsException.class);
    }

    @Test(description="Tests processing of multiple data rows.")
    public void testBulkProcessing() {
        Vector<Object[]> vec = new Vector<>();

        List<Condition> list = Condition.list(vec);

        Assert.assertNotNull(list);
        Assert.assertEquals(list.size(), 0);

        // Next test
        vec.add(new Object[] { "foo1", true, false, "bar1", 23 });
        vec.add(new Object[] { "foo2", false, true, "bar2", 42 });

        list = Condition.list(vec);

        Assert.assertNotNull(list);
        Assert.assertEquals(list.size(), 2);

        Condition item = list.get(0);

        Assert.assertNotNull(item);
        Assert.assertEquals(item.getType(), "foo1");
        Assert.assertTrue(item.isTrigger());
        Assert.assertFalse(item.isReversed());
        Assert.assertEquals(item.getValue(), "bar1");
        Assert.assertEquals(item.getStatus(), 23);

        item = list.get(1);

        Assert.assertNotNull(item);
        Assert.assertEquals(item.getType(), "foo2");
        Assert.assertFalse(item.isTrigger());
        Assert.assertTrue(item.isReversed());
        Assert.assertEquals(item.getValue(), "bar2");
        Assert.assertEquals(item.getStatus(), 42);
    }

    @Test(description="Tests processing failure cases on multiple data rows.")
    public void testBulkProcessingFailures() {
        Vector<Object[]> vec = new Vector<>();
        vec.add(new Object[0]);

        Exception exc = null;

        try {
            Condition.list(vec);
        }
        catch (Exception e) {
            exc = e;
        }

        Assert.assertEquals(exc.getClass(), ArrayIndexOutOfBoundsException.class);

        // Next test
        vec.clear();
        vec.add(new Object[] { "foo", 1 });

        exc = null;

        try {
            Condition.list(vec);
        }
        catch (Exception e) {
            exc = e;
        }

        Assert.assertEquals(exc.getClass(), ClassCastException.class);
    }

}
