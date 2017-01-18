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

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import org.freedesktop.DBus.Properties.PropertiesChanged;
import org.freedesktop.dbus.DBusSigHandler;
import org.freedesktop.dbus.exceptions.DBusException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.thjom.java.systemd.Manager;
import de.thjom.java.systemd.Sequencer;
import de.thjom.java.systemd.Unit;

abstract class UnitMonitor {

    protected final Logger log = LoggerFactory.getLogger(getClass());

    protected final Manager manager;
    protected final ConcurrentMap<String, Unit> monitoredUnits = new ConcurrentHashMap<>();

    protected int signalQueueLength = 1000;

    private PropertiesChangedHandler propertiesChangedHandler;
    private Sequencer<PropertiesChanged> propertiesChangedSequencer;
    private PropertiesChangedProcessor propertiesChangedProcessor;

    private Timer pollingTimer;

    protected UnitMonitor(final Manager manager) {
        this.manager = Objects.requireNonNull(manager);
    }

    public void attach() throws DBusException {
        manager.subscribe();

        propertiesChangedSequencer = new Sequencer<>(signalQueueLength);

        propertiesChangedProcessor = new PropertiesChangedProcessor(propertiesChangedSequencer);
        propertiesChangedProcessor.setName(PropertiesChangedProcessor.class.getSimpleName());
        propertiesChangedProcessor.start();

        propertiesChangedHandler = new PropertiesChangedHandler();
        manager.addHandler(PropertiesChanged.class, propertiesChangedHandler);
    }

    public void detach() throws DBusException {
        if (propertiesChangedHandler != null) {
            manager.removeHandler(PropertiesChanged.class, propertiesChangedHandler);
        }

        propertiesChangedProcessor.setRunning(false);
        propertiesChangedProcessor.interrupt();

        if (propertiesChangedSequencer != null) {
            propertiesChangedSequencer.clear();
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

    private static class PropertiesChangedProcessor extends Thread {

        private final Sequencer<PropertiesChanged> sequencer;

        private volatile boolean running = true;

        public PropertiesChangedProcessor(final Sequencer<PropertiesChanged> sequencer) {
            this.sequencer = sequencer;
        }

        @Override
        public void run() {
            while (running) {
                try {
                    PropertiesChanged signal = sequencer.take();

                    System.out.println("UnitMonitor.PropertiesChangedProcessor.run(): " + signal);
                }
                catch (final InterruptedException e1) {
                    // Do nothing
                }
            }

            List<PropertiesChanged> signals = new ArrayList<>(sequencer.size());
            sequencer.drainTo(signals);

            for (PropertiesChanged signal : signals) {
                System.out.println("UnitMonitor.PropertiesChangedProcessor.run(): " + signal);
            }
        }

        public void setRunning(final boolean running) {
            this.running = running;
        }

    }

    public class PropertiesChangedHandler implements DBusSigHandler<PropertiesChanged> {

        @Override
        public void handle(final PropertiesChanged signal) {
            System.out.println("UnitMonitor.PropertiesChangedHandler.handle(): " + signal);

            try {
                propertiesChangedSequencer.put(signal);
            }
            catch (final InterruptedException e) {
                // Do nothing
            }
        }

    }

}
