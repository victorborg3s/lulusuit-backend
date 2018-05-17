package com.lulusuit.backend.entity;

import org.springframework.security.core.GrantedAuthority;

public enum Authority implements GrantedAuthority {
	
	ROLE_ADMIN("ADMIN"), ROLE_USER("USER");

	private String authority;
	
	private Authority(String auth) {
		this.authority = auth;
	}
	
	@Override
	public String getAuthority() {
		return authority;
	}
	
}
