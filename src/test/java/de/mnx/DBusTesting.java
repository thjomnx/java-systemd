
package de.mnx;

import org.freedesktop.dbus.exceptions.DBusException;

import de.mnx.java.systemd.Systemd;
import de.mnx.java.systemd.types.UnitFileStatus;

public class DBusTesting {

    public static void main(String[] args) {
        try {
            System.out.println(Systemd.bus().getVersion());
            System.out.println(Systemd.bus().getArchitecture());
            
            UnitFileStatus[] unitFiles = Systemd.bus().listUnitFiles();
            
            for (UnitFileStatus unitFile : unitFiles) {
                System.out.println(unitFile.getFile() + " :: " + unitFile.getStatus());
            }
        }
        catch (final DBusException e) {
            e.printStackTrace();
        }
        
        System.exit(0);
    }

}
