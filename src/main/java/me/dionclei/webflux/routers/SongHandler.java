package me.dionclei.webflux.routers;

import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;

import me.dionclei.webflux.Exceptions.ResourceNotFound;
import me.dionclei.webflux.documents.Song;
import me.dionclei.webflux.dto.SongCreateRequest;
import me.dionclei.webflux.enums.Genre;
import me.dionclei.webflux.services.PlaylistService;
import me.dionclei.webflux.services.SongService;
import me.dionclei.webflux.services.UserService;
import reactor.core.publisher.Mono;
import static org.springframework.web.reactive.function.BodyInserters.fromPublisher;
import static org.springframework.web.reactive.function.server.ServerResponse.ok;

import java.util.Map;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.BeanUtils;
import org.springframework.http.MediaType;

@Component
public class SongHandler {
	
	private SongService songService;
	private UserService userService;
	private PlaylistService playlistService;
	
	public SongHandler(SongService songService, UserService userService, PlaylistService playlistService) {
		this.songService = songService;
		this.userService = userService;
		this.playlistService = playlistService;
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
		var song = songService.findById(request.pathVariable("id"));
		return ok().body(song, Song.class);
	}
	
	public Mono<ServerResponse> save(ServerRequest request) {
	    return request.bodyToMono(SongCreateRequest.class)
	            .flatMap(songDto -> userService.findById(songDto.authorId())
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
	                                        .flatMap(savedSong -> {
	                                            return playlistService.addSong(playlist.getId(), savedSong.getId())
	                                                    .then(Mono.just(savedSong));
	                                        })
	                                        .flatMap(savedSong -> ok()
	                                                .contentType(MediaType.APPLICATION_JSON)
	                                                .bodyValue(savedSong));
	                            })
	                    )
	            )
	            .onErrorResume(e -> 
	                    ServerResponse.badRequest()
	                            .contentType(MediaType.APPLICATION_JSON)
	                            .bodyValue(Map.of("error", e.getMessage()))
	            );
	}
	
}
