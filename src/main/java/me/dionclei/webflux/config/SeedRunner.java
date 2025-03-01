package me.dionclei.webflux.config;

import java.util.Arrays;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import me.dionclei.webflux.documents.Playlist;
import me.dionclei.webflux.documents.Song;
import me.dionclei.webflux.repositories.PlaylistRepository;
import me.dionclei.webflux.repositories.SongRepository;
import reactor.core.publisher.Flux;


@Component
public class SeedRunner implements CommandLineRunner {
		
		@Autowired
		private PlaylistRepository playlistRepository;
		@Autowired
		private SongRepository songRepository;
	
	    @Override
	    public void run(String... args) throws Exception {
	    	playlistRepository.deleteAll()
	                .thenMany(
	                        Flux.just("Rock", "Metal", "Van Halen")
	                                .map(nome -> new Playlist(UUID.randomUUID().toString(), nome))
	                                .flatMap(playlistRepository::save))
	                .subscribe();
	    	songRepository.deleteAll().subscribe();
	    	Song s1 = new Song(UUID.randomUUID().toString(), "D.O.A", "Van Halen", "Van Halen II", "https://open.spotify.com/track/5tLbWNYxXdGRQQmW7LtWjg");
	    	Song s2 = new Song(UUID.randomUUID().toString(), "Eruption", "Van Halen", "Van Halen I", "https://open.spotify.com/track/3lW0MLws0srqqR3DRRPLZp");
	    	songRepository.saveAll(Arrays.asList(s1, s2)).subscribe();
	    }
}
