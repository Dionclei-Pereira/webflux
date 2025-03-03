package me.dionclei.webflux.config;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import me.dionclei.webflux.documents.Playlist;
import me.dionclei.webflux.documents.Song;
import me.dionclei.webflux.enums.Gender;
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
	    	playlistRepository.deleteAll().subscribe();
	    	Set<Gender> aux = new HashSet<>();
	    	aux.add(Gender.ROCK);
	        Playlist p1 = new Playlist(UUID.randomUUID().toString(), "Van Halen", "Dionclei", aux);
	        aux = new HashSet<>();
	        aux.add(Gender.METAL);
	        Playlist p2 = new Playlist(UUID.randomUUID().toString(), "The Best Metal", "Dionclei", aux);
	    	playlistRepository.saveAll(Arrays.asList(p1, p2)).subscribe();
	        
	    	songRepository.deleteAll().subscribe();
	    	aux = new HashSet<>();
	    	aux.add(Gender.ROCK);
	    	Song s1 = new Song(UUID.randomUUID().toString(), "D.O.A", "Van Halen", "Van Halen II", "https://open.spotify.com/track/5tLbWNYxXdGRQQmW7LtWjg", aux);
	    	Song s2 = new Song(UUID.randomUUID().toString(), "Eruption", "Van Halen", "Van Halen I", "https://open.spotify.com/track/3lW0MLws0srqqR3DRRPLZp", aux);
	    	songRepository.saveAll(Arrays.asList(s1, s2)).subscribe();
	    }
}
