package com.precognitiveresearch.elevation.exception;

public class UnexpectedElevationQueryException extends RuntimeException {

	private static final long serialVersionUID = -3892522663997311009L;

	public UnexpectedElevationQueryException() {
		super();
	}

	public UnexpectedElevationQueryException(String message) {
		super(message);
	}

	public UnexpectedElevationQueryException(Throwable cause) {
		super(cause);
	}

	public UnexpectedElevationQueryException(String message, Throwable cause) {
		super(message, cause);
	}

}
