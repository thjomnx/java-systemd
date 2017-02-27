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

import java.util.Objects;

import org.freedesktop.dbus.DBusSigHandler;
import org.freedesktop.dbus.DBusSignal;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

class ForwardingHandler<T extends DBusSignal> implements DBusSigHandler<T> {

    private final Logger log = LoggerFactory.getLogger(ForwardingHandler.class);

    private SignalConsumer<T> consumer;
    private Thread consumerThread;

    ForwardingHandler(final SignalConsumer<T> consumer) {
        this.consumer = Objects.requireNonNull(consumer);
    }

    public void startConsumer() {
        if (consumer != null) {
            log.info("Starting signal consumer thread");

            consumerThread = new Thread(consumer);
            consumerThread.setName(SignalConsumer.class.getSimpleName());
            consumerThread.setDaemon(true);
            consumerThread.start();
        }
    }

    public void stopConsumer() {
        if (consumer != null && consumerThread != null) {
            log.info("Stopping signal consumer thread");

            consumer.setRunning(false);

            try {
                consumerThread.interrupt();
                consumerThread.join();
            }
            catch (final InterruptedException | SecurityException e) {
                log.warn(e.getMessage());
            }
        }
    }

    @Override
    public synchronized void handle(final T signal) {
        if (consumer != null) {
            SignalSequencer<T> sequencer = consumer.getSequencer();

            if (sequencer != null) {
                if (log.isDebugEnabled()) {
                    log.debug("Adding signal to sequencer queue: " + signal);
                }

                try {
                    sequencer.put(signal);
                }
                catch (final InterruptedException e) {
                    log.debug(e.getMessage());
                }
            }
        }
    }

    public final SignalConsumer<T> getConsumer() {
        return consumer;
    }

}
