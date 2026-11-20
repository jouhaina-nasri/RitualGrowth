package com.yoterra.hippo.exceptions.verification;

public class VerificationException extends Exception {

	private static final long serialVersionUID = 28224022576924249L;

	public VerificationException() {
		super();
	}

	public VerificationException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	public VerificationException(String message, Throwable cause) {
		super(message, cause);
	}

	public VerificationException(String message) {
		super(message);
	}

	public VerificationException(Throwable cause) {
		super(cause);
	}
}
