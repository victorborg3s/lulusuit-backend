package com.lulusuit.backend.entity;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

import org.hibernate.annotations.Type;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.fasterxml.jackson.databind.ObjectMapper;

@Entity
@Table(name = "usuario")
@NamedQueries(value = { @NamedQuery(name = "findByUsername", query = "from User u where u.username = :username") })
public class User implements Serializable {

	private static final long serialVersionUID = 3025672446530293124L;

	/** Deserializes an Object of class User from its JSON representation */
	public static User fromString(String jsonRepresentation) {
		ObjectMapper mapper = new ObjectMapper(); // Jackson's JSON marshaller
		User o = null;
		try {
			o = mapper.readValue(jsonRepresentation, User.class);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return o;
	}

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	@Column(nullable = false, unique = true)
	private String username;

	@Column(nullable = false)
	private String password;

	@Column(nullable = false)
	private String name;

	@Column(nullable = false)
	@Enumerated(EnumType.STRING)
	private Authority authority;

	@Column(name = "enabled", nullable = false)
	private boolean enabled;
	
	@Column(name = "image_b64")
	@Lob
	@Type(type = "org.hibernate.type.TextType")
	private String imageBase64;
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	protected void setPassword(String password) {
		this.password = password;
	}
	
	public void setPasswordPlain(String password) {
		BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(11);
		this.password = encoder.encode(password);
	}

	public Authority getAuthority() {
		return this.authority;
	}

	public void setAuthority(Authority authority) {
		this.authority = authority;
	}

	public boolean isEnabled() {
		return this.enabled;
	}

	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}

	public Collection<Authority> getAuthorities() {
		ArrayList<Authority> authorities = new ArrayList<Authority>();
		authorities.add(this.authority);
		return authorities;
	}

	public String getImageBase64() {
		return imageBase64;
	}

	public void setImageBase64(String imageBase64) {
		this.imageBase64 = imageBase64;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

}
