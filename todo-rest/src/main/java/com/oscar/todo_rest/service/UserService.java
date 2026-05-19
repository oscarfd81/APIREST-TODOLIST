package com.oscar.todo_rest.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.oscar.todo_rest.dto.NewUserCommand;
import com.oscar.todo_rest.model.User;
import com.oscar.todo_rest.repos.UserRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public User register(NewUserCommand cmd) {
        User user = User.builder()
                .username(cmd.username())
                .email(cmd.email())
                .password(passwordEncoder.encode(cmd.password()))
                .isAdmin(false)
                .build();
        return userRepository.save(user);
    }

    // NUEVO: METODO PARA QUE UN USUARIO AUTENTICADO CAMBIE SU PROPIA CONTRASEÑA ENCRIPTANDOLA CON BCRYPT
    public User changePassword(User user, String newPassword) {
        user.setPassword(passwordEncoder.encode(newPassword));
        return userRepository.save(user);
    }

    // NUEVO: METODO PARA QUE EL ADMIN PROMOCIONE UN USER A GESTOR CAMBIANDO LOS BOOLEANOS DE ROL
    public User promoteToGestor(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
        
        user.setGestor(true);
        user.setAdmin(false);
        user.setUser(false);
        
        return userRepository.save(user);
    }

    // NUEVO: METODO PARA QUE EL ADMIN DEGRADE UN GESTOR A USUARIO NORMAL
    public User demoteToUser(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
        
        user.setUser(true);
        user.setGestor(false);
        user.setAdmin(false);
        
        return userRepository.save(user);
    }

    // NUEVO: METODO CRUD PARA OBTENER TODOS LOS USUARIOS DEL SISTEMA (SOLO PARA EL ADMIN)
    public List<User> findAll() {
        return userRepository.findAll();
    }

    // NUEVO: METODO CRUD PARA ELIMINAR A UN USUARIO POR SU ID (SOLO PARA EL ADMIN)
    public void delete(Long id) {
        if (!userRepository.existsById(id)) {
            throw new RuntimeException("Usuario no encontrado");
        }
        userRepository.deleteById(id);
    }
}