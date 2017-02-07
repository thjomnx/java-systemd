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

package de.thjom.java.systemd.utils;

import java.util.ArrayList;
import java.util.List;

import org.freedesktop.dbus.DBusSigHandler;
import org.freedesktop.dbus.DBusSignal;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class SignalConsumer<T extends DBusSignal> implements DBusSigHandler<T>, Runnable {

    protected final Logger log = LoggerFactory.getLogger(getClass());

    private final SignalSequencer<T> sequencer;

    private volatile boolean running = true;

    public SignalConsumer(final int queueLength) {
        this.sequencer = new SignalSequencer<>(queueLength);
    }

    @Override
    public void run() {
        while (running) {
            try {
                T signal = sequencer.take();

                handle(signal);
            }
            catch (final InterruptedException e1) {
                // Do nothing
            }
        }

        log.debug("Draining sequencer queue");

        List<T> signals = new ArrayList<>(sequencer.size());
        sequencer.drainTo(signals);

        for (T signal : signals) {
            handle(signal);
        }

        sequencer.clear();
    }

    SignalSequencer<T> getSequencer() {
        return sequencer;
    }

    public void setRunning(final boolean running) {
        this.running = running;
    }

}
