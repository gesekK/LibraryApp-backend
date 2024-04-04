package com.example.technologiesieciowe1.security;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Value("${jwt.token.key}")
    private String key;
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception{
        return http
                .csrf(AbstractHttpConfigurer::disable)
                .addFilterBefore(new JWTTokenFilter(key), UsernamePasswordAuthenticationFilter.class)
                .authorizeHttpRequests(
                        authorizationManagerRequestMatcherRegistry ->
                                authorizationManagerRequestMatcherRegistry
                                        .requestMatchers("/login").permitAll()
                                        .requestMatchers("/book/add").hasRole("LIBRARY_EMPLOYEE")
                                        .requestMatchers("/book/delete/{id}").hasRole("LIBRARY_EMPLOYEE")
                                        .requestMatchers("/book/update/{id}").hasRole("LIBRARY_EMPLOYEE")
                                        .requestMatchers("/book/update/all/{id}").hasRole("LIBRARY_EMPLOYEE")
                                        .requestMatchers("/book/getAll").hasAnyRole("LIBRARY_EMPLOYEE","READER")
                                        .requestMatchers("/book/{id}").hasAnyRole("LIBRARY_EMPLOYEE","READER")
                                        .requestMatchers("/book/author/{author}").hasAnyRole("LIBRARY_EMPLOYEE","READER")
                                        .requestMatchers("/book/title/{title}").hasAnyRole("LIBRARY_EMPLOYEE","READER")
                                        .requestMatchers("/book/most-borrowed").hasAnyRole("LIBRARY_EMPLOYEE","READER")
                                        .requestMatchers("/book/details/{title}").hasAnyRole("LIBRARY_EMPLOYEE","READER")
                                        .requestMatchers("/loan/add").hasRole("LIBRARY_EMPLOYEE")
                                        .requestMatchers("/loan//getAll").hasRole("LIBRARY_EMPLOYEE")
                                        .requestMatchers("/loan/{id}").hasRole("LIBRARY_EMPLOYEE")
                                        .requestMatchers("/loan/borrow").hasRole("READER")
                                        .requestMatchers("/loan//return/{loanId}").hasRole("READER")
                                        .requestMatchers("/loan/approve/{loanId}").hasRole("LIBRARY_EMPLOYEE")
                                        .requestMatchers("/loan/confirm-return/{loanId}").hasRole("LIBRARY_EMPLOYEE")
                                        .requestMatchers("/loan/delayed-returns").hasRole("LIBRARY_EMPLOYEE")
                                        .requestMatchers("/reviews/**").hasAnyRole("LIBRARY_EMPLOYEE","READER")
                                        .requestMatchers("/user/**").hasRole("LIBRARY_EMPLOYEE")
                                        .requestMatchers("/error").permitAll()
                )
                .sessionManagement(sessionManagement ->
                        sessionManagement.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .build();
    }
}
