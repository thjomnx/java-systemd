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
import org.systemd.interfaces.MountInterface;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class MountTest extends UnitTest {

    private static final String[] NON_VARIANT_PROPERTIES = {

            Mount.Property.APP_ARMOR_PROFILE,
            Mount.Property.BLOCK_IODEVICE_WEIGHT,
            Mount.Property.BLOCK_IOREAD_BANDWIDTH,
            Mount.Property.BLOCK_IOWRITE_BANDWIDTH,
            Mount.Property.DEVICE_ALLOW,
            Mount.Property.ENVIRONMENT_FILES,
            Mount.Property.EXEC_MOUNT,
            Mount.Property.EXEC_REMOUNT,
            Mount.Property.EXEC_UNMOUNT,
            Mount.Property.RESTRICT_ADDRESS_FAMILIES,
            Mount.Property.SELINUX_CONTEXT,
            Mount.Property.SMACK_PROCESS_LABEL,
            Mount.Property.SYSTEM_CALL_FILTER

    };

    @Mock
    private MountInterface miface;

    private Mount mount;

    @Override
    @BeforeClass
    public void setup() {
        super.setup();

        try {
            Mockito.when(miface.getObjectPath()).thenReturn("foo");
            Mockito.when(dbus.getRemoteObject(Mockito.eq(Systemd.SERVICE_NAME), Mockito.anyString(), Mockito.eq(MountInterface.class))).thenReturn(miface);
        }
        catch (DBusException e) {
            Assert.fail(e.getMessage(), e);
        }

        setupPropertyMocks(Mount.class, Mount.SERVICE_NAME, Mount.Property.getAllNames());
    }

    @Test(description="Tests basic manager accessibility.")
    public void testAccess() {
        try {
            mount = systemd.getManager().getMount("tmp.mount");
        }
        catch (DBusException e) {
            Assert.fail(e.getMessage(), e);
        }

        Assert.assertNotNull(mount);
    }

    @Test(dependsOnMethods={ "testAccess" }, description="Tests property access of service interface.")
    public void testProperties() {
        testUnitProperties(mount);

        List<String> nonVariants = Arrays.asList(NON_VARIANT_PROPERTIES);

        for (String propertyName : Mount.Property.getAllNames()) {
            if (!nonVariants.contains(propertyName)) {
                Object value = mount.getProperties().getVariant(propertyName).getValue();

                Assert.assertNotNull(value);
            }
        }
    }

}
