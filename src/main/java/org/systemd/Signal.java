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

package org.systemd;

import org.freedesktop.dbus.DBusSignal;
import org.freedesktop.dbus.exceptions.DBusException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Signal extends DBusSignal {

    protected final Logger log = LoggerFactory.getLogger(getClass());

    protected Signal(final String objectpath, final Object... args) throws DBusException {
        super(objectpath, args);
    }

    @SuppressWarnings("unchecked")
    protected <T> T getParameter(final int index, final Object defaultValue) {
        try {
            return (T) getParameters()[index];
        }
        catch (final ClassCastException | DBusException e) {
            log.error(e.getMessage());

            return (T) defaultValue;
        }
    }

}
