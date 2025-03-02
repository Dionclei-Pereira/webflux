package me.dionclei.webflux.routers;

import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import me.dionclei.webflux.documents.Song;
import me.dionclei.webflux.services.SongService;
import reactor.core.publisher.Mono;
import static org.springframework.web.reactive.function.BodyInserters.fromPublisher;
import static org.springframework.web.reactive.function.server.ServerResponse.ok;

import org.springframework.http.MediaType;

@Component
public class SongHandler {
	
	private SongService songService;
	
	public SongHandler(SongService songService) {
		this.songService = songService;
	}
	
	public Mono<ServerResponse> findAll(ServerRequest request) {
		var songs = songService.findAll();
		return ok().body(songs, Song.class);
	}
	
	public Mono<ServerResponse> findById(ServerRequest request) {
		var song = songService.findById(request.pathVariable("id"));
		return ok().body(song, Song.class);
	}
	
	public Mono<ServerResponse> save(ServerRequest request) {
		final Mono<Song> song = request.bodyToMono(Song.class);
		return ok().contentType(MediaType.APPLICATION_JSON).body(fromPublisher(song.flatMap(songService::save), Song.class));
	}
	
}
