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

package org.systemd.types;

// TODO Rename 'prefixed' variable to match its real purpose
// http://systemd-devel.freedesktop.narkive.com/6xnzKsRp/patch-export-selinuxcontext-on-the-bus-as-a-structure
public class SELinuxContext {

    private final boolean prefixed;
    private final String userData;

    public SELinuxContext(final Object[] array) {
        this.prefixed = (boolean) array[0];
        this.userData = String.valueOf(array[1]);
    }

    public boolean isPrefixed() {
        return prefixed;
    }

    public String getUserData() {
        return userData;
    }

    @Override
    public String toString() {
        return String.format("SELinuxContext [prefixed=%s, userData=%s]", prefixed, userData);
    }

}
