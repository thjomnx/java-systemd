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

package de.mnx.java.systemd;

import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.freedesktop.dbus.DBusConnection;
import org.freedesktop.dbus.exceptions.DBusException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public final class Systemd {

    public static final String SERVICE_NAME = "org.freedesktop.systemd1";
    public static final String OBJECT_PATH = "/org/freedesktop/systemd1";

    public static final Pattern PATH_ESCAPE_PATTERN = Pattern.compile("(\\W)");

    private static final Logger log = LoggerFactory.getLogger(Systemd.class);

    private DBusConnection dbus;
    private Manager manager;

    public Systemd() {
        // Do nothing (lazy implementation)
    }

    public static final String escapePath(final CharSequence path) {
        StringBuffer escaped = new StringBuffer();
        Matcher matcher = PATH_ESCAPE_PATTERN.matcher(path);

        while (matcher.find()) {
            String replacement = '_' + Integer.toHexString((int) matcher.group().charAt(0));
            matcher.appendReplacement(escaped, replacement);
        }

        matcher.appendTail(escaped);

        return escaped.toString();
    }

    public static final Date timestampToDate(final long timestamp) {
        return new Date(timestamp / 1000);
    }

    public void connect() throws DBusException {
        try {
            dbus = DBusConnection.getConnection(DBusConnection.SYSTEM);
        }
        catch (final DBusException e) {
            log.error("Unable to connect to system bus", e);

            throw e;
        }
    }

    public void disconnect() {
        if (dbus != null) {
            dbus.disconnect();
        }
    }

    DBusConnection getConnection() {
        return dbus;
    }

    public Manager getManager() throws DBusException {
        if (manager == null) {
            manager = Manager.create(dbus);
        }

        return manager;
    }

}
