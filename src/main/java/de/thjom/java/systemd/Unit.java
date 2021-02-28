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

import static de.thjom.java.systemd.Unit.Property.ACTIVE_STATE;
import static de.thjom.java.systemd.Unit.Property.LOAD_STATE;
import static de.thjom.java.systemd.Unit.Property.SUB_STATE;

import java.math.BigInteger;
import java.util.List;
import java.util.Map;

import org.freedesktop.dbus.DBusPath;
import org.freedesktop.dbus.exceptions.DBusException;
import org.freedesktop.dbus.interfaces.DBusSigHandler;
import org.freedesktop.dbus.interfaces.Introspectable;
import org.freedesktop.dbus.interfaces.Properties.PropertiesChanged;
import org.freedesktop.dbus.messages.DBusSignal;
import org.freedesktop.dbus.types.Variant;

import de.thjom.java.systemd.interfaces.PropertyInterface;
import de.thjom.java.systemd.interfaces.UnitInterface;
import de.thjom.java.systemd.types.Condition;
import de.thjom.java.systemd.types.Job;
import de.thjom.java.systemd.types.LoadError;

public abstract class Unit extends InterfaceAdapter implements UnitStateNotifier {

    public static final String SERVICE_NAME = Systemd.SERVICE_NAME + ".Unit";
    public static final String OBJECT_PATH = Systemd.OBJECT_PATH + "/unit/";

    public enum Who {
        MAIN("main"),
        CONTROL("control"),
        ALL("all");

        private final String value;

        private Who(final String value) {
            this.value = value;
        }

        public final String getValue() {
            return value;
        }

        @Override
        public String toString() {
            return value;
        }

    }

    public enum Mode {
        REPLACE("replace"),
        FAIL("fail"),
        ISOLATE("isolate"),
        IGNORE_DEPENDENCIES("ignore-dependencies"),
        IGNORE_REQUIREMENTS("ignore-requirements");

        private final String value;

        private Mode(final String value) {
            this.value = value;
        }

        public final String getValue() {
            return value;
        }

        @Override
        public String toString() {
            return value;
        }

    }

    public static class Property extends InterfaceAdapter.AdapterProperty {

        public static final String ACTIVE_ENTER_TIMESTAMP = "ActiveEnterTimestamp";
        public static final String ACTIVE_ENTER_TIMESTAMP_MONOTONIC = "ActiveEnterTimestampMonotonic";
        public static final String ACTIVE_EXIT_TIMESTAMP = "ActiveExitTimestamp";
        public static final String ACTIVE_EXIT_TIMESTAMP_MONOTONIC = "ActiveExitTimestampMonotonic";
        public static final String ACTIVE_STATE = "ActiveState";
        public static final String AFTER = "After";
        public static final String ALLOW_ISOLATE = "AllowIsolate";
        public static final String ASSERT_RESULT = "AssertResult";
        public static final String ASSERT_TIMESTAMP = "AssertTimestamp";
        public static final String ASSERT_TIMESTAMP_MONOTONIC = "AssertTimestampMonotonic";
        public static final String ASSERTS = "Asserts";
        public static final String BEFORE = "Before";
        public static final String BINDS_TO = "BindsTo";
        public static final String BOUND_BY = "BoundBy";
        public static final String CAN_CLEAN = "CanClean";
        public static final String CAN_FREEZE = "CanFreeze";
        public static final String CAN_ISOLATE = "CanIsolate";
        public static final String CAN_RELOAD = "CanReload";
        public static final String CAN_START = "CanStart";
        public static final String CAN_STOP = "CanStop";
        public static final String COLLECT_MODE = "CollectMode";
        public static final String CONDITION_RESULT = "ConditionResult";
        public static final String CONDITION_TIMESTAMP = "ConditionTimestamp";
        public static final String CONDITION_TIMESTAMP_MONOTONIC = "ConditionTimestampMonotonic";
        public static final String CONDITIONS = "Conditions";
        public static final String CONFLICTED_BY = "ConflictedBy";
        public static final String CONFLICTS = "Conflicts";
        public static final String CONSISTS_OF = "ConsistsOf";
        public static final String DEFAULT_DEPENDENCIES = "DefaultDependencies";
        public static final String DESCRIPTION = "Description";
        public static final String DOCUMENTATION = "Documentation";
        public static final String DROP_IN_PATHS = "DropInPaths";
        public static final String FAILURE_ACTION = "FailureAction";
        public static final String FAILURE_ACTION_EXIT_STATUS = "FailureActionExitStatus";
        public static final String FOLLOWING = "Following";
        public static final String FRAGMENT_PATH = "FragmentPath";
        public static final String FREEZER_STATE = "FreezerState";
        public static final String ID = "Id";
        public static final String IGNORE_ON_ISOLATE = "IgnoreOnIsolate";
        public static final String INACTIVE_ENTER_TIMESTAMP = "InactiveEnterTimestamp";
        public static final String INACTIVE_ENTER_TIMESTAMP_MONOTONIC = "InactiveEnterTimestampMonotonic";
        public static final String INACTIVE_EXIT_TIMESTAMP = "InactiveExitTimestamp";
        public static final String INACTIVE_EXIT_TIMESTAMP_MONOTONIC = "InactiveExitTimestampMonotonic";
        public static final String INVOCATION_ID = "InvocationID";
        public static final String JOB = "Job";
        public static final String JOB_RUNNING_TIMEOUT_USEC = "JobRunningTimeoutUSec";
        public static final String JOB_TIMEOUT_ACTION = "JobTimeoutAction";
        public static final String JOB_TIMEOUT_REBOOT_ARGUMENT = "JobTimeoutRebootArgument";
        public static final String JOB_TIMEOUT_USEC = "JobTimeoutUSec";
        public static final String JOINS_NAMESPACE_OF = "JoinsNamespaceOf";
        public static final String LOAD_ERROR = "LoadError";
        public static final String LOAD_STATE = "LoadState";
        public static final String NAMES = "Names";
        public static final String NEED_DAEMON_RELOAD = "NeedDaemonReload";
        public static final String ON_FAILURE = "OnFailure";
        public static final String ON_FAILURE_JOB_MODE = "OnFailureJobMode";
        public static final String PART_OF = "PartOf";
        public static final String PERPETUAL = "Perpetual";
        public static final String PROPAGATES_RELOAD_TO = "PropagatesReloadTo";
        public static final String REBOOT_ARGUMENT = "RebootArgument";
        public static final String REFS = "Refs";
        public static final String REFUSE_MANUAL_START = "RefuseManualStart";
        public static final String REFUSE_MANUAL_STOP = "RefuseManualStop";
        public static final String RELOAD_PROPAGATED_FROM = "ReloadPropagatedFrom";
        public static final String REQUIRED_BY = "RequiredBy";
        public static final String REQUIRES = "Requires";
        public static final String REQUIRES_MOUNTS_FOR = "RequiresMountsFor";
        public static final String REQUISITE = "Requisite";
        public static final String REQUISITE_OF = "RequisiteOf";
        public static final String SOURCE_PATH = "SourcePath";
        public static final String START_LIMIT_ACTION = "StartLimitAction";
        public static final String START_LIMIT_BURST = "StartLimitBurst";
        public static final String START_LIMIT_INTERVAL_USEC = "StartLimitIntervalUSec";
        public static final String STATE_CHANGE_TIMESTAMP = "StateChangeTimestamp";
        public static final String STATE_CHANGE_TIMESTAMP_MONOTONIC = "StateChangeTimestampMonotonic";
        public static final String STOP_WHEN_UNNEEDED = "StopWhenUnneeded";
        public static final String SUB_STATE = "SubState";
        public static final String SUCCESS_ACTION = "SuccessAction";
        public static final String SUCCESS_ACTION_EXIT_STATUS = "SuccessActionExitStatus";
        public static final String TRANSIENT = "Transient";
        public static final String TRIGGERED_BY = "TriggeredBy";
        public static final String TRIGGERS = "Triggers";
        public static final String UNIT_FILE_PRESET = "UnitFilePreset";
        public static final String UNIT_FILE_STATE = "UnitFileState";
        public static final String WANTED_BY = "WantedBy";
        public static final String WANTS = "Wants";

        private Property() {
            super();
        }

        public static final List<String> getAllNames() {
            return getAllNames(Property.class);
        }

    }

    protected final String name;
    protected final Manager manager;

    private final Properties unitProperties;

    protected Unit(final Manager manager, final UnitInterface iface, final String name) throws DBusException {
        super(manager.dbus, iface);

        this.name = name;
        this.manager = manager;

        this.unitProperties = Properties.create(dbus, iface.getObjectPath(), SERVICE_NAME);
    }

    public static String normalizeName(final String name, final String suffix) {
        String normalized;

        if (name != null) {
            if (suffix != null) {
                normalized = name.endsWith(suffix) ? name : name + suffix;
            }
            else {
                normalized = name;
            }
        }
        else {
            normalized = "";
        }

        return normalized;
    }

    public static String extractName(final String objectPath) {
        String name;

        if (objectPath != null && objectPath.startsWith(Unit.OBJECT_PATH)) {
            name = objectPath.substring(Unit.OBJECT_PATH.length());
        }
        else {
            name = "";
        }

        return name;
    }

    @Override
    public UnitInterface getInterface() {
        return (UnitInterface) super.getInterface();
    }

    /**
     * Returns the {@link PropertyInterface} adapter of the {@link UnitInterface} of
     * this interface adapter.<p>
     *
     * @return The property interface adapter.
     */
    public final Properties getUnitProperties() {
        return unitProperties;
    }

    public boolean isAssignableFrom(final String objectPath) {
        return extractName(objectPath).equals(Systemd.escapePath(name));
    }

    @Override
    public <T extends DBusSignal> void addHandler(final Class<T> type, final DBusSigHandler<T> handler) throws DBusException {
        manager.subscribe();
        dbus.addSigHandler(type, getInterface(), handler);
    }

    @Override
    public <T extends DBusSignal> void removeHandler(final Class<T> type, final DBusSigHandler<T> handler) throws DBusException {
        if (handler != null) {
            dbus.removeSigHandler(type, getInterface(), handler);
        }
    }

    @Override
    protected DBusSigHandler<PropertiesChanged> createStateHandler() {
        return s -> {
            Map<String, Variant<?>> properties = s.getPropertiesChanged();

            if (properties.containsKey(ACTIVE_STATE) || properties.containsKey(LOAD_STATE) || properties.containsKey(SUB_STATE)) {
                synchronized (unitStateListeners) {
                    unitStateListeners.forEach(l -> l.stateChanged(Unit.this, properties));
                }
            }
        };
    }

    public String introspect() throws DBusException {
        Introspectable intro = dbus.getRemoteObject(Systemd.SERVICE_NAME, getInterface().getObjectPath(), Introspectable.class);

        return intro.Introspect();
    }

    public void clean(final List<String> mask) {
        manager.cleanUnit(name, mask);
    }

    public void freeze() {
        manager.freezeUnit(name);
    }

    public void kill(final Who who, final int signal) {
        kill(who.getValue(), signal);
    }

    public void kill(final String who, final int signal) {
        manager.killUnit(name, who, signal);
    }

    public void ref() {
        manager.refUnit(name);
    }

    public DBusPath reload(final Mode mode) {
        return reload(mode.getValue());
    }

    public DBusPath reload(final String mode) {
        return manager.reloadUnit(name, mode);
    }

    public DBusPath reloadOrRestart(final Mode mode) {
        return reloadOrRestart(mode.getValue());
    }

    public DBusPath reloadOrRestart(final String mode) {
        return manager.reloadOrRestartUnit(name, mode);
    }

    public DBusPath reloadOrTryRestart(final Mode mode) {
        return reloadOrTryRestart(mode.getValue());
    }

    public DBusPath reloadOrTryRestart(final String mode) {
        return manager.reloadOrTryRestartUnit(name, mode);
    }

    public void resetFailed() {
        manager.resetFailedUnit(name);
    }

    public DBusPath restart(final Mode mode) {
        return restart(mode.getValue());
    }

    public DBusPath restart(final String mode) {
        return manager.restartUnit(name, mode);
    }

    public DBusPath start(final Mode mode) {
        return start(mode.getValue());
    }

    public DBusPath start(final String mode) {
        return manager.startUnit(name, mode);
    }

    public DBusPath stop(final Mode mode) {
        return stop(mode.getValue());
    }

    public DBusPath stop(final String mode) {
        return manager.stopUnit(name, mode);
    }

    public void thaw() {
        manager.thawUnit(name);
    }

    public DBusPath tryRestart(final Mode mode) {
        return tryRestart(mode.getValue());
    }

    public DBusPath tryRestart(final String mode) {
        return manager.tryRestartUnit(name, mode);
    }

    public void unref() {
        manager.unrefUnit(name);
    }

    public void setProperties(final boolean runtime, final Map<String, Object> properties) {
        throw new UnsupportedOperationException();
    }

    public long getActiveEnterTimestamp() {
        return unitProperties.getLong(Property.ACTIVE_ENTER_TIMESTAMP);
    }

    public long getActiveEnterTimestampMonotonic() {
        return unitProperties.getLong(Property.ACTIVE_ENTER_TIMESTAMP_MONOTONIC);
    }

    public long getActiveExitTimestamp() {
        return unitProperties.getLong(Property.ACTIVE_EXIT_TIMESTAMP);
    }

    public long getActiveExitTimestampMonotonic() {
        return unitProperties.getLong(Property.ACTIVE_EXIT_TIMESTAMP_MONOTONIC);
    }

    public String getActiveState() {
        return unitProperties.getString(Property.ACTIVE_STATE);
    }

    public List<String> getAfter() {
        return unitProperties.getList(Property.AFTER);
    }

    public boolean isAllowIsolate() {
        return unitProperties.getBoolean(Property.ALLOW_ISOLATE);
    }

    public boolean isAssertResult() {
        return unitProperties.getBoolean(Property.ASSERT_RESULT);
    }

    public long getAssertTimestamp() {
        return unitProperties.getLong(Property.ASSERT_TIMESTAMP);
    }

    public long getAssertTimestampMonotonic() {
        return unitProperties.getLong(Property.ASSERT_TIMESTAMP_MONOTONIC);
    }

    public List<Condition> getAsserts() {
        return Condition.list(unitProperties.getList(Property.ASSERTS));
    }

    public List<String> getBefore() {
        return unitProperties.getList(Property.BEFORE);
    }

    public List<String> getBindsTo() {
        return unitProperties.getList(Property.BINDS_TO);
    }

    public List<String> getBoundBy() {
        return unitProperties.getList(Property.BOUND_BY);
    }

    public List<String> isCanClean() {
        return unitProperties.getList(Property.CAN_CLEAN);
    }

    public boolean isCanFreeze() {
        return unitProperties.getBoolean(Property.CAN_FREEZE);
    }

    public boolean isCanIsolate() {
        return unitProperties.getBoolean(Property.CAN_ISOLATE);
    }

    public boolean isCanReload() {
        return unitProperties.getBoolean(Property.CAN_RELOAD);
    }

    public boolean isCanStart() {
        return unitProperties.getBoolean(Property.CAN_START);
    }

    public boolean isCanStop() {
        return unitProperties.getBoolean(Property.CAN_STOP);
    }

    public String getCollectMode() {
        return unitProperties.getString(Property.COLLECT_MODE);
    }

    public boolean getConditionResult() {
        return unitProperties.getBoolean(Property.CONDITION_RESULT);
    }

    public long getConditionTimestamp() {
        return unitProperties.getLong(Property.CONDITION_TIMESTAMP);
    }

    public long getConditionTimestampMonotonic() {
        return unitProperties.getLong(Property.CONDITION_TIMESTAMP_MONOTONIC);
    }

    public List<Condition> getConditions() {
        return Condition.list(unitProperties.getList(Property.CONDITIONS));
    }

    public List<String> getConflictedBy() {
        return unitProperties.getList(Property.CONFLICTED_BY);
    }

    public List<String> getConflicts() {
        return unitProperties.getList(Property.CONFLICTS);
    }

    public List<String> getConsistsOf() {
        return unitProperties.getList(Property.CONSISTS_OF);
    }

    public boolean isDefaultDependencies() {
        return unitProperties.getBoolean(Property.DEFAULT_DEPENDENCIES);
    }

    public String getDescription() {
        return unitProperties.getString(Property.DESCRIPTION);
    }

    public List<String> getDocumentation() {
        return unitProperties.getList(Property.DOCUMENTATION);
    }

    public List<String> getDropInPaths() {
        return unitProperties.getList(Property.DROP_IN_PATHS);
    }

    public String getFailureAction() {
        return unitProperties.getString(Property.FAILURE_ACTION);
    }

    public int getFailureActionExitStatus() {
        return unitProperties.getInteger(Property.FAILURE_ACTION_EXIT_STATUS);
    }

    public String getFollowing() {
        return unitProperties.getString(Property.FOLLOWING);
    }

    public String getFragmentPath() {
        return unitProperties.getString(Property.FRAGMENT_PATH);
    }

    public String getFreezerState() {
        return unitProperties.getString(Property.FREEZER_STATE);
    }

    public String getId() {
        return unitProperties.getString(Property.ID);
    }

    public boolean isIgnoreOnIsolate() {
        return unitProperties.getBoolean(Property.IGNORE_ON_ISOLATE);
    }

    public long getInactiveEnterTimestamp() {
        return unitProperties.getLong(Property.INACTIVE_ENTER_TIMESTAMP);
    }

    public long getInactiveEnterTimestampMonotonic() {
        return unitProperties.getLong(Property.INACTIVE_ENTER_TIMESTAMP_MONOTONIC);
    }

    public long getInactiveExitTimestamp() {
        return unitProperties.getLong(Property.INACTIVE_EXIT_TIMESTAMP);
    }

    public long getInactiveExitTimestampMonotonic() {
        return unitProperties.getLong(Property.INACTIVE_EXIT_TIMESTAMP_MONOTONIC);
    }

    public byte[] getInvocationID() {
        return (byte[]) unitProperties.getVariant(Property.INVOCATION_ID).getValue();
    }

    public Job getJob() {
        Object[] array = (Object[]) unitProperties.getVariant(Property.JOB).getValue();

        return new Job(array);
    }

    public BigInteger getJobRunningTimeoutUSec() {
        return unitProperties.getBigInteger(Property.JOB_RUNNING_TIMEOUT_USEC);
    }

    public String getJobTimeoutAction() {
        return unitProperties.getString(Property.JOB_TIMEOUT_ACTION);
    }

    public String getJobTimeoutRebootArgument() {
        return unitProperties.getString(Property.JOB_TIMEOUT_REBOOT_ARGUMENT);
    }

    public BigInteger getJobTimeoutUSec() {
        return unitProperties.getBigInteger(Property.JOB_TIMEOUT_USEC);
    }

    public List<String> getJoinsNamespaceOf() {
        return unitProperties.getList(Property.JOINS_NAMESPACE_OF);
    }

    public LoadError getLoadError() {
    	Object[] array = (Object[]) unitProperties.getVariant(Property.LOAD_ERROR).getValue();

    	return new LoadError(array);
    }

    public String getLoadState() {
        return unitProperties.getString(Property.LOAD_STATE);
    }

    public List<String> getNames() {
        return unitProperties.getList(Property.NAMES);
    }

    public boolean isNeedDaemonReload() {
        return unitProperties.getBoolean(Property.NEED_DAEMON_RELOAD);
    }

    public List<String> getOnFailure() {
        return unitProperties.getList(Property.ON_FAILURE);
    }

    public String getOnFailureJobMode() {
        return unitProperties.getString(Property.ON_FAILURE_JOB_MODE);
    }

    public List<String> getPartOf() {
        return unitProperties.getList(Property.PART_OF);
    }

    public boolean isPerpetual() {
        return unitProperties.getBoolean(Property.PERPETUAL);
    }

    public List<String> getPropagatesReloadTo() {
        return unitProperties.getList(Property.PROPAGATES_RELOAD_TO);
    }

    public String getRebootArgument() {
        return unitProperties.getString(Property.REBOOT_ARGUMENT);
    }

    public List<String> getRefs() {
        return unitProperties.getList(Property.REFS);
    }

    public boolean isRefuseManualStart() {
        return unitProperties.getBoolean(Property.REFUSE_MANUAL_START);
    }

    public boolean isRefuseManualStop() {
        return unitProperties.getBoolean(Property.REFUSE_MANUAL_STOP);
    }

    public List<String> getReloadPropagatedFrom() {
        return unitProperties.getList(Property.RELOAD_PROPAGATED_FROM);
    }

    public List<String> getRequiredBy() {
        return unitProperties.getList(Property.REQUIRED_BY);
    }

    public List<String> getRequires() {
        return unitProperties.getList(Property.REQUIRES);
    }

    public List<String> getRequiresMountsFor() {
        return unitProperties.getList(Property.REQUIRES_MOUNTS_FOR);
    }

    public List<String> getRequisite() {
        return unitProperties.getList(Property.REQUISITE);
    }

    public List<String> getRequisiteOf() {
        return unitProperties.getList(Property.REQUISITE_OF);
    }

    public String getSourcePath() {
        return unitProperties.getString(Property.SOURCE_PATH);
    }

    public String getStartLimitAction() {
        return unitProperties.getString(Property.START_LIMIT_ACTION);
    }

    public long getStartLimitBurst() {
        return unitProperties.getLong(Property.START_LIMIT_BURST);
    }

    public long getStartLimitIntervalUSec() {
        return unitProperties.getLong(Property.START_LIMIT_INTERVAL_USEC);
    }

    public long getStateChangeTimestamp() {
        return unitProperties.getLong(Property.STATE_CHANGE_TIMESTAMP);
    }

    public long getStateChangeTimestampMonotonic() {
        return unitProperties.getLong(Property.STATE_CHANGE_TIMESTAMP_MONOTONIC);
    }

    public boolean isStopWhenUnneeded() {
        return unitProperties.getBoolean(Property.STOP_WHEN_UNNEEDED);
    }

    public String getSubState() {
        return unitProperties.getString(Property.SUB_STATE);
    }

    public String getSuccessAction() {
        return unitProperties.getString(Property.SUCCESS_ACTION);
    }

    public int getSuccessActionExitStatus() {
        return unitProperties.getInteger(Property.SUCCESS_ACTION_EXIT_STATUS);
    }

    public boolean isTransient() {
        return unitProperties.getBoolean(Property.TRANSIENT);
    }

    public List<String> getTriggeredBy() {
        return unitProperties.getList(Property.TRIGGERED_BY);
    }

    public List<String> getTriggers() {
        return unitProperties.getList(Property.TRIGGERS);
    }

    public String getUnitFilePreset() {
        return unitProperties.getString(Property.UNIT_FILE_PRESET);
    }

    public String getUnitFileState() {
        return unitProperties.getString(Property.UNIT_FILE_STATE);
    }

    public List<String> getWantedBy() {
        return unitProperties.getList(Property.WANTED_BY);
    }

    public List<String> getWants() {
        return unitProperties.getList(Property.WANTS);
    }

    @Override
    public String toString() {
        return name;
    }

    public static final class StateTuple {

        private final String loadState;
        private final String activeState;
        private final String subState;

        public StateTuple(final String loadState, final String activeState, final String subState) {
            this.loadState = loadState;
            this.activeState = activeState;
            this.subState = subState;
        }

        public static StateTuple of(final Unit unit) {
            return new StateTuple(unit.getLoadState(), unit.getActiveState(), unit.getSubState());
        }

        public static StateTuple of(final Map<String, Variant<?>> properties) {
            String loadState = String.valueOf(properties.getOrDefault(LOAD_STATE, new Variant<>("-")).getValue());
            String activeState = String.valueOf(properties.getOrDefault(ACTIVE_STATE, new Variant<>("-")).getValue());
            String subState = String.valueOf(properties.getOrDefault(SUB_STATE, new Variant<>("-")).getValue());

            return new StateTuple(loadState, activeState, subState);
        }

        public static StateTuple of(final Unit unit, final Map<String, Variant<?>> properties) {
            String loadState = String.valueOf(properties.getOrDefault(LOAD_STATE, new Variant<>(unit.getLoadState())).getValue());
            String activeState = String.valueOf(properties.getOrDefault(ACTIVE_STATE, new Variant<>(unit.getActiveState())).getValue());
            String subState = String.valueOf(properties.getOrDefault(SUB_STATE, new Variant<>(unit.getSubState())).getValue());

            return new StateTuple(loadState, activeState, subState);
        }

        public String getLoadState() {
            return loadState;
        }

        public String getActiveState() {
            return activeState;
        }

        public String getSubState() {
            return subState;
        }

        @Override
        public String toString() {
            return String.format("%s - %s (%s)", loadState, activeState, subState);
        }

    }

}
