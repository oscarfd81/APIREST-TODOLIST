package com.oscar.todo_rest.dto;
import com.oscar.todo_rest.users.User;

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