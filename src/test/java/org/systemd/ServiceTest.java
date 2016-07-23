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
import org.systemd.interfaces.ServiceInterface;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class ServiceTest extends UnitTest {

    private static final String[] NON_VARIANT_PROPERTIES = {

            Service.Property.APP_ARMOR_PROFILE,
            Service.Property.BLOCK_IODEVICE_WEIGHT,
            Service.Property.BLOCK_IOREAD_BANDWIDTH,
            Service.Property.BLOCK_IOWRITE_BANDWIDTH,
            Service.Property.DEVICE_ALLOW,
            Service.Property.ENVIRONMENT_FILES,
            Service.Property.EXEC_RELOAD,
            Service.Property.EXEC_START,
            Service.Property.EXEC_START_POST,
            Service.Property.EXEC_START_PRE,
            Service.Property.EXEC_STOP,
            Service.Property.EXEC_STOP_POST,
            Service.Property.RESTRICT_ADDRESS_FAMILIES,
            Service.Property.SELINUX_CONTEXT,
            Service.Property.SMACK_PROCESS_LABEL,
            Service.Property.SYSTEM_CALL_FILTER

    };

    @Mock
    private ServiceInterface siface;

    private Service service;

    @Override
    @BeforeClass
    public void setup() {
        super.setup();

        try {
            Mockito.when(siface.getObjectPath()).thenReturn("foo");
            Mockito.when(dbus.getRemoteObject(Mockito.eq(Systemd.SERVICE_NAME), Mockito.anyString(), Mockito.eq(ServiceInterface.class))).thenReturn(siface);
        }
        catch (DBusException e) {
            Assert.fail(e.getMessage(), e);
        }

        setupPropertyMocks(Service.class, Service.SERVICE_NAME, Service.Property.getAllNames());
    }

    @Test(description="Tests basic manager accessibility.")
    public void testAccess() {
        try {
            service = systemd.getManager().getService("avahi-daemon");
        }
        catch (DBusException e) {
            Assert.fail(e.getMessage(), e);
        }

        Assert.assertNotNull(service);
    }

    @Test(dependsOnMethods={ "testAccess" }, description="Tests property access of service interface.")
    public void testProperties() {
        List<String> nonVariants = Arrays.asList(NON_VARIANT_PROPERTIES);

        for (String propertyName : Service.Property.getAllNames()) {
            if (!nonVariants.contains(propertyName)) {
                System.out.println("ServiceTest.testProperties()" + " - TESTING " + propertyName);
                Object value = service.getProperties().getVariant(propertyName).getValue();

                Assert.assertNotNull(value);
            }
        }
    }

}
