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

// IMPORTS SWAGGER
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

import java.util.List;

@RestController
@RequiredArgsConstructor
@Tag(name = "2. Autenticación y Usuarios", description = "Endpoints para registro, perfiles y panel de administración para control de roles")
public class UserController {

    private final UserService userService;

    // REGISTRO PÚBLICO: ACCESIBLE POR CUALQUIERA (CONFIGURADO EN SECURITYCONFIG)
    @PostMapping("/auth/register")
    @Operation(summary = "Registro público de usuarios")
    public ResponseEntity<NewUserResponse> createUser(@RequestBody NewUserCommand cmd) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(NewUserResponse.of(userService.register(cmd)));
    }

    // CAMBIAR CONTRASEÑA: CUALQUIER USUARIO LOGUEADO PUEDE CAMBIAR LA SUYA PROPIA
    @PreAuthorize("hasAnyRole('USER', 'GESTOR', 'ADMIN')")
    @PutMapping("/user/change-password")
    @Operation(summary = "Cambiar contraseña propia")
    public ResponseEntity<Void> changePassword(
            @AuthenticationPrincipal User user, 
            @RequestBody UpdatePasswordCommand cmd) {
        
        userService.changePassword(user, cmd.newPassword());
        return ResponseEntity.noContent().build(); 
    }

    // MODIFICAR EL PERFIL DEL USUARIO AUTENTICADO 
    @PreAuthorize("hasAnyRole('USER', 'GESTOR', 'ADMIN')")
    @PutMapping("/user/profile")
    @Operation(summary = "Modificar el perfil")
    public ResponseEntity<NewUserResponse> updateProfile(
            @AuthenticationPrincipal User user,
            @RequestBody NewUserCommand cmd) {
        user.setUsername(cmd.username());
        user.setEmail(cmd.email());
        return ResponseEntity.ok(NewUserResponse.of(userService.update(user)));
    }

    // SOLO PARA ADMIN
    // LISTAR TODOS LOS USUARIOS DEL SISTEMA
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/admin/users")
    @Operation(summary = "Listar todos los usuarios (ADMIN)")
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
    @Operation(summary = "Eliminar un usuario (ADMIN)")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
        userService.delete(id);
        return ResponseEntity.noContent().build();
    }

    // PROMOCIONAR UN USUARIO A ROL GESTOR (REQUISITO SECCIÓN 3a DEL ENUNCIADO)
    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/admin/users/{id}/promote")
    @Operation(summary = "Promocionar usuario a GESTOR")
    public ResponseEntity<GetUserDTO> promoteUser(@PathVariable Long id) {
        return ResponseEntity.ok(GetUserDTO.of(userService.promoteToGestor(id)));
    }

    // DEGRADAR UN GESTOR A ROL USUARIO NORMAL (REQUISITO SECCIÓN 3a DEL ENUNCIADO)
    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/admin/users/{id}/demote")
    @Operation(summary = "Degradar GESTOR a USER")
    public ResponseEntity<GetUserDTO> demoteUser(@PathVariable Long id) {
        return ResponseEntity.ok(GetUserDTO.of(userService.demoteToUser(id)));
    }
}