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
import java.util.Collection;
import java.util.Iterator;

import org.freedesktop.dbus.exceptions.DBusException;

import de.thjom.java.systemd.tools.UnitMonitor;
import de.thjom.java.systemd.tools.UnitMonitor.MonitoredType;

public class MonitoringClient implements Runnable {

    private volatile boolean running;

    public MonitoringClient() {
        this.running = true;
    }

    @Override
    public void run() {
        if (running) {
            try (Systemd systemd = Systemd.get()) {
                Manager manager = systemd.getManager();

                UnitMonitor unitMonitor = new UnitMonitor(manager, MonitoredType.SERVICE);
                unitMonitor.attach();

                while (running) {
                    Collection<Unit> units = unitMonitor.getMonitoredUnits().values();
                    Iterator<Unit> it = units.iterator();

                    String[][] matrix = new String[5][units.size()];

                    for (int r = 0; r < units.size(); r++) {
                        Unit unit = it.next();
                        Service service = (Service) unit;

                        matrix[0][r] = unit.toString();
                        matrix[1][r] = service.getLoadState();
                        matrix[2][r] = service.getActiveState();
                        matrix[3][r] = service.getSubState();
                        matrix[4][r] = service.getDescription();
                    }

                    int[] maxCharsPerColumn = calcMaxColumnChars(matrix[0], matrix[1], matrix[2], matrix[3], matrix[4]);

                    for (int r = 0; r < units.size(); r++) {
                        for (int c = 0; c < matrix.length; c++) {
                            System.out.format("%-" + maxCharsPerColumn[c] + "s ", matrix[c][r]);
                        }

                        System.out.println();
                    }

                    System.out.println("Press key to stop polling");

                    try {
                        Thread.sleep(10000);
                    }
                    catch (final InterruptedException e) {
                        // Ignore (occurs on key press)
                    }
                }

                unitMonitor.detach();
                manager.unsubscribe();
            }
            catch (final DBusException e) {
                e.printStackTrace();
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

    public static void main(String[] args) {
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
