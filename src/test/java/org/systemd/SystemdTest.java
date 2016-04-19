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

import java.util.Date;

import org.freedesktop.dbus.DBusConnection;
import org.freedesktop.dbus.exceptions.DBusException;
import org.testng.Assert;
import org.testng.annotations.Test;

public class SystemdTest {

    @Test(description="Tests object path escape function.")
    public void testPathEscaping() {
        Assert.assertEquals(Systemd.escapePath("cronie.service"), "cronie_2eservice");
        Assert.assertEquals(Systemd.escapePath(new StringBuffer("systemd-logind.service")), "systemd_2dlogind_2eservice");
        Assert.assertEquals(Systemd.escapePath(new StringBuilder("user@1000.service")), "user_401000_2eservice");
    }

    @Test(description="Tests micro-timestamp conversion to java.util.Date object.")
    public void testTimestampConversion() {
        long tstamp = System.currentTimeMillis();
        Date now = new Date(tstamp);

        Assert.assertEquals(Systemd.timestampToDate(tstamp * 1000), now);
    }

    @Test(description="Tests D-Bus connectivity to system bus.")
    public void testSystemBusConnectivity() {
        Systemd systemd = new Systemd();

        Assert.assertFalse(systemd.isConnected());

        // Connect to bus
        try {
            systemd.connect();
        }
        catch (DBusException e) {
            Assert.fail(e.getMessage(), e);
        }

        Assert.assertTrue(systemd.isConnected());

        // Disconnect from bus
        systemd.disconnect();

        Assert.assertFalse(systemd.isConnected());

        // Reconnect to bus
        try {
            systemd.connect();
        }
        catch (DBusException e) {
            Assert.fail(e.getMessage(), e);
        }

        Assert.assertTrue(systemd.isConnected());

        // Disconnect from bus
        systemd.disconnect();

        Assert.assertFalse(systemd.isConnected());

        // Test initialization of internal object
        Assert.assertNotNull(systemd.getConnection());
    }

    @Test(description="Tests D-Bus connectivity to session (user) bus.")
    public void testSessionBusConnectivity() {
        Systemd systemd = new Systemd(DBusConnection.SESSION);

        Assert.assertFalse(systemd.isConnected());

        // Connect to bus
        try {
            systemd.connect();
        }
        catch (DBusException e) {
            Assert.fail(e.getMessage(), e);
        }

        Assert.assertTrue(systemd.isConnected());

        // Disconnect from bus
        systemd.disconnect();

        Assert.assertFalse(systemd.isConnected());

        // Reconnect to bus
        try {
            systemd.connect();
        }
        catch (DBusException e) {
            Assert.fail(e.getMessage(), e);
        }

        Assert.assertTrue(systemd.isConnected());

        // Disconnect from bus
        systemd.disconnect();

        Assert.assertFalse(systemd.isConnected());

        // Test initialization of internal object
        Assert.assertNotNull(systemd.getConnection());
    }

    public void testSystemManagerCreation() {
        Systemd systemd = new Systemd();

        try {
            systemd.connect();

            Assert.assertNotNull(systemd.getManager());
        }
        catch (DBusException e) {
            Assert.fail(e.getMessage(), e);
        }
        finally {
            systemd.disconnect();
        }
    }

    public void testSessionManagerCreation() {
        Systemd systemd = new Systemd(DBusConnection.SESSION);

        try {
            systemd.connect();

            Assert.assertNotNull(systemd.getManager());
        }
        catch (DBusException e) {
            Assert.fail(e.getMessage(), e);
        }
        finally {
            systemd.disconnect();
        }
    }

    @Test(description="Tests manager creation on system bus in unconnected state.", expectedExceptions={ DBusException.class })
    public void testSystemManagerCreationUnconnected() throws DBusException {
        new Systemd().getManager();
    }

    @Test(description="Tests manager creation on session (user) bus in unconnected state.", expectedExceptions={ DBusException.class })
    public void testSessionManagerCreationUnconnected() throws DBusException {
        new Systemd(DBusConnection.SESSION).getManager();
    }

}
