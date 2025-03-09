package me.dionclei.webflux.routers;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;

import static org.springframework.web.reactive.function.server.RequestPredicates.*;

@Configuration
public class SongRouter {
	
	@Bean
	RouterFunction<ServerResponse> routeSongs(SongHandler handler) {
		return RouterFunctions.route(POST("songs").and(accept(MediaType.APPLICATION_JSON)), handler::save)
				.andRoute(GET("songs/{id}").and(accept(MediaType.APPLICATION_JSON)), handler::findById)
				.andRoute(GET("songs").and(accept(MediaType.APPLICATION_JSON)), handler::findAll);
	}
}
