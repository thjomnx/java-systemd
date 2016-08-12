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

public class DeviceAllowControlTest {

    @Test(description="Tests parameterized constructor.")
    public void testConstructor() {
        DeviceAllowControl instance = new DeviceAllowControl(new Object[] { "foo", "bar" });

        Assert.assertNotNull(instance);
        Assert.assertEquals(instance.getDeviceIdentifier(), "foo");
        Assert.assertEquals(instance.getAccessControl(), "bar");
    }

    @Test(description="Tests constructor failure cases due to malformed arguments.")
    public void testConstructorFailures() {
        Exception exc = null;

        try {
            new DeviceAllowControl(new Object[0]);
        }
        catch (Exception e) {
            exc = e;
        }

        Assert.assertEquals(exc.getClass(), ArrayIndexOutOfBoundsException.class);
    }

    @Test(description="Tests processing of multiple data rows.")
    public void testBulkProcessing() {
        Vector<Object[]> vec = new Vector<>();

        List<DeviceAllowControl> list = DeviceAllowControl.list(vec);

        Assert.assertNotNull(list);
        Assert.assertEquals(list.size(), 0);

        // Next test
        vec.add(new Object[] { "foo1", "bar1" });
        vec.add(new Object[] { "foo2", "bar2" });

        list = DeviceAllowControl.list(vec);

        Assert.assertNotNull(list);
        Assert.assertEquals(list.size(), 2);

        DeviceAllowControl item = list.get(0);

        Assert.assertNotNull(item);
        Assert.assertEquals(item.getDeviceIdentifier(), "foo1");
        Assert.assertEquals(item.getAccessControl(), "bar1");

        item = list.get(1);

        Assert.assertNotNull(item);
        Assert.assertEquals(item.getDeviceIdentifier(), "foo2");
        Assert.assertEquals(item.getAccessControl(), "bar2");
    }

    @Test(description="Tests processing failure cases on multiple data rows.")
    public void testBulkProcessingFailures() {
        Vector<Object[]> vec = new Vector<>();
        vec.add(new Object[0]);

        Exception exc = null;

        try {
            DeviceAllowControl.list(vec);
        }
        catch (Exception e) {
            exc = e;
        }

        Assert.assertEquals(exc.getClass(), ArrayIndexOutOfBoundsException.class);
    }

}
