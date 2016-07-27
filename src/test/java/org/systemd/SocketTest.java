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

import java.util.Arrays;
import java.util.List;

import org.freedesktop.dbus.exceptions.DBusException;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.systemd.interfaces.SocketInterface;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class SocketTest extends UnitTest {

    private static final String[] NON_VARIANT_PROPERTIES = {

            Socket.Property.APP_ARMOR_PROFILE,
            Socket.Property.BLOCK_IODEVICE_WEIGHT,
            Socket.Property.BLOCK_IOREAD_BANDWIDTH,
            Socket.Property.BLOCK_IOWRITE_BANDWIDTH,
            Socket.Property.DEVICE_ALLOW,
            Socket.Property.ENVIRONMENT_FILES,
            Socket.Property.EXEC_START_POST,
            Socket.Property.EXEC_START_PRE,
            Socket.Property.EXEC_STOP_POST,
            Socket.Property.EXEC_STOP_PRE,
            Socket.Property.LISTEN,
            Socket.Property.RESTRICT_ADDRESS_FAMILIES,
            Socket.Property.SELINUX_CONTEXT,
            Socket.Property.SMACK_PROCESS_LABEL,
            Socket.Property.SYSTEM_CALL_FILTER

    };

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
        testUnitProperties(socket);

        List<String> nonVariants = Arrays.asList(NON_VARIANT_PROPERTIES);

        for (String propertyName : Socket.Property.getAllNames()) {
            if (!nonVariants.contains(propertyName)) {
                Object value = socket.getProperties().getVariant(propertyName).getValue();

                Assert.assertNotNull(value);
            }
        }
    }
}
