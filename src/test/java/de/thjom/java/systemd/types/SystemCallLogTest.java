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

public class SystemCallLogTest {

    @Test(description="Tests parameterized constructor.")
    public void testConstructor() {
        List<String> list = new ArrayList<>();
        list.add("foo");
        list.add("bar");

        SystemCallLog instance = new SystemCallLog(new Object[] { true, list });

        Assert.assertNotNull(instance);
        Assert.assertTrue(instance.isAllowlist());
        Assert.assertEquals(instance.getSysCalls(), list);
    }

    @Test(description="Tests constructor failure cases due to malformed arguments.", expectedExceptions = ArrayIndexOutOfBoundsException.class)
    public void testConstructorFailures() {
        new SystemCallLog(new Object[0]);
    }

}
