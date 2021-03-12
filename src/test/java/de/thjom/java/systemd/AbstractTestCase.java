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

import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.math.BigInteger;
import java.util.Collection;
import java.util.List;

import org.freedesktop.dbus.connections.impl.DBusConnection;
import org.freedesktop.dbus.exceptions.DBusException;
import org.freedesktop.dbus.types.UInt64;
import org.freedesktop.dbus.types.Variant;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;
import org.testng.Assert;

import de.thjom.java.systemd.interfaces.ManagerInterface;
import de.thjom.java.systemd.interfaces.PropertyInterface;

abstract class AbstractTestCase {

    @Mock
    protected DBusConnection dbus;

    @Mock
    protected ManagerInterface miface;

    @Mock
    protected PropertyInterface piface;

    @InjectMocks
    protected Systemd systemd;

    protected void setup() {
        MockitoAnnotations.openMocks(this);

        try {
            Mockito.when(dbus.getRemoteObject(Systemd.SERVICE_NAME, Systemd.OBJECT_PATH, ManagerInterface.class)).thenReturn(miface);
            Mockito.when(dbus.getRemoteObject(Mockito.eq(Systemd.SERVICE_NAME), Mockito.anyString(), Mockito.eq(PropertyInterface.class))).thenReturn(piface);
        }
        catch (DBusException e) {
            Assert.fail(e.getMessage(), e);
        }
    }

    protected void setupPropertyMocks(final Class<?> iface, final String serviceName, final Collection<String> propertyNames) {
        try {
            for (String propertyName : propertyNames) {
                Method method = null;

                try {
                    method = iface.getMethod("get" + propertyName);
                }
                catch (NoSuchMethodException e1) {
                    try {
                        method = iface.getMethod("is" + propertyName);
                    }
                    catch (NoSuchMethodException e2) {
                        Assert.fail(e1.getMessage() + "/" + e2.getMessage());
                    }
                }

                if (method != null) {
                    Class<?> returnType = method.getReturnType();
                    Type genericReturnType = method.getGenericReturnType();

                    Mockito.when(piface.getProperty(serviceName, propertyName)).then(new Answer<Variant<?>>() {

                        @Override
                        public Variant<?> answer(final InvocationOnMock invocation) throws Throwable {
                            if (returnType == boolean.class) {
                                return new Variant<>(Boolean.TRUE);
                            }
                            else if (returnType == boolean[].class) {
                                return new Variant<>(new boolean[0]);
                            }
                            else if (returnType == byte.class) {
                                return new Variant<>(Byte.MAX_VALUE);
                            }
                            else if (returnType == byte[].class) {
                                return new Variant<>(new byte[0]);
                            }
                            else if (returnType == short.class) {
                                return new Variant<>(Short.MAX_VALUE);
                            }
                            else if (returnType == short[].class) {
                                return new Variant<>(new short[0]);
                            }
                            else if (returnType == int.class) {
                                return new Variant<>(Integer.MAX_VALUE);
                            }
                            else if (returnType == int[].class) {
                                return new Variant<>(new int[0]);
                            }
                            else if (returnType == long.class) {
                                return new Variant<>(Long.MAX_VALUE);
                            }
                            else if (returnType == long[].class) {
                                return new Variant<>(new long[0]);
                            }
                            else if (returnType == BigInteger.class) {
                                return new Variant<>(new UInt64(0));
                            }
                            else if (returnType == BigInteger[].class) {
                                return new Variant<>(new UInt64[0]);
                            }
                            else if (returnType == float.class) {
                                return new Variant<>(Float.MAX_VALUE);
                            }
                            else if (returnType == float[].class) {
                                return new Variant<>(new float[0]);
                            }
                            else if (returnType == double.class) {
                                return new Variant<>(Double.MAX_VALUE);
                            }
                            else if (returnType == double[].class) {
                                return new Variant<>(new double[0]);
                            }
                            else if (returnType == String.class) {
                                return new Variant<>(propertyName);
                            }
                            else if (returnType == String[].class) {
                                return new Variant<>(new String[0]);
                            }
                            else if (returnType == List.class) {
                                ParameterizedType paramType = (ParameterizedType) genericReturnType;
                                String typeName = paramType.getActualTypeArguments()[0].getTypeName();

                                if (typeName.equals(String.class.getName())) {
                                    return new Variant<>(new String[] { "foo", "bar" }, "as");
                                }
                                else {
                                    return new Variant<>(null);
                                }
                            }
                            else {
                                try {
                                    return new Variant<>(Mockito.mock(returnType));
                                }
                                catch (IllegalArgumentException e) {
                                    ParameterizedType paramType = (ParameterizedType) genericReturnType;
                                    String typeName = paramType.getActualTypeArguments()[0].getTypeName();

                                    System.err.format("Unit=%s, Property=%s, Property type=%s<%s>%s", serviceName, propertyName, returnType.getName(), typeName, System.lineSeparator());

                                    throw e;
                                }
                            }
                        }

                    });
                }
            }
        }
        catch (SecurityException e) {
            Assert.fail(e.getMessage(), e);
        }
    }

}
