package me.dionclei.webflux.dto;

import java.util.Set;

import me.dionclei.webflux.enums.Genre;

public record SongCreateRequest(
		String name, 
		String authorId, 
		String playlistId,
		String link, 
		Set<Genre> genres
		) {

}
