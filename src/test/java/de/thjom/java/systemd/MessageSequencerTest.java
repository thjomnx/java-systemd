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

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.TimeUnit;

import org.freedesktop.dbus.Message;
import org.freedesktop.dbus.exceptions.DBusException;
import org.testng.Assert;
import org.testng.annotations.Test;

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

            sequencer.add(tm);
        }

        for (long seqNum = -20L; seqNum < 0L; seqNum++) {
            TestMessage tm = new TestMessage(seqNum);

            if (seqNum == -20L) {
                probe1 = tm;
            }

            sequencer.add(tm);
        }

        List<TestMessage> list = new ArrayList<>();

        try {
            TestMessage tm = sequencer.take();

            while (tm != null) {
                list.add(tm);

                tm = sequencer.poll(3000L, TimeUnit.MILLISECONDS);
            }
        }
        catch (final InterruptedException e) {
            Assert.fail(e.getMessage(), e);
        }

        Assert.assertSame(list.get(0), probe1);
        Assert.assertSame(list.get(20), probe2);
        Assert.assertSame(list.get(list.size() - 1), probe3);
    }

    @Test(description="Tests concurrent sequencer access.")
    public void testSequencerAccess() throws DBusException {
        MessageSequencer<TestMessage> sequencer = new MessageSequencer<>(10000);

        MessageProducer reader = new MessageProducer(sequencer);
        reader.start();

        TestMessage tm = null;

        try {
            do {
                tm = sequencer.poll(1000L, TimeUnit.MILLISECONDS);
            }
            while (tm != null);
        }
        catch (final InterruptedException e) {
            Assert.fail(e.getMessage(), e);
        }
    }

    static class MessageProducer extends Thread {

        MessageSequencer<TestMessage> sequencer;
        Random random = new Random();

        MessageProducer(final MessageSequencer<TestMessage> sequencer) {
            this.sequencer = sequencer;
        }

        @Override
        public void run() {
            long seqNum = 0L;

            while (seqNum < 3000L) {
                seqNum = seqNum++ + Math.abs(random.nextLong() % 10);

                try {
                    sequencer.add(new TestMessage(seqNum));
                }
                catch (final DBusException e) {
                    // Do nothing
                }
            }
        }

    }

    static class TestMessage extends Message {

        TestMessage(long serial) throws DBusException {
            super((byte) 0x00, (byte) 0x00, (byte) 0x17);

            this.serial = serial;
        }

    }

}
