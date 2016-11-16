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
            try {
                Manager manager = Systemd.get().getManager();

                UnitMonitor unitMonitor = new UnitMonitor(manager, MonitoredType.SERVICE);
                unitMonitor.attach();

                while (running) {
                    Collection<Unit> units = unitMonitor.getMonitoredUnits().values();
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
                        Thread.sleep(10000);
                    }
                    catch (final InterruptedException e) {
                        // Ignore (occurs on key press)
                    }
                }

                unitMonitor.detach();
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
