# Changelog

All notable changes to this project will be documented in this file.

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
