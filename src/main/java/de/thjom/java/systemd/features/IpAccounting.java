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
import de.thjom.java.systemd.types.IpAddressPolicy;

public interface IpAccounting extends Feature {

    static class Property extends InterfaceAdapter.AdapterProperty {

        public static final String IP_ACCOUNTING = "IPAccounting";
        public static final String IP_ADDRESS_ALLOW = "IPAddressAllow";
        public static final String IP_ADDRESS_DENY = "IPAddressDeny";
        public static final String IP_EGRESS_BYTES = "IPEgressBytes";
        public static final String IP_EGRESS_PACKETS = "IPEgressPackets";
        public static final String IP_INGRESS_BYTES = "IPIngressBytes";
        public static final String IP_INGRESS_PACKETS = "IPIngressPackets";

        private Property() {
            super();
        }

        public static final String[] getAllNames() {
            return getAllNames(Property.class);
        }

    }

    default boolean isIPAccounting() {
        return getProperties().getBoolean(Property.IP_ACCOUNTING);
    }

    default List<IpAddressPolicy> getIPAddressAllow() {
        return IpAddressPolicy.list(getProperties().getVector(Property.IP_ADDRESS_ALLOW));
    }

    default List<IpAddressPolicy> getIPAddressDeny() {
        return IpAddressPolicy.list(getProperties().getVector(Property.IP_ADDRESS_DENY));
    }

    default BigInteger getIPEgressBytes() {
        return getProperties().getBigInteger(Property.IP_EGRESS_BYTES);
    }

    default BigInteger getIPEgressPackets() {
        return getProperties().getBigInteger(Property.IP_EGRESS_PACKETS);
    }

    default BigInteger getIPIngressBytes() {
        return getProperties().getBigInteger(Property.IP_INGRESS_BYTES);
    }

    default BigInteger getIPIngressPackets() {
        return getProperties().getBigInteger(Property.IP_INGRESS_PACKETS);
    }

}
