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

package de.mnx.java.systemd.adapters;

import static de.mnx.java.systemd.Systemd.SYSTEMD_DBUS_NAME;
import static de.mnx.java.systemd.Systemd.SYSTEMD_OBJECT_PATH;

import java.util.List;
import java.util.Vector;

import org.freedesktop.dbus.DBusConnection;
import org.freedesktop.dbus.exceptions.DBusException;

import de.mnx.java.systemd.Systemd;
import de.mnx.java.systemd.interfaces.ManagerInterface;
import de.mnx.java.systemd.interfaces.ServiceInterface;
import de.mnx.java.systemd.types.UnitFileType;
import de.mnx.java.systemd.types.UnitType;

public class Manager extends InterfaceAdapter {

    public static final String SERVICE_NAME = SYSTEMD_DBUS_NAME + ".Manager";

    private Properties properties;

    private Manager(final DBusConnection dbus, final ManagerInterface iface) {
        super(dbus, iface);

        this.properties = createProperties(SERVICE_NAME);
    }

    public static Manager create(final DBusConnection dbus) throws DBusException {
        ManagerInterface iface = dbus.getRemoteObject(SYSTEMD_DBUS_NAME, SYSTEMD_OBJECT_PATH, ManagerInterface.class);

        return new Manager(dbus, iface);
    }

    @Override
    public ManagerInterface getInterface() {
        return (ManagerInterface) super.getInterface();
    }

    public List<UnitFileType> listUnitFiles() {
        return getInterface().listUnitFiles();
    }

    public List<UnitType> listUnits() {
        return getInterface().listUnits();
    }

    public Service getService(final String name) throws DBusException {
        String service = name.endsWith(".service") ? name : name + ".service";
        String objectPath = SYSTEMD_OBJECT_PATH + "/unit/" + Systemd.escapePath(service);

        ServiceInterface iface = dbus.getRemoteObject(SYSTEMD_DBUS_NAME, objectPath, ServiceInterface.class);

        return new Service(dbus, iface);
    }

    public String dump() {
        return getInterface().dump();
    }

    public String getArchitecture() {
        return properties.getString("Architecture");
    }

    public boolean getConfirmSpawn() {
        return properties.getBoolean("ConfirmSpawn");
    }

    public String getControlGroup() {
        return properties.getString("ControlGroup");
    }

    public boolean getDefaultBlockIOAccounting() {
        return properties.getBoolean("DefaultBlockIOAccounting");
    }

    public boolean getDefaultCPUAccounting() {
        return properties.getBoolean("DefaultCPUAccounting");
    }

    public String getDefaultStandardError() {
        return properties.getString("DefaultStandardError");
    }

    public String getDefaultStandardOutput() {
        return properties.getString("DefaultStandardOutput");
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

    public String getLogLevel() {
        return properties.getString("LogLevel");
    }

    public String getLogTarget() {
        return properties.getString("LogTarget");
    }

    public int getNumFailedJobs() {
        return properties.getInteger("NFailedJobs");
    }

    public int getNumFailedUnits() {
        return properties.getInteger("NFailedUnits");
    }

    public int getNumInstalledJobs() {
        return properties.getInteger("NInstalledJobs");
    }

    public int getNumJobs() {
        return properties.getInteger("NJobs");
    }

    public double getProgress() {
        return properties.getDouble("Progress");
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

    public boolean getStatus() {
        return properties.getBoolean("ShowStatus");
    }

    public String getSystemState() {
        return properties.getString("SystemState");
    }

    public String getTainted() {
        return properties.getString("Tainted");
    }

    public Vector<String> getUnitPath() {
        return properties.getVector("UnitPath");
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

    /**
     *
  .Architecture                       property  s                "x86-64"                                 const
  .ConfirmSpawn                       property  b                false                                    const
  .ControlGroup                       property  s                ""                                       -
  .DefaultBlockIOAccounting           property  b                false                                    const
  .DefaultCPUAccounting               property  b                false                                    const
.DefaultLimitAS                     property  t                18446744073709551615                     const
.DefaultLimitCORE                   property  t                18446744073709551615                     const
.DefaultLimitCPU                    property  t                18446744073709551615                     const
.DefaultLimitDATA                   property  t                18446744073709551615                     const
.DefaultLimitFSIZE                  property  t                18446744073709551615                     const
.DefaultLimitLOCKS                  property  t                18446744073709551615                     const
.DefaultLimitMEMLOCK                property  t                65536                                    const
.DefaultLimitMSGQUEUE               property  t                819200                                   const
.DefaultLimitNICE                   property  t                0                                        const
.DefaultLimitNOFILE                 property  t                4096                                     const
.DefaultLimitNPROC                  property  t                48013                                    const
.DefaultLimitRSS                    property  t                18446744073709551615                     const
.DefaultLimitRTPRIO                 property  t                0                                        const
.DefaultLimitRTTIME                 property  t                18446744073709551615                     const
.DefaultLimitSIGPENDING             property  t                48013                                    const
.DefaultLimitSTACK                  property  t                18446744073709551615                     const
.DefaultMemoryAccounting            property  b                false                                    const
.DefaultRestartUSec                 property  t                100000                                   const
  .DefaultStandardError               property  s                "journal"                                const
  .DefaultStandardOutput              property  s                "journal"                                const
.DefaultStartLimitBurst             property  u                5                                        const
.DefaultStartLimitInterval          property  t                10000000                                 const
.DefaultTasksAccounting             property  b                true                                     const
.DefaultTasksMax                    property  t                512                                      const
.DefaultTimeoutStartUSec            property  t                90000000                                 const
.DefaultTimeoutStopUSec             property  t                90000000                                 const
.DefaultTimerAccuracyUSec           property  t                60000000                                 const
  .Environment                        property  as               3 "LANG=en_US.UTF-8" "LC_COLLATE=C" "... -
  .ExitCode                           property  y                0                                        -
  .Features                           property  s                "+PAM -AUDIT -SELINUX -IMA -APPARMOR ... const
.FinishTimestamp                    property  t                1454158054001184                         const
.FinishTimestampMonotonic           property  t                43163488                                 const
.FirmwareTimestamp                  property  t                0                                        const
.FirmwareTimestampMonotonic         property  t                0                                        const
.GeneratorsFinishTimestamp          property  t                1454158016424302                         const
.GeneratorsFinishTimestampMonotonic property  t                6933292                                  const
.GeneratorsStartTimestamp           property  t                1454158016391533                         const
.GeneratorsStartTimestampMonotonic  property  t                6900522                                  const
  .InitRDTimestamp                    property  t                0                                        const
  .InitRDTimestampMonotonic           property  t                0                                        const
  .KernelTimestamp                    property  t                1454158009491011                         const
  .KernelTimestampMonotonic           property  t                0                                        const
.LoaderTimestamp                    property  t                0                                        const
.LoaderTimestampMonotonic           property  t                0                                        const
  .LogLevel                           property  s                "info"                                   writable
  .LogTarget                          property  s                "journal-or-kmsg"                        writable
  .NFailedJobs                        property  u                0                                        -
  .NFailedUnits                       property  u                0                                        emits-change
  .NInstalledJobs                     property  u                112                                      -
  .NJobs                              property  u                0                                        -
.NNames                             property  u                239                                      -
  .Progress                           property  d                1                                        -
.RuntimeWatchdogUSec                property  t                0                                        writable
  .SecurityFinishTimestamp            property  t                1454158016368154                         const
  .SecurityFinishTimestampMonotonic   property  t                6877144                                  const
  .SecurityStartTimestamp             property  t                1454158016367424                         const
  .SecurityStartTimestampMonotonic    property  t                6876413                                  const
  .ShowStatus                         property  b                true                                     const
.ShutdownWatchdogUSec               property  t                600000000                                writable
  .SystemState                        property  s                "running"                                -
  .Tainted                            property  s                ""                                       const
.TimerSlackNSec                     property  t                50000                                    const
  .UnitPath                           property  as               5 "/etc/systemd/system" "/run/systemd... const
.UnitsLoadFinishTimestamp           property  t                1454158016467501                         const
.UnitsLoadFinishTimestampMonotonic  property  t                6976491                                  const
.UnitsLoadStartTimestamp            property  t                1454158016426735                         const
.UnitsLoadStartTimestampMonotonic   property  t                6935724                                  const
  .UserspaceTimestamp                 property  t                1454158016364525                         const
  .UserspaceTimestampMonotonic        property  t                6873515                                  const
  .Version                            property  s                "228"                                    const
  .Virtualization                     property  s                ""                                       const
     */

}
