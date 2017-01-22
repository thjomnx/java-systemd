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

import org.freedesktop.dbus.Message;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class MessageConsumer<E extends Message> extends Thread {

    protected final Logger log = LoggerFactory.getLogger(getClass());

    private final MessageSequencer<E> sequencer;

    private volatile boolean running = true;

    public MessageConsumer(final int queueLength) {
        this.sequencer = new MessageSequencer<>(queueLength);
    }

    @Override
    public void run() {
        while (running) {
            try {
                E signal = sequencer.take();

                propertiesChanged(signal);
            }
            catch (final InterruptedException e1) {
                // Do nothing
            }
        }

        log.debug("Draining sequencer queue");

        List<E> signals = new ArrayList<>(sequencer.size());
        sequencer.drainTo(signals);

        for (E signal : signals) {
            propertiesChanged(signal);
        }

        sequencer.clear();
    }

    public abstract void propertiesChanged(final E signal);

    MessageSequencer<E> getSequencer() {
        return sequencer;
    }

    public void setRunning(final boolean running) {
        this.running = running;
    }

}
