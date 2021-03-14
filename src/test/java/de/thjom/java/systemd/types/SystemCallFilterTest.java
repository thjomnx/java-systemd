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

public class SystemCallFilterTest {

    @Test(description="Tests parameterized constructor.")
    public void testConstructor() {
        List<String> list = new ArrayList<>();
        list.add("foo");
        list.add("bar");

        SystemCallFilter instance = new SystemCallFilter(new Object[] { true, list });

        Assert.assertNotNull(instance);
        Assert.assertTrue(instance.isBlacklist());
        Assert.assertEquals(instance.getSysCalls(), list);
    }

    @Test(description="Tests constructor failure cases due to malformed arguments.")
    public void testConstructorFailures() {
        Exception exc = null;

        try {
            new SystemCallFilter(new Object[0]);
        }
        catch (Exception e) {
            exc = e;
        }

        Assert.assertEquals(exc.getClass(), ArrayIndexOutOfBoundsException.class);
    }

}
