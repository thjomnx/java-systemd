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

import de.thjom.java.systemd.interfaces.ScopeInterface;

public class ScopeTest extends UnitTest {

    @Mock
    private ScopeInterface siface;

    private Scope scope;

    @Override
    @BeforeClass
    public void setup() {
        super.setup();

        try {
            Mockito.when(siface.getObjectPath()).thenReturn("foo");
            Mockito.when(dbus.getRemoteObject(Mockito.eq(Systemd.SERVICE_NAME), Mockito.anyString(), Mockito.eq(ScopeInterface.class))).thenReturn(siface);
        }
        catch (DBusException e) {
            Assert.fail(e.getMessage(), e);
        }

        setupPropertyMocks(Scope.class, Scope.SERVICE_NAME, Scope.Property.getAllNames());

        nonVariantProperties.add(Scope.Property.BLOCK_IO_DEVICE_WEIGHT);
        nonVariantProperties.add(Scope.Property.BLOCK_IO_READ_BANDWIDTH);
        nonVariantProperties.add(Scope.Property.BLOCK_IO_WRITE_BANDWIDTH);
        nonVariantProperties.add(Scope.Property.DEVICE_ALLOW);

        nonVariantProperties.add(IpAccountable.Property.IP_ADDRESS_ALLOW);
        nonVariantProperties.add(IpAccountable.Property.IP_ADDRESS_DENY);

        nonVariantProperties.add(IoAccountable.Property.IO_DEVICE_WEIGHT);
        nonVariantProperties.add(IoAccountable.Property.IO_READ_BANDWIDTH_MAX);
        nonVariantProperties.add(IoAccountable.Property.IO_READ_IOPS_MAX);
        nonVariantProperties.add(IoAccountable.Property.IO_WRITE_BANDWIDTH_MAX);
        nonVariantProperties.add(IoAccountable.Property.IO_WRITE_IOPS_MAX);
    }

    @Test(description="Tests basic manager accessibility.")
    public void testAccess() {
        try {
            scope = systemd.getManager().getScope("init");
        }
        catch (DBusException e) {
            Assert.fail(e.getMessage(), e);
        }

        Assert.assertNotNull(scope);
    }

    @Test(dependsOnMethods={ "testAccess" }, description="Tests property access of scope interface.")
    public void testProperties() {
        testUnitProperties(scope, Scope.Property.getAllNames());
    }

}
