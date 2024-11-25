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
    private UserDetailsService userDetailsService; // This is used to load user details from DB

    @Autowired
    private JwtFilter jwtFilter; // Your custom JWT filter to verify JWT tokens

    // Configure the security filter chain
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        return http.csrf().disable()  // Disable CSRF protection as we are using JWT (stateless)
                .authorizeHttpRequests(request -> request
                        .requestMatchers("/server_details/register", "/server_details/login") // Open endpoints that don't need authentication
                        .permitAll()
                        .anyRequest().authenticated() // Protect all other endpoints
                )
                .httpBasic().disable() // Basic Authentication (can be replaced with JWT authentication)
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS)) // Stateless (no sessions)
                .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class) // Add JWT filter before default UsernamePasswordAuthenticationFilter
                .cors() // Enable CORS (for cross-origin requests)
                .and()
                .build(); // Build the configuration
    }

    // Define the AuthenticationProvider for Spring Security to authenticate users from your DB
    @Bean
    public AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setPasswordEncoder(new BCryptPasswordEncoder(12)); // Password encoder (BCrypt)
        provider.setUserDetailsService(userDetailsService); // Set the UserDetailsService to load users
        return provider;
    }

    // Define the AuthenticationManager bean to be used for authentication
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager(); // Automatically get the AuthenticationManager
    }
}
