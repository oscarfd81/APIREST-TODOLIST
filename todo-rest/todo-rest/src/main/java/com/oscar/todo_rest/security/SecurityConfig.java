package com.oscar.todo_rest.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

import com.oscar.todo_rest.error.CustomAccessDeniedHandler;
import com.oscar.todo_rest.error.CustomAuthenticationEntryPoint;

import lombok.RequiredArgsConstructor;

@Configuration
@EnableMethodSecurity
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final CustomAuthenticationEntryPoint customAuthenticationEntryPoint;
    private final CustomAccessDeniedHandler customAccessDeniedHandler;


    // Gracias a BEAN, permite que el usuario autorizado pueda acceder, y que el usuario no autorizado no pueda 
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

       http.cors(Customizer.withDefaults()) 
            .httpBasic(Customizer.withDefaults())
            .exceptionHandling(excep -> {
                excep.authenticationEntryPoint(customAuthenticationEntryPoint);
                excep.accessDeniedHandler(customAccessDeniedHandler);
            })
            .authorizeHttpRequests(auth -> auth
                .requestMatchers("/auth/register").permitAll()
                .anyRequest().authenticated()
            );

        http.csrf(csrf -> csrf.disable());
        http.headers(headers -> headers.frameOptions(frameOptions -> frameOptions.disable()));


        return http.build();
    }

}
