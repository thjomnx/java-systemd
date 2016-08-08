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

package de.thjom.java.systemd.types;

import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

public class DeviceAllowControl {

    private final String deviceIdentifier;
    private final String accessControl;

    public DeviceAllowControl(final Object[] array) {
        this.deviceIdentifier = String.valueOf(array[0]);
        this.accessControl = String.valueOf(array[1]);
    }

    public static List<DeviceAllowControl> list(final Vector<Object[]> vector) {
        List<DeviceAllowControl> ctrls = new ArrayList<>(vector.size());

        for (Object[] array : vector) {
            DeviceAllowControl ctrl = new DeviceAllowControl(array);

            ctrls.add(ctrl);
        }

        return ctrls;
    }

    public String getDeviceIdentifier() {
        return deviceIdentifier;
    }

    public String getAccessControl() {
        return accessControl;
    }

    public String toConfigString() {
        return String.format("%s %s", deviceIdentifier, accessControl);
    }

    @Override
    public String toString() {
        return String.format("DeviceAllowControl [deviceIdentifier=%s, accessControl=%s]", deviceIdentifier, accessControl);
    }

}
