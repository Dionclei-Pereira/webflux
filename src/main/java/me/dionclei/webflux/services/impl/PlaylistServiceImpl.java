package me.dionclei.webflux.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import me.dionclei.webflux.documents.Playlist;
import me.dionclei.webflux.documents.Song;
import me.dionclei.webflux.enums.Gender;
import me.dionclei.webflux.repositories.PlaylistRepository;
import me.dionclei.webflux.repositories.SongRepository;
import me.dionclei.webflux.services.PlaylistService;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class PlaylistServiceImpl implements PlaylistService{

	@Autowired
	private PlaylistRepository playlistRepository;
	
	@Autowired
	private SongRepository songRepository;
	
	public Flux<Playlist> findAll() {
		return playlistRepository.findAll();
	}

	public Flux<Playlist> findByGender(Gender gender) {
		return playlistRepository.findByGendersContaining(gender);
	}
	
	public Mono<Playlist> findById(String id) {
		return playlistRepository.findById(id);
	}
	
	public Mono<Playlist> save(Playlist playlist) {
		return playlistRepository.save(playlist);
	}
	
	public Flux<Song> getSongsFromPlaylist(String PlaylistId) {
		return findById(PlaylistId).flatMapMany(playlist -> Flux.fromIterable(playlist.getSongs())).flatMap(songRepository::findById);
	}
	
	public Mono<Playlist> addSong(String playlistId, String songId) {
	    return songRepository.findById(songId)
	            .switchIfEmpty(Mono.error(new IllegalArgumentException("Song not found")))
	            .flatMap(song -> findById(playlistId)
	                    .map(playlist -> {
	                        playlist.addSong(songId);
	                        return playlist;
	                    })
	                    .flatMap(playlistRepository::save)
	            );
	}
	
}
