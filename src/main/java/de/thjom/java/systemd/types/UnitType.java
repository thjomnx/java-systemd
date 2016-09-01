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

package de.thjom.java.systemd.types;

import org.freedesktop.dbus.Path;
import org.freedesktop.dbus.Position;
import org.freedesktop.dbus.UInt32;

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
    private final Path unitObjectPath;

    @Position(7)
    private final int jobId;

    @Position(8)
    private final String jobType;

    @Position(9)
    private final Path jobObjectPath;

    public UnitType(final String unitName, final String unitDescription, final String loadState,
            final String activeState, final String subState, final String followingUnit, final Path unitObjectPath,
            final UInt32 jobId, String jobType, final Path jobObjectPath) {
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

    public String getSummary() {
        StringBuilder summary = new StringBuilder(500);
        summary.append(unitName).append(System.lineSeparator());
        summary.append(String.format(" Description: %s", unitDescription)).append(System.lineSeparator());
        summary.append(String.format(" LoadState: %s", loadState)).append(System.lineSeparator());
        summary.append(String.format(" ActiveState: %s", activeState)).append(System.lineSeparator());
        summary.append(String.format(" SubState: %s", subState)).append(System.lineSeparator());
        summary.append(String.format(" FollowingUnit: %s", followingUnit)).append(System.lineSeparator());
        summary.append(String.format(" ObjectPath: %s", unitObjectPath)).append(System.lineSeparator());
        summary.append(String.format(" JobID: %d", jobId)).append(System.lineSeparator());
        summary.append(String.format(" JobType: %s", jobType)).append(System.lineSeparator());
        summary.append(String.format(" JobObjectPath: %s", jobObjectPath));

        summary.trimToSize();

        return summary.toString();
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

    public Path getUnitObjectPath() {
        return unitObjectPath;
    }

    public int getJobId() {
        return jobId;
    }

    public String getJobType() {
        return jobType;
    }

    public Path getJobObjectPath() {
        return jobObjectPath;
    }

    @Override
    public int compareTo(final UnitType o) {
        if (o == null) {
            return Integer.MAX_VALUE;
        }
        else {
            return unitName.compareTo(o.unitName);
        }
    }

}
