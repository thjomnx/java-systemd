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

import org.freedesktop.dbus.Struct;
import org.freedesktop.dbus.annotations.Position;
import org.freedesktop.dbus.types.UInt32;

public class UnitProcessType extends Struct implements Comparable<UnitProcessType> {

    @Position(0)
    private final String cgroupPath;

    @Position(1)
    private final int pid;

    @Position(2)
    private final String commandLine;

    public UnitProcessType(final String cgroupPath, final UInt32 pid, final String commandLine) {
        super();

        this.cgroupPath = cgroupPath;
        this.pid = pid.intValue();
        this.commandLine = commandLine;
    }

    public String getSummary() {
        return String.format("%s %d %s", cgroupPath, pid, commandLine);
    }

    public String getCgroupPath() {
        return cgroupPath;
    }

    public int getPid() {
        return pid;
    }

    public String getCommandLine() {
        return commandLine;
    }

    @Override
    public int compareTo(final UnitProcessType other) {
        if (other == null) {
            return Integer.MAX_VALUE;
        }
        else {
            return Integer.compare(pid, other.pid);
        }
    }

}
