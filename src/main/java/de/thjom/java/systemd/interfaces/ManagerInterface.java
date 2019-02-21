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

import org.freedesktop.dbus.DBusPath;
import org.freedesktop.dbus.annotations.DBusInterfaceName;
import org.freedesktop.dbus.annotations.DBusMemberName;
import org.freedesktop.dbus.exceptions.DBusException;
import org.freedesktop.dbus.interfaces.DBusInterface;

import de.thjom.java.systemd.Signal;
import de.thjom.java.systemd.types.UnitFileChange;
import de.thjom.java.systemd.types.UnitFileType;
import de.thjom.java.systemd.types.UnitProcessType;
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
    DBusPath getUnitByPID(int pid);

    @DBusMemberName(value = "GetUnitFileLinks")
    List<String> getUnitFileLinks(final String name, final boolean runtime);

    @DBusMemberName(value = "GetUnitFileState")
    String getUnitFileState(String name);

    @DBusMemberName(value = "GetUnitProcesses")
    List<UnitProcessType> getUnitProcesses(String name);

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
    DBusPath loadUnit(String name);

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
    DBusPath reloadOrRestartUnit(String name, String mode);

    @DBusMemberName(value = "ReloadOrTryRestartUnit")
    DBusPath reloadOrTryRestartUnit(String name, String mode);

    @DBusMemberName(value = "ReloadUnit")
    DBusPath reloadUnit(String name, String mode);

    @DBusMemberName(value = "ResetFailed")
    void resetFailed();

    @DBusMemberName(value = "ResetFailedUnit")
    void resetFailedUnit(String name);

    @DBusMemberName(value = "RestartUnit")
    DBusPath restartUnit(String name, String mode);

    @DBusMemberName(value = "SetDefaultTarget")
    List<UnitFileChange> setDefaultTarget(String name, boolean force);

    @DBusMemberName(value = "SetEnvironment")
    void setEnvironment(String name);

    @DBusMemberName(value = "SetExitCode")
    void setExitCode(byte value);

    @DBusMemberName(value = "StartUnit")
    DBusPath startUnit(String name, String mode);

    @DBusMemberName(value = "StopUnit")
    DBusPath stopUnit(String name, String mode);

    @DBusMemberName(value = "Subscribe")
    void subscribe();

    @DBusMemberName(value = "SwitchRoot")
    void switchRoot(String newRoot, String init);

    @DBusMemberName(value = "TryRestartUnit")
    DBusPath tryRestartUnit(String name, String mode);

    @DBusMemberName(value = "UnrefUnit")
    void unrefUnit(String name);

    @DBusMemberName(value = "UnsetAndSetEnvironment")
    void unsetAndSetEnvironment(List<String> namesToUnset, List<String> namesToSet);

    @DBusMemberName(value = "UnsetEnvironment")
    void unsetEnvironment(List<String> names);

    @DBusMemberName(value = "Unsubscribe")
    void unsubscribe();

    class JobNew extends Signal {

        public JobNew(String objectPath, long id, DBusPath job, String unit) throws DBusException {
            super(objectPath, id, job, unit);
        }

        public long getId() {
            return getParameter(0, 0L);
        }

        public DBusPath getJob() {
            return getParameter(1, null);
        }

        public String getUnit() {
            return getParameter(2, "");
        }

    }

    class JobRemoved extends Signal {

        public JobRemoved(String objectPath, long id, DBusPath job, String unit, String result) throws DBusException {
            super(objectPath, id, job, unit, result);
        }

        public long getId() {
            return getParameter(0, 0L);
        }

        public DBusPath getJob() {
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

        public UnitNew(String objectPath, String id, DBusPath unit) throws DBusException {
            super(objectPath, id, unit);
        }

        public String getId() {
            return getParameter(0, "");
        }

        public DBusPath getUnit() {
            return getParameter(1, null);
        }

    }

    class UnitRemoved extends Signal {

        public UnitRemoved(String objectPath, String id, DBusPath unit) throws DBusException {
            super(objectPath, id, unit);
        }

        public String getId() {
            return getParameter(0, "");
        }

        public DBusPath getUnit() {
            return getParameter(1, null);
        }

    }

}
