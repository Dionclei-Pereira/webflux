package me.dionclei.webflux.dto;

import java.util.Set;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import me.dionclei.webflux.enums.Genre;

public record SongCreateRequest(
		
		@NotBlank(message = "Name is required")
		@Size(min = 3, max = 20, message = "Name must be between 3 and 20")
		String name,
		
		@NotBlank(message = "AuthorId is required")
		String authorId,
		
		@NotBlank(message = "PlaylistId is required")
		String playlistId,
		
		@NotBlank(message = "Link is required")
		String link,
		
		@NotEmpty(message = "Genres is required")
		Set<Genre> genres
		) {

}
