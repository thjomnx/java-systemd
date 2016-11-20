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

package de.thjom.java.systemd.tools;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import org.freedesktop.dbus.exceptions.DBusException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.thjom.java.systemd.Manager;
import de.thjom.java.systemd.Unit;

public abstract class UnitMonitor {

    protected final Logger log = LoggerFactory.getLogger(getClass());

    protected final Manager manager;
    protected final Map<String, Unit> monitoredUnits = new HashMap<>();

    protected UnitMonitor(final Manager manager) {
        this.manager = Objects.requireNonNull(manager);
    }

    public abstract void attach() throws DBusException;

    public abstract void detach() throws DBusException;

    public Map<String, Unit> getMonitoredUnits() {
        return Collections.unmodifiableMap(monitoredUnits);
    }

}
