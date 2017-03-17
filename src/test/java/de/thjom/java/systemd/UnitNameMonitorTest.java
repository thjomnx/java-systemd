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

import org.freedesktop.dbus.Variant;
import org.freedesktop.dbus.exceptions.DBusException;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import de.thjom.java.systemd.Manager;
import de.thjom.java.systemd.Systemd;
import de.thjom.java.systemd.Unit;
import de.thjom.java.systemd.UnitNameMonitor;
import de.thjom.java.systemd.interfaces.ManagerInterface;
import de.thjom.java.systemd.interfaces.PropertyInterface;
import de.thjom.java.systemd.interfaces.ServiceInterface;

public class UnitNameMonitorTest extends AbstractTestCase {

    private static final String OBJECT_PATH_AVAHI = Unit.OBJECT_PATH + Systemd.escapePath("avahi-daemon.service");
    private static final String OBJECT_PATH_CRONIE = Unit.OBJECT_PATH + Systemd.escapePath("cronie.service");
    private static final String OBJECT_PATH_POLKIT = Unit.OBJECT_PATH + Systemd.escapePath("polkit.service");

    @Mock
    private ServiceInterface siface0, siface1, siface2;

    @Mock
    private PropertyInterface piface0, piface1, piface2;

    @Override
    @BeforeClass
    public void setup() {
        MockitoAnnotations.initMocks(this);

        try {
            Mockito.when(dbus.getRemoteObject(Systemd.SERVICE_NAME, Systemd.OBJECT_PATH, ManagerInterface.class)).thenReturn(miface);
            Mockito.when(dbus.getRemoteObject(Mockito.eq(Systemd.SERVICE_NAME), Mockito.eq(Systemd.OBJECT_PATH), Mockito.eq(PropertyInterface.class))).thenReturn(piface);

            Mockito.when(siface0.getObjectPath()).thenReturn(OBJECT_PATH_AVAHI);
            Mockito.when(dbus.getRemoteObject(Mockito.eq(Systemd.SERVICE_NAME), Mockito.eq(OBJECT_PATH_AVAHI), Mockito.eq(ServiceInterface.class))).thenReturn(siface0);

            Mockito.when(siface1.getObjectPath()).thenReturn(OBJECT_PATH_CRONIE);
            Mockito.when(dbus.getRemoteObject(Mockito.eq(Systemd.SERVICE_NAME), Mockito.eq(OBJECT_PATH_CRONIE), Mockito.eq(ServiceInterface.class))).thenReturn(siface1);

            Mockito.when(siface2.getObjectPath()).thenReturn(OBJECT_PATH_POLKIT);
            Mockito.when(dbus.getRemoteObject(Mockito.eq(Systemd.SERVICE_NAME), Mockito.eq(OBJECT_PATH_POLKIT), Mockito.eq(ServiceInterface.class))).thenReturn(siface2);

            Mockito.when(dbus.getRemoteObject(Mockito.eq(Systemd.SERVICE_NAME), Mockito.eq(OBJECT_PATH_AVAHI), Mockito.eq(PropertyInterface.class))).thenReturn(piface0);
            Mockito.when(dbus.getRemoteObject(Mockito.eq(Systemd.SERVICE_NAME), Mockito.eq(OBJECT_PATH_CRONIE), Mockito.eq(PropertyInterface.class))).thenReturn(piface1);
            Mockito.when(dbus.getRemoteObject(Mockito.eq(Systemd.SERVICE_NAME), Mockito.eq(OBJECT_PATH_POLKIT), Mockito.eq(PropertyInterface.class))).thenReturn(piface2);

            Mockito.when(piface0.getProperty(Mockito.eq(Unit.SERVICE_NAME), Mockito.eq(Unit.Property.ID))).then(new Answer<Variant<?>>() {

                @Override
                public Variant<?> answer(InvocationOnMock invocation) throws Throwable {
                    return new Variant<>("avahi-daemon.service");
                }

            });

            Mockito.when(piface1.getProperty(Mockito.eq(Unit.SERVICE_NAME), Mockito.eq(Unit.Property.ID))).then(new Answer<Variant<?>>() {

                @Override
                public Variant<?> answer(InvocationOnMock invocation) throws Throwable {
                    return new Variant<>("cronie.service");
                }

            });

            Mockito.when(piface2.getProperty(Mockito.eq(Unit.SERVICE_NAME), Mockito.eq(Unit.Property.ID))).then(new Answer<Variant<?>>() {

                @Override
                public Variant<?> answer(InvocationOnMock invocation) throws Throwable {
                    return new Variant<>("polkit.service");
                }

            });
        }
        catch (DBusException e) {
            Assert.fail(e.getMessage(), e);
        }
    }

    @Test(description="Tests monitoring configuration.")
    public void testMonitorConfiguration() {
        UnitNameMonitor monitor = null;

        try {
            monitor = new UnitNameMonitor(systemd.getManager());
        }
        catch (DBusException e) {
            Assert.fail(e.getMessage(), e);
        }

        Assert.assertNotNull(monitor);

        try {
            monitor.addUnits("avahi-daemon.service");
        }
        catch (DBusException e) {
            Assert.fail(e.getMessage(), e);
        }

        Assert.assertEquals(monitor.getMonitoredUnits().size(), 1);

        try {
            monitor.addUnits("cronie.service", "polkit.service");
        }
        catch (DBusException e) {
            Assert.fail(e.getMessage(), e);
        }

        Assert.assertEquals(monitor.getMonitoredUnits().size(), 3);

        monitor.addUnits(monitor.getMonitoredUnits().toArray(new Unit[0]));

        Assert.assertEquals(monitor.getMonitoredUnits().size(), 3);
    }

    @Test(groups="manual", description="Tests concurrent access on monitored collection.")
    public void testConcurrentAccess() {
        Manager manager = null;

        try {
            manager = systemd.getManager();
        }
        catch (DBusException e) {
            Assert.fail(e.getMessage(), e);
        }

        final UnitNameMonitor monitor = new UnitNameMonitor(manager);

        try {
            monitor.addUnits("cronie.service");
            monitor.addUnits("polkit.service");
        }
        catch (DBusException e) {
            Assert.fail(e.getMessage(), e);
        }

        Thread thread = new Thread(new Runnable() {

            @Override
            public void run() {
                for (Unit unit : monitor.getMonitoredUnits()) {
                    System.out.println(Thread.currentThread().getId() + ": " + unit.getId());

                    try {
                        Thread.sleep(1000L);
                    }
                    catch (InterruptedException e) {
                        // Do nothing
                    }

                    try {
                        monitor.addUnits("avahi-daemon.service");
                    }
                    catch (DBusException e) {
                        Assert.fail(e.getMessage(), e);
                    }
                }
            }
        });

        thread.start();

        for (Unit unit : monitor.getMonitoredUnits()) {
            System.out.println(Thread.currentThread().getId() + ": " + unit.getId());

            try {
                Thread.sleep(1000L);
            }
            catch (InterruptedException e) {
                // Do nothing
            }
        }

        System.out.println();

        for (Unit unit : monitor.getMonitoredUnits()) {
            System.out.println(Thread.currentThread().getId() + ": " + unit.getId());
        }
    }

}
