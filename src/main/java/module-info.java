module de.thjom.java.systemd {
	requires transitive org.freedesktop.dbus;
	requires transitive java.xml.bind;
	exports de.thjom.java.systemd;
	exports de.thjom.java.systemd.features;
	exports de.thjom.java.systemd.interfaces;
	exports de.thjom.java.systemd.types;
	opens de.thjom.java.systemd.types to org.freedesktop.dbus;
}
