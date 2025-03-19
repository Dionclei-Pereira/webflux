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
import me.dionclei.webflux.documents.User;
import me.dionclei.webflux.enums.Genre;
import me.dionclei.webflux.repositories.PlaylistRepository;
import me.dionclei.webflux.repositories.SongRepository;
import me.dionclei.webflux.repositories.UserRepository;
import reactor.core.publisher.Flux;


@Component
public class SeedRunner implements CommandLineRunner {
		
		@Autowired
		private PlaylistRepository playlistRepository;
		@Autowired
		private SongRepository songRepository;
		@Autowired
		private UserRepository userRepository; 
		
	    @Override
	    public void run(String... args) throws Exception {
	    	playlistRepository.deleteAll().subscribe();
	    	userRepository.deleteAll().subscribe();
	    	Set<Genre> aux = new HashSet<>();
	    	aux.add(Genre.ROCK);
	        Playlist p1 = new Playlist(UUID.randomUUID().toString(), "Van Halen", "Dionclei", aux, new HashSet<>());
	        aux = new HashSet<>();
	        aux.add(Genre.METAL);
	        Playlist p2 = new Playlist(UUID.randomUUID().toString(), "The Best Metal", "Dionclei", aux, new HashSet<>());
	        
	    	songRepository.deleteAll().subscribe();
	    	aux = new HashSet<>();
	    	aux.add(Genre.ROCK);
	    	Song s1 = new Song(UUID.randomUUID().toString(), "D.O.A", "Van Halen", "Van Halen II", "https://open.spotify.com/track/5tLbWNYxXdGRQQmW7LtWjg", aux);
	    	Song s2 = new Song(UUID.randomUUID().toString(), "Eruption", "Van Halen", "Van Halen I", "https://open.spotify.com/track/3lW0MLws0srqqR3DRRPLZp", aux);
	    	Set<String> aux2 = new HashSet<>();
	    	aux2.add(s2.getId());
	    	p2.setSongs(aux2);
	    	
	    	Set<Song> songs = new HashSet<>();
	    	songs.addAll(Arrays.asList(s1, s2));
	    	
	    	Set<Playlist> playlists = new HashSet<>();
	    	playlists.addAll(Arrays.asList(p1, p2));
	    	
	    	User u1 = new User(UUID.randomUUID().toString(), "Dionclei", "dionclei@gmail.com", "12345678", songs, playlists);
	    	
	    	userRepository.save(u1).subscribe();
	    	songRepository.saveAll(Arrays.asList(s1, s2)).subscribe();
	    	playlistRepository.saveAll(Arrays.asList(p1, p2)).subscribe();
	    	
	    }
}
