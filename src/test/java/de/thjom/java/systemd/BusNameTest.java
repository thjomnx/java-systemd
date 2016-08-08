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

package de.thjom.java.systemd;

import org.freedesktop.dbus.exceptions.DBusException;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import de.thjom.java.systemd.BusName;
import de.thjom.java.systemd.Systemd;
import de.thjom.java.systemd.interfaces.BusNameInterface;

public class BusNameTest extends UnitTest {

    @Mock
    private BusNameInterface bniface;

    private BusName busName;

    @Override
    @BeforeClass
    public void setup() {
        super.setup();

        try {
            Mockito.when(bniface.getObjectPath()).thenReturn("foo");
            Mockito.when(dbus.getRemoteObject(Mockito.eq(Systemd.SERVICE_NAME), Mockito.anyString(), Mockito.eq(BusNameInterface.class))).thenReturn(bniface);
        }
        catch (DBusException e) {
            Assert.fail(e.getMessage(), e);
        }

        setupPropertyMocks(BusName.class, BusName.SERVICE_NAME, BusName.Property.getAllNames());
    }

    @Test(description="Tests basic manager accessibility.")
    public void testAccess() {
        try {
            busName = systemd.getManager().getBusName("dummy");
        }
        catch (DBusException e) {
            Assert.fail(e.getMessage(), e);
        }

        Assert.assertNotNull(busName);
    }

    @Test(dependsOnMethods={ "testAccess" }, description="Tests property access of busname interface.")
    public void testProperties() {
        testUnitProperties(busName, BusName.Property.getAllNames());
    }

}
