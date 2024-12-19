package com.intellect.serverstatuschecker;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

	@Autowired
	private UserDetailsService userDetailsService; // Load user details from the database

	@Autowired
	private JwtFilter jwtFilter; // Custom JWT filter to validate tokens

	@Bean
	public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
		return http.csrf().disable()
				.authorizeHttpRequests(
						request -> request.requestMatchers("/swagger-ui/**", "/v3/api-docs/**").permitAll() // Swagger
																											// endpoints
								.requestMatchers("/server_details/register", "/server_details/login").permitAll() // Public
																													// endpoints
								.anyRequest().authenticated() // Protect other endpoints
				).httpBasic().disable()
				.sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS)) // Stateless
																												// session
				.addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class) // Add JWT filter
				.cors().and().build();
	}

	@Bean
	public AuthenticationProvider authenticationProvider() {
		DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
		provider.setPasswordEncoder(new BCryptPasswordEncoder(12)); // Use BCrypt for password hashing
		provider.setUserDetailsService(userDetailsService); // Use custom UserDetailsService
		return provider;
	}

	@Bean
	public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration)
			throws Exception {
		return authenticationConfiguration.getAuthenticationManager();
	}
}
