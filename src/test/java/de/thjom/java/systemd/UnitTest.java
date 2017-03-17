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

import java.util.HashSet;
import java.util.Set;

import org.testng.Assert;
import org.testng.annotations.Test;

public class UnitTest extends AbstractTestCase {

    protected final Set<String> nonVariantProperties = new HashSet<>();

    @Override
    public void setup() {
        super.setup();

        setupPropertyMocks(Unit.class, Unit.SERVICE_NAME, Unit.Property.getAllNames());

        nonVariantProperties.add(Unit.Property.ASSERTS);
        nonVariantProperties.add(Unit.Property.CONDITIONS);
        nonVariantProperties.add(Unit.Property.JOB);
        nonVariantProperties.add(Unit.Property.LOAD_ERROR);
    }

    @Test(description="Tests unit name normalizer method.")
    public void testNormalizeName() {
        Assert.assertEquals(Unit.normalizeName("dbus", ".service"), "dbus.service");
        Assert.assertEquals(Unit.normalizeName("dbus.service", ".service"), "dbus.service");

        Assert.assertEquals(Unit.normalizeName(null, ".service"), "");
        Assert.assertEquals(Unit.normalizeName("dbus.service", null), "dbus.service");
        Assert.assertEquals(Unit.normalizeName(null, null), "");
    }

    @Test(description="Tests unit name extractor method.")
    public void testExtractName() {
        Assert.assertEquals(Unit.extractName("/org/freedesktop/systemd1/unit/dbus_2eservice"), "dbus_2eservice");
        Assert.assertEquals(Unit.extractName("/org/freedesktop/systemd1"), "");
        Assert.assertEquals(Unit.extractName(""), "");
        Assert.assertEquals(Unit.extractName(null), "");
    }

    // This method is called from derived test classes hence no need for annotation
    public void testUnitProperties(final Unit unit, final String[] propertyNames) {
        for (String propertyName : Unit.Property.getAllNames()) {
            if (!nonVariantProperties.contains(propertyName)) {
                Object value = unit.getUnitProperties().getVariant(propertyName).getValue();

                Assert.assertNotNull(value);
            }
        }

        for (String propertyName : propertyNames) {
            if (!nonVariantProperties.contains(propertyName)) {
                Object value = unit.getProperties().getVariant(propertyName).getValue();

                Assert.assertNotNull(value);
            }
        }
    }

}
