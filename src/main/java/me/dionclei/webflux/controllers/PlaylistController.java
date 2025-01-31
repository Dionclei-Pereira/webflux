package me.dionclei.webflux.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import me.dionclei.webflux.documents.Playlist;
import me.dionclei.webflux.services.PlaylistService;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/playlists")
public class PlaylistController {
	
	@Autowired
	private PlaylistService playlistService;
	
	@GetMapping
	public Mono<ResponseEntity<Flux<Playlist>>> findAll() {
		return Mono.just(ResponseEntity.ok(playlistService.findAll()));
	}
	
	@GetMapping("/{id}")
	public Mono<ResponseEntity<Playlist>> findById(@PathVariable String id) {
		return playlistService.findById(id).map(ResponseEntity::ok);
	}
	
}
