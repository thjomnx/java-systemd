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

import org.freedesktop.dbus.Path;
import org.freedesktop.dbus.UInt32;
import org.testng.Assert;
import org.testng.annotations.Test;

public class JobTest {

    @Test(description="Tests parameterized constructor.")
    public void testConstructor() {
        Path path = new Path("foo");

        Job instance = new Job(new Object[] { new UInt32("1234"), path });

        Assert.assertNotNull(instance);
        Assert.assertEquals(instance.getId(), (long) 1234);
        Assert.assertSame(instance.getObjectPath(), path);
    }

    @Test(description="Tests constructor failure cases due to malformed arguments.")
    public void testConstructorFailures() {
        Exception exc = null;

        try {
            new Job(new Object[0]);
        }
        catch (Exception e) {
            exc = e;
        }

        Assert.assertEquals(exc.getClass(), ArrayIndexOutOfBoundsException.class);

        // Next test
        exc = null;

        try {
            new Job(new Object[] { 1234, null });
        }
        catch (Exception e) {
            exc = e;
        }

        Assert.assertEquals(exc.getClass(), ClassCastException.class);
    }
}
