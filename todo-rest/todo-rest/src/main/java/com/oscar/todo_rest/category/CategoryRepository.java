package com.oscar.todo_rest.category;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category, Long> {
    // METODO PARA BUSCAR POR NOMBRE DE LA CATEGORIA
    Optional<Category> findByName(String name);
}