/*
 * Java-systemd implementation
 * Copyright (c) 2016 Markus Enax
 *
 * This program is free software; you can redistribute it and/or modify it
 * under the terms of either the GNU Lesser General Public License Version 2 or the
 * Academic Free Licence Version 3.0.
 *
 * Full licence texts are included in the COPYING file with this program.
 */

package de.thjom.java.systemd.types;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.freedesktop.dbus.types.UInt32;
import org.testng.Assert;
import org.testng.annotations.Test;

public class IpAddressPolicyTest {

    @Test(description = "Tests parameterized constructor.")
    public void testConstructor() {
        IpAddressPolicy instance = new IpAddressPolicy(new Object[] {
                2,
                Arrays.asList((byte) 127, (byte) 0, (byte) 0, (byte) 1),
                new UInt32("8")
        }
        );

        Assert.assertNotNull(instance);
        Assert.assertEquals(instance.getFamily(), 2);
        Assert.assertEquals(instance.getAddress(), new byte[] { 127, 0, 0, 1 });
        Assert.assertEquals(instance.getPrefix(), 8L);
    }

    @Test(description = "Tests constructor failure cases due to malformed arguments.", expectedExceptions = ArrayIndexOutOfBoundsException.class)
    public void testConstructorFailures() {
        new IpAddressPolicy(new Object[0]);
    }

    @Test(description = "Tests processing of multiple data rows.")
    public void testBulkProcessing() {
        List<Object[]> source = new ArrayList<>();

        List<IpAddressPolicy> list = IpAddressPolicy.list(source);

        Assert.assertNotNull(list);
        Assert.assertEquals(list.size(), 0);

        // Next test
        Byte[] inet4Address = new Byte[] { 127, 0, 0, 1 };

        Byte[] inet6Address = new Byte[16];
        Arrays.fill(inet6Address, (byte) 0);
        inet6Address[15] = (byte) 1;

        source.add(new Object[] {
                4,
                Arrays.asList(inet4Address),
                new UInt32("8")
        });
        source.add(new Object[] {
                6,
                Arrays.asList(inet6Address),
                new UInt32("128")
        });

        list = IpAddressPolicy.list(source);

        Assert.assertNotNull(list);
        Assert.assertEquals(list.size(), 2);

        IpAddressPolicy item = list.get(0);

        Assert.assertNotNull(item);
        Assert.assertEquals(item.getFamily(), 4);
        Assert.assertEquals(item.getAddress(), new byte[] { 127, 0, 0, 1 });
        Assert.assertEquals(item.getPrefix(), 8L);

        item = list.get(1);

        Assert.assertNotNull(item);
        Assert.assertEquals(item.getFamily(), 6);
        Assert.assertEquals(item.getAddress(), new byte[] { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1 });
        Assert.assertEquals(item.getPrefix(), 128L);
    }

    @Test(description = "Tests processing failure cases on multiple data rows.", expectedExceptions = ArrayIndexOutOfBoundsException.class)
    public void testBulkProcessingFailureEmptyArray() {
        List<Object[]> list = new ArrayList<>();
        list.add(new Object[0]);

        IpAddressPolicy.list(list);
    }

    @Test(description = "Tests processing failure cases on multiple data rows.", expectedExceptions = NullPointerException.class)
    public void testBulkProcessingFailureMalformedArray() {
        List<Object[]> list = new ArrayList<>();
        list.add(new Object[] {
                4,
                null,
                new UInt32("16")
        });

        IpAddressPolicy.list(list);
    }

}