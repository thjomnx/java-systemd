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

import org.freedesktop.dbus.Variant;
import org.freedesktop.dbus.exceptions.DBusException;
import org.mockito.Mockito;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class ManagerTest extends AbstractTestCase {

    @Override
    @BeforeClass
    public void setup() {
        super.setup();

        Mockito.when(piface.getProperty(Manager.SERVICE_NAME, Manager.Property.VERSION)).then(new Answer<Variant<?>>() {

            @Override
            public Variant<?> answer(InvocationOnMock invocation) throws Throwable {
                return new Variant<>("systemd 230");
            }

        });
    }

    @Test(description="Tests basic manager accessibility.")
    public void testManagerAccess() {
        Manager manager = null;

        try {
            manager = systemd.getManager();
        }
        catch (DBusException e) {
            Assert.fail(e.getMessage(), e);
        }

        Assert.assertNotNull(manager);
    }

    @Test(description="Tests property access of manager interface.")
    public void testManagerProperties() {
        Manager manager = null;

        try {
            manager = systemd.getManager();
        }
        catch (DBusException e) {
            Assert.fail(e.getMessage(), e);
        }

        Assert.assertEquals(manager.getVersion(), "systemd 230");
    }

}
