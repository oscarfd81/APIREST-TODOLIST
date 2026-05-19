package com.oscar.todo_rest.security;

import com.oscar.todo_rest.repos.TaskRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

// ESTE COMPONENTE VERIFICA SI UN USUARIO ES EL DUEÑO AUTOR DE UNA TAREA PARA CONTROLAR LOS PERMISOS
@Component
@RequiredArgsConstructor
public class OwnerCheck {
    private final TaskRepository taskRepository;    

    // VALIDADOR DE SEGURIDAD: BUSCA LA TAREA EN LA BASE DE DATOS Y VERIFICA SI EL ID DE SU AUTOR COINCIDE CON EL DEL USUARIO LOGUEADO
    public boolean check(Long taskId, Long userId) {
        return taskRepository.findById(taskId)
                .map(t -> t.getAuthor().getId().equals(userId))
                .orElse(false);
    }
}