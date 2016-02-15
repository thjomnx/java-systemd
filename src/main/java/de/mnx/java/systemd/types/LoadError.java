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

public class LoadError {

	private final String id;
	private final String message;

	public LoadError(final Object[] array) {
		this.id = String.valueOf(array[0]);
		this.message = String.valueOf(array[1]);
	}

	public String getId() {
		return id;
	}

	public String getMessage() {
		return message;
	}

	@Override
	public String toString() {
		return String.format("LoadError [id=%s, message=%s]", id, message);
	}

}
