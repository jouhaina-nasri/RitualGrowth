package com.yoterra.hippo.exceptions.permissions;

public class PermissionException extends RuntimeException {
	
	private static final long serialVersionUID = -6071698729377550689L;

	public PermissionException() {
		super();
	}

	public PermissionException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	public PermissionException(String message, Throwable cause) {
		super(message, cause);
	}

	public PermissionException(String message) {
		super(message);
	}

	public PermissionException(Throwable cause) {
		super(cause);
	}

}
