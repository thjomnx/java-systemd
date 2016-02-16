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

package de.mnx.java.systemd.types;

import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

public class EnvironmentFile {

    private final String filePath;
    private final boolean field2;   // TODO Find meaning of this boolean field and rename accordingly

    public EnvironmentFile(final Object[] array) {
        this.filePath = String.valueOf(array[0]);
        this.field2 = (boolean) array[1];
    }

    public static List<EnvironmentFile> list(final Vector<Object[]> vector) {
        List<EnvironmentFile> envFiles = new ArrayList<>(vector.size());

        for (Object[] array : vector) {
            EnvironmentFile envFile = new EnvironmentFile(array);

            envFiles.add(envFile);
        }

        return envFiles;
    }

    public String getFilePath() {
        return filePath;
    }

    public boolean isField2() {
        return field2;
    }

    @Override
    public String toString() {
        return String.format("EnvironmentFile [filePath=%s, field2=%s]", filePath, field2);
    }

}
