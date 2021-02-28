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

import org.freedesktop.dbus.exceptions.DBusException;
import org.freedesktop.dbus.messages.DBusSignal;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Signal extends DBusSignal {

    protected static final Logger LOG = LoggerFactory.getLogger(Signal.class);

    protected Signal(final String objectpath, final Object... args) throws DBusException {
        super(objectpath, args);
    }

    @SuppressWarnings("unchecked")
    protected <T> T getParameter(final int index, final Object defaultValue) {
        try {
            return (T) getParameters()[index];
        }
        catch (final ClassCastException | DBusException e) {
            LOG.error(e.getMessage());

            return (T) defaultValue;
        }
    }

}
