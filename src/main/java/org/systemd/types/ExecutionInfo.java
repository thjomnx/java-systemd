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

package org.systemd.types;

import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

import org.freedesktop.dbus.UInt32;
import org.freedesktop.dbus.UInt64;

public class ExecutionInfo {

    private final String binaryPath;
    private final Vector<String> arguments;
    private final boolean failOnUncleanExit;
    private final long lastStartTimestamp;
    private final long lastStartTimestampMonotonic;
    private final long lastFinishTimestamp;
    private final long lastFinishTimestampMonotonic;
    private final int processId;
    private final int lastExitCode;
    private final int lastExitStatus;

    @SuppressWarnings("unchecked")
    public ExecutionInfo(final Object[] array) {
        this.binaryPath = String.valueOf(array[0]);
        this.arguments = (Vector<String>) array[1];
        this.failOnUncleanExit = (boolean) array[2];
        this.lastStartTimestamp = ((UInt64) array[3]).longValue();
        this.lastStartTimestampMonotonic = ((UInt64) array[4]).longValue();
        this.lastFinishTimestamp = ((UInt64) array[5]).longValue();
        this.lastFinishTimestampMonotonic = ((UInt64) array[6]).longValue();
        this.processId = ((UInt32) array[7]).intValue();
        this.lastExitCode = (int) array[8];
        this.lastExitStatus = (int) array[9];
    }

    public static List<ExecutionInfo> list(final Vector<Object[]> vector) {
        List<ExecutionInfo> execs = new ArrayList<>(vector.size());

        for (Object[] array : vector) {
            ExecutionInfo exec = new ExecutionInfo(array);

            execs.add(exec);
        }

        return execs;
    }

    public String getBinaryPath() {
        return binaryPath;
    }

    public Vector<String> getArguments() {
        return arguments;
    }

    public boolean isFailOnUncleanExit() {
        return failOnUncleanExit;
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
        builder.append("ExecutionInfo [binaryPath=");
        builder.append(binaryPath);
        builder.append(", arguments=");
        builder.append(arguments);
        builder.append(", failOnUncleanExit=");
        builder.append(failOnUncleanExit);
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
