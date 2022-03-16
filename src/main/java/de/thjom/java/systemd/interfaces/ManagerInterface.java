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

import de.thjom.java.systemd.types.Pair;
import de.thjom.java.systemd.Signal;
import de.thjom.java.systemd.types.StructForUnitEnableAndDisable;
import de.thjom.java.systemd.types.*;
import org.freedesktop.dbus.DBusPath;
import org.freedesktop.dbus.annotations.DBusInterfaceName;
import org.freedesktop.dbus.annotations.DBusMemberName;
import org.freedesktop.dbus.exceptions.DBusException;
import org.freedesktop.dbus.interfaces.DBusInterface;

import java.util.List;

@DBusInterfaceName(value = de.thjom.java.systemd.Manager.SERVICE_NAME)
public interface ManagerInterface extends DBusInterface {

    @DBusMemberName(value = "AddDependencyUnitFiles")
    List<UnitFileChange> addDependencyUnitFiles(List<String> names, String target, String type, boolean runtime, boolean force);

    @DBusMemberName(value = "CancelJob")
    void cancelJob(long id);

    @DBusMemberName(value = "CleanUnit")
    void cleanUnit(String name, List<String> mask);

    @DBusMemberName(value = "ClearJobs")
    void clearJobs();

    @DBusMemberName(value = "DisableUnitFiles")
    List<StructForUnitEnableAndDisable> disableUnitFiles(List<String> names, boolean runtime);

    @DBusMemberName(value = "Dump")
    String dump();

    @DBusMemberName(value = "EnableUnitFiles")
    Pair<Boolean, List<StructForUnitEnableAndDisable>> enableUnitFiles(List<String> names, boolean runtime, boolean force);

    @DBusMemberName(value = "Exit")
    void exit();

    @DBusMemberName(value = "FreezeUnit")
    void freezeUnit(String name);

    @DBusMemberName(value = "GetDefaultTarget")
    String getDefaultTarget();

    @DBusMemberName(value = "GetDynamicUsers")
    List<DynamicUser> getDynamicUsers();

    @DBusMemberName(value = "GetUnitByPID")
    DBusPath getUnitByPID(int pid);

    @DBusMemberName(value = "GetUnitFileLinks")
    List<String> getUnitFileLinks(String name, boolean runtime);

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

    @DBusMemberName(value = "LinkUnitFiles")
    List<UnitFileChange> linkUnitFiles(List<String> names, boolean runtime, boolean force);

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

    @DBusMemberName(value = "MaskUnitFiles")
    List<UnitFileChange> maskUnitFiles(List<String> names, boolean runtime, boolean force);

    @DBusMemberName(value = "PowerOff")
    void powerOff();

    @DBusMemberName(value = "PresetUnitFiles")
    List<UnitFileInstallChange> presetUnitFiles(List<String> names, boolean runtime, boolean force);

    @DBusMemberName(value = "PresetUnitFilesWithMode")
    List<UnitFileInstallChange> presetUnitFilesWithMode(List<String> names, String mode, boolean runtime, boolean force);

    @DBusMemberName(value = "Reboot")
    void reboot();

    @DBusMemberName(value = "ReenableUnitFiles")
    List<UnitFileInstallChange> reenableUnitFiles(List<String> names, boolean runtime, boolean force);

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

    @DBusMemberName(value = "RevertUnitFiles")
    List<UnitFileChange> revertUnitFiles(List<String> names);

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

    @DBusMemberName(value = "ThawUnit")
    void thawUnit(String name);

    @DBusMemberName(value = "TryRestartUnit")
    DBusPath tryRestartUnit(String name, String mode);

    @DBusMemberName(value = "UnmaskUnitFiles")
    List<UnitFileChange> unmaskUnitFiles(List<String> names, boolean runtime);

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
