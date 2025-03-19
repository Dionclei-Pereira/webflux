package me.dionclei.webflux.services.impl;

import org.springframework.stereotype.Service;

import me.dionclei.webflux.documents.Song;
import me.dionclei.webflux.enums.Genre;
import me.dionclei.webflux.repositories.SongRepository;
import me.dionclei.webflux.services.SongService;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

@Service
public class SongServiceImpl implements SongService{
	
	private SongRepository songRepository;
	
	public SongServiceImpl(SongRepository songRepository) {
		this.songRepository = songRepository;
	}
	
	public Flux<Song> findByGenre(Genre genre) {
		return songRepository.findByGenresContaining(genre).subscribeOn(Schedulers.boundedElastic());
	}
	
	public Flux<Song> findAll() {
		return songRepository.findAll().subscribeOn(Schedulers.boundedElastic());
	}
	
	public Mono<Song> save(Song song) {
		return Mono.just(song).flatMap(songRepository::save).subscribeOn(Schedulers.boundedElastic());
	}
	
	public Mono<Song> findById(String id) {
		return songRepository.findById(id).switchIfEmpty(Mono.error(new RuntimeException("Song not found"))).subscribeOn(Schedulers.boundedElastic());
	}
	
}
