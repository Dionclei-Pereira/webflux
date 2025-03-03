package me.dionclei.webflux.services.impl;

import org.springframework.stereotype.Service;

import me.dionclei.webflux.documents.Song;
import me.dionclei.webflux.enums.Gender;
import me.dionclei.webflux.repositories.SongRepository;
import me.dionclei.webflux.services.SongService;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class SongServiceImpl implements SongService{
	
	private SongRepository songRepository;
	
	public SongServiceImpl(SongRepository songRepository) {
		this.songRepository = songRepository;
	}
	
	public Flux<Song> findByGender(Gender gender) {
		return songRepository.findByGendersContaining(gender);
	}
	
	public Flux<Song> findAll() {
		return songRepository.findAll();
	}
	
	public Mono<Song> save(Song song) {
		return Mono.just(song).flatMap(songRepository::save);
	}
	
	public Mono<Song> findById(String id) {
		return songRepository.findById(id).switchIfEmpty(Mono.error(new RuntimeException("Song not found")));
	}
	
}
