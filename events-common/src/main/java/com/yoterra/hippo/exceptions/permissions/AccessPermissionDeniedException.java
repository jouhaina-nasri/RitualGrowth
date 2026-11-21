package com.yoterra.hippo.exceptions.permissions;

public class AccessPermissionDeniedException extends PermissionException {
	
	private static final long serialVersionUID = -573681307735975012L;

	public AccessPermissionDeniedException() {
		super();
	}

	public AccessPermissionDeniedException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	public AccessPermissionDeniedException(String message, Throwable cause) {
		super(message, cause);
	}

	public AccessPermissionDeniedException(String message) {
		super(message);
	}

	public AccessPermissionDeniedException(Throwable cause) {
		super(cause);
	}

}
