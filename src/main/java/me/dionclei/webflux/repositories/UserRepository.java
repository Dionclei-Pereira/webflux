package me.dionclei.webflux.repositories;

import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.security.core.userdetails.UserDetails;

import me.dionclei.webflux.documents.User;
import reactor.core.publisher.Mono;

public interface UserRepository extends ReactiveMongoRepository<User, String>{
	
	UserDetails findByEmail(String email);
	
	@Query("{ 'email' : ?0 }")
	Mono<User> findUserByEmail(String email);
	
}
