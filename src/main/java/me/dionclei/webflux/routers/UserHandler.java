package me.dionclei.webflux.routers;

import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;

import me.dionclei.webflux.documents.User;
import me.dionclei.webflux.dto.UserDTO;
import me.dionclei.webflux.exceptions.ResourceNotFound;
import me.dionclei.webflux.services.UserService;
import reactor.core.publisher.Mono;

import static org.springframework.web.reactive.function.server.ServerResponse.ok;
import static org.springframework.web.reactive.function.server.ServerResponse.notFound;
import static org.springframework.web.reactive.function.BodyInserters.fromPublisher;

import org.springframework.http.HttpStatus;
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
	
	public Mono<ServerResponse> findFavoriteSongs(ServerRequest request) {
		String id = request.pathVariable("id");
		return service.getFavoriteSongsByUserId(id)
				.flatMap(songs -> ok().bodyValue(songs))
				.switchIfEmpty(Mono.error(new ResourceNotFound("User not found")))
				.onErrorResume(e -> notFound().build());
	}
	
	public Mono<ServerResponse> removeSongToFavorites(ServerRequest request) {
	    String userId = request.pathVariable("userId");
	    String songId = request.pathVariable("songId");
	    
	    return service.removeSongFromFavorites(userId, songId)
	            .collectList()
	            .flatMap(songs -> request.principal()
	            		.flatMap(principal -> 
	            			service.findByEmail(principal.getName()).flatMap(user ->
	            				service.findById(userId).flatMap(targetUser -> {
		            				if(!principal.getName().equals(targetUser.userEmail()) && user.getAuthorities()
		            						.stream().noneMatch(auth -> auth.getAuthority().equals("ROLE_ADMIN"))) {
		            					return ServerResponse.status(HttpStatus.FORBIDDEN).build();
		            				}
			            			return ServerResponse.ok()
			        	                    .contentType(MediaType.APPLICATION_JSON)
			        	                    .bodyValue(songs);
	            				})
	            			)
	            		 )
	            )
	            .onErrorResume(IllegalArgumentException.class, e -> 
	                ServerResponse.badRequest()
	                    .contentType(MediaType.APPLICATION_JSON)
	                    .bodyValue("Song or Playlist not found")
	            )
	            .switchIfEmpty(
	                ServerResponse.notFound().build()
	            );
	}
	
	public Mono<ServerResponse> addSongToFavorites(ServerRequest request) {
	    String userId = request.pathVariable("userId");
	    String songId = request.pathVariable("songId");
	    return service.addSongToFavorites(userId, songId)
	            .collectList()
	            .flatMap(songs -> request.principal()
	            		.flatMap(principal -> 
	            			service.findByEmail(principal.getName())
	            				.flatMap(user -> 
	            					service.findById(userId).flatMap(targetUser -> {
	            						if(!principal.getName().equals(targetUser.userEmail()) && 
	            								user.getAuthorities().stream().noneMatch(auth -> auth.getAuthority().equals("USER_ADMIN"))) {
	            							return ServerResponse.status(HttpStatus.FORBIDDEN).build();
	            						}
		            					return ServerResponse.ok()
		    	        	                    .contentType(MediaType.APPLICATION_JSON)
		    	        	                    .bodyValue(songs);
	            					})
	            				)
	            		)
	            )
	            .onErrorResume(IllegalArgumentException.class, e -> 
	                ServerResponse.badRequest()
	                    .contentType(MediaType.APPLICATION_JSON)
	                    .bodyValue("Song or Playlist not found")
	            )
	            .switchIfEmpty(
	                ServerResponse.notFound().build()
	            );
	}
	
}
