package com.yoterra.commons;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/** Marks unfinished code paths: {@link #warn} logs, {@link #err} fails fast. */
public final class FIXME {

	private static final Logger LOG = LoggerFactory.getLogger(FIXME.class);

	private FIXME() {}

	public static void warn(String message) {
		LOG.warn("FIXME: {}", message);
	}

	public static <T> T err(String message) {
		throw new UnsupportedOperationException(message);
	}
}
