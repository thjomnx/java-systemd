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

import org.testng.Assert;
import org.testng.annotations.Test;

public class PathInfoTest {

    @Test(description="Tests parameterized constructor.")
    public void testConstructor() {
        PathInfo instance = new PathInfo(new Object[] { "foo", "bar" });

        Assert.assertNotNull(instance);
        Assert.assertEquals(instance.getWatchCondition(), "foo");
        Assert.assertEquals(instance.getWatchedPath(), "bar");
    }

    @Test(description="Tests constructor failure cases due to malformed arguments.")
    public void testConstructorFailures() {
        Exception exc = null;

        try {
            new PathInfo(new Object[0]);
        }
        catch (Exception e) {
            exc = e;
        }

        Assert.assertEquals(exc.getClass(), ArrayIndexOutOfBoundsException.class);
    }

}
