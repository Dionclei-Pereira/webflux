package me.dionclei.webflux.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import me.dionclei.webflux.documents.User;
import me.dionclei.webflux.dto.UserDTO;
import me.dionclei.webflux.repositories.UserRepository;
import me.dionclei.webflux.services.UserService;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	private UserRepository repository;
	
	public Mono<UserDTO> save(User user) {
		return repository.save(user).map(u -> u.toDTO());
	}

	public Mono<UserDTO> findById(String id) {
		return repository.findById(id).map(u -> u.toDTO());
	}

	public Flux<UserDTO> findAll() {
		return repository.findAll().map(u -> u.toDTO());
	}

}
