package com.oscar.todo_rest.dto;
import com.oscar.todo_rest.model.User;

// DTO DE SALIDA: SE USA PARA LA GESTION COMPLETA DEL ADMINISTRADOR (LISTAR USUARIOS, PROMOCIONAR USUARIOS, ETC)
// MUESTRA TODA LA INFORMACIÓN DEL USUARIO PERMITIENDO AL ADMIN MOLDEARLA A SU GUSTO
public record GetUserDTO(
    Long id,
    String username,
    String email,
    boolean isAdmin,
    boolean isGestor,
    boolean isUser
){
    public static GetUserDTO of(User u) {
        return new GetUserDTO(
            u.getId(),
            u.getUsername(),
            u.getEmail(),
            u.isAdmin(),
            u.isGestor(),
            u.isUser()
        );
    }
}