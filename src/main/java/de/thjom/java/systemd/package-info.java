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

/**
 * This package provides classes and interfaces which can be used to interact
 * with "systemd" via D-Bus. The classes provide connectivity to the system
 * and session (user) bus along access to the particular managers and all
 * known units and unit types. Some additional types present pre-built tools
 * which provide basic monitoring features and listener/notifier patterns.
 *
 * @see https://github.com/thjomnx/java-systemd
 * @see https://www.freedesktop.org/wiki/Software/systemd
 * @see https://www.freedesktop.org/wiki/Software/dbus
 */

package de.thjom.java.systemd;
