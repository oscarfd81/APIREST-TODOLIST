package com.oscar.todo_rest.security;

import com.oscar.todo_rest.repos.TaskRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import com.oscar.todo_rest.model.Task;

// ESTE COMPONENTE VERIFICA SI UN USUARIO ES EL DUEÑO AUTOR DE UNA TAREA PARA CONTROLAR LOS PERMISOS
@Component
@RequiredArgsConstructor
public class OwnerCheck {
    private final TaskRepository taskRepository;    

    // PRIMER METODO: COMPRUEBA UNA TAREA QUE YA ESTA CARGADA EN MEMORIA SIN VOLVER A CONSULTAR LA BASE DE DATOS  
    public boolean check(Task task, Long UserId) {
       if (task!=null && task.getAuthor()!=null) {
            return task.getAuthor().getId().equals(UserId);
        }
        return false;
    }

    // SEGUNDO METODO: BUSCA LA TAREA POR ID EN LA BASE DE DATOS PARA COMPROBAR EL PROPIETARIO ANTES DE ENTRAR AL CONTROLADOR
    public boolean check(Long taskId, Long userId) {
        return taskRepository.findById(taskId)
                .map(t -> t.getAuthor().getId().equals(userId))
                .orElse(false);
    }
}