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

package de.thjom.java.systemd;

import java.util.HashSet;
import java.util.Set;

import org.testng.Assert;

import de.thjom.java.systemd.Unit;

public abstract class UnitTest extends AbstractTestCase {

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

    public void testUnitProperties(Unit unit, String[] propertyNames) {
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
