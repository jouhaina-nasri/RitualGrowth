package com.yoterra.hippo.exceptions;

public class UnexpectedAccountStatusException extends RuntimeException {

	private static final long serialVersionUID = -6722111579205938059L;

	public UnexpectedAccountStatusException() {
		super();
	}

	public UnexpectedAccountStatusException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	public UnexpectedAccountStatusException(String message, Throwable cause) {
		super(message, cause);
	}

	public UnexpectedAccountStatusException(String message) {
		super(message);
	}

	public UnexpectedAccountStatusException(Throwable cause) {
		super(cause);
	}

}
