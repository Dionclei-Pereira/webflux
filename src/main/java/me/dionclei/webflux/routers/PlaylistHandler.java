package me.dionclei.webflux.routers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;

import me.dionclei.webflux.documents.Playlist;
import me.dionclei.webflux.documents.Song;
import me.dionclei.webflux.enums.Genre;
import me.dionclei.webflux.services.PlaylistService;
import reactor.core.publisher.Mono;
import static org.springframework.web.reactive.function.BodyInserters.fromPublisher;
import static org.springframework.web.reactive.function.server.ServerResponse.ok;

@Component
public class PlaylistHandler {
	
	@Autowired
	PlaylistService service;
	
	public Mono<ServerResponse> findAll(ServerRequest request) {
		var genreStr = request.queryParam("gender");
		if (genreStr.isPresent()) {
			Genre genre = genreStr.map(String::toUpperCase)
            .map(Genre::valueOf)
            .orElse(null);
			return ok().body(service.findByGenre(genre), Playlist.class);
		}
		
		return ok().contentType(MediaType.APPLICATION_JSON).body(service.findAll(), Playlist.class);
	}
	
	public Mono<ServerResponse> addSongToPlaylist(ServerRequest request) {
		String playlistId = request.pathVariable("playlistId");
		String songId = request.pathVariable("songId");
		var playlist = service.addSong(playlistId, songId);
		return ok().contentType(MediaType.APPLICATION_JSON).body(playlist, Playlist.class);
		
	}
	
	public Mono<ServerResponse> getSongsFromPlaylist(ServerRequest request) {
		String id = request.pathVariable("id");
		return ok().body(service.getSongsFromPlaylist(id), Song.class);
	}
	
	public Mono<ServerResponse> findById(ServerRequest request) {
		String id = request.pathVariable("id");
		return ok().contentType(MediaType.APPLICATION_JSON)
				.body(service.findById(id), Playlist.class);
	}
	
	public Mono<ServerResponse> save(ServerRequest request) {
		final Mono<Playlist> playlist = request.bodyToMono(Playlist.class);
		return ok().contentType(MediaType.APPLICATION_JSON).body(fromPublisher(playlist.flatMap(service::save), Playlist.class));
	}
}
