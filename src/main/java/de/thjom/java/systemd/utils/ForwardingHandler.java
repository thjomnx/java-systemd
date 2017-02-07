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

import org.freedesktop.dbus.DBusSignal;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ForwardingHandler<T extends DBusSignal> implements DBusSigForwarder<T> {

    protected final Logger log = LoggerFactory.getLogger(getClass());

    private SignalConsumer<T> consumer;
    private Thread consumerThread;

    public ForwardingHandler() {
        // Do nothing
    }

    public ForwardingHandler(final SignalConsumer<T> consumer) {
        setConsumer(consumer);
    }

    private synchronized void setConsumer(final SignalConsumer<T> consumer) {
        stopConsumer();

        this.consumer = consumer;

        startConsumer();
    }

    private void startConsumer() {
        if (consumer != null) {
            log.info("Starting signal consumer thread");

            consumerThread = new Thread(consumer);
            consumerThread.setName(SignalConsumer.class.getSimpleName());
            consumerThread.setDaemon(true);
            consumerThread.start();
        }
    }

    private void stopConsumer() {
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

    @Override
    public void forwardTo(final SignalConsumer<T> consumer) {
        setConsumer(consumer);
    }

}
