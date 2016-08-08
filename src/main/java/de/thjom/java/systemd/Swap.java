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

import org.freedesktop.dbus.DBusConnection;
import org.freedesktop.dbus.exceptions.DBusException;

import de.thjom.java.systemd.interfaces.SwapInterface;

public class Swap extends Unit {

    public static final String SERVICE_NAME = Systemd.SERVICE_NAME + ".Swap";
    public static final String UNIT_SUFFIX = ".swap";

    public static class Property extends InterfaceAdapter.Property {

        private Property() {
            super();
        }

        public static final String[] getAllNames() {
            return getAllNames(Property.class);
        }

    }

    private Swap(final DBusConnection dbus, final SwapInterface iface, final String name) throws DBusException {
        super(dbus, iface, name);

        this.properties = Properties.create(dbus, iface.getObjectPath(), SERVICE_NAME);
    }

    static Swap create(final DBusConnection dbus, String name) throws DBusException {
        name = Unit.normalizeName(name, UNIT_SUFFIX);

        String objectPath = Unit.OBJECT_PATH + Systemd.escapePath(name);
        SwapInterface iface = dbus.getRemoteObject(Systemd.SERVICE_NAME, objectPath, SwapInterface.class);

        return new Swap(dbus, iface, name);
    }

    @Override
    public SwapInterface getInterface() {
        return (SwapInterface) super.getInterface();
    }

/*
readonly s What = '/dev/sda3';
readonly i Priority = -1;
readonly t TimeoutUSec = 90000000;
readonly s Slice = 'system.slice';
readonly s ControlGroup = '';
readonly a(sasbttuii) ExecActivate = [];
readonly a(sasbttuii) ExecDeactivate = [];
readonly as Environment = [];
readonly a(sb) EnvironmentFiles = [];
readonly u UMask = 18;
readonly t LimitCPU = 18446744073709551615;
readonly t LimitFSIZE = 18446744073709551615;
readonly t LimitDATA = 18446744073709551615;
readonly t LimitSTACK = 18446744073709551615;
readonly t LimitCORE = 18446744073709551615;
readonly t LimitRSS = 18446744073709551615;
readonly t LimitNOFILE = 4096;
readonly t LimitAS = 18446744073709551615;
readonly t LimitNPROC = 61434;
readonly t LimitMEMLOCK = 65536;
readonly t LimitLOCKS = 18446744073709551615;
readonly t LimitSIGPENDING = 61434;
readonly t LimitMSGQUEUE = 819200;
readonly t LimitNICE = 0;
readonly t LimitRTPRIO = 0;
readonly t LimitRTTIME = 18446744073709551615;
readonly s WorkingDirectory = '';
readonly s RootDirectory = '';
readonly i OOMScoreAdjust = 0;
readonly i Nice = 0;
readonly i IOScheduling = 0;
readonly i CPUSchedulingPolicy = 0;
readonly i CPUSchedulingPriority = 0;
readonly ay CPUAffinity = [];
readonly t TimerSlackNS = 50000;
readonly b CPUSchedulingResetOnFork = false;
readonly b NonBlocking = false;
readonly s StandardInput = 'null';
readonly s StandardOutput = 'journal';
readonly s StandardError = 'inherit';
readonly s TTYPath = '';
readonly b TTYReset = false;
readonly b TTYVHangup = false;
readonly b TTYVTDisallocate = false;
readonly i SyslogPriority = 30;
readonly s SyslogIdentifier = '';
readonly b SyslogLevelPrefix = true;
readonly s Capabilities = '';
readonly i SecureBits = 0;
readonly t CapabilityBoundingSet = 18446744073709551615;
readonly s User = '';
readonly s Group = '';
readonly as SupplementaryGroups = [];
readonly s TCPWrapName = '';
readonly s PAMName = '';
readonly as ReadWriteDirectories = [];
readonly as ReadOnlyDirectories = [];
readonly as InaccessibleDirectories = [];
readonly t MountFlags = 0;
readonly b PrivateTmp = false;
readonly b PrivateNetwork = false;
readonly b SameProcessGroup = false;
readonly s KillMode = 'control-group';
readonly i KillSignal = 15;
readonly s UtmpIdentifier = '';
readonly b IgnoreSIGPIPE = true;
readonly b NoNewPrivileges = false;
readonly au SystemCallFilter = [];
readonly s KillMode = 'control-group';
readonly i KillSignal = 15;
readonly b SendSIGKILL = true;
readonly b SendSIGHUP = false;
readonly b CPUAccounting = false;
readonly t CPUShares = 1024;
readonly b BlockIOAccounting = false;
readonly t BlockIOWeight = 1000;
readonly a(st) BlockIODeviceWeight = [];
readonly a(st) BlockIOReadBandwidth=;
readonly a(st) BlockIOWriteBandwidth=;
readonly b MemoryAccounting = false;
readonly t MemoryLimit = 18446744073709551615;
readonly s DevicePolicy = 'auto';
readonly a(ss) DeviceAllow = [];
readonly u ControlPID = 0;
readonly s Result = 'success';
 */

}
