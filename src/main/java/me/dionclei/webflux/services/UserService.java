package me.dionclei.webflux.services;

import me.dionclei.webflux.documents.User;
import me.dionclei.webflux.dto.UserDTO;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface UserService {
	
	Mono<UserDTO> save(User user);
	
	Mono<UserDTO> findById(String id);
	
	Flux<UserDTO> findAll();

}
