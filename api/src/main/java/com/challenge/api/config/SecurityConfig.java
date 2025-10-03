package com.challenge.api.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

/**
 * Security configuration for the Employee API.
 * Provides basic authentication for webhook consumption.
 * In a production environment, this would be enhanced with:
 * - JWT tokens
 * - API key authentication
 * - OAuth2 integration
 * - Rate limiting
 */
@Slf4j
@Configuration
@EnableWebSecurity
public class SecurityConfig {

    /**
     * Configures HTTP security for the API endpoints.
     * All employee endpoints require authentication.
     */
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        log.info("Configuring security filter chain");

        http.csrf()
                .disable() // Disable CSRF for API endpoints
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .authorizeHttpRequests(authz -> authz.requestMatchers("/api/v1/employee/**")
                        .authenticated()
                        .requestMatchers("/actuator/health")
                        .permitAll() // Health check endpoint
                        .anyRequest()
                        .authenticated())
                .httpBasic(); // Use HTTP Basic Authentication for simplicity

        return http.build();
    }

    /**
     * Creates in-memory user details for webhook authentication.
     * In production, this would connect to a user database or external auth service.
     */
    @Bean
    public UserDetailsService userDetailsService() {
        log.info("Creating in-memory user details service");

        UserDetails webhookUser = User.builder()
                .username("webhook-user")
                .password(passwordEncoder().encode("webhook-secret-password"))
                .roles("WEBHOOK")
                .build();

        UserDetails adminUser = User.builder()
                .username("admin")
                .password(passwordEncoder().encode("admin-secret-password"))
                .roles("ADMIN", "WEBHOOK")
                .build();

        return new InMemoryUserDetailsManager(webhookUser, adminUser);
    }

    /**
     * Password encoder for secure password storage.
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
