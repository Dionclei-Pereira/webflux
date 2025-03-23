package me.dionclei.webflux.routers;

import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;

import me.dionclei.webflux.Exceptions.ResourceNotFound;
import me.dionclei.webflux.documents.User;
import me.dionclei.webflux.dto.UserDTO;
import me.dionclei.webflux.services.UserService;
import reactor.core.publisher.Mono;
import static org.springframework.web.reactive.function.server.ServerResponse.ok;
import static org.springframework.web.reactive.function.server.ServerResponse.notFound;
import static org.springframework.web.reactive.function.BodyInserters.fromPublisher;

import org.springframework.http.MediaType;

@Component
public class UserHandler {
	
	private UserService service;
	
	public UserHandler(UserService service) {
		this.service = service;
	}
	
	public Mono<ServerResponse> findAll(ServerRequest request) {
		return ok().contentType(MediaType.APPLICATION_JSON).body(service.findAll(), UserDTO.class);
	}
	
	public Mono<ServerResponse> findById(ServerRequest request) {
		String id = request.pathVariable("id");
		return service.findById(id).flatMap(user -> ok().contentType(MediaType.APPLICATION_JSON).bodyValue(user))
				.switchIfEmpty(Mono.error(new ResourceNotFound("User not found")))
				.onErrorResume(e -> notFound().build());
	}
	
	public Mono<ServerResponse> save(ServerRequest request) {
		var user = request.bodyToMono(User.class);
		return ok().contentType(MediaType.APPLICATION_JSON).body(fromPublisher(user.flatMap(service::save), UserDTO.class));
	}
	

	
}
