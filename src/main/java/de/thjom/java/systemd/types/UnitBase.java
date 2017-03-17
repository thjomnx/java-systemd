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

package de.thjom.java.systemd.types;

import java.util.Objects;

import org.freedesktop.dbus.Struct;

import de.thjom.java.systemd.Automount;
import de.thjom.java.systemd.BusName;
import de.thjom.java.systemd.Device;
import de.thjom.java.systemd.Mount;
import de.thjom.java.systemd.Path;
import de.thjom.java.systemd.Scope;
import de.thjom.java.systemd.Service;
import de.thjom.java.systemd.Slice;
import de.thjom.java.systemd.Snapshot;
import de.thjom.java.systemd.Socket;
import de.thjom.java.systemd.Swap;
import de.thjom.java.systemd.Target;
import de.thjom.java.systemd.Timer;

abstract class UnitBase extends Struct {

    private final String identifier;

    protected UnitBase(final String identifier) {
        this.identifier = Objects.requireNonNull(identifier);
    }

    public final boolean isAutomount() {
        return identifier.endsWith(Automount.UNIT_SUFFIX);
    }

    public final boolean isBusName() {
        return identifier.endsWith(BusName.UNIT_SUFFIX);
    }

    public final boolean isDevice() {
        return identifier.endsWith(Device.UNIT_SUFFIX);
    }

    public final boolean isMount() {
        return identifier.endsWith(Mount.UNIT_SUFFIX);
    }

    public final boolean isPath() {
        return identifier.endsWith(Path.UNIT_SUFFIX);
    }

    public final boolean isScope() {
        return identifier.endsWith(Scope.UNIT_SUFFIX);
    }

    public final boolean isService() {
        return identifier.endsWith(Service.UNIT_SUFFIX);
    }

    public final boolean isSlice() {
        return identifier.endsWith(Slice.UNIT_SUFFIX);
    }

    public final boolean isSnapshot() {
        return identifier.endsWith(Snapshot.UNIT_SUFFIX);
    }

    public final boolean isSocket() {
        return identifier.endsWith(Socket.UNIT_SUFFIX);
    }

    public final boolean isSwap() {
        return identifier.endsWith(Swap.UNIT_SUFFIX);
    }

    public final boolean isTarget() {
        return identifier.endsWith(Target.UNIT_SUFFIX);
    }

    public final boolean isTimer() {
        return identifier.endsWith(Timer.UNIT_SUFFIX);
    }

}
