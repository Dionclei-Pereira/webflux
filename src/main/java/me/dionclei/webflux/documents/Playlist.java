package me.dionclei.webflux.documents;

import java.util.Set;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import me.dionclei.webflux.enums.Gender;

@Document
public class Playlist {
	
	@Id
	private String id;
	private String name;
	private String author;
	private Set<Gender> genders;
	private Set<String> songs;
	
	public Playlist() {}
	
	public Playlist(String id, String name, String author, Set<Gender> genders, Set<String> songs) {
		super();
		this.id = id;
		this.name = name;
		this.author = author;
		this.genders = genders;
		this.songs = songs;
	}
	
	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	public Set<Gender> getGenders() {
		return genders;
	}

	public void setGenders(Set<Gender> genders) {
		this.genders = genders;
	}

	public String getId() {
		return id;
	}
	
	public void setId(String id) {
		this.id = id;
	}
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}

	public Set<String> addSong(String SongId) {
		songs.add(SongId);
		return getSongs();
	}
	
	public Set<String> getSongs() {
		return songs;
	}

	public void setSongs(Set<String> songs) {
		this.songs = songs;
	}
	
}
