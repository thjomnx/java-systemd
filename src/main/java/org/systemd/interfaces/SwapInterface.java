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

import org.freedesktop.dbus.DBusInterfaceName;

@DBusInterfaceName(value = org.systemd.Swap.SERVICE_NAME)
public interface SwapInterface extends UnitInterface {

}
