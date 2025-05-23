package me.dionclei.webflux.services;

import me.dionclei.webflux.documents.Playlist;
import me.dionclei.webflux.documents.Song;
import me.dionclei.webflux.enums.Genre;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface PlaylistService {
	
	Flux<Playlist> findAll();
	
	Mono<Playlist> findById(String id);
	
	Mono<Playlist> save(Playlist playlist);
	
	Flux<Playlist> findByGenre(Genre genre);
	
	Flux<Song> getSongsFromPlaylist(String id);
	
	Mono<Playlist> addSong(String playlistId, String songId);
	
}
