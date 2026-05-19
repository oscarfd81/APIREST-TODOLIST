package com.oscar.todo_rest.status;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

public interface StatusRepository extends JpaRepository<Status, Long> {
    // METODO PARA BUSCAR POR NOMBRE DE LA CATEGORIA
    Optional<Status> findByName(String name);

}