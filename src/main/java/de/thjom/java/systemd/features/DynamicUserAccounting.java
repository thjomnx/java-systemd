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

import java.math.BigInteger;
import java.util.Vector;

import de.thjom.java.systemd.InterfaceAdapter;
import de.thjom.java.systemd.types.AddressFamilyRestriction;
import de.thjom.java.systemd.types.AppArmorProfile;
import de.thjom.java.systemd.types.SELinuxContext;
import de.thjom.java.systemd.types.SmackProcessLabel;

public interface DynamicUserAccounting extends Feature {

    static class Property extends InterfaceAdapter.AdapterProperty {

        public static final String AMBIENT_CAPABILITIES = "AmbientCapabilities";
        public static final String APP_ARMOR_PROFILE = "AppArmorProfile";
        public static final String CACHE_DIRECTORY = "CacheDirectory";
        public static final String CACHE_DIRECTORY_MODE = "CacheDirectoryMode";
        public static final String CONFIGURATION_DIRECTORY = "ConfigurationDirectory";
        public static final String CONFIGURATION_DIRECTORY_MODE = "ConfigurationDirectoryMode";
        public static final String CONTROL_GROUP = "ControlGroup";
        public static final String DYNAMIC_USER = "DynamicUser";
        public static final String GID = "GID";
        public static final String GROUP = "Group";
        public static final String KEYRING_MODE = "KeyringMode";
        public static final String LOCK_PERSONALITY = "LockPersonality";
        public static final String LOG_LEVEL_MAX = "LogLevelMax";
        public static final String LOGS_DIRECTORY = "LogsDirectory";
        public static final String LOGS_DIRECTORY_MODE = "LogsDirectoryMode";
        public static final String MOUNT_APIVFS = "MountAPIVFS";
        public static final String PASS_ENVIRONMENT = "PassEnvironment";
        public static final String PERSONALITY = "Personality";
        public static final String PRIVATE_DEVICES = "PrivateDevices";
        public static final String PRIVATE_NETWORK = "PrivateNetwork";
        public static final String PRIVATE_TMP = "PrivateTmp";
        public static final String PRIVATE_USERS = "PrivateUsers";
        public static final String PROTECT_CONTROL_GROUPS = "ProtectControlGroups";
        public static final String PROTECT_HOME = "ProtectHome";
        public static final String PROTECT_KERNEL_MODULES = "ProtectKernelModules";
        public static final String PROTECT_KERNEL_TUNABLES = "ProtectKernelTunables";
        public static final String PROTECT_SYSTEM = "ProtectSystem";
        public static final String REMOVE_IPC = "RemoveIPC";
        public static final String RESTRICT_ADDRESS_FAMILIES = "RestrictAddressFamilies";
        public static final String RESTRICT_NAMESPACES = "RestrictNamespaces";
        public static final String RESTRICT_REALTIME = "RestrictRealtime";
        public static final String ROOT_IMAGE = "RootImage";
        public static final String RUNTIME_DIRECTORY = "RuntimeDirectory";
        public static final String RUNTIME_DIRECTORY_MODE = "RuntimeDirectoryMode";
        public static final String RUNTIME_DIRECTORY_PRESERVE = "RuntimeDirectoryPreserve";
        public static final String SELINUX_CONTEXT = "SELinuxContext";
        public static final String SMACK_PROCESS_LABEL = "SmackProcessLabel";
        public static final String STANDARD_ERROR = "StandardError";
        public static final String STANDARD_ERROR_FILE_DESCRIPTOR_NAME = "StandardErrorFileDescriptorName";
        public static final String STANDARD_INPUT = "StandardInput";
        public static final String STANDARD_INPUT_DATA = "StandardInputData";
        public static final String STANDARD_INPUT_FILE_DESCRIPTOR_NAME = "StandardInputFileDescriptorName";
        public static final String STANDARD_OUTPUT = "StandardOutput";
        public static final String STANDARD_OUTPUT_FILE_DESCRIPTOR_NAME = "StandardOutputFileDescriptorName";
        public static final String STATE_DIRECTORY = "StateDirectory";
        public static final String STATE_DIRECTORY_MODE = "StateDirectoryMode";
        public static final String SYSLOG_FACILITY = "SyslogFacility";
        public static final String SYSLOG_LEVEL = "SyslogLevel";
        public static final String SYSTEM_CALL_ARCHITECTURES = "SystemCallArchitectures";
        public static final String SYSTEM_CALL_ERROR_NUMBER = "SystemCallErrorNumber";
        public static final String UID = "UID";
        public static final String USER = "User";
        public static final String UTMP_IDENTIFIER = "UtmpIdentifier";
        public static final String UTMP_MODE = "UtmpMode";

        private Property() {
            super();
        }

        public static final String[] getAllNames() {
            return getAllNames(Property.class);
        }

    }

    default BigInteger getAmbientCapabilities() {
        return getProperties().getBigInteger(Property.AMBIENT_CAPABILITIES);
    }

    default AppArmorProfile getAppArmorProfile() {
        Object[] array = (Object[]) getProperties().getVariant(Property.APP_ARMOR_PROFILE).getValue();

        return new AppArmorProfile(array);
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

    default String getControlGroup() {
        return getProperties().getString(Property.CONTROL_GROUP);
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

    default String getKeyringMode() {
        return getProperties().getString(Property.KEYRING_MODE);
    }

    default boolean isLockPersonality() {
        return getProperties().getBoolean(Property.LOCK_PERSONALITY);
    }

    default int getLogLevelMax() {
        return getProperties().getInteger(Property.LOG_LEVEL_MAX);
    }

    default Vector<String> getLogsDirectory() {
        return getProperties().getVector(Property.LOGS_DIRECTORY);
    }

    default long getLogsDirectoryMode() {
        return getProperties().getLong(Property.LOGS_DIRECTORY_MODE);
    }

    default boolean isMountAPIVFS() {
        return getProperties().getBoolean(Property.MOUNT_APIVFS);
    }

    default Vector<String> getPassEnvironment() {
        return getProperties().getVector(Property.PASS_ENVIRONMENT);
    }

    default String getPersonality() {
        return getProperties().getString(Property.PERSONALITY);
    }

    default boolean isPrivateDevices() {
        return getProperties().getBoolean(Property.PRIVATE_DEVICES);
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

    default boolean isProtectControlGroups() {
        return getProperties().getBoolean(Property.PROTECT_CONTROL_GROUPS);
    }

    default String getProtectHome() {
        return getProperties().getString(Property.PROTECT_HOME);
    }

    default boolean isProtectKernelModules() {
        return getProperties().getBoolean(Property.PROTECT_KERNEL_MODULES);
    }

    default boolean isProtectKernelTunables() {
        return getProperties().getBoolean(Property.PROTECT_KERNEL_TUNABLES);
    }

    default String getProtectSystem() {
        return getProperties().getString(Property.PROTECT_SYSTEM);
    }

    default boolean isRemoveIPC() {
        return getProperties().getBoolean(Property.REMOVE_IPC);
    }

    default AddressFamilyRestriction getRestrictAddressFamilies() {
        Object[] array = (Object[]) getProperties().getVariant(Property.RESTRICT_ADDRESS_FAMILIES).getValue();

        return new AddressFamilyRestriction(array);
    }

    default BigInteger getRestrictNamespaces() {
        return getProperties().getBigInteger(Property.RESTRICT_NAMESPACES);
    }

    default boolean isRestrictRealtime() {
        return getProperties().getBoolean(Property.RESTRICT_REALTIME);
    }

    default String getRootImage() {
        return getProperties().getString(Property.ROOT_IMAGE);
    }

    default Vector<String> getRuntimeDirectory() {
        return getProperties().getVector(Property.RUNTIME_DIRECTORY);
    }

    default long getRuntimeDirectoryMode() {
        return getProperties().getLong(Property.RUNTIME_DIRECTORY_MODE);
    }

    default String getRuntimeDirectoryPreserve() {
        return getProperties().getString(Property.RUNTIME_DIRECTORY_PRESERVE);
    }

    default SELinuxContext getSELinuxContext() {
        Object[] array = (Object[]) getProperties().getVariant(Property.SELINUX_CONTEXT).getValue();

        return new SELinuxContext(array);
    }

    default SmackProcessLabel getSmackProcessLabel() {
        Object[] array = (Object[]) getProperties().getVariant(Property.SMACK_PROCESS_LABEL).getValue();

        return new SmackProcessLabel(array);
    }

    default String getStandardError() {
        return getProperties().getString(Property.STANDARD_ERROR);
    }

    default String getStandardErrorFileDescriptorName() {
        return getProperties().getString(Property.STANDARD_ERROR_FILE_DESCRIPTOR_NAME);
    }

    default String getStandardInput() {
        return getProperties().getString(Property.STANDARD_INPUT);
    }

    default byte[] getStandardInputData() {
        return (byte[]) getProperties().getVariant(Property.STANDARD_INPUT_DATA).getValue();
    }

    default String getStandardInputFileDescriptorName() {
        return getProperties().getString(Property.STANDARD_INPUT_FILE_DESCRIPTOR_NAME);
    }

    default String getStandardOutput() {
        return getProperties().getString(Property.STANDARD_OUTPUT);
    }

    default String getStandardOutputFileDescriptorName() {
        return getProperties().getString(Property.STANDARD_OUTPUT_FILE_DESCRIPTOR_NAME);
    }

    default Vector<String> getStateDirectory() {
        return getProperties().getVector(Property.STATE_DIRECTORY);
    }

    default long getStateDirectoryMode() {
        return getProperties().getLong(Property.STATE_DIRECTORY_MODE);
    }

    default int getSyslogFacility() {
        return getProperties().getInteger(Property.SYSLOG_FACILITY);
    }

    default int getSyslogLevel() {
        return getProperties().getInteger(Property.SYSLOG_LEVEL);
    }

    default Vector<String> getSystemCallArchitectures() {
        return getProperties().getVector(Property.SYSTEM_CALL_ARCHITECTURES);
    }

    default int getSystemCallErrorNumber() {
        return getProperties().getInteger(Property.SYSTEM_CALL_ERROR_NUMBER);
    }

    default int getUID() {
        return getProperties().getInteger(Property.UID);
    }

    default String getUser() {
        return getProperties().getString(Property.USER);
    }

    default String getUtmpIdentifier() {
        return getProperties().getString(Property.UTMP_IDENTIFIER);
    }

    default String getUtmpMode() {
        return getProperties().getString(Property.UTMP_MODE);
    }

}
