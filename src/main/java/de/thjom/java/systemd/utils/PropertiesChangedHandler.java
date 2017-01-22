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

import org.freedesktop.DBus.Properties.PropertiesChanged;
import org.freedesktop.dbus.DBusSigHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class PropertiesChangedHandler implements DBusSigHandler<PropertiesChanged> {

    protected final Logger log = LoggerFactory.getLogger(getClass());

    private final MessageSequencer<PropertiesChanged> sequencer;

    public PropertiesChangedHandler(final MessageConsumer<PropertiesChanged> processor) {
        this.sequencer = processor.getSequencer();
    }

    @Override
    public void handle(final PropertiesChanged signal) {
        log.debug("Adding signal to sequencer queue: " + signal);

        try {
            sequencer.put(signal);
        }
        catch (final InterruptedException e) {
            // Do nothing
        }
    }

}
