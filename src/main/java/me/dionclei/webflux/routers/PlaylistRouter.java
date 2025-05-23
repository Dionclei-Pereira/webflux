package me.dionclei.webflux.routers;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;

import static org.springframework.web.reactive.function.server.RequestPredicates.*;

@Configuration
public class PlaylistRouter {
	
	@Bean
	RouterFunction<ServerResponse> routePlaylists(PlaylistHandler handler) {
		return RouterFunctions.route(GET("playlists").and(accept(MediaType.APPLICATION_JSON)), handler::findAll)
				.andRoute(GET("playlists/{id}").and(accept(MediaType.APPLICATION_JSON)), handler::findById)
				.andRoute(POST("playlists").and(accept(MediaType.APPLICATION_JSON)), handler::save)
				.andRoute(GET("playlists/{id}/songs").and(accept(MediaType.APPLICATION_JSON)), handler::getSongsFromPlaylist)
				.andRoute(PUT("playlists/{playlistId}/songs/{songId}"), handler::addSongToPlaylist);
	}
	
}
