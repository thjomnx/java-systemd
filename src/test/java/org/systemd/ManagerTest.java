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

import org.freedesktop.dbus.DBusConnection;
import org.freedesktop.dbus.Variant;
import org.freedesktop.dbus.exceptions.DBusException;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;
import org.systemd.interfaces.ManagerInterface;
import org.systemd.interfaces.PropertyInterface;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class ManagerTest {

    @Mock
    private DBusConnection dbus;

    @Mock
    private ManagerInterface miface;

    @Mock
    private PropertyInterface piface;

    @InjectMocks
    private Systemd systemd;

    @BeforeClass
    public void setup() {
        MockitoAnnotations.initMocks(this);

        try {
            Mockito.when(dbus.getRemoteObject(Systemd.SERVICE_NAME, Systemd.OBJECT_PATH, ManagerInterface.class)).thenReturn(miface);
            Mockito.when(dbus.getRemoteObject(Systemd.SERVICE_NAME, Systemd.OBJECT_PATH, PropertyInterface.class)).thenReturn(piface);

            Mockito.when(piface.getProperty(Manager.SERVICE_NAME, Manager.Property.VERSION)).then(new Answer<Variant<?>>() {

                @Override
                public Variant<?> answer(InvocationOnMock invocation) throws Throwable {
                    Variant<?> var = new Variant<>("systemd 230");

                    return var;
                }

            });
        }
        catch (DBusException e) {
            Assert.fail(e.getMessage(), e);
        }
    }

    @Test
    public void testFoo() {
        Manager manager = null;

        try {
            manager = systemd.getManager();
        }
        catch (DBusException e) {
            Assert.fail(e.getMessage(), e);
        }

        Assert.assertNotNull(manager);
        Assert.assertEquals(manager.getVersion(), "systemd 230");
    }

}
