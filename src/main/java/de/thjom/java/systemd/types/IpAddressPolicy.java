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

package de.thjom.java.systemd.types;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.freedesktop.dbus.types.UInt32;

public class IpAddressPolicy {

    private final int family;
    private final byte[] address;
    private final long prefix;

    public IpAddressPolicy(final Object[] array) {
        this.family = (int) array[0];

        @SuppressWarnings("unchecked")
        List<Byte> list = (List<Byte>) array[1];
        byte[] bytes = new byte[list.size()];

        for (int i = 0; i < list.size(); i++) {
            bytes[i] = list.get(i);
        }

        this.address = bytes;
        this.prefix = ((UInt32) array[2]).longValue();
    }

    public static List<IpAddressPolicy> list(final List<Object[]> arrays) {
        List<IpAddressPolicy> policies = new ArrayList<>(arrays.size());

        for (Object[] array : arrays) {
            IpAddressPolicy policy = new IpAddressPolicy(array);

            policies.add(policy);
        }

        return policies;
    }

    public int getFamily() {
        return family;
    }

    public byte[] getAddress() {
        return Arrays.copyOf(address, address.length);
    }

    public long getPrefix() {
        return prefix;
    }

    public String toConfigString() throws UnknownHostException {
        return String.format("%s/%d", InetAddress.getByAddress(address).getHostAddress(), prefix);
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder("IpAddressPolicy [family=");

        if (family == 2) {
            builder.append("IPv4");
        }
        else if (family == 10) {
            builder.append("IPv6");
        }
        else {
            builder.append("unknown");
        }

        builder.append('(').append(family).append(')');
        builder.append(", address=");

        try {
            builder.append(InetAddress.getByAddress(address).getHostAddress());
        }
        catch (final UnknownHostException e) {
            builder.append("unknown");
        }

        builder.append("/").append(prefix).append("]");

        return builder.toString();
    }

}
