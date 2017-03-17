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

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.concurrent.TimeUnit;

import org.freedesktop.dbus.DBusInterface;
import org.freedesktop.dbus.DBusSignal;
import org.freedesktop.dbus.exceptions.DBusException;
import org.testng.Assert;
import org.testng.annotations.Test;

public class SignalSequencerTest implements DBusInterface {

    @Test(description="Tests sequencer ordering.")
    public void testSequencerLogic() throws DBusException {
        SignalSequencer<TestSignal> sequencer = new SignalSequencer<>(1000);

        TestSignal probe1 = null;
        TestSignal probe2 = null;
        TestSignal probe3 = null;

        for (long seqNum = 20L; seqNum >= 0L; seqNum--) {
            TestSignal tm = new TestSignal(seqNum);

            if (seqNum == 20L) {
                probe3 = tm;
            }
            else if (seqNum == 0L) {
                probe2 = tm;
            }

            try {
                sequencer.put(tm);
            }
            catch (InterruptedException e) {
                Assert.fail(e.getMessage(), e);
            }
        }

        for (long seqNum = -20L; seqNum < 0L; seqNum++) {
            TestSignal tm = new TestSignal(seqNum);

            if (seqNum == -20L) {
                probe1 = tm;
            }

            try {
                sequencer.put(tm);
            }
            catch (InterruptedException e) {
                Assert.fail(e.getMessage(), e);
            }
        }

        List<TestSignal> list = new ArrayList<>();

        try {
            TestSignal tm = sequencer.take();

            while (tm != null) {
                list.add(tm);

                tm = sequencer.poll(250L, TimeUnit.MILLISECONDS);
            }
        }
        catch (InterruptedException e) {
            Assert.fail(e.getMessage(), e);
        }

        Assert.assertSame(list.get(0), probe1);
        Assert.assertSame(list.get(20), probe2);
        Assert.assertSame(list.get(list.size() - 1), probe3);
    }

    @Test(description="Tests concurrent sequencer access.")
    public void testSequencerAccess() throws DBusException {
        SignalSequencer<TestSignal> sequencer = new SignalSequencer<>(10000);
        int numSignals = 30;

        SignalProducer producer = new SignalProducer(sequencer, numSignals);
        producer.start();

        try {
            Thread.sleep(50L);
        }
        catch (InterruptedException e) {
            Assert.fail(e.getMessage(), e);
        }

        List<TestSignal> drainedData = new ArrayList<>();
        TestSignal tm = null;

        try {
            do {
                if (producer.isAlive()) {
                    tm = sequencer.poll(10000L, TimeUnit.MILLISECONDS);
                }
                else {
                    tm = sequencer.poll(250L, TimeUnit.MILLISECONDS);
                }

                if (tm != null) {
                    drainedData.add(tm);
                }
            }
            while (tm != null);

            producer.join();
        }
        catch (InterruptedException e) {
            Assert.fail(e.getMessage(), e);
        }

        List<TestSignal> testData = producer.getTestData();

        Assert.assertFalse(Arrays.equals(drainedData.toArray(), testData.toArray()));

        Collections.sort(testData, new SignalSequencer.SignalComparator<>());

        Assert.assertTrue(Arrays.equals(drainedData.toArray(), testData.toArray()));
    }

    private static class SignalProducer extends Thread {

        private final Random random = new Random();
        private final List<TestSignal> testData = new ArrayList<>();

        private SignalSequencer<TestSignal> sequencer;
        private int numSignals;

        public SignalProducer(final SignalSequencer<TestSignal> sequencer, final int numSignals) {
            this.sequencer = sequencer;
            this.numSignals = numSignals;
        }

        @Override
        public void run() {
            int counter = 0;

            while (counter < numSignals) {
                long seqNum = counter++ + Math.abs(random.nextLong() % 10);

                try {
                    TestSignal tm = new TestSignal(seqNum);

                    sequencer.put(tm);
                    testData.add(tm);
                }
                catch (InterruptedException | DBusException e) {
                    // Do nothing
                }
            }
        }

        public List<TestSignal> getTestData() {
            return testData;
        }

    }

    private static class TestSignal extends DBusSignal {

        public TestSignal(final long serial) throws DBusException {
            super(Systemd.OBJECT_PATH);

            this.serial = serial;
        }

        @Override
        public boolean equals(final Object obj) {
            return getSerial() == ((TestSignal) obj).getSerial();
        }

        @Override
        public int hashCode() {
            return Long.hashCode(getSerial());
        }

    }

    @Override
    public boolean isRemote() {
        return false;
    }

    @Override
    public String getObjectPath() {
        return Systemd.OBJECT_PATH;
    }

}
