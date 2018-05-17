package com.lulusuit.backend.security;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;

import com.lulusuit.backend.entity.Authority;
import com.lulusuit.backend.entity.User;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

public class TokenAuthenticationService {

	// EXPIRATION_TIME = 10 dias
	static final long EXPIRATION_TIME = 860_000_000;
	static final String SECRET = "MySecret";
	static final String TOKEN_PREFIX = "Bearer";
	static final String HEADER_STRING = "Authorization";

	static void addAuthentication(HttpServletResponse response, User user) {
		String JWT = Jwts.builder()
				.claim("id", user.getId())
				.claim("username", user.getUsername())
				.claim("password", "")
				.claim("authority", user.getAuthority())
				.claim("name", user.getName())
				.setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
				.signWith(SignatureAlgorithm.HS512, SECRET).compact();

		response.addHeader(HEADER_STRING, TOKEN_PREFIX + " " + JWT);
		try {
			String json = "";
			json += "{\"token\": ";
			json += "\"" + JWT + "\"";
			json += ", \"data\": {";
			json += "\"messages\": [";
			json += "\"Tudo certo na Bahia!\"";
			json += "]";
			json += "}";
			json += "}";
			response.getOutputStream().print(json);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	static Authentication getAuthentication(HttpServletRequest request) {
		String token = request.getHeader(HEADER_STRING);

		if (token != null) {
			// faz parse do token
			Claims claims = Jwts.parser().setSigningKey(SECRET).parseClaimsJws(token.replace(TOKEN_PREFIX, "")).getBody();
			String user = (String) claims.get("username");

			Collection<Authority> auths = new ArrayList<Authority>();
			auths.add(Authority.valueOf((String) claims.get("authority")));
			
			if (user != null) {
				return new UsernamePasswordAuthenticationToken(user, null, auths);
			}
		}
		return null;
	}

}
