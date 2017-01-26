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

import org.freedesktop.dbus.DBusSigHandler;
import org.freedesktop.dbus.DBusSignal;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SignalHandler<T extends DBusSignal> implements DBusSigHandler<T> {

    protected final Logger log = LoggerFactory.getLogger(getClass());

    private MessageConsumer<T> consumer;
    private Thread consumerThread;

    public SignalHandler() {
        // Do nothing
    }

    public SignalHandler(final MessageConsumer<T> consumer) {
        setConsumer(consumer);
    }

    private void setConsumer(final MessageConsumer<T> consumer) {
        stopConsumer();

        this.consumer = consumer;

        if (consumer != null) {
            startConsumer();
        }
    }

    private void startConsumer() {
        if (consumer != null) {
            log.info("Starting message consumer thread");

            consumerThread = new Thread(consumer);
            consumerThread.setName(MessageConsumer.class.getSimpleName());
            consumerThread.setDaemon(true);
            consumerThread.start();
        }
    }

    private void stopConsumer() {
        if (consumer != null && consumerThread != null && consumerThread.isAlive()) {
            log.info("Stopping message consumer thread");

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
    public void handle(final T signal) {
        if (consumer != null) {
            MessageSequencer<T> sequencer = consumer.getSequencer();

            if (sequencer != null) {
                log.debug("Adding signal to sequencer queue: " + signal);

                try {
                    sequencer.put(signal);
                }
                catch (final InterruptedException e) {
                    log.debug(e.getMessage());
                }
            }
        }
    }

    public void forwardTo(final MessageConsumer<T> consumer) {
        setConsumer(consumer);
    }

}
