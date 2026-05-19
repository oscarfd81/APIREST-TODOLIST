package com.oscar.todo_rest.users;

public record NewUserCommand(
    String username, String email, String password) {
}