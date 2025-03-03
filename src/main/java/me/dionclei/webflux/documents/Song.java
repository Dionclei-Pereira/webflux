package me.dionclei.webflux.documents;

import java.util.HashSet;
import java.util.Set;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import me.dionclei.webflux.enums.Gender;

@Document
public class Song {
	
	@Id
	private String id;
	private String name;
	private String author;
	private String album;
	private String link;
	private Set<Gender> genders;
	
	public Song() {
		super();
	}

	public Song(String id, String name, String author, String album, String link, Set<Gender> genders) {
		super();
		this.id = id;
		this.author = author;
		this.album = album;
		this.link = link;
		this.name = name;
		this.genders = genders;
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

	public Set<Gender> getGenders() {
		return genders;
	}

	public void setGenders(Set<Gender> genders) {
		this.genders = genders;
	}
}
