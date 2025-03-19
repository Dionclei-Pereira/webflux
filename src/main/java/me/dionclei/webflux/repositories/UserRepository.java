package me.dionclei.webflux.repositories;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

import me.dionclei.webflux.documents.User;
import reactor.core.publisher.Flux;

public interface UserRepository extends ReactiveMongoRepository<User, String>{

}
