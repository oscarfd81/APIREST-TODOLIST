package com.oscar.todo_rest.repos;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.oscar.todo_rest.model.Category;

public interface CategoryRepository extends JpaRepository<Category, Long> {
    // METODO PARA BUSCAR POR NOMBRE DE LA CATEGORIA
    Optional<Category> findByName(String name);
}