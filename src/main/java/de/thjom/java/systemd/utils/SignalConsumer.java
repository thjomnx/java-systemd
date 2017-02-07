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
import java.util.Objects;

import org.freedesktop.dbus.DBusSigHandler;
import org.freedesktop.dbus.DBusSignal;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SignalConsumer<T extends DBusSignal> implements Runnable {

    protected final Logger log = LoggerFactory.getLogger(getClass());

    private final SignalSequencer<T> sequencer;
    private final DBusSigHandler<T> handler;

    private volatile boolean running = true;

    public SignalConsumer(final DBusSigHandler<T> handler) {
        this(100, handler);
    }

    public SignalConsumer(final int queueLength, final DBusSigHandler<T> handler) {
        this.sequencer = new SignalSequencer<>(queueLength);
        this.handler = Objects.requireNonNull(handler);
    }

    @Override
    public void run() {
        while (running) {
            try {
                T signal = sequencer.take();

                handler.handle(signal);
            }
            catch (final InterruptedException e1) {
                // Do nothing
            }
        }

        log.debug("Draining sequencer queue");

        List<T> signals = new ArrayList<>(sequencer.size());
        sequencer.drainTo(signals);

        for (T signal : signals) {
            handler.handle(signal);
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
