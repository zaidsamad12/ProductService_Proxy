package com.example.productservice_proxyzee.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SpringSecurityConfig {
    @Bean
    public SecurityFilterChain defaultSecurityFilterChain(HttpSecurity http) throws Exception {

        http
                .csrf(csrf -> csrf
                        .ignoringRequestMatchers("/search","/search/*") // skip CSRF check here
                )
                .authorizeHttpRequests((authorize) -> authorize
                .requestMatchers("/products","/products/*").authenticated()
                .requestMatchers("/search","/search/*").permitAll()
                        .anyRequest().permitAll());

        return http.build();
    }
}
