package com.intellect.serverstatuschecker.exception;

public class ServerDetailsBusinessException extends Exception {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String message = "";

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public ServerDetailsBusinessException() {
		super();
	}

	public ServerDetailsBusinessException(String message) {
		super(message);
		this.message = message;
	}

	public ServerDetailsBusinessException(Throwable message) {
		super(message);
	}

	@Override
	public String toString() {
		return message;

	}

}