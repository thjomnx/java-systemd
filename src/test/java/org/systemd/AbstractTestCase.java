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

import org.freedesktop.dbus.DBusConnection;
import org.freedesktop.dbus.exceptions.DBusException;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.systemd.interfaces.ManagerInterface;
import org.systemd.interfaces.PropertyInterface;
import org.testng.Assert;

public class AbstractTestCase {

    @Mock
    protected DBusConnection dbus;

    @Mock
    protected ManagerInterface miface;

    @Mock
    protected PropertyInterface piface;

    @InjectMocks
    protected Systemd systemd;

    public void setup() {
        MockitoAnnotations.initMocks(this);

        try {
            Mockito.when(dbus.getRemoteObject(Systemd.SERVICE_NAME, Systemd.OBJECT_PATH, ManagerInterface.class)).thenReturn(miface);
            Mockito.when(dbus.getRemoteObject(Systemd.SERVICE_NAME, Systemd.OBJECT_PATH, PropertyInterface.class)).thenReturn(piface);
        }
        catch (DBusException e) {
            Assert.fail(e.getMessage(), e);
        }
    }

}
