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

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.freedesktop.dbus.types.UInt32;
import org.freedesktop.dbus.types.UInt64;

public class ExtendedExecutionInfo {

    private final String binaryPath;
    private final List<String> arguments;
    private final List<String> commandFlags;
    private final long lastStartTimestamp;
    private final long lastStartTimestampMonotonic;
    private final long lastFinishTimestamp;
    private final long lastFinishTimestampMonotonic;
    private final int processId;
    private final int lastExitCode;
    private final int lastExitStatus;

    @SuppressWarnings("unchecked")
    public ExtendedExecutionInfo(final Object[] array) {
        this.binaryPath = String.valueOf(array[0]);
        this.arguments = (List<String>) array[1];
        this.commandFlags = (List<String>) array[2];
        this.lastStartTimestamp = ((UInt64) array[3]).longValue();
        this.lastStartTimestampMonotonic = ((UInt64) array[4]).longValue();
        this.lastFinishTimestamp = ((UInt64) array[5]).longValue();
        this.lastFinishTimestampMonotonic = ((UInt64) array[6]).longValue();
        this.processId = ((UInt32) array[7]).intValue();
        this.lastExitCode = (int) array[8];
        this.lastExitStatus = (int) array[9];
    }

    public static List<ExtendedExecutionInfo> list(final Collection<Object[]> arrays) {
        List<ExtendedExecutionInfo> execs = new ArrayList<>(arrays.size());

        for (Object[] array : arrays) {
            ExtendedExecutionInfo exec = new ExtendedExecutionInfo(array);

            execs.add(exec);
        }

        return execs;
    }

    public String getBinaryPath() {
        return binaryPath;
    }

    public List<String> getArguments() {
        return arguments;
    }

    public List<String> getCommandFlags() {
        return commandFlags;
    }

    public long getLastStartTimestamp() {
        return lastStartTimestamp;
    }

    public long getLastStartTimestampMonotonic() {
        return lastStartTimestampMonotonic;
    }

    public long getLastFinishTimestamp() {
        return lastFinishTimestamp;
    }

    public long getLastFinishTimestampMonotonic() {
        return lastFinishTimestampMonotonic;
    }

    public int getProcessId() {
        return processId;
    }

    public int getLastExitCode() {
        return lastExitCode;
    }

    public int getLastExitStatus() {
        return lastExitStatus;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("ExtendedExecutionInfo [binaryPath=");
        builder.append(binaryPath);
        builder.append(", arguments=");
        builder.append(arguments);
        builder.append(", commandFlags=");
        builder.append(commandFlags);
        builder.append(", lastStartTimestamp=");
        builder.append(lastStartTimestamp);
        builder.append(", lastStartTimestampMonotonic=");
        builder.append(lastStartTimestampMonotonic);
        builder.append(", lastFinishTimestamp=");
        builder.append(lastFinishTimestamp);
        builder.append(", lastFinishTimestampMonotonic=");
        builder.append(lastFinishTimestampMonotonic);
        builder.append(", processId=");
        builder.append(processId);
        builder.append(", lastExitCode=");
        builder.append(lastExitCode);
        builder.append(", lastExitStatus=");
        builder.append(lastExitStatus);
        builder.append("]");

        return builder.toString();
    }

}
