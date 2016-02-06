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

package de.mnx.java.systemd;

import java.math.BigInteger;
import java.util.Vector;

import org.freedesktop.dbus.DBusConnection;
import org.freedesktop.dbus.UInt64;
import org.freedesktop.dbus.Variant;
import org.freedesktop.dbus.exceptions.DBusException;

import de.mnx.java.systemd.interfaces.PropertyInterface;

public class Properties extends InterfaceAdapter {

    public static final String SERVICE_NAME = "org.freedesktop.DBus.Properties";

    private final PropertyInterface iface;
    private final String serviceName;

    Properties(final DBusConnection dbus, final String serviceName, final String objectPath) throws DBusException {
        super(dbus);

        this.iface = dbus.getRemoteObject(Systemd.SYSTEMD_DBUS_NAME, objectPath, PropertyInterface.class);
        this.serviceName = serviceName;
    }

    @Override
    public PropertyInterface getInterface() {
        return iface;
    }

    public Variant<?> getVariant(final String propertyName) {
        return getInterface().getProperty(this.serviceName, propertyName);
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
        return (String) getVariant(propertyName).getValue();
    }

    @SuppressWarnings("unchecked")
    public <T> Vector<T> getVector(final String propertyName) {
        return (Vector<T>) getVariant(propertyName).getValue();
    }

}
