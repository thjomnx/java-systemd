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
import de.thjom.java.systemd.interfaces.SocketInterface;

public class SocketTest extends UnitTest {

    @Mock
    private SocketInterface siface;

    private Socket socket;

    @Override
    @BeforeClass
    public void setup() {
        super.setup();

        try {
            Mockito.when(siface.getObjectPath()).thenReturn("foo");
            Mockito.when(dbus.getRemoteObject(Mockito.eq(Systemd.SERVICE_NAME), Mockito.anyString(), Mockito.eq(SocketInterface.class))).thenReturn(siface);
        }
        catch (DBusException e) {
            Assert.fail(e.getMessage(), e);
        }

        setupPropertyMocks(Socket.class, Socket.SERVICE_NAME, Socket.Property.getAllNames());

        nonVariantProperties.add(Socket.Property.DEVICE_ALLOW);
        nonVariantProperties.add(Socket.Property.ENVIRONMENT_FILES);
        nonVariantProperties.add(Socket.Property.EXEC_START_POST);
        nonVariantProperties.add(Socket.Property.EXEC_START_PRE);
        nonVariantProperties.add(Socket.Property.EXEC_STOP_POST);
        nonVariantProperties.add(Socket.Property.EXEC_STOP_PRE);
        nonVariantProperties.add(Socket.Property.LISTEN);
        nonVariantProperties.add(Socket.Property.SELINUX_CONTEXT);
        nonVariantProperties.add(Socket.Property.SMACK_PROCESS_LABEL);
        nonVariantProperties.add(Socket.Property.SYSTEM_CALL_FILTER);

        nonVariantProperties.add(DynamicUserAccounting.Property.APP_ARMOR_PROFILE);
        nonVariantProperties.add(DynamicUserAccounting.Property.RESTRICT_ADDRESS_FAMILIES);

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
            socket = systemd.getManager().getSocket("dbus");
        }
        catch (DBusException e) {
            Assert.fail(e.getMessage(), e);
        }

        Assert.assertNotNull(socket);
    }

    @Test(dependsOnMethods={ "testAccess" }, description="Tests property access of socket interface.")
    public void testProperties() {
        testUnitProperties(socket, Socket.Property.getAllNames());
    }
}
