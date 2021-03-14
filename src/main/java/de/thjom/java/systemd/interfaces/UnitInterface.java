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

import org.freedesktop.dbus.DBusPath;
import org.freedesktop.dbus.annotations.DBusInterfaceName;
import org.freedesktop.dbus.annotations.DBusMemberName;
import org.freedesktop.dbus.interfaces.DBusInterface;

import java.util.List;

@DBusInterfaceName(value = de.thjom.java.systemd.Unit.SERVICE_NAME)
public interface UnitInterface extends DBusInterface {

    @DBusMemberName(value = "Clean")
    void clean(List<String> mask);

    @DBusMemberName(value = "Freeze")
    void freeze();

    @DBusMemberName(value = "Kill")
    void kill(String who, int signal);

    @DBusMemberName(value = "Ref")
    void ref();

    @DBusMemberName(value = "Reload")
    DBusPath reload(String mode);

    @DBusMemberName(value = "ReloadOrRestart")
    DBusPath reloadOrRestart(String mode);

    @DBusMemberName(value = "ReloadOrTryRestart")
    DBusPath reloadOrTryRestart(String mode);

    @DBusMemberName(value = "ResetFailed")
    void resetFailed();

    @DBusMemberName(value = "Restart")
    DBusPath restart(String mode);

    @DBusMemberName(value = "Start")
    DBusPath start(String mode);

    @DBusMemberName(value = "Stop")
    DBusPath stop(String mode);

    @DBusMemberName(value = "Thaw")
    void thaw();

    @DBusMemberName(value = "TryRestart")
    DBusPath tryRestart(String mode);

    @DBusMemberName(value = "Unref")
    void unref();

}
