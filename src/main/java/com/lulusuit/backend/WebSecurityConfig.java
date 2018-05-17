package com.lulusuit.backend;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.lulusuit.backend.data.UserDao;
import com.lulusuit.backend.security.JWTAuthenticationFilter;
import com.lulusuit.backend.security.JWTLoginFilter;
import com.lulusuit.backend.service.UserDetailsServiceImpl;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

	@Autowired
	private UserDetailsServiceImpl userDetailsService;

	@Autowired
	private DataSource dataSource;

	@Autowired
	private UserDao userDao;

	@Override
	protected void configure(HttpSecurity httpSecurity) throws Exception {
		httpSecurity
				.cors()
				.and()
				.csrf().disable()
				.authorizeRequests()
				.antMatchers(HttpMethod.POST, "/api/auth/login").permitAll()
				.antMatchers(HttpMethod.GET, "/login").permitAll()
				.antMatchers(HttpMethod.GET, "/user/admin-insert").permitAll()
				.anyRequest().authenticated()
				.and()

				// filtra requisições de login
				.addFilterBefore(new JWTLoginFilter("/api/auth/login", authenticationManager(), userDao),
						UsernamePasswordAuthenticationFilter.class)

				// filtra outras requisições para verificar a presença do JWT no header
				.addFilterBefore(new JWTAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class);
	}

	@Bean
	public DaoAuthenticationProvider authenticationProvider() {
		DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
		authProvider.setUserDetailsService(userDetailsService);
		authProvider.setPasswordEncoder(encoder());
		return authProvider;
	}

	@Bean
	public PasswordEncoder encoder() {
		return new BCryptPasswordEncoder(11);
	}

	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		/* cria uma conta default para efeito de teste */
		// auth.inMemoryAuthentication()
		// .passwordEncoder(new BCryptPasswordEncoder())
		// .withUser("admin")
		// .password("$2a$10$oHwLwpMGDoFVdQt1s2t2EO1AR6AZbjzk2Ld.zNj9iKlT5iAAlWvgi")
		// .roles("ADMIN");

		auth.jdbcAuthentication().dataSource(dataSource)
			.passwordEncoder(encoder()).rolePrefix("ROLE_")
			.authoritiesByUsernameQuery(
					"select " + 
					"	u.username, " + 
					"	u.authority " + 
					"from " + 
					"	usuario u " +
					"where " +
					"   u.username = ? ")
			.usersByUsernameQuery(
					"select " +
					"	u.username," +
					"	u.password," +
					"	u.enabled  " +
					"from " +
					"	usuario u " +
					"where " +
					"	u.username = ? ")
			.and()
			.authenticationProvider(authenticationProvider());
	}

}
