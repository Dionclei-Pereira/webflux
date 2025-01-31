package me.dionclei.webflux.config;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import me.dionclei.webflux.documents.Playlist;
import me.dionclei.webflux.repositories.PlaylistRepository;
import reactor.core.publisher.Flux;


@Component
public class SeedRunner implements CommandLineRunner {
		
		@Autowired
		private PlaylistRepository playlistRepository;
	
	    @Override
	    public void run(String... args) throws Exception {
	    	playlistRepository.deleteAll()
	                .thenMany(
	                        Flux.just("Rock", "Metal", "Van Halen")
	                                .map(nome -> new Playlist(UUID.randomUUID().toString(), nome))
	                                .flatMap(playlistRepository::save))
	                .subscribe(System.out::println);
	    }
}
