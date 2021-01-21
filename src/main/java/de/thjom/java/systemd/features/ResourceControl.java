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

package de.thjom.java.systemd.features;

import java.util.List;

import de.thjom.java.systemd.InterfaceAdapter;

public interface ResourceControl extends Feature {

    static class Property extends InterfaceAdapter.AdapterProperty {

        public static final String DELEGATE = "Delegate";
        public static final String DELEGATE_CONTROLLERS = "DelegateControllers";
        public static final String DISABLE_CONTROLLERS = "DisableControllers";

        private Property() {
            super();
        }

        public static final String[] getAllNames() {
            return getAllNames(Property.class);
        }

    }

    default boolean isDelegate() {
        return getProperties().getBoolean(Property.DELEGATE);
    }

    default List<String> getDelegateControllers() {
        return getProperties().getList(Property.DELEGATE_CONTROLLERS);
    }

    default List<String> getDisableControllers() {
        return getProperties().getList(Property.DISABLE_CONTROLLERS);
    }

}
