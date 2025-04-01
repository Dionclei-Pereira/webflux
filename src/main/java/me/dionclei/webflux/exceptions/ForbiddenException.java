package me.dionclei.webflux.exceptions;

public class ForbiddenException extends RuntimeException {
	
	private static final long serialVersionUID = 1L;

	public ForbiddenException() {
		super("Forbidden access");
	}

}
