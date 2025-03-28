package me.dionclei.webflux.controllers;

import java.net.Authenticator;
import java.util.UUID;

import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.ReactiveAuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import me.dionclei.webflux.documents.User;
import me.dionclei.webflux.dto.LoginRequest;
import me.dionclei.webflux.dto.RegisterRequest;
import me.dionclei.webflux.dto.TokenResponse;
import me.dionclei.webflux.dto.UserDTO;
import me.dionclei.webflux.services.TokenService;
import me.dionclei.webflux.services.UserService;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/auth")
public class AuthController {
	
	private UserService userService;
	private PasswordEncoder encoder;
	private TokenService tokenService;
	
	public AuthController(UserService userService, PasswordEncoder encoder, TokenService tokenService) {
		this.userService = userService;
		this.encoder = encoder;
		this.tokenService = tokenService;
	}
	
	@PostMapping("/register")
	public Mono<ResponseEntity<Mono<UserDTO>>> register(RegisterRequest dto) {
		User u = new User(UUID.randomUUID().toString(), dto.name(), dto.email(), dto.password(), null, null);
		var savedUser = userService.save(u);
		return Mono.just(ResponseEntity.ok(savedUser));
	}
	
	@PostMapping("/login")
	public Mono<ResponseEntity<? extends Object>> login(@RequestBody LoginRequest dto) {
		var user = userService.findByEmail(dto.email());
		return user.map(u -> {
			if(encoder.matches(dto.password(), u.getPassword())) {
				var token = new TokenResponse(tokenService.generateToken(dto.email()));
				return ResponseEntity.ok().body(token);
			}
			return ResponseEntity.badRequest().build();
		}).switchIfEmpty(Mono.just(ResponseEntity.notFound().build()));
	}
}
