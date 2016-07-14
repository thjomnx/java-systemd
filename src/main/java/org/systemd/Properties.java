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

import java.math.BigInteger;
import java.util.Vector;

import org.freedesktop.dbus.DBusConnection;
import org.freedesktop.dbus.UInt64;
import org.freedesktop.dbus.Variant;
import org.freedesktop.dbus.exceptions.DBusException;
import org.systemd.interfaces.PropertyInterface;

public class Properties extends InterfaceAdapter {

    public static final String SERVICE_NAME = "org.freedesktop.DBus.Properties";

    private final String serviceName;

    private Properties(final DBusConnection dbus, final PropertyInterface iface, final String serviceName) {
        super(dbus, iface);

        this.serviceName = serviceName;
    }

    static Properties create(final DBusConnection dbus, final String objectPath, final String serviceName) throws DBusException {
        PropertyInterface iface = dbus.getRemoteObject(Systemd.SERVICE_NAME, objectPath, PropertyInterface.class);

        return new Properties(dbus, iface, serviceName);
    }

    @Override
    public PropertyInterface getInterface() {
        return (PropertyInterface) super.getInterface();
    }

    @Override
    public Properties getProperties() {
        throw new UnsupportedOperationException("No self implementation available");
    }

    public Variant<?> getVariant(final String propertyName) {
        return getInterface().getProperty(serviceName, propertyName);
    }

    public boolean getBoolean(final String propertyName) {
        Boolean value = (Boolean) getVariant(propertyName).getValue();

        return value.booleanValue();
    }

    public byte getByte(final String propertyName) {
        Byte value = (Byte) getVariant(propertyName).getValue();

        return value.byteValue();
    }

    public short getShort(final String propertyName) {
        Number value = (Number) getVariant(propertyName).getValue();

        return value.shortValue();
    }

    public int getInteger(final String propertyName) {
        Number value = (Number) getVariant(propertyName).getValue();

        return value.intValue();
    }

    public long getLong(final String propertyName) {
        Number value = (Number) getVariant(propertyName).getValue();

        return value.longValue();
    }

    public BigInteger getBigInteger(final String propertyName) {
        UInt64 value = (UInt64) getVariant(propertyName).getValue();

        return value.value();
    }

    public double getDouble(final String propertyName) {
        Number value = (Number) getVariant(propertyName).getValue();

        return value.doubleValue();
    }

    public String getString(final String propertyName) {
        return String.valueOf(getVariant(propertyName).getValue());
    }

    @SuppressWarnings("unchecked")
    public <T> Vector<T> getVector(final String propertyName) {
        return (Vector<T>) getVariant(propertyName).getValue();
    }

}
