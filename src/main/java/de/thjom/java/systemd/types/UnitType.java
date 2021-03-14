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

import org.freedesktop.dbus.DBusPath;
import org.freedesktop.dbus.annotations.Position;
import org.freedesktop.dbus.types.UInt32;


public class UnitType extends UnitBase implements Comparable<UnitType> {

    @Position(0)
    private final String unitName;

    @Position(1)
    private final String unitDescription;

    @Position(2)
    private final String loadState;

    @Position(3)
    private final String activeState;

    @Position(4)
    private final String subState;

    @Position(5)
    private final String followingUnit;

    @Position(6)
    private final DBusPath unitObjectPath;

    @Position(7)
    private final int jobId;

    @Position(8)
    private final String jobType;

    @Position(9)
    private final DBusPath jobObjectPath;

    public UnitType(final String unitName, final String unitDescription, final String loadState,
            final String activeState, final String subState, final String followingUnit, final DBusPath unitObjectPath,
            final UInt32 jobId, String jobType, final DBusPath jobObjectPath) {
        super(unitName);

        this.unitName = unitName;
        this.unitDescription = unitDescription;
        this.loadState = loadState;
        this.activeState = activeState;
        this.subState = subState;
        this.followingUnit = followingUnit;
        this.unitObjectPath = unitObjectPath;
        this.jobId = jobId.intValue();
        this.jobType = jobType;
        this.jobObjectPath = jobObjectPath;
    }

    public String getUnitName() {
        return unitName;
    }

    public String getUnitDescription() {
        return unitDescription;
    }

    public String getLoadState() {
        return loadState;
    }

    public String getActiveState() {
        return activeState;
    }

    public String getSubState() {
        return subState;
    }

    public String getFollowingUnit() {
        return followingUnit;
    }

    public DBusPath getUnitObjectPath() {
        return unitObjectPath;
    }

    public int getJobId() {
        return jobId;
    }

    public String getJobType() {
        return jobType;
    }

    public DBusPath getJobObjectPath() {
        return jobObjectPath;
    }

    public String toFormattedString() {
        StringBuilder builder = new StringBuilder(500)
                .append(unitName).append(System.lineSeparator())
                .append(" Description: ").append(unitDescription).append(System.lineSeparator())
                .append(" LoadState: ").append(loadState).append(System.lineSeparator())
                .append(" ActiveState: ").append(activeState).append(System.lineSeparator())
                .append(" SubState: ").append(subState).append(System.lineSeparator())
                .append(" FollowingUnit: ").append(followingUnit).append(System.lineSeparator())
                .append(" ObjectPath: ").append(unitObjectPath).append(System.lineSeparator())
                .append(" JobID: ").append(jobId).append(System.lineSeparator())
                .append(" JobType: ").append(jobType).append(System.lineSeparator())
                .append(" JobObjectPath: ").append(jobObjectPath).append(System.lineSeparator());

        builder.trimToSize();

        return builder.toString();
    }

    @Override
    public int compareTo(final UnitType other) {
        if (other == null) {
            return Integer.MAX_VALUE;
        }
        else {
            return unitName.compareTo(other.unitName);
        }
    }

}
