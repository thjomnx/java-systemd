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

import java.util.Vector;

import de.thjom.java.systemd.InterfaceAdapter;

public interface DynamicUserAccounting extends Feature {

    static class Property extends InterfaceAdapter.AdapterProperty {

        public static final String CACHE_DIRECTORY = "CacheDirectory";
        public static final String CACHE_DIRECTORY_MODE = "CacheDirectoryMode";
        public static final String CONFIGURATION_DIRECTORY = "ConfigurationDirectory";
        public static final String CONFIGURATION_DIRECTORY_MODE = "ConfigurationDirectoryMode";
        public static final String DYNAMIC_USER = "DynamicUser";
        public static final String GID = "GID";
        public static final String GROUP = "Group";
        public static final String LOGS_DIRECTORY = "LogsDirectory";
        public static final String LOGS_DIRECTORY_MODE = "LogsDirectoryMode";
        public static final String PRIVATE_NETWORK = "PrivateNetwork";
        public static final String PRIVATE_TMP = "PrivateTmp";
        public static final String PRIVATE_USERS = "PrivateUsers";
        public static final String PROTECT_HOME = "ProtectHome";
        public static final String PROTECT_SYSTEM = "ProtectSystem";
        public static final String REMOVE_IPC = "RemoveIPC";
        public static final String RUNTIME_DIRECTORY = "RuntimeDirectory";
        public static final String RUNTIME_DIRECTORY_MODE = "RuntimeDirectoryMode";
        public static final String STATE_DIRECTORY = "StateDirectory";
        public static final String STATE_DIRECTORY_MODE = "StateDirectoryMode";
        public static final String UID = "UID";
        public static final String USER = "User";

        private Property() {
            super();
        }

        public static final String[] getAllNames() {
            return getAllNames(Property.class);
        }

    }

    default Vector<String> getCacheDirectory() {
        return getProperties().getVector(Property.CACHE_DIRECTORY);
    }

    default long getCacheDirectoryMode() {
        return getProperties().getLong(Property.CACHE_DIRECTORY_MODE);
    }

    default Vector<String> getConfigurationDirectory() {
        return getProperties().getVector(Property.CONFIGURATION_DIRECTORY);
    }

    default long getConfigurationDirectoryMode() {
        return getProperties().getLong(Property.CONFIGURATION_DIRECTORY_MODE);
    }

    default boolean isDynamicUser() {
        return getProperties().getBoolean(Property.DYNAMIC_USER);
    }

    default int getGID() {
        return getProperties().getInteger(Property.GID);
    }

    default String getGroup() {
        return getProperties().getString(Property.GROUP);
    }

    default Vector<String> getLogsDirectory() {
        return getProperties().getVector(Property.LOGS_DIRECTORY);
    }

    default long getLogsDirectoryMode() {
        return getProperties().getLong(Property.LOGS_DIRECTORY_MODE);
    }

    default boolean isPrivateNetwork() {
        return getProperties().getBoolean(Property.PRIVATE_NETWORK);
    }

    default boolean isPrivateTmp() {
        return getProperties().getBoolean(Property.PRIVATE_TMP);
    }

    default boolean isPrivateUsers() {
        return getProperties().getBoolean(Property.PRIVATE_USERS);
    }

    default String getProtectHome() {
        return getProperties().getString(Property.PROTECT_HOME);
    }

    default String getProtectSystem() {
        return getProperties().getString(Property.PROTECT_SYSTEM);
    }

    default boolean isRemoveIPC() {
        return getProperties().getBoolean(Property.REMOVE_IPC);
    }

    default Vector<String> getRuntimeDirectory() {
        return getProperties().getVector(Property.RUNTIME_DIRECTORY);
    }

    default long getRuntimeDirectoryMode() {
        return getProperties().getLong(Property.RUNTIME_DIRECTORY_MODE);
    }

    default Vector<String> getStateDirectory() {
        return getProperties().getVector(Property.STATE_DIRECTORY);
    }

    default long getStateDirectoryMode() {
        return getProperties().getLong(Property.STATE_DIRECTORY_MODE);
    }

    default int getUID() {
        return getProperties().getInteger(Property.UID);
    }

    default String getUser() {
        return getProperties().getString(Property.USER);
    }

}
