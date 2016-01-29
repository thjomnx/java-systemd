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

package de.mnx.java.systemd.types;

import org.freedesktop.dbus.Path;
import org.freedesktop.dbus.Position;
import org.freedesktop.dbus.Struct;
import org.freedesktop.dbus.UInt32;

public class UnitStatus extends Struct {

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
    private final UInt32 jobId;

    @Position(8)
    private final String jobType;

    @Position(9)
    private final Path jobObjectPath;

    public UnitStatus(final String unitName, final String unitDescription, final String loadState,
            final String activeState, final String subState, final String followingUnit, final Path unitObjectPath,
            final UInt32 jobId, String jobType, final Path jobObjectPath) {
        this.unitName = unitName;
        this.unitDescription = unitDescription;
        this.loadState = loadState;
        this.activeState = activeState;
        this.subState = subState;
        this.followingUnit = followingUnit;
        this.unitObjectPath = unitObjectPath;
        this.jobId = jobId;
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

    public Path getUnitObjectPath() {
        return unitObjectPath;
    }

    public UInt32 getJobId() {
        return jobId;
    }

    public String getJobType() {
        return jobType;
    }

    public Path getJobObjectPath() {
        return jobObjectPath;
    }

}
