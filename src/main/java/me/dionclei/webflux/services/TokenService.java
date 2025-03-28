package me.dionclei.webflux.services;

public interface TokenService {
	
	String generateToken(String email);
	
	String validateToken(String token);
	
}
