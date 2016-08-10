/*
 * Java-systemd implementation
 * Copyright (c) 2016 Markus Enax
 *
 * This program is free software; you can redistribute it and/or modify it under
 * the terms of either the GNU Lesser General Public License Version 2 or the
 * Academic Free Licence Version 2.1.
 *
 * Full licence texts are included in the COPYING file with this program.
 */

package de.thjom.java.systemd.types;

import java.util.Vector;

import org.testng.Assert;
import org.testng.annotations.Test;

public class AddressFamilyRestrictionTest {

    @Test(description="Tests parameterized constructor.")
    public void testConstructor() {
        Vector<String> vec = new Vector<>();
        vec.add("foo");
        vec.add("bar");

        AddressFamilyRestriction instance = new AddressFamilyRestriction(new Object[] { true, vec });

        Assert.assertNotNull(instance);
        Assert.assertTrue(instance.isBlacklist());
        Assert.assertEquals(instance.getAddressFamilies(), vec);
    }

    @Test(description="Tests constructor failure cases due to malformed arguments.")
    public void testConstructorFailures() {
        Exception exc = null;

        try {
            new AddressFamilyRestriction(new Object[0]);
        }
        catch (Exception e) {
            exc = e;
        }

        Assert.assertEquals(exc.getClass(), ArrayIndexOutOfBoundsException.class);
    }

}
