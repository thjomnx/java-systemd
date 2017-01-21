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

package de.thjom.java.systemd.utils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.concurrent.TimeUnit;

import org.freedesktop.dbus.Message;
import org.freedesktop.dbus.exceptions.DBusException;
import org.testng.Assert;
import org.testng.annotations.Test;

import de.thjom.java.systemd.utils.MessageSequencer;

public class MessageSequencerTest {

    @Test(description="Tests sequencer ordering.")
    public void testSequencerLogic() throws DBusException {
        MessageSequencer<TestMessage> sequencer = new MessageSequencer<>(1000);

        TestMessage probe1 = null;
        TestMessage probe2 = null;
        TestMessage probe3 = null;

        for (long seqNum = 20L; seqNum >= 0L; seqNum--) {
            TestMessage tm = new TestMessage(seqNum);

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
            TestMessage tm = new TestMessage(seqNum);

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

        List<TestMessage> list = new ArrayList<>();

        try {
            TestMessage tm = sequencer.take();

            while (tm != null) {
                list.add(tm);

                tm = sequencer.poll(3000L, TimeUnit.MILLISECONDS);
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
        MessageSequencer<TestMessage> sequencer = new MessageSequencer<>(10000);
        int numMessages = 30;

        MessageProducer reader = new MessageProducer(sequencer, numMessages);
        reader.start();

        try {
            Thread.sleep(50L);
        }
        catch (InterruptedException e) {
            Assert.fail(e.getMessage(), e);
        }

        List<TestMessage> drainedData = new ArrayList<>();
        TestMessage tm = null;

        try {
            do {
                tm = sequencer.poll(1000L, TimeUnit.MILLISECONDS);

                if (tm != null) {
                    drainedData.add(tm);
                }
            }
            while (tm != null);

            reader.join();
        }
        catch (InterruptedException e) {
            Assert.fail(e.getMessage(), e);
        }

        List<TestMessage> testData = reader.getTestData();

        Assert.assertFalse(Arrays.equals(drainedData.toArray(), testData.toArray()));

        Collections.sort(testData, new MessageSequencer.MessageComparator<>());

        Assert.assertTrue(Arrays.equals(drainedData.toArray(), testData.toArray()));
    }

    private static class MessageProducer extends Thread {

        private final Random random = new Random();
        private final List<TestMessage> testData = new ArrayList<>();

        private MessageSequencer<TestMessage> sequencer;
        private int numMessages;

        public MessageProducer(final MessageSequencer<TestMessage> sequencer, final int numMessages) {
            this.sequencer = sequencer;
            this.numMessages = numMessages;
        }

        @Override
        public void run() {
            int counter = 0;

            while (counter < numMessages) {
                long seqNum = counter++ + Math.abs(random.nextLong() % 10);

                try {
                    TestMessage tm = new TestMessage(seqNum);

                    sequencer.put(tm);
                    testData.add(tm);
                }
                catch (InterruptedException | DBusException e) {
                    // Do nothing
                }
            }
        }

        public List<TestMessage> getTestData() {
            return testData;
        }

    }

    private static class TestMessage extends Message {

        public TestMessage(final long serial) throws DBusException {
            super((byte) 0x00, (byte) 0x00, (byte) 0x17);

            this.serial = serial;
        }

        @Override
        public boolean equals(final Object obj) {
            return getSerial() == ((TestMessage) obj).getSerial();
        }

        @Override
        public int hashCode() {
            return Long.hashCode(getSerial());
        }

    }

}
