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
import org.systemd.interfaces.SnapshotInterface;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class SnapshotTest extends UnitTest {

    @Mock
    private SnapshotInterface siface;

    private Snapshot snapshot;

    @Override
    @BeforeClass
    public void setup() {
        super.setup();

        try {
            Mockito.when(siface.getObjectPath()).thenReturn("foo");
            Mockito.when(dbus.getRemoteObject(Mockito.eq(Systemd.SERVICE_NAME), Mockito.anyString(), Mockito.eq(SnapshotInterface.class))).thenReturn(siface);
        }
        catch (DBusException e) {
            Assert.fail(e.getMessage(), e);
        }

        setupPropertyMocks(Snapshot.class, Snapshot.SERVICE_NAME, Snapshot.Property.getAllNames());
    }

    @Test(description="Tests basic manager accessibility.")
    public void testAccess() {
        try {
            snapshot = systemd.getManager().getSnapshot("there is none so far");
        }
        catch (DBusException e) {
            Assert.fail(e.getMessage(), e);
        }

        Assert.assertNotNull(snapshot);
    }

    @Test(dependsOnMethods={ "testAccess" }, description="Tests property access of snapshot interface.")
    public void testProperties() {
        testUnitProperties(snapshot);

        for (String propertyName : Snapshot.Property.getAllNames()) {
            Object value = snapshot.getProperties().getVariant(propertyName).getValue();

            Assert.assertNotNull(value);
        }
    }

}
