package com.expense.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.expense.filter.JwtFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
	
	@Autowired
	private JwtFilter jwtFilter;

    @Bean
    public SecurityFilterChain securityFilterChain(
            HttpSecurity http)
            throws Exception {

    			http
    			    .csrf(csrf -> csrf.disable())
    			    .authorizeHttpRequests(auth -> auth

    			        .requestMatchers(
    			            "/",
    			            "/index.html",
    			            "/login.html",
    			            "/register.html",
    			            "/auth/login",
    			            "/auth/register",
    			            "/css/**",
    			            "/js/**",
    			            "/profile-images/**",
    			            "/auth/forgot-password"
    			        ).permitAll()

    			        .anyRequest()
    			        .authenticated()
    			    );
    			


        http.addFilterBefore(
                jwtFilter,
                UsernamePasswordAuthenticationFilter.class);

        return http.build();

    
    }
}