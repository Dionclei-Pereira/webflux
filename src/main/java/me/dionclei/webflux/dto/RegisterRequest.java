package me.dionclei.webflux.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record RegisterRequest(
		
		@NotBlank(message = "Name is required")
		@Size(min = 3, max = 25, message = "Name must be between 3 and 25")
		String name,
		
		@Size(min = 3, max = 25, message = "Email must be between 3 and 25")
		@NotBlank(message = "Email is required")
		String email,
		
		@Size(min = 8, max = 18, message = "Password must be between 8 and 18")
		@NotBlank(message = "Password is required")
		String password) {

}
