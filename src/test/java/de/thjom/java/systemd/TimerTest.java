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

import org.freedesktop.dbus.exceptions.DBusException;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import de.thjom.java.systemd.interfaces.TimerInterface;

public class TimerTest extends UnitTest {

    @Mock
    private TimerInterface tiface;

    private Timer timer;

    @Override
    @BeforeClass
    public void setup() {
        super.setup();

        try {
            Mockito.when(tiface.getObjectPath()).thenReturn("foo");
            Mockito.when(dbus.getRemoteObject(Mockito.eq(Systemd.SERVICE_NAME), Mockito.anyString(), Mockito.eq(TimerInterface.class))).thenReturn(tiface);
        }
        catch (DBusException e) {
            Assert.fail(e.getMessage(), e);
        }

        setupPropertyMocks(Timer.class, Timer.SERVICE_NAME, Timer.Property.getAllNames());

        nonVariantProperties.add(Timer.Property.TIMERS_MONOTONIC);
        nonVariantProperties.add(Timer.Property.TIMERS_CALENDAR);
    }

    @Test(description="Tests basic manager accessibility.")
    public void testAccess() {
        try {
            timer = systemd.getManager().getTimer("systemd-tmpfiles-clean");
        }
        catch (DBusException e) {
            Assert.fail(e.getMessage(), e);
        }

        Assert.assertNotNull(timer);
    }

    @Test(dependsOnMethods={ "testAccess" }, description="Tests property access of timer interface.")
    public void testProperties() {
        testUnitProperties(timer, Timer.Property.getAllNames());
    }

}
