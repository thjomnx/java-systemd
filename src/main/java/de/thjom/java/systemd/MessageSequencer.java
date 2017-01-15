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

import java.util.Comparator;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.TimeUnit;

import org.freedesktop.dbus.Message;

public class MessageSequencer<E extends Message> {

    public static final long TIMEOUT_INFINITE = -1L;

    private final BlockingQueue<E> buffer;
    private final Queue<E> sequencer;

    private long transferDelay = 50L;
    private int chunkSize;

    public MessageSequencer(final int capacity) {
        this(capacity, 100);
    }

    public MessageSequencer(final int capacity, final int chunkSize) {
        this.buffer = new ArrayBlockingQueue<>(capacity);
        this.sequencer = new PriorityQueue<>(capacity, new MessageComparator<>());

        if (chunkSize < 0) {
            throw new IllegalArgumentException();
        }

        this.chunkSize = chunkSize;
    }

    public final boolean add(final E item) {
        return buffer.add(item);
    }

    public final E take() throws InterruptedException {
        return poll(TIMEOUT_INFINITE, null);
    }

    public final E poll(final long timeout, final TimeUnit unit) throws InterruptedException {
        E head = sequencer.poll();

        if (head == null) {
            E pending = transfer(timeout, unit, chunkSize);

            if (pending != null) {
                head = sequencer.poll();
                sequencer.offer(pending);
            }
            else {
                head = sequencer.poll();
            }
        }

        return head;
    }

    private E transfer(final long timeout, final TimeUnit unit, int chunkSize) throws InterruptedException {
        boolean once = false;

        do {
            E head;

            if (timeout < 0) {
                head = buffer.take();
            }
            else {
                head = buffer.poll(timeout, unit);
            }

            if (head != null) {
                if (!sequencer.offer(head)) {
                    return head;
                }

                if (!once) {
                    Thread.sleep(transferDelay);

                    once = true;
                }
            }
            else {
                break;
            }
        }
        while (!buffer.isEmpty() && chunkSize-- > 0);

        return null;
    }

    public long getTransferDelay() {
        return transferDelay;
    }

    public void setTransferDelay(final long transferDelay) {
        if (transferDelay < 0L) {
            throw new IllegalArgumentException();
        }

        this.transferDelay = transferDelay;
    }

    public int getChunkSize() {
        return chunkSize;
    }

    public void setChunkSize(final int chunkSize) {
        if (chunkSize < 0) {
            throw new IllegalArgumentException();
        }

        this.chunkSize = chunkSize;
    }

    static final class MessageComparator<E extends Message> implements Comparator<E> {

        @Override
        public int compare(final E m1, final E m2) {
            if (m1 == null) {
                return Integer.MAX_VALUE;
            }
            else if (m2 == null) {
                return Integer.MIN_VALUE;
            }
            else {
                return Long.compare(m1.getSerial(), m2.getSerial());
            }
        }

    }

}
