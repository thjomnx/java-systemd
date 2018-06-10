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

import de.thjom.java.systemd.features.DynamicUserAccounting;
import de.thjom.java.systemd.features.IoAccounting;
import de.thjom.java.systemd.features.IpAccounting;
import de.thjom.java.systemd.interfaces.MountInterface;

public class MountTest extends UnitTest {

    @Mock
    private MountInterface miface;

    private Mount mount;

    @Override
    @BeforeClass
    public void setup() {
        super.setup();

        try {
            Mockito.when(miface.getObjectPath()).thenReturn("foo");
            Mockito.when(dbus.getRemoteObject(Mockito.eq(Systemd.SERVICE_NAME), Mockito.anyString(), Mockito.eq(MountInterface.class))).thenReturn(miface);
        }
        catch (DBusException e) {
            Assert.fail(e.getMessage(), e);
        }

        setupPropertyMocks(Mount.class, Mount.SERVICE_NAME, Mount.Property.getAllNames());

        nonVariantProperties.add(Mount.Property.DEVICE_ALLOW);
        nonVariantProperties.add(Mount.Property.ENVIRONMENT_FILES);
        nonVariantProperties.add(Mount.Property.EXEC_MOUNT);
        nonVariantProperties.add(Mount.Property.EXEC_REMOUNT);
        nonVariantProperties.add(Mount.Property.EXEC_UNMOUNT);
        nonVariantProperties.add(Mount.Property.SYSTEM_CALL_FILTER);

        nonVariantProperties.add(DynamicUserAccounting.Property.APP_ARMOR_PROFILE);
        nonVariantProperties.add(DynamicUserAccounting.Property.BIND_PATHS);
        nonVariantProperties.add(DynamicUserAccounting.Property.BIND_READ_ONLY_PATHS);
        nonVariantProperties.add(DynamicUserAccounting.Property.LOG_EXTRA_FIELDS);
        nonVariantProperties.add(DynamicUserAccounting.Property.RESTRICT_ADDRESS_FAMILIES);
        nonVariantProperties.add(DynamicUserAccounting.Property.SELINUX_CONTEXT);
        nonVariantProperties.add(DynamicUserAccounting.Property.SMACK_PROCESS_LABEL);
        nonVariantProperties.add(DynamicUserAccounting.Property.TEMPORARY_FILE_SYSTEM);

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
            mount = systemd.getManager().getMount("tmp.mount");
        }
        catch (DBusException e) {
            Assert.fail(e.getMessage(), e);
        }

        Assert.assertNotNull(mount);
    }

    @Test(dependsOnMethods={ "testAccess" }, description="Tests property access of mount interface.")
    public void testProperties() {
        testUnitProperties(mount, Mount.Property.getAllNames());
    }

}
