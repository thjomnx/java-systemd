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

package de.thjom.java.systemd;

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

    @Test(groups="requireSystemd", description="Tests D-Bus connectivity to system bus.")
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
        try {
            systemd.close();
        }
        catch (DBusException e) {
            Assert.fail(e.getMessage(), e);
        }

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
        try {
            systemd.close();
        }
        catch (DBusException e) {
            Assert.fail(e.getMessage(), e);
        }

        Assert.assertFalse(systemd.isConnected());

        // Test initialization of internal object
        Assert.assertNotNull(systemd.getConnection());
        Assert.assertFalse(systemd.getConnection().isPresent());
    }

    @Test(groups="requireSystemd", description="Tests D-Bus connectivity to session (user) bus.")
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
        try {
            systemd.close();
        }
        catch (DBusException e) {
            Assert.fail(e.getMessage(), e);
        }

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
        try {
            systemd.close();
        }
        catch (DBusException e) {
            Assert.fail(e.getMessage(), e);
        }

        Assert.assertFalse(systemd.isConnected());

        // Test initialization of internal object
        Assert.assertNotNull(systemd.getConnection());
        Assert.assertFalse(systemd.getConnection().isPresent());
    }

    @Test(groups="requireSystemd", description="Tests that 'close' method is idempotent (i.e. can be called multiple times without error).")
    public void testCloseIdempotence() {
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
        try {
            systemd.close();
        }
        catch (DBusException e) {
            Assert.fail(e.getMessage(), e);
        }

        Assert.assertFalse(systemd.isConnected());

        // Disconnect once more #1
        try {
            systemd.close();
        }
        catch (DBusException e) {
            Assert.fail(e.getMessage(), e);
        }

        Assert.assertFalse(systemd.isConnected());

        // Disconnect once more #2
        try {
            systemd.close();
        }
        catch (DBusException e) {
            Assert.fail(e.getMessage(), e);
        }

        Assert.assertFalse(systemd.isConnected());
    }

    @Test(groups="requireSystemd", description="Tests manager creation while connected to system bus.")
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
            try {
                systemd.close();
            }
            catch (DBusException e) {
                Assert.fail(e.getMessage(), e);
            }
        }
    }

    @Test(groups="requireSystemd", description="Tests manager creation while connected to session (user) bus.")
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
            try {
                systemd.close();
            }
            catch (DBusException e) {
                Assert.fail(e.getMessage(), e);
            }
        }
    }

    @SuppressWarnings("resource")
    @Test(groups="requireSystemd", description="Tests manager creation on system bus in unconnected state.", expectedExceptions={ DBusException.class })
    public void testSystemManagerCreationUnconnected() throws DBusException {
        new Systemd().getManager();
    }

    @SuppressWarnings("resource")
    @Test(groups="requireSystemd", description="Tests manager creation on session (user) bus in unconnected state.", expectedExceptions={ DBusException.class })
    public void testSessionManagerCreationUnconnected() throws DBusException {
        new Systemd(DBusConnection.SESSION).getManager();
    }

}
