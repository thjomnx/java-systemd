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

import de.thjom.java.systemd.interfaces.PathInterface;

public class PathTest extends UnitTest {

    @Mock
    private PathInterface piface;

    private Path path;

    @Override
    @BeforeClass
    public void setup() {
        super.setup();

        try {
            Mockito.when(piface.getObjectPath()).thenReturn("foo");
            Mockito.when(dbus.getRemoteObject(Mockito.eq(Systemd.SERVICE_NAME), Mockito.anyString(), Mockito.eq(PathInterface.class))).thenReturn(piface);
        }
        catch (DBusException e) {
            Assert.fail(e.getMessage(), e);
        }

        setupPropertyMocks(Path.class, Path.SERVICE_NAME, Path.Property.getAllNames());

        nonVariantProperties.add(Path.Property.PATHS);
    }

    @Test(description="Tests basic manager accessibility.")
    public void testAccess() {
        try {
            path = systemd.getManager().getPath("systemd-ask-password-console");
        }
        catch (DBusException e) {
            Assert.fail(e.getMessage(), e);
        }

        Assert.assertNotNull(path);
    }

    @Test(dependsOnMethods={ "testAccess" }, description="Tests property access of path interface.")
    public void testProperties() {
        testUnitProperties(path, Path.Property.getAllNames());
    }

}
