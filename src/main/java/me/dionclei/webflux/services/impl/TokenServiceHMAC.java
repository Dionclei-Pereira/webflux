package me.dionclei.webflux.services.impl;

import java.time.Instant;
import java.time.ZoneOffset;

import org.springframework.stereotype.Service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;

import me.dionclei.webflux.services.TokenService;

@Service
public class TokenServiceHMAC implements TokenService {

	private String key = "MySecretKey";
	
		@Override
		public String generateToken(String email) {
			Algorithm algorithm = Algorithm.HMAC256(key);
			return JWT.create().withIssuer("WebFluxSongs").withSubject(email)
					.withExpiresAt(generateExpiresAt())
					.sign(algorithm);
		}
	
		@Override
		public String validateToken(String token) {
			Algorithm algorithm = Algorithm.HMAC256(key);
			return JWT.require(algorithm).withIssuer("WebFluxSongs")
					.build()
					.verify(token)
					.getSubject();
		}
		
		private Instant generateExpiresAt() {
			return Instant.now().atZone(ZoneOffset.UTC).plusDays(5).toInstant();
		}

}
