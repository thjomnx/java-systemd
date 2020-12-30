package de.thjom.java.systemd.types;

import org.freedesktop.dbus.types.UInt32;

import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

public class DynamicUser {

    private final long uid;
    private final String name;

    public DynamicUser(final Object[] array) {
        this.uid = ((UInt32) array[0]).longValue();
        this.name = String.valueOf(array[1]);
    }

    public static List<DynamicUser> list(final Vector<Object[]> vector) {
        List<DynamicUser> infos = new ArrayList<>(vector.size());

        for (Object[] array : vector) {
            DynamicUser info = new DynamicUser(array);

            infos.add(info);
        }

        return infos;
    }

    public long getUid() {
        return uid;
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return String.format("DynamicUser [uid=%s, name=%s]", uid, name);
    }

}
