package me.dionclei.webflux.repositories;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

import me.dionclei.webflux.documents.Song;

public interface SongRepository extends ReactiveMongoRepository<Song, String>{

}
