package com.yoterra.hippo.exceptions.verification;

public class NotVerifiedException extends VerificationException {

	private static final long serialVersionUID = -3938872234337419880L;

	public NotVerifiedException() {
		super();
	}

	public NotVerifiedException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	public NotVerifiedException(String message, Throwable cause) {
		super(message, cause);
	}

	public NotVerifiedException(String message) {
		super(message);
	}

	public NotVerifiedException(Throwable cause) {
		super(cause);
	}
}
