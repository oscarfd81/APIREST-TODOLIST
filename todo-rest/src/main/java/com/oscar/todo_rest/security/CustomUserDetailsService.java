package com.oscar.todo_rest.security;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.oscar.todo_rest.repos.UserRepository;

@Service
@RequiredArgsConstructor
// IMPLEMENTA EL CONTROL DEL LOGIN
public class CustomUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    // ESTE METODO SE ADUEÑA DEL PROCESO DE LOGUEO DE SPRING SECURITY
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // BUSCA AL USUARIO EN TU BASE DE DATOS POR SU NOMBRE DE USUARIO (username)
        return userRepository.findFirstByUsername(username)
                // SI EL USUARIO NO EXISTE EN LA BASE DE DATOS, LANZA ESTE ERROR AUTOMATICAMENTE
                .orElseThrow(() -> new UsernameNotFoundException("User don't exist"));
    }
}