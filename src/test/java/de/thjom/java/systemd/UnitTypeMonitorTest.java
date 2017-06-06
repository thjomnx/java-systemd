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

import java.util.ArrayList;
import java.util.List;

import org.freedesktop.dbus.UInt32;
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

import de.thjom.java.systemd.UnitTypeMonitor.MonitoredType;
import de.thjom.java.systemd.interfaces.ManagerInterface;
import de.thjom.java.systemd.interfaces.MountInterface;
import de.thjom.java.systemd.interfaces.PropertyInterface;
import de.thjom.java.systemd.interfaces.ServiceInterface;
import de.thjom.java.systemd.interfaces.SocketInterface;
import de.thjom.java.systemd.types.UnitType;

public class UnitTypeMonitorTest extends AbstractTestCase {

    private static final String OBJECT_PATH_MOUNT_BOOT = Unit.OBJECT_PATH + Systemd.escapePath("boot.mount");
    private static final String OBJECT_PATH_MOUNT_RUNUSER1000 = Unit.OBJECT_PATH + Systemd.escapePath("run-user-1000.mount");
    private static final String OBJECT_PATH_MOUNT_TMP = Unit.OBJECT_PATH + Systemd.escapePath("tmp.mount");

    private static final String OBJECT_PATH_SERVICE_AVAHI = Unit.OBJECT_PATH + Systemd.escapePath("avahi-daemon.service");
    private static final String OBJECT_PATH_SERVICE_CRONIE = Unit.OBJECT_PATH + Systemd.escapePath("cronie.service");

    private static final String OBJECT_PATH_SOCKET_INITCTL = Unit.OBJECT_PATH + Systemd.escapePath("systemd-initctl.socket");

    @Mock
    private MountInterface miface0, miface1, miface2;

    @Mock
    private ServiceInterface sviface3, sviface4;

    @Mock
    private SocketInterface soiface5;

    @Mock
    private PropertyInterface piface0, piface1, piface2, piface3, piface4, piface5;

    @Override
    @BeforeClass
    public void setup() {
        MockitoAnnotations.initMocks(this);

        try {
            Mockito.when(dbus.getRemoteObject(Systemd.SERVICE_NAME, Systemd.OBJECT_PATH, ManagerInterface.class)).thenReturn(miface);
            Mockito.when(dbus.getRemoteObject(Mockito.eq(Systemd.SERVICE_NAME), Mockito.eq(Systemd.OBJECT_PATH), Mockito.eq(PropertyInterface.class))).thenReturn(piface);

            Mockito.when(miface.listUnits()).then(new Answer<List<UnitType>>() {

                @Override
                public List<UnitType> answer(final InvocationOnMock invocation) throws Throwable {
                    List<UnitType> list = new ArrayList<>();
                    list.add(new UnitType("boot.mount", null, null, null, null, null, null, new UInt32(0L), null, null));
                    list.add(new UnitType("run-user-1000.mount", null, null, null, null, null, null, new UInt32(0L), null, null));
                    list.add(new UnitType("tmp.mount", null, null, null, null, null, null, new UInt32(0L), null, null));

                    list.add(new UnitType("avahi-daemon.service", null, null, null, null, null, null, new UInt32(0L), null, null));
                    list.add(new UnitType("cronie.service", null, null, null, null, null, null, new UInt32(0L), null, null));

                    list.add(new UnitType("systemd-initctl.socket", null, null, null, null, null, null, new UInt32(0L), null, null));

                    return list;
                }

            });

            Mockito.when(miface0.getObjectPath()).thenReturn(OBJECT_PATH_MOUNT_BOOT);
            Mockito.when(dbus.getRemoteObject(Mockito.eq(Systemd.SERVICE_NAME), Mockito.eq(OBJECT_PATH_MOUNT_BOOT), Mockito.eq(MountInterface.class))).thenReturn(miface0);

            Mockito.when(miface1.getObjectPath()).thenReturn(OBJECT_PATH_MOUNT_RUNUSER1000);
            Mockito.when(dbus.getRemoteObject(Mockito.eq(Systemd.SERVICE_NAME), Mockito.eq(OBJECT_PATH_MOUNT_RUNUSER1000), Mockito.eq(MountInterface.class))).thenReturn(miface1);

            Mockito.when(miface2.getObjectPath()).thenReturn(OBJECT_PATH_MOUNT_TMP);
            Mockito.when(dbus.getRemoteObject(Mockito.eq(Systemd.SERVICE_NAME), Mockito.eq(OBJECT_PATH_MOUNT_TMP), Mockito.eq(MountInterface.class))).thenReturn(miface2);

            Mockito.when(sviface3.getObjectPath()).thenReturn(OBJECT_PATH_SERVICE_AVAHI);
            Mockito.when(dbus.getRemoteObject(Mockito.eq(Systemd.SERVICE_NAME), Mockito.eq(OBJECT_PATH_SERVICE_AVAHI), Mockito.eq(ServiceInterface.class))).thenReturn(sviface3);

            Mockito.when(sviface4.getObjectPath()).thenReturn(OBJECT_PATH_SERVICE_CRONIE);
            Mockito.when(dbus.getRemoteObject(Mockito.eq(Systemd.SERVICE_NAME), Mockito.eq(OBJECT_PATH_SERVICE_CRONIE), Mockito.eq(ServiceInterface.class))).thenReturn(sviface4);

            Mockito.when(soiface5.getObjectPath()).thenReturn(OBJECT_PATH_SOCKET_INITCTL);
            Mockito.when(dbus.getRemoteObject(Mockito.eq(Systemd.SERVICE_NAME), Mockito.eq(OBJECT_PATH_SOCKET_INITCTL), Mockito.eq(SocketInterface.class))).thenReturn(soiface5);

            Mockito.when(dbus.getRemoteObject(Mockito.eq(Systemd.SERVICE_NAME), Mockito.eq(OBJECT_PATH_MOUNT_BOOT), Mockito.eq(PropertyInterface.class))).thenReturn(piface0);
            Mockito.when(dbus.getRemoteObject(Mockito.eq(Systemd.SERVICE_NAME), Mockito.eq(OBJECT_PATH_MOUNT_RUNUSER1000), Mockito.eq(PropertyInterface.class))).thenReturn(piface1);
            Mockito.when(dbus.getRemoteObject(Mockito.eq(Systemd.SERVICE_NAME), Mockito.eq(OBJECT_PATH_MOUNT_TMP), Mockito.eq(PropertyInterface.class))).thenReturn(piface2);
            Mockito.when(dbus.getRemoteObject(Mockito.eq(Systemd.SERVICE_NAME), Mockito.eq(OBJECT_PATH_SERVICE_AVAHI), Mockito.eq(PropertyInterface.class))).thenReturn(piface3);
            Mockito.when(dbus.getRemoteObject(Mockito.eq(Systemd.SERVICE_NAME), Mockito.eq(OBJECT_PATH_SERVICE_CRONIE), Mockito.eq(PropertyInterface.class))).thenReturn(piface4);
            Mockito.when(dbus.getRemoteObject(Mockito.eq(Systemd.SERVICE_NAME), Mockito.eq(OBJECT_PATH_SOCKET_INITCTL), Mockito.eq(PropertyInterface.class))).thenReturn(piface5);

            Mockito.when(piface0.getProperty(Mockito.eq(Unit.SERVICE_NAME), Mockito.eq(Unit.Property.ID))).then(new Answer<Variant<?>>() {

                @Override
                public Variant<?> answer(final InvocationOnMock invocation) throws Throwable {
                    return new Variant<>("boot.mount");
                }

            });

            Mockito.when(piface1.getProperty(Mockito.eq(Unit.SERVICE_NAME), Mockito.eq(Unit.Property.ID))).then(new Answer<Variant<?>>() {

                @Override
                public Variant<?> answer(final InvocationOnMock invocation) throws Throwable {
                    return new Variant<>("run-user-1000.mount");
                }

            });

            Mockito.when(piface2.getProperty(Mockito.eq(Unit.SERVICE_NAME), Mockito.eq(Unit.Property.ID))).then(new Answer<Variant<?>>() {

                @Override
                public Variant<?> answer(final InvocationOnMock invocation) throws Throwable {
                    return new Variant<>("tmp.mount");
                }

            });

            Mockito.when(piface3.getProperty(Mockito.eq(Unit.SERVICE_NAME), Mockito.eq(Unit.Property.ID))).then(new Answer<Variant<?>>() {

                @Override
                public Variant<?> answer(final InvocationOnMock invocation) throws Throwable {
                    return new Variant<>("avahi-daemon.service");
                }

            });

            Mockito.when(piface4.getProperty(Mockito.eq(Unit.SERVICE_NAME), Mockito.eq(Unit.Property.ID))).then(new Answer<Variant<?>>() {

                @Override
                public Variant<?> answer(final InvocationOnMock invocation) throws Throwable {
                    return new Variant<>("cronie.service");
                }

            });

            Mockito.when(piface5.getProperty(Mockito.eq(Unit.SERVICE_NAME), Mockito.eq(Unit.Property.ID))).then(new Answer<Variant<?>>() {

                @Override
                public Variant<?> answer(final InvocationOnMock invocation) throws Throwable {
                    return new Variant<>("systemd-initctl.socket");
                }

            });
        }
        catch (DBusException e) {
            Assert.fail(e.getMessage(), e);
        }
    }

    @Test(description="Tests monitoring configuration.")
    public void testMonitorConfiguration() {
        UnitTypeMonitor monitor = null;

        // Test addition and removal of all types
        try {
            monitor = new UnitTypeMonitor(systemd.getManager());
            monitor.addMonitoredTypes(MonitoredType.values());
            monitor.removeMonitoredTypes(MonitoredType.values());
        }
        catch (DBusException e) {
            Assert.fail(e.getMessage(), e);
        }

        // Test specific additions (subset of types)
        try {
            monitor.addMonitoredTypes(MonitoredType.MOUNT, MonitoredType.SOCKET);
        }
        catch (DBusException e) {
            Assert.fail(e.getMessage(), e);
        }

        Assert.assertEquals(monitor.getMonitoredUnits().size(), 4);

        // Test another specific addition (adds to subset)
        try {
            monitor.addMonitoredTypes(MonitoredType.SERVICE);
        }
        catch (DBusException e) {
            Assert.fail(e.getMessage(), e);
        }

        Assert.assertEquals(monitor.getMonitoredUnits().size(), 6);

        // Test re-addition of already added types
        try {
            monitor.addMonitoredTypes(MonitoredType.MOUNT, MonitoredType.SERVICE, MonitoredType.SOCKET);
        }
        catch (DBusException e) {
            Assert.fail(e.getMessage(), e);
        }

        Assert.assertEquals(monitor.getMonitoredUnits().size(), 6);

        // Test specific removal (removes from subset)
        try {
            monitor.removeMonitoredTypes(MonitoredType.MOUNT);
        }
        catch (DBusException e) {
            Assert.fail(e.getMessage(), e);
        }

        Assert.assertEquals(monitor.getMonitoredUnits().size(), 3);
    }

    @Test(description="Tests reset of monitoring configuration.")
    public void testMonitorResetting() {
        UnitTypeMonitor monitor = null;

        try {
            monitor = new UnitTypeMonitor(systemd.getManager());
            monitor.addMonitoredTypes(MonitoredType.values());
        }
        catch (DBusException e) {
            Assert.fail(e.getMessage(), e);
        }

        Assert.assertEquals(monitor.getMonitoredUnits().size(), 6);

        monitor.reset();

        Assert.assertEquals(monitor.getMonitoredUnits().size(), 0);
    }

    @Test(description="Tests configuration of default handlers.")
    public void testDefaultHandlers() {
        UnitTypeMonitor monitor = null;

        try {
            monitor = new UnitTypeMonitor(systemd.getManager());
            monitor.addDefaultHandlers();
        }
        catch (DBusException e) {
            Assert.fail(e.getMessage(), e);
        }
    }

    @Test(description="Tests query methods on a configured monitor.")
    public void testMonitorInterrogation() {
        UnitTypeMonitor monitor = null;

        try {
            monitor = new UnitTypeMonitor(systemd.getManager());
            monitor.addMonitoredTypes(MonitoredType.MOUNT);
        }
        catch (DBusException e) {
            Assert.fail(e.getMessage(), e);
        }

        Assert.assertTrue(monitor.monitorsUnit("boot.mount"));
        Assert.assertFalse(monitor.monitorsUnit("systemd-initctl.socket"));

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

        final UnitTypeMonitor monitor = new UnitTypeMonitor(manager);

        try {
            monitor.addMonitoredTypes(MonitoredType.MOUNT, MonitoredType.SOCKET);
            monitor.addDefaultHandlers();
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
                        monitor.addMonitoredTypes(MonitoredType.SERVICE);
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
