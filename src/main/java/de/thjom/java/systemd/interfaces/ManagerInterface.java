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

package de.thjom.java.systemd.interfaces;

import java.util.List;

import org.freedesktop.dbus.DBusInterface;
import org.freedesktop.dbus.DBusInterfaceName;
import org.freedesktop.dbus.DBusMemberName;
import org.freedesktop.dbus.Path;
import org.freedesktop.dbus.exceptions.DBusException;

import de.thjom.java.systemd.Signal;
import de.thjom.java.systemd.types.UnitFileType;
import de.thjom.java.systemd.types.UnitType;

@DBusInterfaceName(value = de.thjom.java.systemd.Manager.SERVICE_NAME)
public interface ManagerInterface extends DBusInterface {

    @DBusMemberName(value = "CancelJob")
    void cancelJob(long id);

    @DBusMemberName(value = "ClearJobs")
    void clearJobs();

    @DBusMemberName(value = "Dump")
    String dump();

    @DBusMemberName(value = "Exit")
    void exit();

    @DBusMemberName(value = "GetDefaultTarget")
    String getDefaultTarget();

    @DBusMemberName(value = "GetUnitByPID")
    Path getUnitByPID(int pid);

    @DBusMemberName(value = "Halt")
    void halt();

    @DBusMemberName(value = "KExec")
    void kExec();

    @DBusMemberName(value = "KillUnit")
    void killUnit(String name, String who, int signal);

    @DBusMemberName(value = "ListUnitFiles")
    List<UnitFileType> listUnitFiles();

    @DBusMemberName(value = "ListUnits")
    List<UnitType> listUnits();

    @DBusMemberName(value = "LoadUnit")
    Path loadUnit(String name);

    @DBusMemberName(value = "LookupDynamicUserByName")
    long lookupDynamicUserByName(String name);

    @DBusMemberName(value = "LookupDynamicUserByUID")
    String lookupDynamicUserByUID(long uid);

    @DBusMemberName(value = "PowerOff")
    void powerOff();

    @DBusMemberName(value = "Reboot")
    void reboot();

    @DBusMemberName(value = "Reexecute")
    void reexecute();

    @DBusMemberName(value = "RefUnit")
    void refUnit(String name);

    @DBusMemberName(value = "Reload")
    void reload();

    @DBusMemberName(value = "ReloadOrRestartUnit")
    Path reloadOrRestartUnit(String name, String mode);

    @DBusMemberName(value = "ReloadOrTryRestartUnit")
    Path reloadOrTryRestartUnit(String name, String mode);

    @DBusMemberName(value = "ReloadUnit")
    Path reloadUnit(String name, String mode);

    @DBusMemberName(value = "ResetFailed")
    void resetFailed();

    @DBusMemberName(value = "ResetFailedUnit")
    void resetFailedUnit(String name);

    @DBusMemberName(value = "RestartUnit")
    Path restartUnit(String name, String mode);

    @DBusMemberName(value = "SetExitCode")
    void setExitCode(byte value);

    @DBusMemberName(value = "StartUnit")
    Path startUnit(String name, String mode);

    @DBusMemberName(value = "StopUnit")
    Path stopUnit(String name, String mode);

    @DBusMemberName(value = "Subscribe")
    void subscribe();

    @DBusMemberName(value = "TryRestartUnit")
    Path tryRestartUnit(String name, String mode);

    @DBusMemberName(value = "UnrefUnit")
    void unrefUnit(String name);

    @DBusMemberName(value = "Unsubscribe")
    void unsubscribe();

    class JobNew extends Signal {

        public JobNew(String objectPath, long id, Path job, String unit) throws DBusException {
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

    class JobRemoved extends Signal {

        public JobRemoved(String objectPath, long id, Path job, String unit, String result) throws DBusException {
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

    class Reloading extends Signal {

        public Reloading(String objectPath, boolean active) throws DBusException {
            super(objectPath, active);
        }

        public boolean isActive() {
            return getParameter(0, false);
        }

    }

    class StartupFinished extends Signal {

        public StartupFinished(String objectPath, long firmware, long loader, long kernel, long initrd,
                long userspace, long total) throws DBusException {
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

    class UnitFilesChanged extends Signal {

        public UnitFilesChanged(String objectPath) throws DBusException {
            super(objectPath);
        }

    }

    class UnitNew extends Signal {

        public UnitNew(String objectPath, String id, Path unit) throws DBusException {
            super(objectPath, id, unit);
        }

        public String getId() {
            return getParameter(0, "");
        }

        public Path getUnit() {
            return getParameter(1, null);
        }

    }

    class UnitRemoved extends Signal {

        public UnitRemoved(String objectPath, String id, Path unit) throws DBusException {
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
