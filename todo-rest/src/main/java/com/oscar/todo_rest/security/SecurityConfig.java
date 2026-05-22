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
@EnableMethodSecurity // ACTIVA EL USO DE @PREAUTHORIZE EN LOS CONTROLADORES PARA PROTEGER ENDPOINTS POR ROL
@EnableWebSecurity    // ACTIVA LA SEGURIDAD WEB DE SPRING EN TODA LA APLICACION
@RequiredArgsConstructor
public class SecurityConfig {

    // MANEJADORES DE ERRORES PERSONALIZADOS (LOS QUE DEVUELVEN EL JSON BONITO CUANDO ALGO FALLA)
    private final CustomAuthenticationEntryPoint customAuthenticationEntryPoint;
    private final CustomAccessDeniedHandler customAccessDeniedHandler;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        http.cors(Customizer.withDefaults()) 
            
            .httpBasic(Customizer.withDefaults()) // ACTIVA LA AUTENTICACION BASICA (MANDAR USUARIO Y CONTRASEÑA EN LA CABECERA)
            
            // CONFIGURACION DE ERRORES DE SEGURIDAD
            .exceptionHandling(excep -> {
                excep.authenticationEntryPoint(customAuthenticationEntryPoint);
                excep.accessDeniedHandler(customAccessDeniedHandler);
            })
            
            // REGLAS DE ACCESO A LAS URLS (EL FILTRO PRINCIPAL)
            .authorizeHttpRequests(auth -> auth
                // RUTAS PÚBLICAS DE SWAGGER / DOCUMENTACIÓN ABIERTAS PARA QUE LA PROFE PUEDA ENTRAR
                // NOTA: SE USA /** EN LUGAR DE /* PARA CUBRIR TODAS LAS SUBRUTAS (SPRING SECURITY 7)
                .requestMatchers(
                    "/v3/api-docs",
                    "/v3/api-docs/**",
                    "/swagger-ui/**",
                    "/swagger-ui.html",
                    "/swagger-resources/**",
                    "/webjars/**"
                ).permitAll()
                // LA URL DE REGISTRARSE ES LIBRE, CUALQUIERA PUEDE ENTRAR SIN LOGUEARSE
                .requestMatchers("/auth/register").permitAll()
                // CUALQUIER OTRA RUTA DE LA API EXIGE ESTAR AUTENTICADO DE FORMA OBLIGATORIA
                .anyRequest().authenticated()
            );

        http.csrf(csrf -> csrf.disable());
        
        http.headers(headers -> headers.frameOptions(frameOptions -> frameOptions.disable()));

        return http.build(); 
        // CONSTRUYE Y ACTIVA TODOESTE BLOQUE DE SEGURIDAD
    }
}