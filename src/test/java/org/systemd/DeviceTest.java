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

import org.freedesktop.dbus.exceptions.DBusException;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.systemd.interfaces.DeviceInterface;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class DeviceTest extends UnitTest {

    @Mock
    private DeviceInterface diface;

    private Device device;

    @Override
    @BeforeClass
    public void setup() {
        super.setup();

        try {
            Mockito.when(diface.getObjectPath()).thenReturn("foo");
            Mockito.when(dbus.getRemoteObject(Mockito.eq(Systemd.SERVICE_NAME), Mockito.anyString(), Mockito.eq(DeviceInterface.class))).thenReturn(diface);
        }
        catch (DBusException e) {
            Assert.fail(e.getMessage(), e);
        }

        setupPropertyMocks(Device.class, Device.SERVICE_NAME, Device.Property.getAllNames());
    }

    @Test(description="Tests basic manager accessibility.")
    public void testAccess() {
        try {
            device = systemd.getManager().getDevice("sys-module-configfs.device");
        }
        catch (DBusException e) {
            Assert.fail(e.getMessage(), e);
        }

        Assert.assertNotNull(device);
    }

    @Test(dependsOnMethods={ "testAccess" }, description="Tests property access of device interface.")
    public void testProperties() {
        testUnitProperties(device, Device.Property.getAllNames());
    }

}
