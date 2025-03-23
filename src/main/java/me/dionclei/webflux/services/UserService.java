package me.dionclei.webflux.services;

import java.util.Set;

import me.dionclei.webflux.documents.Song;
import me.dionclei.webflux.documents.User;
import me.dionclei.webflux.dto.UserDTO;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface UserService {
	
	Mono<UserDTO> save(User user);
	
	Mono<UserDTO> findById(String id);
	
	Flux<UserDTO> findAll();
	
	Mono<Set<Song>> getFavoriteSongsByUserId(String id);
	
	Flux<Song> addSongToFavorites(String userId, String songId);
	
	Flux<Song> removeSongFromFavorites(String userId, String songId); 
}
