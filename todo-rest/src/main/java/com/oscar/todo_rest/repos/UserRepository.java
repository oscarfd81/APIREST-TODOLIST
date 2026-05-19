package com.oscar.todo_rest.repos;

import org.springframework.data.jpa.repository.JpaRepository;

import com.oscar.todo_rest.model.User;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findFirstByUsername(String username);

}
