package com.oscar.todo_rest.dto;

// DTO DE ENTRADA: SE USA EXCLUSIVAMENTE CUANDO UN USUARIO NUEVO SE REGISTRA DESDE FUERA.
public record NewUserCommand(
    String username, String email, String password) {
}