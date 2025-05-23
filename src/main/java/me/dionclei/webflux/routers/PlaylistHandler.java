package me.dionclei.webflux.routers;

import static org.springframework.web.reactive.function.server.ServerResponse.notFound;
import static org.springframework.web.reactive.function.server.ServerResponse.ok;

import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.beans.BeanUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;

import me.dionclei.webflux.documents.Playlist;
import me.dionclei.webflux.documents.Song;
import me.dionclei.webflux.dto.PlaylistCreateRequest;
import me.dionclei.webflux.enums.Genre;
import me.dionclei.webflux.exceptions.ResourceNotFound;
import me.dionclei.webflux.services.PlaylistService;
import me.dionclei.webflux.services.UserService;
import reactor.core.publisher.Mono;

@Component
public class PlaylistHandler {
	
	PlaylistService playlistService;
	UserService userService;
	Validator validator;
	
	public PlaylistHandler(PlaylistService playlistService,
	UserService userService,
	Validator validator) {
		this.playlistService = playlistService;
		this.userService = userService;
		this.validator = validator;
	}
	
	public Mono<ServerResponse> findAll(ServerRequest request) {
		var genreStr = request.queryParam("genre");
		if (genreStr.isPresent()) {
			Genre genre = genreStr.map(String::toUpperCase)
            .map(Genre::valueOf)
            .orElse(null);
			return ok().body(playlistService.findByGenre(genre), Playlist.class);
		}
		
		return ok().contentType(MediaType.APPLICATION_JSON).body(playlistService.findAll(), Playlist.class);
	}
	
	public Mono<ServerResponse> addSongToPlaylist(ServerRequest request) {
		String playlistId = request.pathVariable("playlistId");
		String songId = request.pathVariable("songId");
		return request.principal()
				.flatMap(principal -> 
					userService.findByEmail(principal.getName())
						.flatMap(user -> 
							playlistService.findById(playlistId)
								.flatMap(playlist -> {
									if(playlist.getAuthor().equals(user.getName())) {
										return playlistService.addSong(playlistId, songId)
												.flatMap(savedPlaylist -> ok().bodyValue(savedPlaylist));
									}
									return ServerResponse.status(HttpStatus.FORBIDDEN).build();
								}).switchIfEmpty(ServerResponse.notFound().build())
								.onErrorResume(IllegalArgumentException.class, e -> notFound().build())
						).switchIfEmpty(ServerResponse.notFound().build())
				);
	}
	
	public Mono<ServerResponse> getSongsFromPlaylist(ServerRequest request) {
		String id = request.pathVariable("id");
		return playlistService.getSongsFromPlaylist(id).collectList()
				.flatMap(songs -> {
	                if (songs.isEmpty()) {
	                    return Mono.error(new ResourceNotFound("Playlist not Found"));
	                }
	                return ok().body(playlistService.getSongsFromPlaylist(id), Song.class);
				})
				.switchIfEmpty(Mono.error(new ResourceNotFound("Playlist not Found")))
				.onErrorResume(e -> notFound().build());
	}
	
	public Mono<ServerResponse> findById(ServerRequest request) {
		String id = request.pathVariable("id");
		return playlistService.findById(id).flatMap(playlist -> 
				ok().contentType(MediaType.APPLICATION_JSON).bodyValue(playlist))
				.switchIfEmpty(Mono.error(new ResourceNotFound("Playlist not found")))
				.onErrorResume(e -> notFound().build());

	}

	public Mono<ServerResponse> save(ServerRequest request) {
	    return request.bodyToMono(PlaylistCreateRequest.class)
	        .flatMap(playlistDto -> {
	            Errors errors = new BeanPropertyBindingResult(playlistDto, "playlistCreateRequest");
	            validator.validate(playlistDto, errors);
	            if (errors.hasErrors()) {
	                var errorMessages = errors.getFieldErrors().stream()
	                    .map(err -> err.getField() + ": " + err.getDefaultMessage())
	                    .collect(Collectors.toList());
	                return ServerResponse.badRequest().bodyValue(errorMessages);
	            }
	            return request.principal().flatMap(principal -> 
	                userService.findByEmail(principal.getName())
	                    .flatMap(user -> {
	                        var playlist = new Playlist();
	                        playlist.setId(UUID.randomUUID().toString());
	                        BeanUtils.copyProperties(playlistDto, playlist);
	                        playlist.setAuthor(user.getName());
	                        playlist.setAuthorEmail(user.getEmail());
	                        return playlistService.save(playlist)
	                            .flatMap(savedPlaylist -> 
	                                userService.addPlaylist(user.getId(), savedPlaylist.getId())
	                                    .then(ServerResponse.ok()
	                                        .contentType(MediaType.APPLICATION_JSON)
	                                        .bodyValue(savedPlaylist))
	                            );
	                    })
	            );
	        })
	        .onErrorResume(e -> ServerResponse.badRequest()
	            .contentType(MediaType.APPLICATION_JSON)
	            .bodyValue(e.getMessage()));
	}
}
