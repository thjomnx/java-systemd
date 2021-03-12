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

import de.thjom.java.systemd.interfaces.PropertyInterface;

public class PropertiesTest extends AbstractTestCase {

    @Mock
    PropertyInterface piface;

    @Override
    @BeforeClass
    public void setup() {
        super.setup();

        try {
            Mockito.when(piface.getObjectPath()).thenReturn("foo");
            Mockito.when(dbus.getRemoteObject(Mockito.eq(Systemd.SERVICE_NAME), Mockito.anyString(), Mockito.eq(PropertyInterface.class))).thenReturn(piface);
            Mockito.when(dbus.getUniqueName()).thenReturn("unique");
        }
        catch (DBusException e) {
            Assert.fail(e.getMessage(), e);
        }

        setupPropertyMocks(Unit.class, Unit.SERVICE_NAME, Unit.Property.getAllNames());
    }

    @Test
    public void testEquals() throws DBusException {
        Properties foo0 = Properties.create(dbus, "dummy", Properties.SERVICE_NAME + "foo");
        Properties foo1 = Properties.create(dbus, "dummy", Properties.SERVICE_NAME + "foo");
        Properties bar = Properties.create(dbus, "dummy", Properties.SERVICE_NAME + "bar");

        Assert.assertNotEquals(foo0, null);
        Assert.assertNotEquals(foo0, new Object());
        Assert.assertEquals(foo0, foo0);
        Assert.assertEquals(foo0, foo1);
        Assert.assertNotEquals(foo0, bar);
        Assert.assertNotEquals(foo1, bar);
    }

    @Test
    public void testHashCode() throws DBusException {
        Properties foo0 = Properties.create(dbus, "dummy", Properties.SERVICE_NAME + "foo");
        Properties foo1 = Properties.create(dbus, "dummy", Properties.SERVICE_NAME + "foo");
        Properties bar = Properties.create(dbus, "dummy", Properties.SERVICE_NAME + "bar");

        Assert.assertEquals(foo0.hashCode(), foo0.hashCode());
        Assert.assertEquals(foo0.hashCode(), foo1.hashCode());
        Assert.assertNotEquals(foo0.hashCode(), bar.hashCode());
        Assert.assertNotEquals(foo1.hashCode(), bar.hashCode());
    }

}
