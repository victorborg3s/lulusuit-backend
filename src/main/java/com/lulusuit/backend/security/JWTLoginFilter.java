package com.lulusuit.backend.security;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.lulusuit.backend.data.UserDao;
import com.lulusuit.backend.entity.Authority;
import com.lulusuit.backend.entity.User;

public class JWTLoginFilter extends AbstractAuthenticationProcessingFilter {

	private UserDao userDao;
	
	public JWTLoginFilter(String url, AuthenticationManager authManager, UserDao userDao) {
		super(new AntPathRequestMatcher(url));
		setAuthenticationManager(authManager);
		this.userDao = userDao;
	}

	@Override
	public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
			throws AuthenticationException, IOException, ServletException {
		
		AccountCredentials credentials = new ObjectMapper()
				.readValue(request.getInputStream(), AccountCredentials.class);
		
		User user = userDao.findByUsername(credentials.getUsername());
		Collection<Authority> authorityList = new ArrayList<Authority>();
		if (user != null) {
			authorityList.add(user.getAuthority());
		}
		
		return getAuthenticationManager().authenticate(
				new UsernamePasswordAuthenticationToken(
						credentials.getUsername(), 
						credentials.getPassword(), 
						authorityList
						)
				);
	}
	
	@Override
	protected void successfulAuthentication(
			HttpServletRequest request, 
			HttpServletResponse response,
			FilterChain filterChain,
			Authentication auth) throws IOException, ServletException {
		
		User user = userDao.findByUsername(auth.getName());
		
		TokenAuthenticationService.addAuthentication(response, user);
	}

}
