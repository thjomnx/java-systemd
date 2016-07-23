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
import org.systemd.interfaces.AutomountInterface;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class AutomountTest extends UnitTest {

    @Mock
    private AutomountInterface amiface;

    private Automount automount;

    @Override
    @BeforeClass
    public void setup() {
        super.setup();

        try {
            Mockito.when(amiface.getObjectPath()).thenReturn("foo");
            Mockito.when(dbus.getRemoteObject(Mockito.eq(Systemd.SERVICE_NAME), Mockito.anyString(), Mockito.eq(AutomountInterface.class))).thenReturn(amiface);
        }
        catch (DBusException e) {
            Assert.fail(e.getMessage(), e);
        }

        setupPropertyMocks(Automount.class, Automount.SERVICE_NAME, Automount.Property.getAllNames());
    }

    @Test(description="Tests basic manager accessibility.")
    public void testAccess() {
        try {
            automount = systemd.getManager().getAutomount("proc-sys-fs-binfmt_misc.automount");
        }
        catch (DBusException e) {
            Assert.fail(e.getMessage(), e);
        }

        Assert.assertNotNull(automount);
    }

    @Test(dependsOnMethods={ "testAccess" }, description="Tests property access of automount interface.")
    public void testProperties() {
        testUnitProperties(automount);

        for (String propertyName : Automount.Property.getAllNames()) {
            Object value = automount.getProperties().getVariant(propertyName).getValue();

            Assert.assertNotNull(value);
        }
    }

}
