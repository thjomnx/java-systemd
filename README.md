[![Build Status](https://travis-ci.org/thjomnx/java-systemd.svg?branch=master)](https://travis-ci.org/thjomnx/java-systemd)
[![Sonarqube quality gate](https://sonarqube.com/api/badges/gate?key=de.thjom.java.systemd:java-systemd)](https://sonarqube.com/dashboard/index?id=de.thjom.java.systemd:java-systemd)
[![License: LGPL v2.1](https://img.shields.io/badge/License-LGPL%20v2-blue.svg)](https://www.gnu.org/licenses/old-licenses/lgpl-2.1)
[![License: AFL 3.0](https://img.shields.io/badge/License-AFL%203.0-yellow.svg)](https://opensource.org/licenses/AFL-3.0)

# java-systemd

Java access to systemd via D-Bus. Java D-Bus bindings (dbus-java) are required.

This library shall:
- Provide Java access to systemd interfaces via D-Bus (Manager, Unit, Service, Target, etc.)
- Provide adapters for the internals of the dbus-java API in order to
    - ease the access to systemds methods and properties
    - omit automatic (and low-performance) reflection stuff being done in dbus-java
    - provide observer patterns for objects along D-Bus signalling

## Usage

Documentation and instructions how to use this library can be found in the [Wiki](https://github.com/thjomnx/java-systemd/wiki).

## Testing

Unit tests are based on [TestNG](http://testng.org/doc/index.html) and [Mockito](http://mockito.org/). Test methods which require a deployed systemd environment are grouped
in TestNG group "requireSystemd". There are two TestNG suites available:
- src/test/resources/suites/**auto-systemd.xml** (executes all tests)
- src/test/resources/suites/**auto.xml** (excludes group "requireSystemd")

## License

LGPL v2.1 and AFL 3.0.

## Disclaimer

These are no "official" Java bindings for systemd via D-Bus. I only work on it if the following requisites are met:
- The kid is sleeping
- My wife is hacking on her medical research topics
- No beers in the fridge

This project is currently in some sort of prototyping phase. Be aware, things may change.
