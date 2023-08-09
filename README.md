[![Build Status](https://github.com/thjomnx/java-systemd/actions/workflows/ci.yml/badge.svg)](https://github.com/thjomnx/java-systemd/actions)
[![Quality Gate Status](https://sonarcloud.io/api/project_badges/measure?project=thjomnx_java-systemd&metric=alert_status)](https://sonarcloud.io/summary/new_code?id=thjomnx_java-systemd)
[![Maintainability Rating](https://sonarcloud.io/api/project_badges/measure?project=thjomnx_java-systemd&metric=sqale_rating)](https://sonarcloud.io/summary/new_code?id=thjomnx_java-systemd)
[![Coverage](https://sonarcloud.io/api/project_badges/measure?project=thjomnx_java-systemd&metric=coverage)](https://sonarcloud.io/summary/new_code?id=thjomnx_java-systemd)
[![Lines of Code](https://sonarcloud.io/api/project_badges/measure?project=thjomnx_java-systemd&metric=ncloc)](https://sonarcloud.io/summary/new_code?id=thjomnx_java-systemd)
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

## Versions

There are two major versions available on the [central repository](https://search.maven.org/artifact/com.github.thjomnx/java-systemd):

- **2.x** (depends on dbus-java version 3)
  - 2.1.x and later deploy this library as a Java module in the JPMS
- **1.x** (depends on dbus-java version 2)

## Usage

Documentation and instructions how to use this library can be found in the [Wiki](https://github.com/thjomnx/java-systemd/wiki).

## Testing

Unit tests are based on [TestNG](http://testng.org/doc/index.html) and [Mockito](http://mockito.org/). Test methods which require
a deployed systemd environment are grouped in TestNG group "requireSystemd". There are two TestNG suites available:

- src/test/resources/suites/**auto-systemd.xml** (executes all tests)
- src/test/resources/suites/**auto.xml** (excludes group "requireSystemd")

## Contributions

- Thanks to [brett-smith](https://github.com/brett-smith) for initial migration work to support dbus-java version 3 and for JPMS support.

## License

LGPL v2.1 and AFL 3.0.

## Disclaimer

These are no "official" Java bindings for systemd via D-Bus. I only work on it if the following requisites are met:

- The kids are sleeping
- My wife is hacking on medical research
- No beers in the fridge
