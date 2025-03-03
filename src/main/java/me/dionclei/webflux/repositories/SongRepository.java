package me.dionclei.webflux.repositories;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

import me.dionclei.webflux.documents.Song;
import me.dionclei.webflux.enums.Gender;
import reactor.core.publisher.Flux;

public interface SongRepository extends ReactiveMongoRepository<Song, String>{
	
	Flux<Song> findByGendersContaining(Gender gender);
	
}
