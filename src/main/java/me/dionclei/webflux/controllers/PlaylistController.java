package me.dionclei.webflux.controllers;

import java.net.URI;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import me.dionclei.webflux.documents.Playlist;
import me.dionclei.webflux.services.PlaylistService;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public class PlaylistController {
	
	@Autowired
	private PlaylistService playlistService;
	
	@GetMapping("/playlist")
	public Flux<Playlist> findAll() {
		return playlistService.findAll();
	}
	
	@GetMapping("/playlist/{id}")
	public Mono<Playlist> findById(@PathVariable String id) {
		return playlistService.findById(id);
	}
	
	@PostMapping("/playlist")
	public Mono<Playlist> save(@RequestBody Playlist playlist, ServerHttpRequest request) {
	    return playlistService.save(playlist);
	}
	
}
