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

import java.util.Arrays;
import java.util.List;

import org.testng.Assert;

public abstract class UnitTest extends AbstractTestCase {

    private static final String[] NON_VARIANT_PROPERTIES = {

            Unit.Property.ASSERTS,
            Unit.Property.CONDITIONS,
            Unit.Property.JOB,
            Unit.Property.LOAD_ERROR

    };

    @Override
    public void setup() {
        super.setup();

        setupPropertyMocks(Unit.class, Unit.SERVICE_NAME, Unit.Property.getAllNames());
    }

    public void testUnitProperties(Unit unit) {
        List<String> nonVariants = Arrays.asList(NON_VARIANT_PROPERTIES);

        for (String propertyName : Unit.Property.getAllNames()) {
            if (!nonVariants.contains(propertyName)) {
                Object value = unit.getUnitProperties().getVariant(propertyName).getValue();

                Assert.assertNotNull(value);
            }
        }
    }

}
