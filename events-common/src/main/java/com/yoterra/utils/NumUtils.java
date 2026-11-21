package com.yoterra.utils;

public final class NumUtils {

	private NumUtils() {}

	/**
	 * Parses a long, returning {@code null} for a null or blank value.
	 *
	 * @throws NumberFormatException if the value is not a valid long
	 */
	public static Long toLong(String value) {
		if (value == null || value.trim().isEmpty())
			return null;
		return Long.valueOf(value.trim());
	}
}
