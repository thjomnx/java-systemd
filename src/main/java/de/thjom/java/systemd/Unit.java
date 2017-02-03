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

package de.thjom.java.systemd;

import static de.thjom.java.systemd.Unit.Property.ACTIVE_STATE;
import static de.thjom.java.systemd.Unit.Property.LOAD_STATE;
import static de.thjom.java.systemd.Unit.Property.SUB_STATE;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Vector;

import org.freedesktop.DBus.Introspectable;
import org.freedesktop.DBus.Properties.PropertiesChanged;
import org.freedesktop.dbus.DBusSigHandler;
import org.freedesktop.dbus.DBusSignal;
import org.freedesktop.dbus.Path;
import org.freedesktop.dbus.Variant;
import org.freedesktop.dbus.exceptions.DBusException;

import de.thjom.java.systemd.interfaces.PropertyInterface;
import de.thjom.java.systemd.interfaces.UnitInterface;
import de.thjom.java.systemd.types.Condition;
import de.thjom.java.systemd.types.Job;
import de.thjom.java.systemd.types.LoadError;
import de.thjom.java.systemd.utils.ForwardingHandler;
import de.thjom.java.systemd.utils.MessageConsumer;

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

    public static class Property extends InterfaceAdapter.Property {

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
        public static final String CAN_ISOLATE = "CanIsolate";
        public static final String CAN_RELOAD = "CanReload";
        public static final String CAN_START = "CanStart";
        public static final String CAN_STOP = "CanStop";
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
        public static final String FOLLOWING = "Following";
        public static final String FRAGMENT_PATH = "FragmentPath";
        public static final String ID = "Id";
        public static final String IGNORE_ON_ISOLATE = "IgnoreOnIsolate";
        public static final String INACTIVE_ENTER_TIMESTAMP = "InactiveEnterTimestamp";
        public static final String INACTIVE_ENTER_TIMESTAMP_MONOTONIC = "InactiveEnterTimestampMonotonic";
        public static final String INACTIVE_EXIT_TIMESTAMP = "InactiveExitTimestamp";
        public static final String INACTIVE_EXIT_TIMESTAMP_MONOTONIC = "InactiveExitTimestampMonotonic";
        public static final String JOB = "Job";
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
        public static final String PROPAGATES_RELOAD_TO = "PropagatesReloadTo";
        public static final String REFUSE_MANUAL_START = "RefuseManualStart";
        public static final String REFUSE_MANUAL_STOP = "RefuseManualStop";
        public static final String RELOAD_PROPAGATED_FROM = "ReloadPropagatedFrom";
        public static final String REQUIRED_BY = "RequiredBy";
        public static final String REQUIRES = "Requires";
        public static final String REQUIRES_MOUNTS_FOR = "RequiresMountsFor";
        public static final String REQUISITE = "Requisite";
        public static final String REQUISITE_OF = "RequisiteOf";
        public static final String SOURCE_PATH = "SourcePath";
        public static final String STOP_WHEN_UNNEEDED = "StopWhenUnneeded";
        public static final String SUB_STATE = "SubState";
        public static final String TRANSIENT = "Transient";
        public static final String TRIGGERED_BY = "TriggeredBy";
        public static final String TRIGGERS = "Triggers";
        public static final String WANTED_BY = "WantedBy";
        public static final String WANTS = "Wants";

        private Property() {
            super();
        }

        public static final String[] getAllNames() {
            return getAllNames(Property.class);
        }

    }

    protected final String name;
    protected final Manager manager;

    private final Properties unitProperties;

    private final List<UnitStateListener> unitStateListeners = new ArrayList<>();
    private ForwardingHandler<PropertiesChanged> defaultHandler;

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

        super.addHandler(type, handler);
    }

    @Override
    public <T extends DBusSignal> void removeHandler(final Class<T> type, final DBusSigHandler<T> handler) throws DBusException {
        if (handler instanceof ForwardingHandler) {
            ((ForwardingHandler<?>) handler).forwardTo(null);
        }

        super.removeHandler(type, handler);
    }

    @Override
    public synchronized void addListener(final UnitStateListener listener) throws DBusException {
        if (defaultHandler == null) {
            defaultHandler = new ForwardingHandler<>();
            defaultHandler.forwardTo(new MessageConsumer<PropertiesChanged>(100) {

                @Override
                public void propertiesChanged(final PropertiesChanged signal) {
                    if (isAssignableFrom(signal.getPath())) {
                        Map<String, Variant<?>> properties = signal.getChangedProperties();

                        if (properties.containsKey(ACTIVE_STATE) || properties.containsKey(LOAD_STATE) || properties.containsKey(SUB_STATE)) {
                            synchronized (unitStateListeners) {
                                for (UnitStateListener listener : unitStateListeners) {
                                    listener.stateChanged(Unit.this, properties);
                                }
                            }
                        }
                    }
                }

            });

            addHandler(PropertiesChanged.class, defaultHandler);
        }

        unitStateListeners.add(listener);
    }

    @Override
    public synchronized void removeListener(final UnitStateListener listener) throws DBusException {
        unitStateListeners.remove(listener);

        if (unitStateListeners.isEmpty() && defaultHandler != null) {
            removeHandler(PropertiesChanged.class, defaultHandler);

            defaultHandler = null;
        }
    }

    public String introspect() throws DBusException {
        Introspectable intro = dbus.getRemoteObject(Systemd.SERVICE_NAME, getInterface().getObjectPath(), Introspectable.class);

        return intro.Introspect();
    }

    public Path start(final Mode mode) {
        return start(mode.getValue());
    }

    public Path start(final String mode) {
        return manager.startUnit(name, mode);
    }

    public Path stop(final Mode mode) {
        return stop(mode.getValue());
    }

    public Path stop(final String mode) {
        return manager.stopUnit(name, mode);
    }

    public Path reload(final Mode mode) {
        return reload(mode.getValue());
    }

    public Path reload(final String mode) {
        return manager.reloadUnit(name, mode);
    }

    public Path restart(final Mode mode) {
        return restart(mode.getValue());
    }

    public Path restart(final String mode) {
        return manager.restartUnit(name, mode);
    }

    public Path tryRestart(final Mode mode) {
        return tryRestart(mode.getValue());
    }

    public Path tryRestart(final String mode) {
        return manager.tryRestartUnit(name, mode);
    }

    public Path reloadOrRestart(final Mode mode) {
        return reloadOrRestart(mode.getValue());
    }

    public Path reloadOrRestart(final String mode) {
        return manager.reloadOrRestartUnit(name, mode);
    }

    public Path reloadOrTryRestart(final Mode mode) {
        return reloadOrTryRestart(mode.getValue());
    }

    public Path reloadOrTryRestart(final String mode) {
        return manager.reloadOrTryRestartUnit(name, mode);
    }

    public void kill(final Who who, final int signal) {
        kill(who.getValue(), signal);
    }

    public void kill(final String who, final int signal) {
        manager.killUnit(name, who, signal);
    }

    public void resetFailed() {
        manager.resetFailedUnit(name);
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

    public Vector<String> getAfter() {
        return unitProperties.getVector(Property.AFTER);
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
        return Condition.list(unitProperties.getVector(Property.ASSERTS));
    }

    public Vector<String> getBefore() {
        return unitProperties.getVector(Property.BEFORE);
    }

    public Vector<String> getBindsTo() {
        return unitProperties.getVector(Property.BINDS_TO);
    }

    public Vector<String> getBoundBy() {
        return unitProperties.getVector(Property.BOUND_BY);
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
        return Condition.list(unitProperties.getVector(Property.CONDITIONS));
    }

    public Vector<String> getConflictedBy() {
        return unitProperties.getVector(Property.CONFLICTED_BY);
    }

    public Vector<String> getConflicts() {
        return unitProperties.getVector(Property.CONFLICTS);
    }

    public Vector<String> getConsistsOf() {
        return unitProperties.getVector(Property.CONSISTS_OF);
    }

    public boolean isDefaultDependencies() {
        return unitProperties.getBoolean(Property.DEFAULT_DEPENDENCIES);
    }

    public String getDescription() {
        return unitProperties.getString(Property.DESCRIPTION);
    }

    public Vector<String> getDocumentation() {
        return unitProperties.getVector(Property.DOCUMENTATION);
    }

    public Vector<String> getDropInPaths() {
        return unitProperties.getVector(Property.DROP_IN_PATHS);
    }

    public String getFollowing() {
        return unitProperties.getString(Property.FOLLOWING);
    }

    public String getFragmentPath() {
        return unitProperties.getString(Property.FRAGMENT_PATH);
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

    public Job getJob() {
        Object[] array = (Object[]) unitProperties.getVariant(Property.JOB).getValue();

        return new Job(array);
    }

    public String getJobTimeoutAction() {
        return unitProperties.getString(Property.JOB_TIMEOUT_ACTION);
    }

    public String getJobTimeoutRebootArgument() {
        return unitProperties.getString(Property.JOB_TIMEOUT_REBOOT_ARGUMENT);
    }

    public long getJobTimeoutUSec() {
        return unitProperties.getLong(Property.JOB_TIMEOUT_USEC);
    }

    public Vector<String> getJoinsNamespaceOf() {
        return unitProperties.getVector(Property.JOINS_NAMESPACE_OF);
    }

    public LoadError getLoadError() {
    	Object[] array = (Object[]) unitProperties.getVariant(Property.LOAD_ERROR).getValue();

    	return new LoadError(array);
    }

    public String getLoadState() {
        return unitProperties.getString(Property.LOAD_STATE);
    }

    public Vector<String> getNames() {
        return unitProperties.getVector(Property.NAMES);
    }

    public boolean isNeedDaemonReload() {
        return unitProperties.getBoolean(Property.NEED_DAEMON_RELOAD);
    }

    public Vector<String> getOnFailure() {
        return unitProperties.getVector(Property.ON_FAILURE);
    }

    public String getOnFailureJobMode() {
        return unitProperties.getString(Property.ON_FAILURE_JOB_MODE);
    }

    public Vector<String> getPartOf() {
        return unitProperties.getVector(Property.PART_OF);
    }

    public Vector<String> getPropagatesReloadTo() {
        return unitProperties.getVector(Property.PROPAGATES_RELOAD_TO);
    }

    public boolean isRefuseManualStart() {
        return unitProperties.getBoolean(Property.REFUSE_MANUAL_START);
    }

    public boolean isRefuseManualStop() {
        return unitProperties.getBoolean(Property.REFUSE_MANUAL_STOP);
    }

    public Vector<String> getReloadPropagatedFrom() {
        return unitProperties.getVector(Property.RELOAD_PROPAGATED_FROM);
    }

    public Vector<String> getRequiredBy() {
        return unitProperties.getVector(Property.REQUIRED_BY);
    }

    public Vector<String> getRequires() {
        return unitProperties.getVector(Property.REQUIRES);
    }

    public Vector<String> getRequiresMountsFor() {
        return unitProperties.getVector(Property.REQUIRES_MOUNTS_FOR);
    }

    public Vector<String> getRequisite() {
        return unitProperties.getVector(Property.REQUISITE);
    }

    public Vector<String> getRequisiteOf() {
        return unitProperties.getVector(Property.REQUISITE_OF);
    }

    public String getSourcePath() {
        return unitProperties.getString(Property.SOURCE_PATH);
    }

    public boolean isStopWhenUnneeded() {
        return unitProperties.getBoolean(Property.STOP_WHEN_UNNEEDED);
    }

    public String getSubState() {
        return unitProperties.getString(Property.SUB_STATE);
    }

    public boolean isTransient() {
        return unitProperties.getBoolean(Property.TRANSIENT);
    }

    public Vector<String> getTriggeredBy() {
        return unitProperties.getVector(Property.TRIGGERED_BY);
    }

    public Vector<String> getTriggers() {
        return unitProperties.getVector(Property.TRIGGERS);
    }

    public Vector<String> getWantedBy() {
        return unitProperties.getVector(Property.WANTED_BY);
    }

    public Vector<String> getWants() {
        return unitProperties.getVector(Property.WANTS);
    }

    @Override
    public String toString() {
        return name;
    }

}
