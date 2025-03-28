package me.dionclei.webflux.routers;

import org.springframework.stereotype.Component;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.Validator;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;

import me.dionclei.webflux.documents.Song;
import me.dionclei.webflux.dto.SongCreateRequest;
import me.dionclei.webflux.enums.Genre;
import me.dionclei.webflux.exceptions.ResourceNotFound;
import me.dionclei.webflux.services.PlaylistService;
import me.dionclei.webflux.services.SongService;
import me.dionclei.webflux.services.UserService;
import reactor.core.publisher.Mono;
import static org.springframework.web.reactive.function.BodyInserters.fromPublisher;
import static org.springframework.web.reactive.function.server.ServerResponse.ok;
import static org.springframework.web.reactive.function.server.ServerResponse.badRequest;
import static org.springframework.web.reactive.function.server.ServerResponse.notFound;

import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.beans.BeanUtils;
import org.springframework.http.MediaType;

@Component
public class SongHandler {
	
	private SongService songService;
	private UserService userService;
	private PlaylistService playlistService;
	private Validator validator;
	
	public SongHandler(SongService songService,
			UserService userService,
			PlaylistService playlistService,
			Validator validator) {
		this.songService = songService;
		this.userService = userService;
		this.playlistService = playlistService;
		this.validator = validator;
	}
	
	public Mono<ServerResponse> findAll(ServerRequest request) {
		Optional<String> genreStr = request.queryParam("gender");
		if (genreStr.isPresent()) {
			Genre genre = genreStr.map(String::toUpperCase)
                    .map(Genre::valueOf)
                    .orElse(null);
			var songs = songService.findByGenre(genre);
			return ok().body(songs, Song.class);
		}
		var songs = songService.findAll();
		return ok().body(songs, Song.class);
	}
	
	public Mono<ServerResponse> findById(ServerRequest request) {
		return songService.findById(request.pathVariable("id")).flatMap(song -> ok().body(song, Song.class))
				.switchIfEmpty(Mono.error(new ResourceNotFound("Song not found")))
				.onErrorResume(e -> notFound().build());
	}
	
	public Mono<ServerResponse> save(ServerRequest request) {
	    return request.bodyToMono(SongCreateRequest.class)
	            .flatMap(songDto -> {
	                var errors = new BeanPropertyBindingResult(songDto, "SongCreateRequest");
	                validator.validate(songDto, errors);
	                if (errors.hasErrors()) {
	                    var errorMessages = errors.getFieldErrors().stream()
	                            .map(err -> err.getField() + " : " + err.getDefaultMessage())
	                            .collect(Collectors.toList());
	                    return ServerResponse.badRequest()
	                            .contentType(MediaType.APPLICATION_JSON)
	                            .bodyValue(Map.of("errors", errorMessages));
	                }

	                return userService.findById(songDto.authorId())
	                        .switchIfEmpty(Mono.error(new ResourceNotFound("User not found")))
	                        .flatMap(user -> playlistService.findById(songDto.playlistId())
	                                .switchIfEmpty(Mono.error(new ResourceNotFound("Album not found")))
	                                .flatMap(playlist -> {
	                                    Song song = new Song();
	                                    BeanUtils.copyProperties(songDto, song);
	                                    song.setId(UUID.randomUUID().toString());
	                                    song.setAuthor(user.name());
	                                    song.setAlbum(playlist.getName());

	                                    return songService.save(song)
	                                            .flatMap(savedSong -> playlistService.addSong(playlist.getId(), savedSong.getId())
	                                                    .then(ServerResponse.ok()
	                                                            .contentType(MediaType.APPLICATION_JSON)
	                                                            .bodyValue(savedSong))
	                                            );
	                                })
	                        );
	            })
	            .onErrorResume(e -> 
	                    ServerResponse.badRequest()
	                            .contentType(MediaType.APPLICATION_JSON)
	                            .bodyValue(Map.of("error", e.getMessage()))
	            );
	}
	
}
