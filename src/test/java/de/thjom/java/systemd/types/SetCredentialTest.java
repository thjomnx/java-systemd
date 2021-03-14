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

import org.testng.Assert;
import org.testng.annotations.Test;

public class SetCredentialTest {

    @Test(description = "Tests parameterized constructor.")
    public void testConstructor() {
        SetCredential instance = new SetCredential(new Object[] {
                "foo",
                Arrays.asList((byte) 0x00, (byte) 0x23, (byte) 0x42)
        });

        Assert.assertNotNull(instance);
        Assert.assertEquals(instance.getId(), "foo");
        Assert.assertEquals(instance.getData(), new byte[] { 0x00, 0x23, 0x42 });
    }

    @Test(description = "Tests constructor failure cases due to malformed arguments.", expectedExceptions = ArrayIndexOutOfBoundsException.class)
    public void testConstructorFailures() {
        new SetCredential(new Object[0]);
    }

    @Test(description = "Tests processing of multiple data rows.")
    public void testBulkProcessing() {
        List<Object[]> source = new ArrayList<>();

        List<SetCredential> list = SetCredential.list(source);

        Assert.assertNotNull(list);
        Assert.assertEquals(list.size(), 0);

        // Next test
        source.add(new Object[] {
                "foo",
                Arrays.asList((byte) 0x23, (byte) 0x42)
        });
        source.add(new Object[] {
                "bar",
                Arrays.asList((byte) 0x42, (byte) 0x23)
        });

        list = SetCredential.list(source);

        Assert.assertNotNull(list);
        Assert.assertEquals(list.size(), 2);

        SetCredential item = list.get(0);

        Assert.assertNotNull(item);
        Assert.assertEquals(item.getId(), "foo");
        Assert.assertEquals(item.getData(), new byte[] { 0x23, 0x42 });

        item = list.get(1);

        Assert.assertNotNull(item);
        Assert.assertEquals(item.getId(), "bar");
        Assert.assertEquals(item.getData(), new byte[] { 0x42, 0x23 });
    }

    @Test(description = "Tests processing failure cases on multiple data rows.", expectedExceptions = ArrayIndexOutOfBoundsException.class)
    public void testBulkProcessingFailureEmptyArray() {
        List<Object[]> list = new ArrayList<>();
        list.add(new Object[0]);

        SetCredential.list(list);
    }

    @Test(description = "Tests processing failure cases on multiple data rows.", expectedExceptions = NullPointerException.class)
    public void testBulkProcessingFailureMalformedArray() {
        List<Object[]> list = new ArrayList<>();
        list.add(new Object[] {
                "foo",
                null
        });

        SetCredential.list(list);
    }

}
