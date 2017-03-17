/*
 * Java-systemd implementation
 * Copyright (c) 2016 Markus Enax
 *
 * This program is free software; you can redistribute it and/or modify it under
 * the terms of either the GNU Lesser General Public License Version 2 or the
 * Academic Free Licence Version 3.0.
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

import de.thjom.java.systemd.interfaces.TargetInterface;

public class TargetTest extends UnitTest {

    @Mock
    private TargetInterface tiface;

    private Target target;

    @Override
    @BeforeClass
    public void setup() {
        super.setup();

        try {
            Mockito.when(tiface.getObjectPath()).thenReturn("foo");
            Mockito.when(dbus.getRemoteObject(Mockito.eq(Systemd.SERVICE_NAME), Mockito.anyString(), Mockito.eq(TargetInterface.class))).thenReturn(tiface);
        }
        catch (DBusException e) {
            Assert.fail(e.getMessage(), e);
        }
    }

    @Test(description="Tests basic manager accessibility.")
    public void testAccess() {
        try {
            target = systemd.getManager().getTarget("basic.target");
        }
        catch (DBusException e) {
            Assert.fail(e.getMessage(), e);
        }

        Assert.assertNotNull(target);
    }

    @Test(dependsOnMethods={ "testAccess" }, description="Tests property access of target interface.")
    public void testProperties() {
        testUnitProperties(target, Target.Property.getAllNames());
    }

}
