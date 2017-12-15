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

package de.thjom.java.systemd;

import java.math.BigInteger;
import java.util.List;

import de.thjom.java.systemd.types.IOBandwidth;
import de.thjom.java.systemd.types.IODeviceWeight;
import de.thjom.java.systemd.types.IOIops;

public interface IoAccountable {

    static class Property extends InterfaceAdapter.AdapterProperty {

        public static final String IO_ACCOUNTING = "IOAccounting";
        public static final String IO_DEVICE_WEIGHT = "IODeviceWeight";
        public static final String IO_READ_BANDWIDTH_MAX = "IOReadBandwidthMax";
        public static final String IO_READ_IOPS_MAX = "IOReadIOPSMax";
        public static final String IO_WEIGHT = "IOWeight";
        public static final String IO_WRITE_BANDWIDTH_MAX = "IOWriteBandwidthMax";
        public static final String IO_WRITE_IOPS_MAX = "IOWriteIOPSMax";

        private Property() {
            super();
        }

        public static final String[] getAllNames() {
            return getAllNames(Property.class);
        }

    }

    Properties getProperties();

    default boolean isIOAccounting() {
        return getProperties().getBoolean(Property.IO_ACCOUNTING);
    }

    default List<IODeviceWeight> getIODeviceWeight() {
        return IODeviceWeight.list(getProperties().getVector(Property.IO_DEVICE_WEIGHT));
    }

    default List<IOBandwidth> getIOReadBandwidthMax() {
        return IOBandwidth.list(getProperties().getVector(Property.IO_READ_BANDWIDTH_MAX));
    }

    default List<IOIops> getIOReadIOPSMax() {
        return IOIops.list(getProperties().getVector(Property.IO_READ_IOPS_MAX));
    }

    default BigInteger getIOWeight() {
        return getProperties().getBigInteger(Property.IO_WEIGHT);
    }

    default List<IOBandwidth> getIOWriteBandwidthMax() {
        return IOBandwidth.list(getProperties().getVector(Property.IO_WRITE_BANDWIDTH_MAX));
    }

    default List<IOIops> getIOWriteIOPSMax() {
        return IOIops.list(getProperties().getVector(Property.IO_WRITE_IOPS_MAX));
    }

}
