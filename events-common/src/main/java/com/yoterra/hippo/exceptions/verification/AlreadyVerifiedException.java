package com.yoterra.hippo.exceptions.verification;

public class AlreadyVerifiedException extends VerificationException {

	private static final long serialVersionUID = -7700489687668567820L;

	public AlreadyVerifiedException() {
		super();
	}

	public AlreadyVerifiedException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	public AlreadyVerifiedException(String message, Throwable cause) {
		super(message, cause);
	}

	public AlreadyVerifiedException(String message) {
		super(message);
	}

	public AlreadyVerifiedException(Throwable cause) {
		super(cause);
	}
}
