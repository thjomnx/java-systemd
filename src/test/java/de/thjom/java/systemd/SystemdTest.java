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

import org.freedesktop.dbus.exceptions.DBusException;
import org.testng.Assert;
import org.testng.annotations.Test;

import de.thjom.java.systemd.Systemd.InstanceType;

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

    @Test(groups="requireSystemd", description="Tests D-Bus connectivity to system instance.")
    public void testSystemInstanceConnectivity() {
        // Connects automatically to bus
        Systemd systemd = null;

        try {
            systemd = Systemd.get();
        }
        catch (DBusException e) {
            Assert.fail(e.getMessage(), e);
        }

        Assert.assertTrue(systemd.isConnected());

        // Disconnect from bus
        try {
            Systemd.disconnect();
        }
        catch (DBusException e) {
            Assert.fail(e.getMessage(), e);
        }

        Assert.assertFalse(systemd.isConnected());

        // Reconnect to bus
        try {
            systemd = Systemd.get();
        }
        catch (DBusException e) {
            Assert.fail(e.getMessage(), e);
        }

        Assert.assertTrue(systemd.isConnected());

        // Disconnect from bus
        try {
            Systemd.disconnect();
        }
        catch (DBusException e) {
            Assert.fail(e.getMessage(), e);
        }

        Assert.assertFalse(systemd.isConnected());

        // Test initialization of internal object
        Assert.assertNotNull(systemd.getConnection());
        Assert.assertFalse(systemd.getConnection().isPresent());
    }

    @Test(groups="requireSystemd", description="Tests D-Bus connectivity to user instance.")
    public void testUserInstanceConnectivity() {
        // Connects automatically to bus
        Systemd systemd = null;

        try {
            systemd = Systemd.get(InstanceType.USER);
        }
        catch (DBusException e) {
            Assert.fail(e.getMessage(), e);
        }

        Assert.assertTrue(systemd.isConnected());

        // Disconnect from bus
        try {
            Systemd.disconnect(InstanceType.USER);
        }
        catch (DBusException e) {
            Assert.fail(e.getMessage(), e);
        }

        Assert.assertFalse(systemd.isConnected());

        // Reconnect to bus
        try {
            systemd = Systemd.get(InstanceType.USER);
        }
        catch (DBusException e) {
            Assert.fail(e.getMessage(), e);
        }

        Assert.assertTrue(systemd.isConnected());

        // Disconnect from bus
        try {
            Systemd.disconnect(InstanceType.USER);
        }
        catch (DBusException e) {
            Assert.fail(e.getMessage(), e);
        }

        Assert.assertFalse(systemd.isConnected());

        // Test initialization of internal object
        Assert.assertNotNull(systemd.getConnection());
        Assert.assertFalse(systemd.getConnection().isPresent());
    }

    @Test(groups="requireSystemd", description="Tests that 'disconnect' method is idempotent (i.e. can be called multiple times without error).")
    public void testDisconnectIdempotence() {
        // Connects automatically to bus
        Systemd systemd = null;

        try {
            systemd = Systemd.get();
        }
        catch (DBusException e) {
            Assert.fail(e.getMessage(), e);
        }

        Assert.assertTrue(systemd.isConnected());

        // Disconnect from bus
        try {
            Systemd.disconnect();
        }
        catch (DBusException e) {
            Assert.fail(e.getMessage(), e);
        }

        Assert.assertFalse(systemd.isConnected());

        // Disconnect once more #1
        try {
            Systemd.disconnect();
        }
        catch (DBusException e) {
            Assert.fail(e.getMessage(), e);
        }

        Assert.assertFalse(systemd.isConnected());

        // Disconnect once more #2
        try {
            Systemd.disconnect();
        }
        catch (DBusException e) {
            Assert.fail(e.getMessage(), e);
        }

        Assert.assertFalse(systemd.isConnected());
    }

    @Test(groups="requireSystemd", description="Tests 'disconnectAll' method.")
    public void testGlobalDisconnection() {
        Systemd system = null;
        Systemd user = null;

        try {
            system = Systemd.get(InstanceType.SYSTEM);
            user = Systemd.get(InstanceType.USER);
        }
        catch (DBusException e) {
            Assert.fail(e.getMessage(), e);
        }

        Assert.assertTrue(system.isConnected());
        Assert.assertTrue(user.isConnected());

        Systemd.disconnectAll();

        Assert.assertFalse(system.isConnected());
        Assert.assertFalse(user.isConnected());
    }

    @Test(groups="requireSystemd", description="Tests manager creation while connected to system instance.")
    public void testSystemManagerCreation() {
        Systemd systemd = null;

        try {
            systemd = Systemd.get();
        }
        catch (DBusException e) {
            Assert.fail(e.getMessage(), e);
        }

        try {
            Assert.assertNotNull(systemd.getManager());
        }
        catch (DBusException e) {
            Assert.fail(e.getMessage(), e);
        }
        finally {
            try {
                Systemd.disconnect();
            }
            catch (DBusException e) {
                Assert.fail(e.getMessage(), e);
            }
        }
    }

    @Test(groups="requireSystemd", description="Tests manager creation while connected to user instance.")
    public void testSessionManagerCreation() {
        Systemd systemd = null;

        try {
            systemd = Systemd.get(InstanceType.USER);
        }
        catch (DBusException e) {
            Assert.fail(e.getMessage(), e);
        }

        try {
            Assert.assertNotNull(systemd.getManager());
        }
        catch (DBusException e) {
            Assert.fail(e.getMessage(), e);
        }
        finally {
            try {
                Systemd.disconnect(InstanceType.USER);
            }
            catch (DBusException e) {
                Assert.fail(e.getMessage(), e);
            }
        }
    }

    @Test(groups="requireSystemd", description="Tests manager creation on system instance in unconnected state.", expectedExceptions={ DBusException.class })
    public void testSystemManagerCreationUnconnected() throws DBusException {
        Systemd instance = Systemd.get();
        Systemd.disconnect();

        instance.getManager();
    }

    @Test(groups="requireSystemd", description="Tests manager creation on user instance in unconnected state.", expectedExceptions={ DBusException.class })
    public void testSessionManagerCreationUnconnected() throws DBusException {
        Systemd instance = Systemd.get(InstanceType.USER);
        Systemd.disconnect(InstanceType.USER);

        instance.getManager();
    }

}
