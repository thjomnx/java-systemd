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

package de.thjom.java.systemd.features;

import java.math.BigInteger;
import java.util.List;

import de.thjom.java.systemd.InterfaceAdapter;
import de.thjom.java.systemd.types.IOBandwidth;
import de.thjom.java.systemd.types.IODeviceLatency;
import de.thjom.java.systemd.types.IODeviceWeight;
import de.thjom.java.systemd.types.IOIops;

public interface IoAccounting extends Feature {

    static class Property extends InterfaceAdapter.AdapterProperty {

        public static final String BLOCK_IO_ACCOUNTING = "BlockIOAccounting";
        public static final String BLOCK_IO_DEVICE_WEIGHT = "BlockIODeviceWeight";
        public static final String BLOCK_IO_READ_BANDWIDTH = "BlockIOReadBandwidth";
        public static final String BLOCK_IO_WEIGHT = "BlockIOWeight";
        public static final String BLOCK_IO_WRITE_BANDWIDTH = "BlockIOWriteBandwidth";
        public static final String IO_ACCOUNTING = "IOAccounting";
        public static final String IO_DEVICE_LATENCY_TARGET_USEC = "IODeviceLatencyTargetUSec";
        public static final String IO_DEVICE_WEIGHT = "IODeviceWeight";
        public static final String IO_READ_BANDWIDTH_MAX = "IOReadBandwidthMax";
        public static final String IO_READ_BYTES = "IOReadBytes";
        public static final String IO_READ_IOPS_MAX = "IOReadIOPSMax";
        public static final String IO_READ_OPERATIONS = "IOReadOperations";
        public static final String IO_WEIGHT = "IOWeight";
        public static final String IO_WRITE_BANDWIDTH_MAX = "IOWriteBandwidthMax";
        public static final String IO_WRITE_BYTES = "IOWriteBytes";
        public static final String IO_WRITE_IOPS_MAX = "IOWriteIOPSMax";
        public static final String IO_WRITE_OPERATIONS = "IOWriteOperations";
        public static final String IP_EGRESS_FILTER_PATH = "IPEgressFilterPath";
        public static final String IP_INGRESS_FILTER_PATH = "IPIngressFilterPath";
        public static final String STARTUP_BLOCK_IO_WEIGHT = "StartupBlockIOWeight";
        public static final String STARTUP_IO_WEIGHT = "StartupIOWeight";

        private Property() {
            super();
        }

        public static final String[] getAllNames() {
            return getAllNames(Property.class);
        }

    }

    default boolean isBlockIOAccounting() {
        return getProperties().getBoolean(Property.BLOCK_IO_ACCOUNTING);
    }

    default List<IODeviceWeight> getBlockIODeviceWeight() {
        return IODeviceWeight.list(getProperties().getList(Property.BLOCK_IO_DEVICE_WEIGHT));
    }

    default List<IOBandwidth> getBlockIOReadBandwidth() {
        return IOBandwidth.list(getProperties().getList(Property.BLOCK_IO_READ_BANDWIDTH));
    }

    default BigInteger getBlockIOWeight() {
        return getProperties().getBigInteger(Property.BLOCK_IO_WEIGHT);
    }

    default List<IOBandwidth> getBlockIOWriteBandwidth() {
        return IOBandwidth.list(getProperties().getList(Property.BLOCK_IO_WRITE_BANDWIDTH));
    }

    default boolean isIOAccounting() {
        return getProperties().getBoolean(Property.IO_ACCOUNTING);
    }

    default List<IODeviceLatency> getIODeviceLatencyTargetUSec() {
        return IODeviceLatency.list(getProperties().getList(Property.IO_DEVICE_LATENCY_TARGET_USEC));
    }

    default List<IODeviceWeight> getIODeviceWeight() {
        return IODeviceWeight.list(getProperties().getList(Property.IO_DEVICE_WEIGHT));
    }

    default List<IOBandwidth> getIOReadBandwidthMax() {
        return IOBandwidth.list(getProperties().getList(Property.IO_READ_BANDWIDTH_MAX));
    }

    default BigInteger getIOReadBytes() {
        return getProperties().getBigInteger(Property.IO_READ_BYTES);
    }

    default List<IOIops> getIOReadIOPSMax() {
        return IOIops.list(getProperties().getList(Property.IO_READ_IOPS_MAX));
    }

    default BigInteger getIOReadOperations() {
        return getProperties().getBigInteger(Property.IO_READ_OPERATIONS);
    }

    default BigInteger getIOWeight() {
        return getProperties().getBigInteger(Property.IO_WEIGHT);
    }

    default List<IOBandwidth> getIOWriteBandwidthMax() {
        return IOBandwidth.list(getProperties().getList(Property.IO_WRITE_BANDWIDTH_MAX));
    }

    default BigInteger getIOWriteBytes() {
        return getProperties().getBigInteger(Property.IO_WRITE_BYTES);
    }

    default List<IOIops> getIOWriteIOPSMax() {
        return IOIops.list(getProperties().getList(Property.IO_WRITE_IOPS_MAX));
    }

    default BigInteger getIOWriteOperations() {
        return getProperties().getBigInteger(Property.IO_WRITE_OPERATIONS);
    }

    default List<String> getIPEgressFilterPath() {
        return getProperties().getList(Property.IP_EGRESS_FILTER_PATH);
    }

    default List<String> getIPIngressFilterPath() {
        return getProperties().getList(Property.IP_INGRESS_FILTER_PATH);
    }

    default BigInteger getStartupBlockIOWeight() {
        return getProperties().getBigInteger(Property.STARTUP_BLOCK_IO_WEIGHT);
    }

    default BigInteger getStartupIOWeight() {
        return getProperties().getBigInteger(Property.STARTUP_IO_WEIGHT);
    }

}
