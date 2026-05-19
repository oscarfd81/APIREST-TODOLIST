package com.oscar.todo_rest.dto;

import com.oscar.todo_rest.model.User;

public record NewUserResponse(Long id, String username, String email) {
    // DTO DE SALIDA: SE USA PARA RESPONDER TRAS UN REGISTRO CORRECTO
    // FILTRA LOS DATOS DEL USUARIO CREADO PARA MOSTRAR SOLO LO BONITO (ID, USERNAME, EMAIL)
    public static NewUserResponse of(User user) {
        return new NewUserResponse(
                user.getId(),
                user.getUsername(),
                user.getEmail()
        );
    }

}