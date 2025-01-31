package me.dionclei.webflux.repositories;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

import me.dionclei.webflux.documents.Playlist;

public interface PlaylistRepository extends ReactiveMongoRepository<Playlist, String>{

}
