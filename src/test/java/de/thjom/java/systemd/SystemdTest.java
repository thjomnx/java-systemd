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

import java.time.Duration;
import java.time.Instant;
import java.time.temporal.ChronoUnit;

import org.freedesktop.dbus.exceptions.DBusException;
import org.testng.Assert;
import org.testng.annotations.Test;

import de.thjom.java.systemd.Systemd.InstanceType;

public class SystemdTest {

    @Test(description="Tests object path escape function.")
    public void testPathEscaping() {
        Assert.assertEquals(Systemd.escapePath("systemd-backlight@leds:tpacpi::kbd_backlight.service"),
                "systemd_2dbacklight_40leds_3atpacpi_3a_3akbd_5fbacklight_2eservice");
        Assert.assertEquals(Systemd.escapePath(new StringBuffer("systemd-logind.service")), "systemd_2dlogind_2eservice");
        Assert.assertEquals(Systemd.escapePath(new StringBuilder("user@1000.service")), "user_401000_2eservice");
        Assert.assertEquals(Systemd.escapePath(null), "");
    }

    @Test(description="Tests micro-timestamp conversion to java.time.Instant object.")
    public void testTimestampConversion() {
        long usecTimestamp = System.currentTimeMillis() * 1000 + 987;
        Instant now = Instant.EPOCH.plus(usecTimestamp, ChronoUnit.MICROS);

        Assert.assertEquals(Systemd.timestampToInstant(usecTimestamp), now);
    }

    @Test(description="Tests microsecond duration conversion to java.time.Duration object.")
    public void testUsecsConversion() {
        long usecs = 1234567890L;
        Duration duration = Duration.of(usecs, ChronoUnit.MICROS);

        Assert.assertEquals(Systemd.usecsToDuration(usecs), duration);
    }

    @Test(description="Tests conversion of SD-ID128 structs (e.g. 'InvocationID') to java.lang.String object.")
    public void testId128StringConversion() {
        byte[] id128 = new byte[] { 90, -125, -39, -3, -28, 103, 64, 10, -122, -97, -50, 13, 67, 113, 123, -125 };
        String str = Systemd.id128ToString(id128);

        Assert.assertEquals(str, "5a83d9fde467400a869fce0d43717b83");
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
        Systemd.disconnect();

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
        Systemd.disconnect();

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
        Systemd.disconnect(InstanceType.USER);

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
        Systemd.disconnect(InstanceType.USER);

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
        Systemd.disconnect();

        Assert.assertFalse(systemd.isConnected());

        // Disconnect once more #1
        Systemd.disconnect();

        Assert.assertFalse(systemd.isConnected());

        // Disconnect once more #2
        Systemd.disconnect();

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
            Systemd.disconnect();
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
            Systemd.disconnect(InstanceType.USER);
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
