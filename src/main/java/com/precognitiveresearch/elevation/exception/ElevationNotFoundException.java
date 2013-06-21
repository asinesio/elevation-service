package com.precognitiveresearch.elevation.exception;

public class ElevationNotFoundException extends Exception {

	private static final long serialVersionUID = 8643548614921560255L;
	
	public ElevationNotFoundException() {
		super();
	}

	public ElevationNotFoundException(String message, Throwable cause) {
		super(message, cause);
	}

	public ElevationNotFoundException(String message) {
		super(message);
	}

	public ElevationNotFoundException(Throwable cause) {
		super(cause);
	}
}
