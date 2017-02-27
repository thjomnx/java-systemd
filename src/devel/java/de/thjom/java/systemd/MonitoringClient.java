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

import org.freedesktop.DBus.Properties.PropertiesChanged;
import org.freedesktop.dbus.exceptions.DBusException;

import de.thjom.java.systemd.Unit.StateTuple;
import de.thjom.java.systemd.UnitTypeMonitor.MonitoredType;

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

                cronie.addHandler(PropertiesChanged.class, s -> {
                    System.out.println("MonitoringClient.run().cronie.addHandler().handle(): " + s);
                });

                cronie.addConsumer(PropertiesChanged.class, s -> {
                    System.out.println("MonitoringClient.run().cronie.addConsumer().handler(): " + s);
                });

                cronie.addListener((u, p) -> System.out.format("%s changed state to %s\n", u, StateTuple.from(u, p)));

                // Unit monitoring based on names
                UnitNameMonitor miscMonitor = new UnitNameMonitor(manager);
                miscMonitor.addUnits(cronie);
                miscMonitor.addUnits("avahi-daemon.service");
                miscMonitor.addDefaultHandlers();

                miscMonitor.addHandler(PropertiesChanged.class, s -> {
                    if (miscMonitor.monitorsUnit(Unit.extractName(s.getPath()))) {
                        System.out.println("MonitoringClient.run().miscMonitor.addHandler().handle(): " + s);
                    }
                });

                miscMonitor.addConsumer(PropertiesChanged.class, s -> {
                    if (miscMonitor.monitorsUnit(Unit.extractName(s.getPath()))) {
                        System.out.println("MonitoringClient.run().miscMonitor.addConsumer().handle(): " + s);
                    }
                });

                miscMonitor.addListener((u, p) -> System.out.format("%s changed state to %s\n", u, StateTuple.from(u, p)));

                // Unit monitoring based on types
                UnitTypeMonitor serviceMonitor = new UnitTypeMonitor(manager);
                serviceMonitor.addMonitoredTypes(MonitoredType.SERVICE);
                serviceMonitor.addDefaultHandlers();

                serviceMonitor.addHandler(PropertiesChanged.class, s -> {
                    if (serviceMonitor.monitorsUnit(Unit.extractName(s.getPath()))) {
                        System.out.println("MonitoringClient.run().serviceMonitor.addHandler().handle(): " + s);
                    }
                });

                serviceMonitor.addConsumer(PropertiesChanged.class, s -> {
                    if (serviceMonitor.monitorsUnit(Unit.extractName(s.getPath()))) {
                        System.out.println("MonitoringClient.run().serviceMonitor.addConsumer().handle(): " + s);
                    }
                });

                serviceMonitor.addListener((u, p) -> System.out.format("%s changed state to %s\n", u, StateTuple.from(u, p)));

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
                miscMonitor.removeDefaultHandlers();
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
