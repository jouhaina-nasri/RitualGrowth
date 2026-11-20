package com.yoterra.hippo.exceptions.verification;

public class TokenExpiredException extends VerificationException {

	private static final long serialVersionUID = -2938666268833763926L;

	public TokenExpiredException() {
		super();
	}

	public TokenExpiredException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	public TokenExpiredException(String message, Throwable cause) {
		super(message, cause);
	}

	public TokenExpiredException(String message) {
		super(message);
	}

	public TokenExpiredException(Throwable cause) {
		super(cause);
	}
}
