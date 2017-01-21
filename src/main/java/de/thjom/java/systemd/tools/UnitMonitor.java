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

package de.thjom.java.systemd.tools;

import java.util.Collection;
import java.util.Objects;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import org.freedesktop.DBus.Properties.PropertiesChanged;
import org.freedesktop.dbus.exceptions.DBusException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.thjom.java.systemd.Manager;
import de.thjom.java.systemd.Unit;
import de.thjom.java.systemd.utils.PropertiesChangedHandler;
import de.thjom.java.systemd.utils.PropertiesChangedProcessor;

abstract class UnitMonitor {

    protected final Logger log = LoggerFactory.getLogger(getClass());

    protected final Manager manager;
    protected final ConcurrentMap<String, Unit> monitoredUnits = new ConcurrentHashMap<>();

    protected int signalQueueLength = 1000;

    private PropertiesChangedProcessor propertiesChangedProcessor;
    private PropertiesChangedHandler propertiesChangedHandler;

    private Timer pollingTimer;

    protected UnitMonitor(final Manager manager) {
        this.manager = Objects.requireNonNull(manager);
    }

    public void attach() throws DBusException {
        manager.subscribe();

        propertiesChangedProcessor = new PropertiesChangedProcessor(signalQueueLength) {

            @Override
            public void propertiesChanged(final PropertiesChanged signal) {
                log.debug("Processing dequeued signal: " + signal);

                String unitName = Unit.extractName(signal.getPath());

                if (monitoredUnits.containsKey(unitName)) {
                    System.out.println("UnitMonitor.attach().new PropertiesChangedProcessor() {...}.propertiesChanged(): " + signal);
                }
            }

        };

        propertiesChangedProcessor.setName(PropertiesChangedProcessor.class.getSimpleName());
        propertiesChangedProcessor.setDaemon(true);
        propertiesChangedProcessor.start();

        propertiesChangedHandler = new PropertiesChangedHandler(propertiesChangedProcessor);
        manager.addHandler(PropertiesChanged.class, propertiesChangedHandler);
    }

    public void detach() throws DBusException {
        if (propertiesChangedHandler != null) {
            manager.removeHandler(PropertiesChanged.class, propertiesChangedHandler);
        }

        if (propertiesChangedProcessor != null) {
            propertiesChangedProcessor.setRunning(false);
            propertiesChangedProcessor.interrupt();
        }
    }

    public abstract void refresh() throws DBusException;

    public synchronized void startPolling(final long delay, final long period) {
        TimerTask task = new TimerTask() {

            @Override
            public void run() {
                try {
                    refresh();
                }
                catch (final DBusException e) {
                    log.error("Error while refreshing internal monitor state", e);
                }
            }

        };

        if (pollingTimer != null) {
            pollingTimer.cancel();
        }

        pollingTimer = createTimer();
        pollingTimer.schedule(task, delay, period);
    }

    public synchronized void stopPolling() {
        if (pollingTimer != null) {
            pollingTimer.cancel();
        }
    }

    protected Timer createTimer() {
        return new Timer(getClass().getSimpleName() + "-pollingTimer", true);
    }

    public Collection<Unit> getMonitoredUnits() {
        return monitoredUnits.values();
    }

    public int getSignalQueueLength() {
        return signalQueueLength;
    }

    public void setSignalQueueLength(final int signalQueueLength) {
        this.signalQueueLength = signalQueueLength;
    }

}
