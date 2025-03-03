package me.dionclei.webflux.repositories;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

import me.dionclei.webflux.documents.Playlist;
import me.dionclei.webflux.enums.Gender;
import reactor.core.publisher.Flux;

public interface PlaylistRepository extends ReactiveMongoRepository<Playlist, String>{
	
	Flux<Playlist> findByGendersContaining(Gender gender);
	
}
