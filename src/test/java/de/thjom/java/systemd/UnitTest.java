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

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import org.freedesktop.dbus.exceptions.DBusException;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import de.thjom.java.systemd.interfaces.UnitInterface;

public class UnitTest extends AbstractTestCase {

    protected final Set<String> nonVariantProperties = new HashSet<>();

    @Mock
    UnitInterface uiface;

    @Override
    @BeforeClass
    public void setup() {
        super.setup();

        try {
            Mockito.when(uiface.getObjectPath()).thenReturn("foo");
            Mockito.when(dbus.getRemoteObject(Mockito.eq(Systemd.SERVICE_NAME), Mockito.anyString(), Mockito.eq(UnitInterface.class))).thenReturn(uiface);
            Mockito.when(dbus.getUniqueName()).thenReturn("unique");
        }
        catch (DBusException e) {
            Assert.fail(e.getMessage(), e);
        }

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

    @Test(description = "")
    public void testEquals() throws DBusException {
        TestUnit foo0 = TestUnit.create(systemd.getManager(), "foo.service");
        TestUnit foo1 = TestUnit.create(systemd.getManager(), "foo.service");
        TestUnit bar = TestUnit.create(systemd.getManager(), "bar.service");

        Assert.assertNotEquals(foo0, null);
        Assert.assertNotEquals(foo0, new Object());
        Assert.assertEquals(foo0, foo0);
        Assert.assertEquals(foo0, foo1);
        Assert.assertNotEquals(foo0, bar);
        Assert.assertNotEquals(foo1, bar);
    }

    @Test(description = "")
    public void testHashCode() throws DBusException {
        TestUnit foo0 = TestUnit.create(systemd.getManager(), "foo.service");
        TestUnit foo1 = TestUnit.create(systemd.getManager(), "foo.service");
        TestUnit bar = TestUnit.create(systemd.getManager(), "bar.service");

        Assert.assertEquals(foo0.hashCode(), foo0.hashCode());
        Assert.assertEquals(foo0.hashCode(), foo1.hashCode());
        Assert.assertNotEquals(foo0.hashCode(), bar.hashCode());
        Assert.assertNotEquals(foo1.hashCode(), bar.hashCode());
    }

    // This method is called from derived test classes hence no need for annotation
    public void testUnitProperties(final Unit unit, final Collection<String> propertyNames) {
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

    private static class TestUnit extends Unit {

        static final String SERVICE_NAME = Unit.SERVICE_NAME;
        static final String UNIT_SUFFIX = ".dummy";

        private TestUnit(final Manager manager, final UnitInterface iface, final String name) throws DBusException {
            super(manager, iface, name);

            this.properties = Properties.create(dbus, iface.getObjectPath(), SERVICE_NAME);
        }

        static TestUnit create(final Manager manager, String name) throws DBusException {
            name = Unit.normalizeName(name, UNIT_SUFFIX);

            String objectPath = Unit.OBJECT_PATH + Systemd.escapePath(name);
            UnitInterface iface = manager.dbus.getRemoteObject(Systemd.SERVICE_NAME, objectPath, UnitInterface.class);

            return new TestUnit(manager, iface, name);
        }

    }

}
