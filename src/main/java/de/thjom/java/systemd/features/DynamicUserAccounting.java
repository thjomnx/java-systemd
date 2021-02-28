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
import java.util.List;

import de.thjom.java.systemd.InterfaceAdapter;
import de.thjom.java.systemd.types.AddressFamilyRestriction;
import de.thjom.java.systemd.types.AppArmorProfile;
import de.thjom.java.systemd.types.BindPath;
import de.thjom.java.systemd.types.FileSystemInfo;
import de.thjom.java.systemd.types.ImageOptions;
import de.thjom.java.systemd.types.LoadCredential;
import de.thjom.java.systemd.types.MountImage;
import de.thjom.java.systemd.types.SELinuxContext;
import de.thjom.java.systemd.types.SetCredential;
import de.thjom.java.systemd.types.SmackProcessLabel;
import de.thjom.java.systemd.types.SystemCallLog;

public interface DynamicUserAccounting extends Feature {

    static class Property extends InterfaceAdapter.AdapterProperty {

        public static final String AMBIENT_CAPABILITIES = "AmbientCapabilities";
        public static final String APP_ARMOR_PROFILE = "AppArmorProfile";
        public static final String BIND_PATHS = "BindPaths";
        public static final String BIND_READ_ONLY_PATHS = "BindReadOnlyPaths";
        public static final String CACHE_DIRECTORY = "CacheDirectory";
        public static final String CACHE_DIRECTORY_MODE = "CacheDirectoryMode";
        public static final String CONFIGURATION_DIRECTORY = "ConfigurationDirectory";
        public static final String CONFIGURATION_DIRECTORY_MODE = "ConfigurationDirectoryMode";
        public static final String CONTROL_GROUP = "ControlGroup";
        public static final String COREDUMP_FILTER = "CoredumpFilter";
        public static final String DYNAMIC_USER = "DynamicUser";
        public static final String GID = "GID";
        public static final String GROUP = "Group";
        public static final String KEYRING_MODE = "KeyringMode";
        public static final String LOAD_CREDENTIAL = "LoadCredential";
        public static final String LOCK_PERSONALITY = "LockPersonality";
        public static final String LOG_EXTRA_FIELDS = "LogExtraFields";
        public static final String LOG_LEVEL_MAX = "LogLevelMax";
        public static final String LOG_NAMESPACE = "LogNamespace";
        public static final String LOG_RATE_LIMIT_BURST = "LogRateLimitBurst";
        public static final String LOG_RATE_LIMIT_INTERVAL_USEC = "LogRateLimitIntervalUSec";
        public static final String LOGS_DIRECTORY = "LogsDirectory";
        public static final String LOGS_DIRECTORY_MODE = "LogsDirectoryMode";
        public static final String MOUNT_APIVFS = "MountAPIVFS";
        public static final String MOUNT_IMAGES = "MountImages";
        public static final String NETWORK_NAMESPACE_PATH = "NetworkNamespacePath";
        public static final String PASS_ENVIRONMENT = "PassEnvironment";
        public static final String PERSONALITY = "Personality";
        public static final String PRIVATE_DEVICES = "PrivateDevices";
        public static final String PRIVATE_MOUNTS = "PrivateMounts";
        public static final String PRIVATE_NETWORK = "PrivateNetwork";
        public static final String PRIVATE_TMP = "PrivateTmp";
        public static final String PRIVATE_USERS = "PrivateUsers";
        public static final String PROC_SUBSET = "ProcSubset";
        public static final String PROTECT_CLOCK = "ProtectClock";
        public static final String PROTECT_CONTROL_GROUPS = "ProtectControlGroups";
        public static final String PROTECT_HOME = "ProtectHome";
        public static final String PROTECT_HOSTNAME = "ProtectHostname";
        public static final String PROTECT_KERNEL_LOGS = "ProtectKernelLogs";
        public static final String PROTECT_KERNEL_MODULES = "ProtectKernelModules";
        public static final String PROTECT_KERNEL_TUNABLES = "ProtectKernelTunables";
        public static final String PROTECT_PROC = "ProtectProc";
        public static final String PROTECT_SYSTEM = "ProtectSystem";
        public static final String REMOVE_IPC = "RemoveIPC";
        public static final String RESTRICT_ADDRESS_FAMILIES = "RestrictAddressFamilies";
        public static final String RESTRICT_NAMESPACES = "RestrictNamespaces";
        public static final String RESTRICT_REALTIME = "RestrictRealtime";
        public static final String RESTRICT_SUID_SGID = "RestrictSUIDSGID";
        public static final String ROOT_HASH = "RootHash";
        public static final String ROOT_HASH_PATH = "RootHashPath";
        public static final String ROOT_HASH_SIGNATURE = "RootHashSignature";
        public static final String ROOT_HASH_SIGNATURE_PATH = "RootHashSignaturePath";
        public static final String ROOT_IMAGE = "RootImage";
        public static final String ROOT_IMAGE_OPTIONS = "RootImageOptions";
        public static final String ROOT_VERITY = "RootVerity";
        public static final String RUNTIME_DIRECTORY = "RuntimeDirectory";
        public static final String RUNTIME_DIRECTORY_MODE = "RuntimeDirectoryMode";
        public static final String RUNTIME_DIRECTORY_PRESERVE = "RuntimeDirectoryPreserve";
        public static final String SELINUX_CONTEXT = "SELinuxContext";
        public static final String SET_CREDENTIAL = "SetCredential";
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
        public static final String SYSTEM_CALL_LOG = "SystemCallLog";
        public static final String TEMPORARY_FILE_SYSTEM = "TemporaryFileSystem";
        public static final String TIMEOUT_CLEAN_USEC = "TimeoutCleanUSec";
        public static final String UID = "UID";
        public static final String UNSET_ENVIRONMENT = "UnsetEnvironment";
        public static final String USER = "User";
        public static final String UTMP_IDENTIFIER = "UtmpIdentifier";
        public static final String UTMP_MODE = "UtmpMode";

        private Property() {
            super();
        }

        public static final List<String> getAllNames() {
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

    default List<BindPath> getBindPaths() {
        return BindPath.list(getProperties().getList(Property.BIND_PATHS));
    }

    default List<BindPath> getBindReadOnlyPaths() {
        return BindPath.list(getProperties().getList(Property.BIND_READ_ONLY_PATHS));
    }

    default List<String> getCacheDirectory() {
        return getProperties().getList(Property.CACHE_DIRECTORY);
    }

    default long getCacheDirectoryMode() {
        return getProperties().getLong(Property.CACHE_DIRECTORY_MODE);
    }

    default List<String> getConfigurationDirectory() {
        return getProperties().getList(Property.CONFIGURATION_DIRECTORY);
    }

    default long getConfigurationDirectoryMode() {
        return getProperties().getLong(Property.CONFIGURATION_DIRECTORY_MODE);
    }

    default String getControlGroup() {
        return getProperties().getString(Property.CONTROL_GROUP);
    }

    default BigInteger getCoredumpFilter() {
        return getProperties().getBigInteger(Property.COREDUMP_FILTER);
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

    default List<LoadCredential> getLoadCredential() {
        return LoadCredential.list(getProperties().getList(Property.LOAD_CREDENTIAL));
    }

    default boolean isLockPersonality() {
        return getProperties().getBoolean(Property.LOCK_PERSONALITY);
    }

    default List<byte[]> getLogExtraFields() {
        return getProperties().getList(Property.LOG_EXTRA_FIELDS);
    }

    default int getLogLevelMax() {
        return getProperties().getInteger(Property.LOG_LEVEL_MAX);
    }

    default String getLogNamespace() {
        return getProperties().getString(Property.LOG_NAMESPACE);
    }

    default long getLogRateLimitBurst() {
        return getProperties().getLong(Property.LOG_RATE_LIMIT_BURST);
    }

    default BigInteger getLogRateLimitIntervalUSec() {
        return getProperties().getBigInteger(Property.LOG_RATE_LIMIT_INTERVAL_USEC);
    }

    default List<String> getLogsDirectory() {
        return getProperties().getList(Property.LOGS_DIRECTORY);
    }

    default long getLogsDirectoryMode() {
        return getProperties().getLong(Property.LOGS_DIRECTORY_MODE);
    }

    default boolean isMountAPIVFS() {
        return getProperties().getBoolean(Property.MOUNT_APIVFS);
    }

    default List<MountImage> getMountImages() {
        return MountImage.list(getProperties().getList(Property.MOUNT_IMAGES));
    }

    default String getNetworkNamespacePath() {
        return getProperties().getString(Property.NETWORK_NAMESPACE_PATH);
    }

    default List<String> getPassEnvironment() {
        return getProperties().getList(Property.PASS_ENVIRONMENT);
    }

    default String getPersonality() {
        return getProperties().getString(Property.PERSONALITY);
    }

    default boolean isPrivateDevices() {
        return getProperties().getBoolean(Property.PRIVATE_DEVICES);
    }

    default boolean isPrivateMounts() {
        return getProperties().getBoolean(Property.PRIVATE_MOUNTS);
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

    default String getProcSubset() {
        return getProperties().getString(Property.PROC_SUBSET);
    }

    default boolean isProtectClock() {
        return getProperties().getBoolean(Property.PROTECT_CLOCK);
    }

    default boolean isProtectControlGroups() {
        return getProperties().getBoolean(Property.PROTECT_CONTROL_GROUPS);
    }

    default String getProtectHome() {
        return getProperties().getString(Property.PROTECT_HOME);
    }

    default boolean isProtectHostname() {
        return getProperties().getBoolean(Property.PROTECT_HOSTNAME);
    }

    default boolean isProtectKernelLogs() {
        return getProperties().getBoolean(Property.PROTECT_KERNEL_LOGS);
    }

    default boolean isProtectKernelModules() {
        return getProperties().getBoolean(Property.PROTECT_KERNEL_MODULES);
    }

    default boolean isProtectKernelTunables() {
        return getProperties().getBoolean(Property.PROTECT_KERNEL_TUNABLES);
    }

    default String getProtectProc() {
        return getProperties().getString(Property.PROTECT_PROC);
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

    default boolean isRestrictSUIDSGID() {
        return getProperties().getBoolean(Property.RESTRICT_SUID_SGID);
    }

    default byte[] getRootHash() {
        return (byte[]) getProperties().getVariant(Property.ROOT_HASH).getValue();
    }

    default String getRootHashPath() {
        return getProperties().getString(Property.ROOT_HASH_PATH);
    }

    default byte[] getRootHashSignature() {
        return (byte[]) getProperties().getVariant(Property.ROOT_HASH_SIGNATURE).getValue();
    }

    default String getRootHashSignaturePath() {
        return getProperties().getString(Property.ROOT_HASH_SIGNATURE_PATH);
    }

    default String getRootImage() {
        return getProperties().getString(Property.ROOT_IMAGE);
    }

    default List<ImageOptions> getRootImageOptions() {
        return ImageOptions.list(getProperties().getList(Property.ROOT_IMAGE_OPTIONS));
    }

    default String getRootVerity() {
        return getProperties().getString(Property.ROOT_VERITY);
    }

    default List<String> getRuntimeDirectory() {
        return getProperties().getList(Property.RUNTIME_DIRECTORY);
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

    default List<SetCredential> getSetCredential() {
        return SetCredential.list(getProperties().getList(Property.SET_CREDENTIAL));
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

    default List<String> getStateDirectory() {
        return getProperties().getList(Property.STATE_DIRECTORY);
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

    default List<String> getSystemCallArchitectures() {
        return getProperties().getList(Property.SYSTEM_CALL_ARCHITECTURES);
    }

    default int getSystemCallErrorNumber() {
        return getProperties().getInteger(Property.SYSTEM_CALL_ERROR_NUMBER);
    }

    default SystemCallLog getSystemCallLog() {
        Object[] array = (Object[]) getProperties().getVariant(Property.SYSTEM_CALL_LOG).getValue();

        return new SystemCallLog(array);
    }

    default List<FileSystemInfo> getTemporaryFileSystem() {
        return FileSystemInfo.list(getProperties().getList(Property.TEMPORARY_FILE_SYSTEM));
    }

    default BigInteger getTimeoutCleanUSec() {
        return getProperties().getBigInteger(Property.TIMEOUT_CLEAN_USEC);
    }

    default int getUID() {
        return getProperties().getInteger(Property.UID);
    }

    default List<String> getUnsetEnvironment() {
        return getProperties().getList(Property.UNSET_ENVIRONMENT);
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
