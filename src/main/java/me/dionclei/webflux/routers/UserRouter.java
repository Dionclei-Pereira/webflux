package me.dionclei.webflux.routers;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;
import static org.springframework.web.reactive.function.server.RequestPredicates.*;

@Configuration
public class UserRouter {
	
	@Bean
	RouterFunction<ServerResponse> routeUsers(UserHandler handler) {
		return RouterFunctions.route(GET("users/{id}").and(accept(MediaType.APPLICATION_JSON)), handler::findById)
				.andRoute(GET("users"), handler::findAll)
				.andRoute(GET("users/{id}/songs"), handler::findFavoriteSongs)
				.andRoute(POST("users"), handler::save)
				.andRoute(POST("users/{userId}/add-song/{songId}"), handler::addSongToFavorites)
				.andRoute(DELETE("users/{userId}/songs/{songId}"), handler::removeSongToFavorites);
	}
	
}
