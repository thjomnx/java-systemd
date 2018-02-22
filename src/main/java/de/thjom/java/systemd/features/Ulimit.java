/*
 * Java-systemd implementation
 * Copyright (c) 2016 Markus Enax
 *
 * This program is free software; you can redistribute it and/or modify it under
 * the terms of either the GNU Lesser General default License Version 2 or the
 * Academic Free Licence Version 3.0.
 *
 * Full licence texts are included in the COPYING file with this program.
 */

package de.thjom.java.systemd.features;

import java.math.BigInteger;

import de.thjom.java.systemd.InterfaceAdapter;
import de.thjom.java.systemd.Properties;

public interface Ulimit {

    static class Property extends InterfaceAdapter.AdapterProperty {

        public static final String LIMIT_AS = "LimitAS";
        public static final String LIMIT_AS_SOFT = "LimitASSoft";
        public static final String LIMIT_CORE = "LimitCORE";
        public static final String LIMIT_CORE_SOFT = "LimitCORESoft";
        public static final String LIMIT_CPU = "LimitCPU";
        public static final String LIMIT_CPU_SOFT = "LimitCPUSoft";
        public static final String LIMIT_DATA = "LimitDATA";
        public static final String LIMIT_DATA_SOFT = "LimitDATASoft";
        public static final String LIMIT_FSIZE = "LimitFSIZE";
        public static final String LIMIT_FSIZE_SOFT = "LimitFSIZESoft";
        public static final String LIMIT_LOCKS = "LimitLOCKS";
        public static final String LIMIT_LOCKS_SOFT = "LimitLOCKSSoft";
        public static final String LIMIT_MEMLOCK = "LimitMEMLOCK";
        public static final String LIMIT_MEMLOCK_SOFT = "LimitMEMLOCKSoft";
        public static final String LIMIT_MSGQUEUE = "LimitMSGQUEUE";
        public static final String LIMIT_MSGQUEUE_SOFT = "LimitMSGQUEUESoft";
        public static final String LIMIT_NICE = "LimitNICE";
        public static final String LIMIT_NICE_SOFT = "LimitNICESoft";
        public static final String LIMIT_NOFILE = "LimitNOFILE";
        public static final String LIMIT_NOFILE_SOFT = "LimitNOFILESoft";
        public static final String LIMIT_NPROC = "LimitNPROC";
        public static final String LIMIT_NPROC_SOFT = "LimitNPROCSoft";
        public static final String LIMIT_RSS = "LimitRSS";
        public static final String LIMIT_RSS_SOFT = "LimitRSSSoft";
        public static final String LIMIT_RTPRIO = "LimitRTPRIO";
        public static final String LIMIT_RTPRIO_SOFT = "LimitRTPRIOSoft";
        public static final String LIMIT_RTTIME = "LimitRTTIME";
        public static final String LIMIT_RTTIME_SOFT = "LimitRTTIMESoft";
        public static final String LIMIT_SIGPENDING = "LimitSIGPENDING";
        public static final String LIMIT_SIGPENDING_SOFT = "LimitSIGPENDINGSoft";
        public static final String LIMIT_STACK = "LimitSTACK";
        public static final String LIMIT_STACK_SOFT = "LimitSTACKSoft";

        private Property() {
            super();
        }

        public static final String[] getAllNames() {
            return getAllNames(Property.class);
        }

    }

    Properties getProperties();

    default BigInteger getLimitAS() {
        return getProperties().getBigInteger(Property.LIMIT_AS);
    }

    default BigInteger getLimitASSoft() {
        return getProperties().getBigInteger(Property.LIMIT_AS_SOFT);
    }

    default BigInteger getLimitCORE() {
        return getProperties().getBigInteger(Property.LIMIT_CORE);
    }

    default BigInteger getLimitCORESoft() {
        return getProperties().getBigInteger(Property.LIMIT_CORE_SOFT);
    }

    default BigInteger getLimitCPU() {
        return getProperties().getBigInteger(Property.LIMIT_CPU);
    }

    default BigInteger getLimitCPUSoft() {
        return getProperties().getBigInteger(Property.LIMIT_CPU_SOFT);
    }

    default BigInteger getLimitDATA() {
        return getProperties().getBigInteger(Property.LIMIT_DATA);
    }

    default BigInteger getLimitDATASoft() {
        return getProperties().getBigInteger(Property.LIMIT_DATA_SOFT);
    }

    default BigInteger getLimitFSIZE() {
        return getProperties().getBigInteger(Property.LIMIT_FSIZE);
    }

    default BigInteger getLimitFSIZESoft() {
        return getProperties().getBigInteger(Property.LIMIT_FSIZE_SOFT);
    }

    default BigInteger getLimitLOCKS() {
        return getProperties().getBigInteger(Property.LIMIT_LOCKS);
    }

    default BigInteger getLimitLOCKSSoft() {
        return getProperties().getBigInteger(Property.LIMIT_LOCKS_SOFT);
    }

    default BigInteger getLimitMEMLOCK() {
        return getProperties().getBigInteger(Property.LIMIT_MEMLOCK);
    }

    default BigInteger getLimitMEMLOCKSoft() {
        return getProperties().getBigInteger(Property.LIMIT_MEMLOCK_SOFT);
    }

    default BigInteger getLimitMSGQUEUE() {
        return getProperties().getBigInteger(Property.LIMIT_MSGQUEUE);
    }

    default BigInteger getLimitMSGQUEUESoft() {
        return getProperties().getBigInteger(Property.LIMIT_MSGQUEUE_SOFT);
    }

    default BigInteger getLimitNICE() {
        return getProperties().getBigInteger(Property.LIMIT_NICE);
    }

    default BigInteger getLimitNICESoft() {
        return getProperties().getBigInteger(Property.LIMIT_NICE_SOFT);
    }

    default BigInteger getLimitNOFILE() {
        return getProperties().getBigInteger(Property.LIMIT_NOFILE);
    }

    default BigInteger getLimitNOFILESoft() {
        return getProperties().getBigInteger(Property.LIMIT_NOFILE_SOFT);
    }

    default BigInteger getLimitNPROC() {
        return getProperties().getBigInteger(Property.LIMIT_NPROC);
    }

    default BigInteger getLimitNPROCSoft() {
        return getProperties().getBigInteger(Property.LIMIT_NPROC_SOFT);
    }

    default BigInteger getLimitRSS() {
        return getProperties().getBigInteger(Property.LIMIT_RSS);
    }

    default BigInteger getLimitRSSSoft() {
        return getProperties().getBigInteger(Property.LIMIT_RSS_SOFT);
    }

    default BigInteger getLimitRTPRIO() {
        return getProperties().getBigInteger(Property.LIMIT_RTPRIO);
    }

    default BigInteger getLimitRTPRIOSoft() {
        return getProperties().getBigInteger(Property.LIMIT_RTPRIO_SOFT);
    }

    default BigInteger getLimitRTTIME() {
        return getProperties().getBigInteger(Property.LIMIT_RTTIME);
    }

    default BigInteger getLimitRTTIMESoft() {
        return getProperties().getBigInteger(Property.LIMIT_RTTIME_SOFT);
    }

    default BigInteger getLimitSIGPENDING() {
        return getProperties().getBigInteger(Property.LIMIT_SIGPENDING);
    }

    default BigInteger getLimitSIGPENDINGSoft() {
        return getProperties().getBigInteger(Property.LIMIT_SIGPENDING_SOFT);
    }

    default BigInteger getLimitSTACK() {
        return getProperties().getBigInteger(Property.LIMIT_STACK);
    }

    default BigInteger getLimitSTACKSoft() {
        return getProperties().getBigInteger(Property.LIMIT_STACK_SOFT);
    }

}
