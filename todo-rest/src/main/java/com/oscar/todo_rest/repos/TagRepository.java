package com.oscar.todo_rest.repos;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.oscar.todo_rest.model.Tag;

public interface TagRepository extends JpaRepository<Tag, Long> {
    Optional<Tag> findByName(String Name);

}