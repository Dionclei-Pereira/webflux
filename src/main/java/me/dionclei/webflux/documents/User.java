package me.dionclei.webflux.documents;

import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import me.dionclei.webflux.dto.UserDTO;
import me.dionclei.webflux.enums.UserRole;

@Document
public class User implements UserDetails {
	
	@Id
	private String id;
	private String name;
	private String email;
	private String password;
	private UserRole role;
	private Set<Song> favoriteSongs = new HashSet<>();
	private Set<String> playlists = new HashSet<>();
	
	public User() {
		super();
	}
	
	public User(String id, String name, String email, String password, Set<Song> favoriteSongs,
			Set<String> playlists, UserRole role) {
		super();
		this.id = id;
		this.name = name;
		this.email = email;
		this.password = new BCryptPasswordEncoder().encode(password);
		this.favoriteSongs = favoriteSongs;
		this.playlists = playlists;
		this.role = role; 
	}
	
	public User(String id, String name, String email, String password, Set<Song> favoriteSongs,
			Set<String> playlists) {
		super();
		this.id = id;
		this.name = name;
		this.email = email;
		this.password = new BCryptPasswordEncoder().encode(password);
		this.favoriteSongs = favoriteSongs;
		this.playlists = playlists;
		this.role = UserRole.USER; 
	}
	
	public UserDTO toDTO() {
		return new UserDTO(id, name, favoriteSongs, playlists);
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

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = new BCryptPasswordEncoder().encode(password);
	}

	public Set<Song> getFavoriteSongs() {
		return favoriteSongs;
	}

	public void setFavoriteSongs(Set<Song> favoriteSongs) {
		this.favoriteSongs = favoriteSongs;
	}

	public Set<String> getPlaylists() {
		return playlists;
	}

	public void setPlaylists(Set<String> playlists) {
		this.playlists = playlists;
	}

	@Override
	public int hashCode() {
		return Objects.hash(email);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		User other = (User) obj;
		return Objects.equals(email, other.email);
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		if(role == UserRole.ADMIN) return List.of(new SimpleGrantedAuthority("ROLE_ADMIN"), new SimpleGrantedAuthority("ROLE_USER"));
		return List.of(new SimpleGrantedAuthority("ROLE_USER"));
	}

	@Override
	public String getUsername() {
		return email;
	}
	
}
