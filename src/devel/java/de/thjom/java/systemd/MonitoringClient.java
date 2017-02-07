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

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.freedesktop.DBus.Properties.PropertiesChanged;
import org.freedesktop.dbus.DBusSigHandler;
import org.freedesktop.dbus.Variant;
import org.freedesktop.dbus.exceptions.DBusException;

import de.thjom.java.systemd.tools.UnitNameMonitor;
import de.thjom.java.systemd.tools.UnitTypeMonitor;
import de.thjom.java.systemd.tools.UnitTypeMonitor.MonitoredType;
import de.thjom.java.systemd.utils.ForwardingHandler;
import de.thjom.java.systemd.utils.SignalConsumer;

public class MonitoringClient implements Runnable {

    private volatile boolean running;

    public MonitoringClient() {
        this.running = true;
    }

    @Override
    public void run() {
        if (running) {
            try {
                Manager manager = Systemd.get().getManager();

                // 'cronie' monitoring
                Unit cronie = manager.getService("cronie");

                ForwardingHandler<PropertiesChanged> cronieHandler = new ForwardingHandler<PropertiesChanged>() {

                    @Override
                    public void handle(final PropertiesChanged signal) {
                        super.handle(signal);

                        if (cronie.isAssignableFrom(signal.getPath())) {
                            System.out.println("MonitoringClient.run().cronieHandler.new PropertiesChangedHandler() {...}.handle() : " + signal);
                        }
                    }

                };

                SignalConsumer<PropertiesChanged> consumer = new SignalConsumer<>(new DBusSigHandler<PropertiesChanged>() {

                    @Override
                    public void handle(final PropertiesChanged signal) {
                        if (cronie.isAssignableFrom(signal.getPath())) {
                            System.out.println("MonitoringClient.run().cronieHandler.new SignalConsumer() {...}.handle(): " + signal);
                        }
                    }

                });

                cronieHandler.forwardTo(consumer);

                cronie.addHandler(PropertiesChanged.class, cronieHandler);
                cronie.addListener(new UnitStateListener() {

                    @Override
                    public void stateChanged(final Unit unit, final Map<String, Variant<?>> changedProperties) {
                        String loadState = changedProperties.getOrDefault(Unit.Property.LOAD_STATE, new Variant<>("-")).toString();
                        String activeState = changedProperties.getOrDefault(Unit.Property.ACTIVE_STATE, new Variant<>("-")).toString();
                        String subState = changedProperties.getOrDefault(Unit.Property.SUB_STATE, new Variant<>("-")).toString();

                        System.out.format("MonitoringClient.run().cronie.new UnitStateListener() {...}.stateChanged() to %s - %s (%s)\n", loadState, activeState, subState);
                    }

                });

                // Unit monitoring based on names
                UnitNameMonitor miscMonitor = new UnitNameMonitor(manager);
                miscMonitor.addUnits(cronie);
                miscMonitor.addUnits("dbus.service");
                miscMonitor.addDefaultHandlers();

                ForwardingHandler<PropertiesChanged> miscMonitorHandler = new ForwardingHandler<PropertiesChanged>() {

                    @Override
                    public void handle(final PropertiesChanged signal) {
                        super.handle(signal);

                        if (miscMonitor.monitorsUnit(Unit.extractName(signal.getPath()))) {
                            System.out.println("MonitoringClient.run().miscMonitorHandler.new ForwardingHandler() {...}.handle(): " + signal);
                        }
                    }

                };

                consumer = new SignalConsumer<>(new DBusSigHandler<PropertiesChanged>() {

                    @Override
                    public void handle(final PropertiesChanged signal) {
                        if (miscMonitor.monitorsUnit(Unit.extractName(signal.getPath()))) {
                            System.out.println("MonitoringClient.run().miscMonitorHandler.new SignalConsumer() {...}.handle(): " + signal);
                        }
                    }

                });

                miscMonitorHandler.forwardTo(consumer);

                miscMonitor.addHandler(PropertiesChanged.class, miscMonitorHandler);
                miscMonitor.addListener(new UnitStateListener() {

                    @Override
                    public void stateChanged(final Unit unit, final Map<String, Variant<?>> changedProperties) {
                        String loadState = changedProperties.getOrDefault(Unit.Property.LOAD_STATE, new Variant<>("-")).toString();
                        String activeState = changedProperties.getOrDefault(Unit.Property.ACTIVE_STATE, new Variant<>("-")).toString();
                        String subState = changedProperties.getOrDefault(Unit.Property.SUB_STATE, new Variant<>("-")).toString();

                        System.out.format("MonitoringClient.run().miscMonitor.new UnitStateListener() {...}.stateChanged() to %s - %s (%s)\n", loadState, activeState, subState);
                    }

                });

                // Unit monitoring based on types
                UnitTypeMonitor serviceMonitor = new UnitTypeMonitor(manager);
                serviceMonitor.addMonitoredTypes(MonitoredType.SERVICE);
                serviceMonitor.addDefaultHandlers();

                ForwardingHandler<PropertiesChanged> serviceMonitorHandler = new ForwardingHandler<PropertiesChanged>() {

                    @Override
                    public void handle(final PropertiesChanged signal) {
                        super.handle(signal);

                        if (serviceMonitor.monitorsUnit(Unit.extractName(signal.getPath()))) {
                            System.out.println("MonitoringClient.run().serviceMonitorHandler.new ForwardingHandler() {...}.handle(): " + signal);
                        }
                    }

                };

                consumer = new SignalConsumer<>(new DBusSigHandler<PropertiesChanged>() {

                    @Override
                    public void handle(final PropertiesChanged signal) {
                        if (serviceMonitor.monitorsUnit(Unit.extractName(signal.getPath()))) {
                            System.out.println("MonitoringClient.run().serviceMonitorHandler.new SignalConsumer() {...}.handle(): " + signal);
                        }
                    }

                });

                serviceMonitorHandler.forwardTo(consumer);

                serviceMonitor.addHandler(PropertiesChanged.class, serviceMonitorHandler);
                serviceMonitor.addListener(new UnitStateListener() {

                    @Override
                    public void stateChanged(final Unit unit, final Map<String, Variant<?>> changedProperties) {
                        String loadState = changedProperties.getOrDefault(Unit.Property.LOAD_STATE, new Variant<>("-")).toString();
                        String activeState = changedProperties.getOrDefault(Unit.Property.ACTIVE_STATE, new Variant<>("-")).toString();
                        String subState = changedProperties.getOrDefault(Unit.Property.SUB_STATE, new Variant<>("-")).toString();

                        System.out.format("MonitoringClient.run().serviceMonitor.new UnitStateListener() {...}.stateChanged() to %s - %s (%s)\n", loadState, activeState, subState);
                    }

                });

                while (running) {
                    List<Unit> units = new ArrayList<>();
                    units.addAll(serviceMonitor.getMonitoredUnits());
                    units.addAll(miscMonitor.getMonitoredUnits());

                    Iterator<Unit> it = units.iterator();

                    String[][] colsRows = new String[5][units.size()];

                    for (int row = 0; row < units.size(); row++) {
                        Unit unit = it.next();
                        Service service = (Service) unit;

                        int col = 0;

                        colsRows[col++][row] = unit.toString();
                        colsRows[col++][row] = service.getLoadState();
                        colsRows[col++][row] = service.getActiveState();
                        colsRows[col++][row] = service.getSubState();
                        colsRows[col++][row] = service.getDescription();
                    }

                    int[] maxCharsPerColumn = calcMaxColumnChars(colsRows[0], colsRows[1], colsRows[2], colsRows[3], colsRows[4]);

                    for (int row = 0; row < units.size(); row++) {
                        for (int col = 0; col < colsRows.length; col++) {
                            System.out.format("%-" + maxCharsPerColumn[col] + "s ", colsRows[col][row]);
                        }

                        System.out.println();
                    }

                    System.out.println("Press key to stop polling");

                    try {
                        Thread.sleep(60000);
                    }
                    catch (final InterruptedException e) {
                        // Ignore (occurs on key press)
                    }
                }

                // Cleanup
                cronie.removeHandler(PropertiesChanged.class, cronieHandler);

                miscMonitor.removeHandler(PropertiesChanged.class, miscMonitorHandler);
                miscMonitor.removeDefaultHandlers();

                serviceMonitor.removeHandler(PropertiesChanged.class, serviceMonitorHandler);
                serviceMonitor.removeDefaultHandlers();
            }
            catch (final DBusException e) {
                e.printStackTrace();
            }
            finally {
                Systemd.disconnectAll();
            }
        }
    }

    private static int[] calcMaxColumnChars(final String[]... values) {
        int[] maxColChars = new int[values.length];

        for (int i = 0; i < values.length; i++) {
            int maxChars = 0;

            for (String value : values[i]) {
                int numChars = value.length();

                if (numChars > maxChars) {
                    maxChars = numChars;
                }
            }

            maxColChars[i] = maxChars;
        }

        return maxColChars;
    }

    public static void main(final String[] args) {
        MonitoringClient client = new MonitoringClient();

        Thread t = new Thread(client);
        t.start();

        try {
            System.in.read();
        }
        catch (final IOException e) {
            e.printStackTrace();
        }
        finally {
            client.running = false;
            t.interrupt();
        }
    }

}
