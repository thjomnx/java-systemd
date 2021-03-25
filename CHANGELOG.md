# Changelog

All notable changes to this project will be documented in this file.

## [2.1.0] - 2021-03-25

### Added

- Java modularization (`module-info.java`).
  - Thanks for contribution of [brett-smith](https://github.com/thjomnx/java-systemd/commit/5650f9f67c8389ba2cd7b9321cf4453baa7c764e).
  - [dbus-java](https://github.com/hypfvieh/dbus-java) is now used in version 3.3.0.

## [2.0.0] - 2021-03-14

The project has been migrated to use [dbus-java](https://github.com/hypfvieh/dbus-java) version 3.2.4.
This introduces some breaking changes on **this project** compared to the previous 1.x stream.
Many thanks for contributions of [brett-smith](https://github.com/thjomnx/java-systemd/commit/da9d79d15b9c30f8ba90bfa378bc8676e7e275fb).

The build environment is now based on JDK 11.

### Added

- New methods and property getters for latest systemd (version 247 as of today).
- Conversion method `Systemd.usecsToDuration(long)`.

### Changed

- `<unit>.Property.getAllNames()` now returns `List<String>` instead of `String[]`.
- Property getters which have returned `Vector<T>` do now return `List<T>` or `Collection<T>`.
- `getSummary()` methods of classes in package `de.thjom.java.systemd.types` have been renamed to `toFormattedString()`.
- Date/Time handling has been migrated to use `java.time.*` package.
- D-Bus connection now configures a single threaded signal handler.

### Removed

- Concept around `addConsumer`/`removeConsumer` methods (just use `addHandler`/`removeHandler` instead).
- Retardation time after D-Bus disconnect (newer dbus-java doesn't seem to be affected by timing issues anymore).
- `Snapshot` unit support (concept has been dropped by systemd some time ago).
- Property getters and methods that have been removed in systemd lately.

### Known issues

- `Unit` interface lacking support for `EnqueueJob` method.
- `Manager` interface still not complete (some D-Bus methods are missing).

## [1.1.0] - 2018-06-18

### Added

- Remaining property getters for `Manager` interface.
- More D-Bus methods for `Manager` interface.
- Remaining property getters for `Unit` interface.
- Remaining property getters for dedicated unit interfaces (`Mount`, `Service`, etc.).
- `RequestStop` signal on `Scope` unit interface.

### Changed

- Property getters and methods have been updated to be aligned with systemd version 238.
- Many property getters have been moved into *feature* interfaces (reduces code redundancies).
- `getAllNames` in `AdapterProperty` proxy now accepts `varargs` argument.
- COPYING text formatting corrected and LGPL set to be explicitly 2.1.

### Removed

- Property getters and methods that have been removed in systemd lately.

### Known issues

- `Manager` interface still not complete (some D-Bus methods are missing).

## [1.0.0] - 2017-06-07

This marks the initial release.
