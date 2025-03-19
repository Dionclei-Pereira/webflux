package me.dionclei.webflux.dto;

import java.util.Set;

import me.dionclei.webflux.enums.Genre;

public record PlaylistCreateRequest( 
		String name, 
		String authorId,
		Set<Genre> genres, 
		Set<String> songs) {

}
