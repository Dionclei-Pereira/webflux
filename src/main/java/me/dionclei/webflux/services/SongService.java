package me.dionclei.webflux.services;

import me.dionclei.webflux.documents.Song;
import me.dionclei.webflux.enums.Genre;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface SongService {
	
	Mono<Song> save(Song song);
	
	Mono<Song> findById(String id);
	
	Flux<Song> findAll();
	
	Flux<Song> findByGenre(Genre genre);
	
}
