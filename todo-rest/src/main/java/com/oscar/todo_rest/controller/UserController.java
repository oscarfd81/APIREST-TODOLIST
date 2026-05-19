package com.oscar.todo_rest.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import com.oscar.todo_rest.dto.NewUserCommand;
import com.oscar.todo_rest.dto.NewUserResponse;
import com.oscar.todo_rest.dto.GetUserDTO;
import com.oscar.todo_rest.dto.UpdatePasswordCommand;
import com.oscar.todo_rest.model.User; 
import com.oscar.todo_rest.service.UserService;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    // REGISTRO PÚBLICO: ACCESIBLE POR CUALQUIERA (CONFIGURADO EN SECURITYCONFIG)
    @PostMapping("/auth/register")
    public ResponseEntity<NewUserResponse> createUser(@RequestBody NewUserCommand cmd) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(NewUserResponse.of(userService.register(cmd)));
    }

    // CAMBIAR CONTRASEÑA: CUALQUIER USUARIO LOGUEADO PUEDE CAMBIAR LA SUYA PROPIA
    @PreAuthorize("hasAnyRole('USER', 'GESTOR', 'ADMIN')")
    @PutMapping("/user/change-password")
    public ResponseEntity<Void> changePassword(
            @AuthenticationPrincipal User user, 
            @RequestBody UpdatePasswordCommand cmd) {
        
        userService.changePassword(user, cmd.newPassword());
        return ResponseEntity.noContent().build(); 
    }

    // MODIFICAR EL PERFIL DEL USUARIO AUTENTICADO 
    @PreAuthorize("hasAnyRole('USER', 'GESTOR', 'ADMIN')")
    @PutMapping("/user/profile")
    public ResponseEntity<NewUserResponse> updateProfile(
            @AuthenticationPrincipal User user,
            @RequestBody NewUserCommand cmd) {
        user.setUsername(cmd.username());
        user.setEmail(cmd.email());
        return ResponseEntity.ok(NewUserResponse.of(userService.changePassword(user, user.getPassword())));
    }

    // SOLO PARA ADMIN
    // LISTAR TODOS LOS USUARIOS DEL SISTEMA
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/admin/users")
    public List<GetUserDTO> getAllUsers() {
        return userService.findAll()
                .stream()
                .map(GetUserDTO::of)
                .toList();
    }

    // SOLO PARA ADMIN
    // ELIMINAR UN USUARIO POR SU ID
    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/admin/user/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
        userService.delete(id);
        return ResponseEntity.noContent().build();
    }

    // PROMOCIONAR UN USUARIO A ROL GESTOR (REQUISITO SECCIÓN 3a DEL ENUNCIADO)
    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/admin/users/{id}/promote")
    public ResponseEntity<GetUserDTO> promoteUser(@PathVariable Long id) {
        return ResponseEntity.ok(GetUserDTO.of(userService.promoteToGestor(id)));
    }

    // DEGRADAR UN GESTOR A ROL USUARIO NORMAL (REQUISITO SECCIÓN 3a DEL ENUNCIADO)
    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/admin/users/{id}/demote")
    public ResponseEntity<GetUserDTO> demoteUser(@PathVariable Long id) {
        return ResponseEntity.ok(GetUserDTO.of(userService.demoteToUser(id)));
    }
}