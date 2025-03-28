package me.dionclei.webflux.documents;

import java.util.HashSet;
import java.util.Set;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import me.dionclei.webflux.enums.Genre;

@Document
public class Song {
	
	@Id
	private String id;
	private String name;
	private String author;
	private String authorEmail;
	private String album;
	private String link;
	private Set<Genre> genres = new HashSet<>();
	
	public Song() {
		super();
	}

	public Song(String id, String name, String author, String authorEmail, String album, String link, Set<Genre> genres) {
		super();
		this.id = id;
		this.author = author;
		this.album = album;
		this.authorEmail = authorEmail;
		this.link = link;
		this.name = name;
		this.genres = genres;
	}
	
	public String getAuthorEmail() {
		return authorEmail;
	}

	public void setAuthorEmail(String authorEmail) {
		this.authorEmail = authorEmail;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getAuthor() {
		return author;
	}
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	public String getAlbum() {
		return album;
	}

	public void setAlbum(String album) {
		this.album = album;
	}

	public String getLink() {
		return link;
	}

	public void setLink(String link) {
		this.link = link;
	}

	public Set<Genre> getGenres() {
		return genres;
	}

	public void setGenres(Set<Genre> genres) {
		this.genres = genres;
	}
}
