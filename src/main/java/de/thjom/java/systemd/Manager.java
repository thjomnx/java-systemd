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

package de.thjom.java.systemd;

import java.math.BigInteger;
import java.util.List;
import java.util.Vector;

import org.freedesktop.DBus.Introspectable;
import org.freedesktop.dbus.DBusConnection;
import org.freedesktop.dbus.exceptions.DBusException;

import de.thjom.java.systemd.Unit.Mode;
import de.thjom.java.systemd.Unit.Who;
import de.thjom.java.systemd.interfaces.ManagerInterface;
import de.thjom.java.systemd.types.UnitFileType;
import de.thjom.java.systemd.types.UnitType;

public class Manager extends InterfaceAdapter {

    public static final String SERVICE_NAME = Systemd.SERVICE_NAME + ".Manager";

    public static class Property extends InterfaceAdapter.Property {

        public static final String ARCHITECTURE = "Architecture";
        public static final String CONFIRM_SPAWN = "ConfirmSpawn";
        public static final String CONTROL_GROUP = "ControlGroup";
        public static final String DEFAULT_BLOCK_IOACCOUNTING = "DefaultBlockIOAccounting";
        public static final String DEFAULT_CPUACCOUNTING = "DefaultCPUAccounting";
        public static final String DEFAULT_LIMIT_AS = "DefaultLimitAS";
        public static final String DEFAULT_LIMIT_CORE = "DefaultLimitCORE";
        public static final String DEFAULT_LIMIT_CPU = "DefaultLimitCPU";
        public static final String DEFAULT_LIMIT_DATA = "DefaultLimitDATA";
        public static final String DEFAULT_LIMIT_FSIZE = "DefaultLimitFSIZE";
        public static final String DEFAULT_LIMIT_LOCKS = "DefaultLimitLOCKS";
        public static final String DEFAULT_LIMIT_MEMLOCK = "DefaultLimitMEMLOCK";
        public static final String DEFAULT_LIMIT_MSGQUEUE = "DefaultLimitMSGQUEUE";
        public static final String DEFAULT_LIMIT_NICE = "DefaultLimitNICE";
        public static final String DEFAULT_LIMIT_NOFILE = "DefaultLimitNOFILE";
        public static final String DEFAULT_LIMIT_NPROC = "DefaultLimitNPROC";
        public static final String DEFAULT_LIMIT_RSS = "DefaultLimitRSS";
        public static final String DEFAULT_LIMIT_RTPRIO = "DefaultLimitRTPRIO";
        public static final String DEFAULT_LIMIT_RTTIME = "DefaultLimitRTTIME";
        public static final String DEFAULT_LIMIT_SIGPENDING = "DefaultLimitSIGPENDING";
        public static final String DEFAULT_LIMIT_STACK = "DefaultLimitSTACK";
        public static final String DEFAULT_MEMORY_ACCOUNTING = "DefaultMemoryAccounting";
        public static final String DEFAULT_RESTART_USEC = "DefaultRestartUSec";
        public static final String DEFAULT_STANDARD_ERROR = "DefaultStandardError";
        public static final String DEFAULT_STANDARD_OUTPUT = "DefaultStandardOutput";
        public static final String DEFAULT_START_LIMIT_BURST = "DefaultStartLimitBurst";
        public static final String DEFAULT_START_LIMIT_INTERVAL = "DefaultStartLimitInterval";
        public static final String DEFAULT_TASKS_ACCOUNTING = "DefaultTasksAccounting";
        public static final String DEFAULT_TASKS_MAX = "DefaultTasksMax";
        public static final String DEFAULT_TIMEOUT_START_USEC = "DefaultTimeoutStartUSec";
        public static final String DEFAULT_TIMEOUT_STOP_USEC = "DefaultTimeoutStopUSec";
        public static final String DEFAULT_TIMER_ACCURACY_USEC = "DefaultTimerAccuracyUSec";
        public static final String ENVIRONMENT = "Environment";
        public static final String EXIT_CODE = "ExitCode";
        public static final String FEATURES = "Features";
        public static final String FINISH_TIMESTAMP = "FinishTimestamp";
        public static final String FINISH_TIMESTAMP_MONOTONIC = "FinishTimestampMonotonic";
        public static final String FIRMWARE_TIMESTAMP = "FirmwareTimestamp";
        public static final String FIRMWARE_TIMESTAMP_MONOTONIC = "FirmwareTimestampMonotonic";
        public static final String GENERATORS_FINISH_TIMESTAMP = "GeneratorsFinishTimestamp";
        public static final String GENERATORS_FINISH_TIMESTAMP_MONOTONIC = "GeneratorsFinishTimestampMonotonic";
        public static final String GENERATORS_START_TIMESTAMP = "GeneratorsStartTimestamp";
        public static final String GENERATORS_START_TIMESTAMP_MONOTONIC = "GeneratorsStartTimestampMonotonic";
        public static final String INIT_RDTIMESTAMP = "InitRDTimestamp";
        public static final String INIT_RDTIMESTAMP_MONOTONIC = "InitRDTimestampMonotonic";
        public static final String KERNEL_TIMESTAMP = "KernelTimestamp";
        public static final String KERNEL_TIMESTAMP_MONOTONIC = "KernelTimestampMonotonic";
        public static final String LOADER_TIMESTAMP = "LoaderTimestamp";
        public static final String LOADER_TIMESTAMP_MONOTONIC = "LoaderTimestampMonotonic";
        public static final String LOG_LEVEL = "LogLevel";
        public static final String LOG_TARGET = "LogTarget";
        public static final String NFAILED_JOBS = "NFailedJobs";
        public static final String NFAILED_UNITS = "NFailedUnits";
        public static final String NINSTALLED_JOBS = "NInstalledJobs";
        public static final String NJOBS = "NJobs";
        public static final String NNAMES = "NNames";
        public static final String PROGRESS = "Progress";
        public static final String RUNTIME_WATCHDOG_USEC = "RuntimeWatchdogUSec";
        public static final String SECURITY_FINISH_TIMESTAMP = "SecurityFinishTimestamp";
        public static final String SECURITY_FINISH_TIMESTAMP_MONOTONIC = "SecurityFinishTimestampMonotonic";
        public static final String SECURITY_START_TIMESTAMP = "SecurityStartTimestamp";
        public static final String SECURITY_START_TIMESTAMP_MONOTONIC = "SecurityStartTimestampMonotonic";
        public static final String SHOW_STATUS = "ShowStatus";
        public static final String SHUTDOWN_WATCHDOG_USEC = "ShutdownWatchdogUSec";
        public static final String SYSTEM_STATE = "SystemState";
        public static final String TAINTED = "Tainted";
        public static final String TIMER_SLACK_NSEC = "TimerSlackNSec";
        public static final String UNIT_PATH = "UnitPath";
        public static final String UNITS_LOAD_FINISH_TIMESTAMP = "UnitsLoadFinishTimestamp";
        public static final String UNITS_LOAD_FINISH_TIMESTAMP_MONOTONIC = "UnitsLoadFinishTimestampMonotonic";
        public static final String UNITS_LOAD_START_TIMESTAMP = "UnitsLoadStartTimestamp";
        public static final String UNITS_LOAD_START_TIMESTAMP_MONOTONIC = "UnitsLoadStartTimestampMonotonic";
        public static final String USERSPACE_TIMESTAMP = "UserspaceTimestamp";
        public static final String USERSPACE_TIMESTAMP_MONOTONIC = "UserspaceTimestampMonotonic";
        public static final String VERSION = "Version";
        public static final String VIRTUALIZATION = "Virtualization";

        private Property() {
            super();
        }

        public static final String[] getAllNames() {
            return getAllNames(Property.class);
        }

    }

    private Manager(final DBusConnection dbus, final ManagerInterface iface) throws DBusException {
        super(dbus, iface);

        this.properties = Properties.create(dbus, Systemd.OBJECT_PATH, SERVICE_NAME);
    }

    static Manager create(final DBusConnection dbus) throws DBusException {
        ManagerInterface iface = dbus.getRemoteObject(Systemd.SERVICE_NAME, Systemd.OBJECT_PATH, ManagerInterface.class);

        return new Manager(dbus, iface);
    }

    @Override
    public ManagerInterface getInterface() {
        return (ManagerInterface) super.getInterface();
    }

    public String introspect() throws DBusException {
        Introspectable intro = dbus.getRemoteObject(Systemd.SERVICE_NAME, Systemd.OBJECT_PATH, Introspectable.class);

        return intro.Introspect();
    }

    public List<UnitFileType> listUnitFiles() {
        return getInterface().listUnitFiles();
    }

    public List<UnitType> listUnits() {
        return getInterface().listUnits();
    }

    public org.freedesktop.dbus.Path startUnit(final String name, final Mode mode) {
        return startUnit(name, mode.getValue());
    }

    public org.freedesktop.dbus.Path startUnit(final String name, final String mode) {
        return getInterface().startUnit(name, mode);
    }

    public org.freedesktop.dbus.Path stopUnit(final String name, final Mode mode) {
        return stopUnit(name, mode.getValue());
    }

    public org.freedesktop.dbus.Path stopUnit(final String name, final String mode) {
        return getInterface().stopUnit(name, mode);
    }

    public org.freedesktop.dbus.Path reloadUnit(final String name, final Mode mode) {
        return reloadUnit(name, mode.getValue());
    }

    public org.freedesktop.dbus.Path reloadUnit(final String name, final String mode) {
        return getInterface().reloadUnit(name, mode);
    }

    public org.freedesktop.dbus.Path restartUnit(final String name, final Mode mode) {
        return restartUnit(name, mode.getValue());
    }

    public org.freedesktop.dbus.Path restartUnit(final String name, final String mode) {
        return getInterface().restartUnit(name, mode);
    }

    public org.freedesktop.dbus.Path tryRestartUnit(final String name, final Mode mode) {
        return tryRestartUnit(name, mode.getValue());
    }

    public org.freedesktop.dbus.Path tryRestartUnit(final String name, final String mode) {
        return getInterface().tryRestartUnit(name, mode);
    }

    public org.freedesktop.dbus.Path reloadOrRestartUnit(final String name, final Mode mode) {
        return reloadOrRestartUnit(name, mode.getValue());
    }

    public org.freedesktop.dbus.Path reloadOrRestartUnit(final String name, final String mode) {
        return getInterface().reloadOrRestartUnit(name, mode);
    }

    public org.freedesktop.dbus.Path reloadOrTryRestartUnit(final String name, final Mode mode) {
        return reloadOrTryRestartUnit(name, mode.getValue());
    }

    public org.freedesktop.dbus.Path reloadOrTryRestartUnit(final String name, final String mode) {
        return getInterface().reloadOrTryRestartUnit(name, mode);
    }

    public void killUnit(final String name, final Who who, final int signal) {
        killUnit(name, who.getValue(), signal);
    }

    public void killUnit(final String name, final String who, final int signal) {
        getInterface().killUnit(name, who, signal);
    }

    public void resetFailedUnit(final String name) {
        getInterface().resetFailedUnit(name);
    }

    public Automount getAutomount(final String name) throws DBusException {
        return Automount.create(this, name);
    }

    public BusName getBusName(final String name) throws DBusException {
        return BusName.create(this, name);
    }

    public Device getDevice(final String name) throws DBusException {
        return Device.create(this, name);
    }

    public Mount getMount(final String name) throws DBusException {
        return Mount.create(this, name);
    }

    public Path getPath(final String name) throws DBusException {
        return Path.create(this, name);
    }

    public Scope getScope(final String name) throws DBusException {
        return Scope.create(this, name);
    }

    public Service getService(final String name) throws DBusException {
        return Service.create(this, name);
    }

    public Slice getSlice(final String name) throws DBusException {
        return Slice.create(this, name);
    }

    public Snapshot getSnapshot(final String name) throws DBusException {
        return Snapshot.create(this, name);
    }

    public Socket getSocket(final String name) throws DBusException {
        return Socket.create(this, name);
    }

    public Swap getSwap(final String name) throws DBusException {
        return Swap.create(this, name);
    }

    public Target getTarget(final String name) throws DBusException {
        return Target.create(this, name);
    }

    public Timer getTimer(final String name) throws DBusException {
        return Timer.create(this, name);
    }

    public Unit getUnit(final String fullName) throws DBusException {
        String suffix = fullName.substring(fullName.lastIndexOf('.'));
        Unit unit;

        switch (suffix) {
            case Automount.UNIT_SUFFIX:
                unit = getAutomount(fullName);
                break;
            case BusName.UNIT_SUFFIX:
                unit = getBusName(fullName);
                break;
            case Device.UNIT_SUFFIX:
                unit = getDevice(fullName);
                break;
            case Mount.UNIT_SUFFIX:
                unit = getMount(fullName);
                break;
            case Path.UNIT_SUFFIX:
                unit = getPath(fullName);
                break;
            case Scope.UNIT_SUFFIX:
                unit = getScope(fullName);
                break;
            case Service.UNIT_SUFFIX:
                unit = getService(fullName);
                break;
            case Slice.UNIT_SUFFIX:
                unit = getSlice(fullName);
                break;
            case Snapshot.UNIT_SUFFIX:
                unit = getSnapshot(fullName);
                break;
            case Socket.UNIT_SUFFIX:
                unit = getSocket(fullName);
                break;
            case Swap.UNIT_SUFFIX:
                unit = getSwap(fullName);
                break;
            case Target.UNIT_SUFFIX:
                unit = getTarget(fullName);
                break;
            case Timer.UNIT_SUFFIX:
                unit = getTimer(fullName);
                break;
            default:
                throw new IllegalArgumentException(String.format("Unable to determine unit type by name suffix from '%s'", fullName));
        }

        return unit;
    }

    public String dump() {
        return getInterface().dump();
    }

    public String getArchitecture() {
        return properties.getString(Property.ARCHITECTURE);
    }

    public boolean isConfirmSpawn() {
        return properties.getBoolean(Property.CONFIRM_SPAWN);
    }

    public String getControlGroup() {
        return properties.getString(Property.CONTROL_GROUP);
    }

    public boolean isDefaultBlockIOAccounting() {
        return properties.getBoolean(Property.DEFAULT_BLOCK_IOACCOUNTING);
    }

    public boolean isDefaultCPUAccounting() {
        return properties.getBoolean(Property.DEFAULT_CPUACCOUNTING);
    }

    public BigInteger getDefaultLimitAS() {
        return properties.getBigInteger(Property.DEFAULT_LIMIT_AS);
    }

    public BigInteger getDefaultLimitCORE() {
        return properties.getBigInteger(Property.DEFAULT_LIMIT_CORE);
    }

    public BigInteger getDefaultLimitCPU() {
        return properties.getBigInteger(Property.DEFAULT_LIMIT_CPU);
    }

    public BigInteger getDefaultLimitDATA() {
        return properties.getBigInteger(Property.DEFAULT_LIMIT_DATA);
    }

    public BigInteger getDefaultLimitFSIZE() {
        return properties.getBigInteger(Property.DEFAULT_LIMIT_FSIZE);
    }

    public BigInteger getDefaultLimitLOCKS() {
        return properties.getBigInteger(Property.DEFAULT_LIMIT_LOCKS);
    }

    public BigInteger getDefaultLimitMEMLOCK() {
        return properties.getBigInteger(Property.DEFAULT_LIMIT_MEMLOCK);
    }

    public BigInteger getDefaultLimitMSGQUEUE() {
        return properties.getBigInteger(Property.DEFAULT_LIMIT_MSGQUEUE);
    }

    public BigInteger getDefaultLimitNICE() {
        return properties.getBigInteger(Property.DEFAULT_LIMIT_NICE);
    }

    public BigInteger getDefaultLimitNOFILE() {
        return properties.getBigInteger(Property.DEFAULT_LIMIT_NOFILE);
    }

    public BigInteger getDefaultLimitNPROC() {
        return properties.getBigInteger(Property.DEFAULT_LIMIT_NPROC);
    }

    public BigInteger getDefaultLimitRSS() {
        return properties.getBigInteger(Property.DEFAULT_LIMIT_RSS);
    }

    public BigInteger getDefaultLimitRTPRIO() {
        return properties.getBigInteger(Property.DEFAULT_LIMIT_RTPRIO);
    }

    public BigInteger getDefaultLimitRTTIME() {
        return properties.getBigInteger(Property.DEFAULT_LIMIT_RTTIME);
    }

    public BigInteger getDefaultLimitSIGPENDING() {
        return properties.getBigInteger(Property.DEFAULT_LIMIT_SIGPENDING);
    }

    public BigInteger getDefaultLimitSTACK() {
        return properties.getBigInteger(Property.DEFAULT_LIMIT_STACK);
    }

    public boolean isDefaultMemoryAccounting() {
        return properties.getBoolean(Property.DEFAULT_MEMORY_ACCOUNTING);
    }

    public long getDefaultRestartUSec() {
        return properties.getLong(Property.DEFAULT_RESTART_USEC);
    }

    public String getDefaultStandardError() {
        return properties.getString(Property.DEFAULT_STANDARD_ERROR);
    }

    public String getDefaultStandardOutput() {
        return properties.getString(Property.DEFAULT_STANDARD_OUTPUT);
    }

    public long getDefaultStartLimitBurst() {
        return properties.getLong(Property.DEFAULT_START_LIMIT_BURST);
    }

    public BigInteger getDefaultStartLimitInterval() {
        return properties.getBigInteger(Property.DEFAULT_START_LIMIT_INTERVAL);
    }

    public boolean isDefaultTasksAccounting() {
        return properties.getBoolean(Property.DEFAULT_TASKS_ACCOUNTING);
    }

    public BigInteger getDefaultTasksMax() {
        return properties.getBigInteger(Property.DEFAULT_TASKS_MAX);
    }

    public long getDefaultTimeoutStartUSec() {
        return properties.getLong(Property.DEFAULT_TIMEOUT_START_USEC);
    }

    public long getDefaultTimeoutStopUSec() {
        return properties.getLong(Property.DEFAULT_TIMEOUT_STOP_USEC);
    }

    public long getDefaultTimerAccuracyUSec() {
        return properties.getLong(Property.DEFAULT_TIMER_ACCURACY_USEC);
    }

    public Vector<String> getEnvironment() {
        return properties.getVector(Property.ENVIRONMENT);
    }

    public byte getExitCode() {
        return properties.getByte(Property.EXIT_CODE);
    }

    public String getFeatures() {
        return properties.getString(Property.FEATURES);
    }

    public long getFinishTimestamp() {
        return properties.getLong(Property.FINISH_TIMESTAMP);
    }

    public long getFinishTimestampMonotonic() {
        return properties.getLong(Property.FINISH_TIMESTAMP_MONOTONIC);
    }

    public long getFirmwareTimestamp() {
        return properties.getLong(Property.FIRMWARE_TIMESTAMP);
    }

    public long getFirmwareTimestampMonotonic() {
        return properties.getLong(Property.FIRMWARE_TIMESTAMP_MONOTONIC);
    }

    public long getGeneratorsFinishTimestamp() {
        return properties.getLong(Property.GENERATORS_FINISH_TIMESTAMP);
    }

    public long getGeneratorsFinishTimestampMonotonic() {
        return properties.getLong(Property.GENERATORS_FINISH_TIMESTAMP_MONOTONIC);
    }

    public long getGeneratorsStartTimestamp() {
        return properties.getLong(Property.GENERATORS_START_TIMESTAMP);
    }

    public long getGeneratorsStartTimestampMonotonic() {
        return properties.getLong(Property.GENERATORS_START_TIMESTAMP_MONOTONIC);
    }

    public long getInitRDTimestamp() {
        return properties.getLong(Property.INIT_RDTIMESTAMP);
    }

    public long getInitRDTimestampMonotonic() {
        return properties.getLong(Property.INIT_RDTIMESTAMP_MONOTONIC);
    }

    public long getKernelTimestamp() {
        return properties.getLong(Property.KERNEL_TIMESTAMP);
    }

    public long getKernelTimestampMonotonic() {
        return properties.getLong(Property.KERNEL_TIMESTAMP_MONOTONIC);
    }

    public long getLoaderTimestamp() {
        return properties.getLong(Property.LOADER_TIMESTAMP);
    }

    public long getLoaderTimestampMonotonic() {
        return properties.getLong(Property.LOADER_TIMESTAMP_MONOTONIC);
    }

    public String getLogLevel() {
        return properties.getString(Property.LOG_LEVEL);
    }

    public String getLogTarget() {
        return properties.getString(Property.LOG_TARGET);
    }

    public long getNFailedJobs() {
        return properties.getLong(Property.NFAILED_JOBS);
    }

    public long getNFailedUnits() {
        return properties.getLong(Property.NFAILED_UNITS);
    }

    public long getNInstalledJobs() {
        return properties.getLong(Property.NINSTALLED_JOBS);
    }

    public long getNJobs() {
        return properties.getLong(Property.NJOBS);
    }

    public long getNNames() {
        return properties.getLong(Property.NNAMES);
    }

    public double getProgress() {
        return properties.getDouble(Property.PROGRESS);
    }

    public long getRuntimeWatchdogUSec() {
        return properties.getLong(Property.RUNTIME_WATCHDOG_USEC);
    }

    public long getSecurityFinishTimestamp() {
        return properties.getLong(Property.SECURITY_FINISH_TIMESTAMP);
    }

    public long getSecurityFinishTimestampMonotonic() {
        return properties.getLong(Property.SECURITY_FINISH_TIMESTAMP_MONOTONIC);
    }

    public long getSecurityStartTimestamp() {
        return properties.getLong(Property.SECURITY_START_TIMESTAMP);
    }

    public long getSecurityStartTimestampMonotonic() {
        return properties.getLong(Property.SECURITY_START_TIMESTAMP_MONOTONIC);
    }

    public boolean isShowStatus() {
        return properties.getBoolean(Property.SHOW_STATUS);
    }

    public long getShutdownWatchdogUSec() {
        return properties.getLong(Property.SHUTDOWN_WATCHDOG_USEC);
    }

    public String getSystemState() {
        return properties.getString(Property.SYSTEM_STATE);
    }

    public String getTainted() {
        return properties.getString(Property.TAINTED);
    }

    public long getTimerSlackNSec() {
        return properties.getLong(Property.TIMER_SLACK_NSEC);
    }

    public Vector<String> getUnitPath() {
        return properties.getVector(Property.UNIT_PATH);
    }

    public long getUnitsLoadFinishTimestamp() {
        return properties.getLong(Property.UNITS_LOAD_FINISH_TIMESTAMP);
    }

    public long getUnitsLoadFinishTimestampMonotonic() {
        return properties.getLong(Property.UNITS_LOAD_FINISH_TIMESTAMP_MONOTONIC);
    }

    public long getUnitsLoadStartTimestamp() {
        return properties.getLong(Property.UNITS_LOAD_START_TIMESTAMP);
    }

    public long getUnitsLoadStartTimestampMonotonic() {
        return properties.getLong(Property.UNITS_LOAD_START_TIMESTAMP_MONOTONIC);
    }

    public long getUserspaceTimestamp() {
        return properties.getLong(Property.USERSPACE_TIMESTAMP);
    }

    public long getUserspaceTimestampMonotonic() {
        return properties.getLong(Property.USERSPACE_TIMESTAMP_MONOTONIC);
    }

    public String getVersion() {
        return properties.getString(Property.VERSION);
    }

    public String getVirtualization() {
        return properties.getString(Property.VIRTUALIZATION);
    }

}
