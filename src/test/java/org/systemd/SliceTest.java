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

package org.systemd;

import java.util.Arrays;
import java.util.List;

import org.freedesktop.dbus.exceptions.DBusException;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.systemd.interfaces.SliceInterface;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class SliceTest extends UnitTest {

    private static final String[] NON_VARIANT_PROPERTIES = {

            Slice.Property.BLOCK_IODEVICE_WEIGHT,
            Slice.Property.BLOCK_IOREAD_BANDWIDTH,
            Slice.Property.BLOCK_IOWRITE_BANDWIDTH,
            Slice.Property.DEVICE_ALLOW

    };

    @Mock
    private SliceInterface siface;

    private Slice slice;

    @Override
    @BeforeClass
    public void setup() {
        super.setup();

        try {
            Mockito.when(siface.getObjectPath()).thenReturn("foo");
            Mockito.when(dbus.getRemoteObject(Mockito.eq(Systemd.SERVICE_NAME), Mockito.anyString(), Mockito.eq(SliceInterface.class))).thenReturn(siface);
        }
        catch (DBusException e) {
            Assert.fail(e.getMessage(), e);
        }

        setupPropertyMocks(Slice.class, Slice.SERVICE_NAME, Slice.Property.getAllNames());
    }

    @Test(description="Tests basic manager accessibility.")
    public void testAccess() {
        try {
            slice = systemd.getManager().getSlice("system");
        }
        catch (DBusException e) {
            Assert.fail(e.getMessage(), e);
        }

        Assert.assertNotNull(slice);
    }

    @Test(dependsOnMethods={ "testAccess" }, description="Tests property access of slice interface.")
    public void testProperties() {
        testUnitProperties(slice);

        List<String> nonVariants = Arrays.asList(NON_VARIANT_PROPERTIES);

        for (String propertyName : Slice.Property.getAllNames()) {
            if (!nonVariants.contains(propertyName)) {
                Object value = slice.getProperties().getVariant(propertyName).getValue();

                Assert.assertNotNull(value);
            }
        }
    }

}
