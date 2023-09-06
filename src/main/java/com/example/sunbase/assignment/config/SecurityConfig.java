package com.example.sunbase.assignment.config;

import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.client.RestTemplate;

@Configuration
public class SecurityConfig {
    @Bean
    public RestTemplate restTemplate(RestTemplateBuilder builder) {
        return builder.build();
    }
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.authorizeHttpRequests(config->
            config.anyRequest().authenticated()
        ).formLogin(form->
                form
                        .loginPage("/loginPage")
                        .permitAll()
        )
                .logout(logout->logout.permitAll())
                .exceptionHandling(config-> config.accessDeniedPage("/error"))
        ;
        return http.build();
    }
}
