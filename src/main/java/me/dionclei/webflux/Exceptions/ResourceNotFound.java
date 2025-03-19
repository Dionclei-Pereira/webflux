package me.dionclei.webflux.Exceptions;

public class ResourceNotFound extends RuntimeException {
	
	public ResourceNotFound(String message) {
		super(message);
	}
	
}
