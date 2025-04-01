package me.dionclei.webflux.dto;

import java.util.Set;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import me.dionclei.webflux.enums.Genre;

public record PlaylistCreateRequest(
		
		@NotBlank(message = "Name is required")
		@Size(min = 3, max = 20, message = "Name must be between 3 and 20")
		String name,
		@NotEmpty(message = "Genres is required")
		Set<Genre> genres) {

}
