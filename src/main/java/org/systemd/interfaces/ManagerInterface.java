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

package org.systemd.interfaces;

import java.util.List;

import org.freedesktop.dbus.DBusInterface;
import org.freedesktop.dbus.DBusInterfaceName;
import org.freedesktop.dbus.DBusMemberName;
import org.freedesktop.dbus.Path;
import org.freedesktop.dbus.exceptions.DBusException;
import org.systemd.Signal;
import org.systemd.types.UnitFileType;
import org.systemd.types.UnitType;

@DBusInterfaceName(value = org.systemd.Manager.SERVICE_NAME)
public interface ManagerInterface extends DBusInterface {

    @DBusMemberName(value = "ListUnitFiles")
    List<UnitFileType> listUnitFiles();

    @DBusMemberName(value = "ListUnits")
    List<UnitType> listUnits();

    @DBusMemberName(value = "GetUnit")
    DBusInterface getUnit(final String name);

    @DBusMemberName(value = "Dump")
    String dump();

    public class JobNew extends Signal {

        public JobNew(final String objectPath, final long id, final Path job, final String unit) throws DBusException {
            super(objectPath, id, job, unit);
        }

        public long getId() {
            return getParameter(0, 0L);
        }

        public Path getJob() {
            return getParameter(1, null);
        }

        public String getUnit() {
            return getParameter(2, "");
        }

    }

    public class JobRemoved extends Signal {

        public JobRemoved(final String objectPath, final long id, final Path job, final String unit, final String result) throws DBusException {
            super(objectPath, id, job, unit, result);
        }

        public long getId() {
            return getParameter(0, 0L);
        }

        public Path getJob() {
            return getParameter(1, null);
        }

        public String getUnit() {
            return getParameter(2, "");
        }

        public String getResult() {
            return getParameter(3, "");
        }

    }

    public class Reloading extends Signal {

        public Reloading(final String objectPath, final boolean active) throws DBusException {
            super(objectPath, active);
        }

        public boolean isActive() {
            return getParameter(0, false);
        }

    }

    public class StartupFinished extends Signal {

        public StartupFinished(final String objectPath, final long firmware, final long loader, final long kernel, final long initrd,
                final long userspace, final long total) throws DBusException {
            super(objectPath, firmware, loader, kernel, initrd, userspace, total);
        }

        public long getFirmware() {
            return getParameter(0, -1L);
        }

        public long getLoader() {
            return getParameter(1, -1L);
        }

        public long getKernel() {
            return getParameter(2, -1L);
        }

        public long getInitRD() {
            return getParameter(3, -1L);
        }

        public long getUserspace() {
            return getParameter(4, -1L);
        }

        public long getTotal() {
            return getParameter(5, -1L);
        }

    }

    public class UnitFilesChanged extends Signal {

        public UnitFilesChanged(final String objectPath) throws DBusException {
            super(objectPath);
        }

    }

    public class UnitNew extends Signal {

        public UnitNew(final String objectPath, final String id, final Path unit) throws DBusException {
            super(objectPath, id, unit);
        }

        public String getId() {
            return getParameter(0, "");
        }

        public Path getUnit() {
            return getParameter(1, null);
        }

    }

    public class UnitRemoved extends Signal {

        public UnitRemoved(final String objectPath, final String id, final Path unit) throws DBusException {
            super(objectPath, id, unit);
        }

        public String getId() {
            return getParameter(0, "");
        }

        public Path getUnit() {
            return getParameter(1, null);
        }

    }

}
