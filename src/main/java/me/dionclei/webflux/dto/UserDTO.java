package me.dionclei.webflux.dto;

import java.util.Set;

import me.dionclei.webflux.documents.Playlist;
import me.dionclei.webflux.documents.Song;

public record UserDTO(String id, String name, Set<Song> favoriteSongs, Set<Playlist> playlists) {

}
