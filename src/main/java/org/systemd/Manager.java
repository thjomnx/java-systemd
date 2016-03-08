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

package org.systemd;

import java.math.BigInteger;
import java.util.List;
import java.util.Vector;

import org.freedesktop.DBus.Introspectable;
import org.freedesktop.dbus.DBusConnection;
import org.freedesktop.dbus.DBusSigHandler;
import org.freedesktop.dbus.exceptions.DBusException;
import org.systemd.interfaces.ManagerInterface;
import org.systemd.interfaces.ManagerInterface.Reloading;
import org.systemd.interfaces.ManagerInterface.UnitFilesChanged;
import org.systemd.types.UnitFileType;
import org.systemd.types.UnitType;

public class Manager extends InterfaceAdapter {

    public static final String SERVICE_NAME = Systemd.SERVICE_NAME + ".Manager";

    protected final ReloadingHandler reloadingHandler = new ReloadingHandler();
    protected final UnitFilesChangedHandler unitFilesChangedHandler = new UnitFilesChangedHandler();

    private final Properties properties;

    private Manager(final DBusConnection dbus, final ManagerInterface iface) throws DBusException {
        super(dbus, iface);

        this.properties = Properties.create(dbus, Systemd.OBJECT_PATH, SERVICE_NAME);

        dbus.addSigHandler(ManagerInterface.Reloading.class, reloadingHandler);
        dbus.addSigHandler(ManagerInterface.UnitFilesChanged.class, unitFilesChangedHandler);
    }

    static Manager create(final DBusConnection dbus) throws DBusException {
        ManagerInterface iface = dbus.getRemoteObject(Systemd.SERVICE_NAME, Systemd.OBJECT_PATH, ManagerInterface.class);

        return new Manager(dbus, iface);
    }

    @Override
    protected void finalize() throws Throwable {
        dbus.removeSigHandler(ManagerInterface.Reloading.class, reloadingHandler);
        dbus.removeSigHandler(ManagerInterface.UnitFilesChanged.class, unitFilesChangedHandler);
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

    public Automount getAutomount(final String name) throws DBusException {
        return Automount.create(dbus, name);
    }

    public BusName getBusName(final String name) throws DBusException {
        return BusName.create(dbus, name);
    }

    public Device getDevice(final String name) throws DBusException {
        return Device.create(dbus, name);
    }

    public Mount getMount(final String name) throws DBusException {
        return Mount.create(dbus, name);
    }

    public Path getPath(final String name) throws DBusException {
        return Path.create(dbus, name);
    }

    public Scope getScope(final String name) throws DBusException {
        return Scope.create(dbus, name);
    }

    public Service getService(final String name) throws DBusException {
        return Service.create(dbus, name);
    }

    public Slice getSlice(final String name) throws DBusException {
        return Slice.create(dbus, name);
    }

    public Snapshot getSnapshot(final String name) throws DBusException {
        return Snapshot.create(dbus, name);
    }

    public Socket getSocket(final String name) throws DBusException {
        return Socket.create(dbus, name);
    }

    public Swap getSwap(final String name) throws DBusException {
        return Swap.create(dbus, name);
    }

    public Target getTarget(final String name) throws DBusException {
        return Target.create(dbus, name);
    }

    public Timer getTimer(final String name) throws DBusException {
        return Timer.create(dbus, name);
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
        return properties.getString("Architecture");
    }

    public boolean isConfirmSpawn() {
        return properties.getBoolean("ConfirmSpawn");
    }

    public String getControlGroup() {
        return properties.getString("ControlGroup");
    }

    public boolean isDefaultBlockIOAccounting() {
        return properties.getBoolean("DefaultBlockIOAccounting");
    }

    public boolean isDefaultCPUAccounting() {
        return properties.getBoolean("DefaultCPUAccounting");
    }

    public BigInteger getDefaultLimitAS() {
        return properties.getBigInteger("DefaultLimitAS");
    }

    public BigInteger getDefaultLimitCORE() {
        return properties.getBigInteger("DefaultLimitCORE");
    }

    public BigInteger getDefaultLimitCPU() {
        return properties.getBigInteger("DefaultLimitCPU");
    }

    public BigInteger getDefaultLimitDATA() {
        return properties.getBigInteger("DefaultLimitDATA");
    }

    public BigInteger getDefaultLimitFSIZE() {
        return properties.getBigInteger("DefaultLimitFSIZE");
    }

    public BigInteger getDefaultLimitLOCKS() {
        return properties.getBigInteger("DefaultLimitLOCKS");
    }

    public BigInteger getDefaultLimitMEMLOCK() {
        return properties.getBigInteger("DefaultLimitMEMLOCK");
    }

    public BigInteger getDefaultLimitMSGQUEUE() {
        return properties.getBigInteger("DefaultLimitMSGQUEUE");
    }

    public BigInteger getDefaultLimitNICE() {
        return properties.getBigInteger("DefaultLimitNICE");
    }

    public BigInteger getDefaultLimitNOFILE() {
        return properties.getBigInteger("DefaultLimitNOFILE");
    }

    public BigInteger getDefaultLimitNPROC() {
        return properties.getBigInteger("DefaultLimitNPROC");
    }

    public BigInteger getDefaultLimitRSS() {
        return properties.getBigInteger("DefaultLimitRSS");
    }

    public BigInteger getDefaultLimitRTPRIO() {
        return properties.getBigInteger("DefaultLimitRTPRIO");
    }

    public BigInteger getDefaultLimitRTTIME() {
        return properties.getBigInteger("DefaultLimitRTTIME");
    }

    public BigInteger getDefaultLimitSIGPENDING() {
        return properties.getBigInteger("DefaultLimitSIGPENDING");
    }

    public BigInteger getDefaultLimitSTACK() {
        return properties.getBigInteger("DefaultLimitSTACK");
    }

    public boolean isDefaultMemoryAccounting() {
        return properties.getBoolean("DefaultMemoryAccounting");
    }

    public long getDefaultRestartUSec() {
        return properties.getLong("DefaultRestartUSec");
    }

    public String getDefaultStandardError() {
        return properties.getString("DefaultStandardError");
    }

    public String getDefaultStandardOutput() {
        return properties.getString("DefaultStandardOutput");
    }

    public long getDefaultStartLimitBurst() {
        return properties.getLong("DefaultStartLimitBurst");
    }

    public BigInteger getDefaultStartLimitInterval() {
        return properties.getBigInteger("DefaultStartLimitInterval");
    }

    public boolean isDefaultTasksAccounting() {
        return properties.getBoolean("DefaultTasksAccounting");
    }

    public BigInteger getDefaultTasksMax() {
        return properties.getBigInteger("DefaultTasksMax");
    }

    public long getDefaultTimeoutStartUSec() {
        return properties.getLong("DefaultTimeoutStartUSec");
    }

    public long getDefaultTimeoutStopUSec() {
        return properties.getLong("DefaultTimeoutStopUSec");
    }

    public long getDefaultTimerAccuracyUSec() {
        return properties.getLong("DefaultTimerAccuracyUSec");
    }

    public Vector<String> getEnvironment() {
        return properties.getVector("Environment");
    }

    public byte getExitCode() {
        return properties.getByte("ExitCode");
    }

    public String getFeatures() {
        return properties.getString("Features");
    }

    public long getFinishTimestamp() {
        return properties.getLong("FinishTimestamp");
    }

    public long getFinishTimestampMonotonic() {
        return properties.getLong("FinishTimestampMonotonic");
    }

    public long getFirmwareTimestamp() {
        return properties.getLong("FirmwareTimestamp");
    }

    public long getFirmwareTimestampMonotonic() {
        return properties.getLong("FirmwareTimestampMonotonic");
    }

    public long getGeneratorsFinishTimestamp() {
        return properties.getLong("GeneratorsFinishTimestamp");
    }

    public long getGeneratorsFinishTimestampMonotonic() {
        return properties.getLong("GeneratorsFinishTimestampMonotonic");
    }

    public long getGeneratorsStartTimestamp() {
        return properties.getLong("GeneratorsStartTimestamp");
    }

    public long getGeneratorsStartTimestampMonotonic() {
        return properties.getLong("GeneratorsStartTimestampMonotonic");
    }

    public long getInitRDTimestamp() {
        return properties.getLong("InitRDTimestamp");
    }

    public long getInitRDTimestampMonotonic() {
        return properties.getLong("InitRDTimestampMonotonic");
    }

    public long getKernelTimestamp() {
        return properties.getLong("KernelTimestamp");
    }

    public long getKernelTimestampMonotonic() {
        return properties.getLong("KernelTimestampMonotonic");
    }

    public long getLoaderTimestamp() {
        return properties.getLong("LoaderTimestamp");
    }

    public long getLoaderTimestampMonotonic() {
        return properties.getLong("LoaderTimestampMonotonic");
    }

    public String getLogLevel() {
        return properties.getString("LogLevel");
    }

    public String getLogTarget() {
        return properties.getString("LogTarget");
    }

    public long getNFailedJobs() {
        return properties.getLong("NFailedJobs");
    }

    public long getNFailedUnits() {
        return properties.getLong("NFailedUnits");
    }

    public long getNInstalledJobs() {
        return properties.getLong("NInstalledJobs");
    }

    public long getNJobs() {
        return properties.getLong("NJobs");
    }

    public long getNNames() {
        return properties.getLong("NNames");
    }

    public double getProgress() {
        return properties.getDouble("Progress");
    }

    public long getRuntimeWatchdogUSec() {
        return properties.getLong("RuntimeWatchdogUSec");
    }

    public long getSecurityFinishTimestamp() {
        return properties.getLong("SecurityFinishTimestamp");
    }

    public long getSecurityFinishTimestampMonotonic() {
        return properties.getLong("SecurityFinishTimestampMonotonic");
    }

    public long getSecurityStartTimestamp() {
        return properties.getLong("SecurityStartTimestamp");
    }

    public long getSecurityStartTimestampMonotonic() {
        return properties.getLong("SecurityStartTimestampMonotonic");
    }

    public boolean isShowStatus() {
        return properties.getBoolean("ShowStatus");
    }

    public long getShutdownWatchdogUSec() {
        return properties.getLong("ShutdownWatchdogUSec");
    }

    public String getSystemState() {
        return properties.getString("SystemState");
    }

    public String getTainted() {
        return properties.getString("Tainted");
    }

    public long getTimerSlackNSec() {
        return properties.getLong("TimerSlackNSec");
    }

    public Vector<String> getUnitPath() {
        return properties.getVector("UnitPath");
    }

    public long getUnitsLoadFinishTimestamp() {
        return properties.getLong("UnitsLoadFinishTimestamp");
    }

    public long getUnitsLoadFinishTimestampMonotonic() {
        return properties.getLong("UnitsLoadFinishTimestampMonotonic");
    }

    public long getUnitsLoadStartTimestamp() {
        return properties.getLong("UnitsLoadStartTimestamp");
    }

    public long getUnitsLoadStartTimestampMonotonic() {
        return properties.getLong("UnitsLoadStartTimestampMonotonic");
    }

    public long getUserspaceTimestamp() {
        return properties.getLong("UserspaceTimestamp");
    }

    public long getUserspaceTimestampMonotonic() {
        return properties.getLong("UserspaceTimestampMonotonic");
    }

    public String getVersion() {
        return properties.getString("Version");
    }

    public String getVirtualization() {
        return properties.getString("Virtualization");
    }

    public class ReloadingHandler implements DBusSigHandler<ManagerInterface.Reloading> {

        @Override
        public void handle(final Reloading signal) {
            setChanged();
            notifyObservers(signal);
        }

    }

    public class UnitFilesChangedHandler implements DBusSigHandler<ManagerInterface.UnitFilesChanged> {

        @Override
        public void handle(final UnitFilesChanged signal) {
            setChanged();
            notifyObservers(signal);
        }

    }

}
