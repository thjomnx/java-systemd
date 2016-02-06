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

package de.mnx.java.systemd.interfaces;

import org.freedesktop.dbus.DBusInterface;
import org.freedesktop.dbus.DBusInterfaceName;
import org.freedesktop.dbus.DBusMemberName;
import org.freedesktop.dbus.Path;

@DBusInterfaceName(value = de.mnx.java.systemd.Unit.SERVICE_NAME)
public interface UnitInterface extends DBusInterface {

    @DBusMemberName(value = "Kill")
    void kill(final String who, final int signal);

    @DBusMemberName(value = "Reload")
    Path reload(final String mode);

    @DBusMemberName(value = "ReloadOrRestart")
    Path reloadOrRestart(final String mode);

    @DBusMemberName(value = "ReloadOrTryRestart")
    Path reloadOrTryRestart(final String mode);

    @DBusMemberName(value = "ResetFailed")
    void resetFailed();

    @DBusMemberName(value = "Restart")
    Path restart(final String mode);

    @DBusMemberName(value = "Start")
    Path start(final String mode);

    @DBusMemberName(value = "Stop")
    Path stop(final String mode);

    @DBusMemberName(value = "TryRestart")
    Path tryRestart(final String mode);

}
