package me.dionclei.webflux.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import me.dionclei.webflux.documents.Song;
import me.dionclei.webflux.documents.User;
import me.dionclei.webflux.dto.UserDTO;
import me.dionclei.webflux.repositories.SongRepository;
import me.dionclei.webflux.repositories.UserRepository;
import me.dionclei.webflux.services.UserService;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	private UserRepository repository;
	@Autowired
	private SongRepository songRepository;
	
	public Mono<UserDTO> save(User user) {
		return repository.save(user).map(u -> u.toDTO()).subscribeOn(Schedulers.boundedElastic());
	}

	public Mono<UserDTO> findById(String id) {
		return repository.findById(id).map(u -> u.toDTO()).subscribeOn(Schedulers.boundedElastic());
	}

	public Flux<UserDTO> findAll() {
		return repository.findAll().map(u -> u.toDTO()).subscribeOn(Schedulers.boundedElastic());
	}

	public Flux<Song> addSongToFavorites(String userId, String songId) {
	    return songRepository.findById(songId)
	            .switchIfEmpty(Mono.error(new IllegalArgumentException("Song not found")))
	            .flatMap(song -> repository.findById(userId)
	                    .map(user -> {
	                    	var aux = user.getFavoriteSongs();
	                    	aux.add(song);
	                    	repository.save(user);
	                        return user;
	                    })
	                    .flatMap(repository::save).map(u -> u.getFavoriteSongs())
	            ).flatMapMany(Flux::fromIterable)
	            .subscribeOn(Schedulers.boundedElastic());
	}

}
