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

import java.util.List;
import java.util.Map;
import java.util.Vector;

import org.freedesktop.DBus.Introspectable;
import org.freedesktop.dbus.DBusConnection;
import org.freedesktop.dbus.Path;
import org.freedesktop.dbus.exceptions.DBusException;
import org.systemd.interfaces.UnitInterface;
import org.systemd.types.Condition;
import org.systemd.types.Job;
import org.systemd.types.LoadError;

public abstract class Unit extends InterfaceAdapter {

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

    protected final String name;

    private final Properties properties;

    protected Unit(final DBusConnection dbus, final UnitInterface iface, final String name) throws DBusException {
        super(dbus, iface);

        this.name = name;
        this.properties = Properties.create(dbus, iface.getObjectPath(), SERVICE_NAME);
    }

    public static String normalizeName(final String name, final String suffix) {
        return name.endsWith(suffix) ? name : name + suffix;
    }

    @Override
    public UnitInterface getInterface() {
        return (UnitInterface) super.getInterface();
    }

    public String introspect() throws DBusException {
        Introspectable intro = dbus.getRemoteObject(Systemd.SERVICE_NAME, getInterface().getObjectPath(), Introspectable.class);

        return intro.Introspect();
    }

    public void kill(final Who who, final int signal) {
        getInterface().kill(who.getValue(), signal);
    }

    public Path reload(final Mode mode) {
        return reload(mode.getValue());
    }

    public Path reload(final String mode) {
        return getInterface().reload(mode);
    }

    public Path reloadOrRestart(final Mode mode) {
        return reloadOrRestart(mode.getValue());
    }

    public Path reloadOrRestart(final String mode) {
        return getInterface().reloadOrRestart(mode);
    }

    public Path reloadOrTryRestart(final Mode mode) {
        return reloadOrTryRestart(mode.getValue());
    }

    public Path reloadOrTryRestart(final String mode) {
        return getInterface().reloadOrTryRestart(mode);
    }

    public void resetFailed() {
        getInterface().resetFailed();
    }

    public Path restart(final Mode mode) {
        return restart(mode.getValue());
    }

    public Path restart(final String mode) {
        return getInterface().restart(mode);
    }

    public void setProperties(final boolean runtime, final Map<String, Object> properties) {
        throw new UnsupportedOperationException();
    }

    public Path start(final Mode mode) {
        return start(mode.getValue());
    }

    public Path start(final String mode) {
        return getInterface().start(mode);
    }

    public Path stop(final Mode mode) {
        return stop(mode.getValue());
    }

    public Path stop(final String mode) {
        return getInterface().stop(mode);
    }

    public Path tryRestart(final Mode mode) {
        return tryRestart(mode.getValue());
    }

    public Path tryRestart(final String mode) {
        return getInterface().tryRestart(mode);
    }

    public long getActiveEnterTimestamp() {
        return properties.getLong("ActiveEnterTimestamp");
    }

    public long getActiveEnterTimestampMonotonic() {
        return properties.getLong("ActiveEnterTimestampMonotonic");
    }

    public long getActiveExitTimestamp() {
        return properties.getLong("ActiveExitTimestamp");
    }

    public long getActiveExitTimestampMonotonic() {
        return properties.getLong("ActiveExitTimestampMonotonic");
    }

    public String getActiveState() {
        return properties.getString("ActiveState");
    }

    public Vector<String> getAfter() {
        return properties.getVector("After");
    }

    public boolean isAllowIsolate() {
        return properties.getBoolean("AllowIsolate");
    }

    public boolean isAssertResult() {
        return properties.getBoolean("AssertResult");
    }

    public long getAssertTimestamp() {
        return properties.getLong("AssertTimestamp");
    }

    public long getAssertTimestampMonotonic() {
        return properties.getLong("AssertTimestampMonotonic");
    }

    public List<Condition> getAsserts() {
        return Condition.list(properties.getVector("Asserts"));
    }

    public Vector<String> getBefore() {
        return properties.getVector("Before");
    }

    public Vector<String> getBindsTo() {
        return properties.getVector("BindsTo");
    }

    public Vector<String> getBoundBy() {
        return properties.getVector("BoundBy");
    }

    public boolean canIsolate() {
        return properties.getBoolean("CanIsolate");
    }

    public boolean canReload() {
        return properties.getBoolean("CanReload");
    }

    public boolean canStart() {
        return properties.getBoolean("CanStart");
    }

    public boolean canStop() {
        return properties.getBoolean("CanStop");
    }

    public boolean getConditionResult() {
        return properties.getBoolean("ConditionResult");
    }

    public long getConditionTimestamp() {
        return properties.getLong("ConditionTimestamp");
    }

    public long getConditionTimestampMonotonic() {
        return properties.getLong("ConditionTimestampMonotonic");
    }

    public List<Condition> getConditions() {
        return Condition.list(properties.getVector("Conditions"));
    }

    public Vector<String> getConflictedBy() {
        return properties.getVector("ConflictedBy");
    }

    public Vector<String> getConflicts() {
        return properties.getVector("Conflicts");
    }

    public Vector<String> getConsistsOf() {
        return properties.getVector("ConsistsOf");
    }

    public boolean hasDefaultDependencies() {
        return properties.getBoolean("DefaultDependencies");
    }

    public String getDescription() {
        return properties.getString("Description");
    }

    public Vector<String> getDocumentation() {
        return properties.getVector("Documentation");
    }

    public Vector<String> getDropInPaths() {
        return properties.getVector("DropInPaths");
    }

    public String getFollowing() {
        return properties.getString("Following");
    }

    public String getFragmentPath() {
        return properties.getString("FragmentPath");
    }

    public String getId() {
        return properties.getString("Id");
    }

    public boolean isIgnoreOnIsolate() {
        return properties.getBoolean("IgnoreOnIsolate");
    }

    public long getInactiveEnterTimestamp() {
        return properties.getLong("InactiveEnterTimestamp");
    }

    public long getInactiveEnterTimestampMonotonic() {
        return properties.getLong("InactiveEnterTimestampMonotonic");
    }

    public long getInactiveExitTimestamp() {
        return properties.getLong("InactiveExitTimestamp");
    }

    public long getInactiveExitTimestampMonotonic() {
        return properties.getLong("InactiveExitTimestampMonotonic");
    }

    public Job getJob() {
        Object[] array = (Object[]) properties.getVariant("Job").getValue();

        return new Job(array);
    }

    public String getJobTimeoutAction() {
        return properties.getString("JobTimeoutAction");
    }

    public String getJobTimeoutRebootArgument() {
        return properties.getString("JobTimeoutRebootArgument");
    }

    public long getJobTimeoutUSec() {
        return properties.getLong("JobTimeoutUSec");
    }

    public Vector<String> getJoinsNamespaceOf() {
        return properties.getVector("JoinsNamespaceOf");
    }

    public LoadError getLoadError() {
    	Object[] array = (Object[]) properties.getVariant("LoadError").getValue();

    	return new LoadError(array);
    }

    public String getLoadState() {
        return properties.getString("LoadState");
    }

    public Vector<String> getNames() {
        return properties.getVector("Names");
    }

    public boolean isNeedDaemonReload() {
        return properties.getBoolean("NeedDaemonReload");
    }

    public Vector<String> getOnFailure() {
        return properties.getVector("OnFailure");
    }

    public String getOnFailureJobMode() {
        return properties.getString("OnFailureJobMode");
    }

    public Vector<String> getPartOf() {
        return properties.getVector("PartOf");
    }

    public Vector<String> getPropagatesReloadTo() {
        return properties.getVector("PropagatesReloadTo");
    }

    public boolean isRefuseManualStart() {
        return properties.getBoolean("RefuseManualStart");
    }

    public boolean isRefuseManualStop() {
        return properties.getBoolean("RefuseManualStop");
    }

    public Vector<String> getReloadPropagatedFrom() {
        return properties.getVector("ReloadPropagatedFrom");
    }

    public Vector<String> getRequiredBy() {
        return properties.getVector("RequiredBy");
    }

    public Vector<String> getRequires() {
        return properties.getVector("Requires");
    }

    public Vector<String> getRequiresMountsFor() {
        return properties.getVector("RequiresMountsFor");
    }

    public Vector<String> getRequisite() {
        return properties.getVector("Requisite");
    }

    public Vector<String> getRequisiteOf() {
        return properties.getVector("RequisiteOf");
    }

    public String getSourcePath() {
        return properties.getString("SourcePath");
    }

    public boolean isStopWhenUnneeded() {
        return properties.getBoolean("StopWhenUnneeded");
    }

    public String getSubState() {
        return properties.getString("SubState");
    }

    public boolean isTransient() {
        return properties.getBoolean("Transient");
    }

    public Vector<String> getTriggeredBy() {
        return properties.getVector("TriggeredBy");
    }

    public Vector<String> getTriggers() {
        return properties.getVector("Triggers");
    }

    public Vector<String> getWantedBy() {
        return properties.getVector("WantedBy");
    }

    public Vector<String> getWants() {
        return properties.getVector("Wants");
    }

    @Override
    public String toString() {
        return name;
    }

}