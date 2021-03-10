[![Build Status](https://github.com/thjomnx/java-systemd/actions/workflows/ci.yml/badge.svg)](https://github.com/thjomnx/java-systemd/actions)
[![Quality Gate Status](https://sonarcloud.io/api/project_badges/measure?project=com.github.thjomnx%3Ajava-systemd&metric=alert_status)](https://sonarcloud.io/dashboard?id=com.github.thjomnx%3Ajava-systemd)
[![Maintainability Rating](https://sonarcloud.io/api/project_badges/measure?project=com.github.thjomnx%3Ajava-systemd&metric=sqale_rating)](https://sonarcloud.io/dashboard?id=com.github.thjomnx%3Ajava-systemd)
[![Coverage](https://sonarcloud.io/api/project_badges/measure?project=com.github.thjomnx%3Ajava-systemd&metric=coverage)](https://sonarcloud.io/dashboard?id=com.github.thjomnx%3Ajava-systemd)
[![Lines of Code](https://sonarcloud.io/api/project_badges/measure?project=com.github.thjomnx%3Ajava-systemd&metric=ncloc)](https://sonarcloud.io/dashboard?id=com.github.thjomnx%3Ajava-systemd)
[![Maven Central](https://img.shields.io/maven-central/v/com.github.thjomnx/java-systemd.svg)](https://maven-badges.herokuapp.com/maven-central/com.github.thjomnx/java-systemd)
[![License: LGPL v2.1](https://img.shields.io/badge/license-LGPL%20v2-blue.svg)](https://www.gnu.org/licenses/old-licenses/lgpl-2.1)
[![License: AFL 3.0](https://img.shields.io/badge/license-AFL%203.0-yellow.svg)](https://opensource.org/licenses/AFL-3.0)

# java-systemd

Java access to systemd via D-Bus. Java D-Bus bindings (dbus-java) are required.

This library shall:
- Provide Java access to systemd interfaces via D-Bus (Manager, Unit, Service, Target, etc.)
- Provide adapters for the internals of the dbus-java API in order to
    - ease the access to systemds methods and properties
    - omit automatic (and low-performance) reflection stuff being done in dbus-java
    - provide observer-like patterns for objects along D-Bus signalling

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
- The kids are sleeping
- My wife is hacking on medical research
- No beers in the fridge
