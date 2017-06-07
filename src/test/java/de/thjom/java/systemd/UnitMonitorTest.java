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

import java.util.Collection;
import java.util.Map;
import java.util.concurrent.Callable;
import java.util.concurrent.atomic.AtomicBoolean;

import org.awaitility.Awaitility;
import org.freedesktop.DBus;
import org.freedesktop.DBus.Properties.PropertiesChanged;
import org.freedesktop.dbus.DBusSigHandler;
import org.freedesktop.dbus.Variant;
import org.freedesktop.dbus.exceptions.DBusException;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class UnitMonitorTest extends AbstractTestCase {

    @Override
    @BeforeClass
    public void setup() {
        // Override only for annotation
        super.setup();
    }

    @Test(description="Tests configuration with handler callbacks.")
    public void testMonitorConfiguration() {
        UnitMonitor monitor = null;

        // Test addition and removal of all types
        try {
            monitor = new UnitMonitor(systemd.getManager()) {

                @Override
                public void reset() {
                    // Do nothing (simple mock object)
                }

                @Override
                public void refresh() throws DBusException {
                    // Do nothing (simple mock object)
                }

            };

            // Test handler addition and removal
            DBusSigHandler<PropertiesChanged> handler = new DBusSigHandler<DBus.Properties.PropertiesChanged>() {

                @Override
                public void handle(PropertiesChanged s) {
                    // Do nothing (test case)
                }

            };

            monitor.addHandler(PropertiesChanged.class, handler);
            monitor.removeHandler(PropertiesChanged.class, handler);

            // Test lambda expression
            monitor.addHandler(PropertiesChanged.class, s -> { /* Do nothing (test case) */ });

            // Test consumer addition and removal
            monitor.addConsumer(PropertiesChanged.class, handler);
            monitor.removeConsumer(PropertiesChanged.class, handler);

            // Test lambda expression
            monitor.addConsumer(PropertiesChanged.class, s -> { /* Do nothing (test case) */ });

            // Test monitor listener addition and removal
            UnitMonitorListener monitorListener= new UnitMonitorListener() {

                @Override
                public void monitorRefreshed(Collection<Unit> monitoredUnits) {
                    // Do nothing (test case)
                }

            };

            monitor.addListener(monitorListener);
            monitor.removeListener(monitorListener);

            // Test lambda expression
            monitor.addListener(s -> { /* Do nothing (test case) */ });

            // Test state listener addition and removal
            UnitStateListener stateListener = new UnitStateListener() {

                @Override
                public void stateChanged(Unit unit, Map<String, Variant<?>> changedProperties) {
                    // Do nothing (test case)
                }

            };

            monitor.addListener(stateListener);
            monitor.removeListener(stateListener);

            // Test lambda expression
            monitor.addListener((u, p) -> { /* Do nothing (test case) */ });
        }
        catch (DBusException e) {
            Assert.fail(e.getMessage(), e);
        }
    }

    @Test(description="Tests configuration of timer feature.")
    public void testTimerConfiguration() {
        final AtomicBoolean refreshed = new AtomicBoolean();

        UnitMonitor monitor = null;

        // Test addition and removal of all types
        try {
            monitor = new UnitMonitor(systemd.getManager()) {

                @Override
                public void reset() {
                    // Do nothing (simple mock object)
                }

                @Override
                public void refresh() throws DBusException {
                    refreshed.set(true);
                }

            };

            monitor.startPolling(250L, 360000L);

            // Use 'until(..)' with 'Callable<T>' until Hamcrest dependency errors are fixed
            Awaitility.await().until(new Callable<Boolean>() {

                @Override
                public Boolean call() throws Exception {
                    return refreshed.get();
                }

            });

            monitor.stopPolling();
        }
        catch (DBusException e) {
            Assert.fail(e.getMessage(), e);
        }
    }

}
