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
import java.util.Collection;
import java.util.Comparator;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.TimeUnit;

import org.freedesktop.dbus.DBusSignal;

final class SignalSequencer<T extends DBusSignal> {

    public static final long TIMEOUT_INFINITE = -1L;

    private final BlockingQueue<T> buffer;
    private final Queue<T> sequencer;

    private long transferDelay = 50L;
    private int transferChunkSize;
    private int dequeueChunkSize;

    private int dequeued = 0;

    public SignalSequencer(final int capacity) {
        this(capacity, 100);
    }

    public SignalSequencer(final int capacity, final int chunkSize) {
        this.buffer = new ArrayBlockingQueue<>(capacity);
        this.sequencer = new PriorityQueue<>(capacity, new SignalComparator<>());

        if (chunkSize < 1) {
            throw new IllegalArgumentException();
        }

        this.transferChunkSize = chunkSize;
        this.dequeueChunkSize = chunkSize > 1 ? chunkSize / 2 : 1;
    }

    public void put(final T item) throws InterruptedException {
        buffer.put(item);
    }

    public T take() throws InterruptedException {
        return poll(TIMEOUT_INFINITE, null);
    }

    public T poll(final long timeout, final TimeUnit unit) throws InterruptedException {
        T head = null;

        if (dequeued < dequeueChunkSize) {
            head = sequencer.poll();
        }
        else {
            dequeued = 0;
        }

        if (head == null) {
            T pending = transfer(timeout, unit, transferChunkSize);

            if (pending != null) {
                head = sequencer.poll();
                sequencer.offer(pending);
            }
            else {
                head = sequencer.poll();
            }
        }
        else {
            dequeued++;
        }

        return head;
    }

    public int drainTo(final Collection<? super T> drain) {
        List<T> buffered = new ArrayList<>(buffer.size());
        buffer.drainTo(buffered);

        sequencer.addAll(buffered);

        int count = 0;
        T head;

        do {
            head = sequencer.poll();

            if (head != null) {
                drain.add(head);
                count++;
            }
        }
        while (head != null);

        return count;
    }

    public void clear() {
        buffer.clear();
        sequencer.clear();
    }

    public int size() {
        return buffer.size() + sequencer.size();
    }

    private T transfer(final long timeout, final TimeUnit unit, int chunkSize) throws InterruptedException {
        boolean delayed = false;

        do {
            T head;

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

                if (!delayed) {
                    Thread.sleep(transferDelay);

                    delayed = true;
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

    public int getTransferChunkSize() {
        return transferChunkSize;
    }

    public void setTransferChunkSize(final int transferChunkSize) {
        if (transferChunkSize < 0) {
            throw new IllegalArgumentException();
        }

        this.transferChunkSize = transferChunkSize;
    }

    static final class SignalComparator<T extends DBusSignal> implements Comparator<T> {

        @Override
        public int compare(final T s1, final T s2) {
            if (s1 == null) {
                return Integer.MAX_VALUE;
            }
            else if (s2 == null) {
                return Integer.MIN_VALUE;
            }
            else {
                return Long.compare(s1.getSerial(), s2.getSerial());
            }
        }

    }

}
