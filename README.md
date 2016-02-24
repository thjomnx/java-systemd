# java-systemd

Java access to systemd via D-Bus. Java D-Bus bindings (dbus-java) are required.

This library shall:
- Provide Java access to systemd interfaces via D-Bus (Manager, Unit, Service, Target, etc.)
- Provide adapters for the internals of the dbus-java API in order to
    - ease the access to systemds methods and properties
    - omit automatic (and low-performance) reflection stuff being done in dbus-java
    - provide observer patterns for objects along D-Bus signalling

## Usage

```java
import org.systemd.Manager;
import org.systemd.Service;
import org.systemd.Systemd;

Systemd systemd = new Systemd();

try {
    systemd.connect();

    Manager manager = systemd.getManager();

    System.out.println("== Manager ==");
    System.out.println("Architecture: " + manager.getArchitecture());
    System.out.println("Environment: " + manager.getEnvironment());
    System.out.println("System state: " + manager.getSystemState());

    System.out.println();

    Service avahi = manager.getService("avahi-daemon");

    System.out.println("== Service 'avahi-daemon' ==");
    System.out.println("Names: " + avahi.getNames());
    System.out.println("PID: " + avahi.getMainPID());
}
catch (final Exception e) {
    e.printStackTrace();
}
finally {
    systemd.disconnect();

    System.exit(0);
}
```

## Playground

Lookout for class org.systemd.Playground and run its main method to test some simple stuff.

## License

LGPL v2 and AFL v2.1 - just like dbus-java.

## Disclaimer

These are no "official" Java bindings for systemd via D-Bus. I only work on it if the following requisites are met:
- The kid is sleeping
- My wife is hacking on her medical research topics
- No beers in the fridge

This project is currently in some sort of prototyping phase. Be aware, things may change.
