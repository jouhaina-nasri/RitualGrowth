package com.yoterra.hippo.exceptions;

public class MissingEntityException extends RuntimeException {

	private static final long serialVersionUID = -2715046930497102799L;

	public MissingEntityException() {
		super();
	}

	public MissingEntityException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	public MissingEntityException(String message, Throwable cause) {
		super(message, cause);
	}

	public MissingEntityException(String message) {
		super(message);
	}

	public MissingEntityException(Throwable cause) {
		super(cause);
	}

}
