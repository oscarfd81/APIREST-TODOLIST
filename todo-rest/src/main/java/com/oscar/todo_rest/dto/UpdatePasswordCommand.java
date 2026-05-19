package com.oscar.todo_rest.dto;

// DTO DE ENTRADA: SE USA CUANDO UN USUARIO QUIERE CAMBIAR SU CONTRASEÑA ACTUAL
public record UpdatePasswordCommand(String newPassword){}