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

import de.thjom.java.systemd.features.IoAccounting;
import de.thjom.java.systemd.features.IpAccounting;
import de.thjom.java.systemd.interfaces.SwapInterface;

public class SwapTest extends UnitTest {

    @Mock
    private SwapInterface siface;

    private Swap swap;

    @Override
    @BeforeClass
    public void setup() {
        super.setup();

        try {
            Mockito.when(siface.getObjectPath()).thenReturn("foo");
            Mockito.when(dbus.getRemoteObject(Mockito.eq(Systemd.SERVICE_NAME), Mockito.anyString(), Mockito.eq(SwapInterface.class))).thenReturn(siface);
        }
        catch (DBusException e) {
            Assert.fail(e.getMessage(), e);
        }

        setupPropertyMocks(Swap.class, Swap.SERVICE_NAME, Swap.Property.getAllNames());

        nonVariantProperties.add(Swap.Property.DEVICE_ALLOW);
        nonVariantProperties.add(Swap.Property.ENVIRONMENT_FILES);
        nonVariantProperties.add(Swap.Property.EXEC_ACTIVATE);
        nonVariantProperties.add(Swap.Property.EXEC_DEACTIVATE);
        nonVariantProperties.add(Swap.Property.SYSTEM_CALL_FILTER);

        nonVariantProperties.add(IpAccounting.Property.IP_ADDRESS_ALLOW);
        nonVariantProperties.add(IpAccounting.Property.IP_ADDRESS_DENY);

        nonVariantProperties.add(IoAccounting.Property.BLOCK_IO_DEVICE_WEIGHT);
        nonVariantProperties.add(IoAccounting.Property.BLOCK_IO_READ_BANDWIDTH);
        nonVariantProperties.add(IoAccounting.Property.BLOCK_IO_WRITE_BANDWIDTH);
        nonVariantProperties.add(IoAccounting.Property.IO_DEVICE_WEIGHT);
        nonVariantProperties.add(IoAccounting.Property.IO_READ_BANDWIDTH_MAX);
        nonVariantProperties.add(IoAccounting.Property.IO_READ_IOPS_MAX);
        nonVariantProperties.add(IoAccounting.Property.IO_WRITE_BANDWIDTH_MAX);
        nonVariantProperties.add(IoAccounting.Property.IO_WRITE_IOPS_MAX);
    }

    @Test(description="Tests basic manager accessibility.")
    public void testAccess() {
        try {
            swap = systemd.getManager().getSwap("dev-disk-by\\x2duuid-something");
        }
        catch (DBusException e) {
            Assert.fail(e.getMessage(), e);
        }

        Assert.assertNotNull(swap);
    }

    @Test(dependsOnMethods={ "testAccess" }, description="Tests property access of swap interface.")
    public void testProperties() {
        testUnitProperties(swap, Swap.Property.getAllNames());
    }

}
