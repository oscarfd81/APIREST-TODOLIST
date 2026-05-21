package com.oscar.todo_rest.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import com.oscar.todo_rest.service.TaskService;

import lombok.RequiredArgsConstructor;

import com.oscar.todo_rest.dto.EditTaskCommand;
import com.oscar.todo_rest.dto.GetTaskDto;
import com.oscar.todo_rest.model.Task;
import com.oscar.todo_rest.model.User;
import com.oscar.todo_rest.enums.enumStat;
import com.oscar.todo_rest.enums.enumPrio;

// IMPORTS SWAGGER
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

import java.util.List;

@RestController
@RequestMapping("/task")
@RequiredArgsConstructor
@Tag(name = "1. Gestión de Tareas", description = "Endpoints principales para crear, editar, eliminar y filtrar tareas de usuario")
public class TaskController {

    private final TaskService taskService;
    
    // COMANDO HASANYROLE SIRVE PARA COMPROBAR SI EL USUARIO TIENE ESOS ROLES
    @PreAuthorize("hasAnyRole('USER', 'GESTOR', 'ADMIN')")
    @GetMapping
    @Operation(summary = "Listar todas las tareas")
    public List<GetTaskDto> getAll(@AuthenticationPrincipal User author) {
        return taskService.findByAuthor(author)
                .stream()
                .map(GetTaskDto::of)
                .toList();
    }

    // Si el usuario autenticado es el autor de la tarea, se permite el acceso a los detalles de la tarea, si no se lanza una excepción
    @PreAuthorize("hasAnyRole('USER', 'GESTOR', 'ADMIN')")
    @PostAuthorize("returnObject.author.username == authentication.principal.username")
    @GetMapping("/{id}")
    @Operation(summary = "Obtener una tarea por ID")
    public Task getById(@PathVariable Long id) {
        return taskService.findById(id);
    }

    @PostMapping
    @PreAuthorize("hasAnyRole('USER', 'GESTOR', 'ADMIN')")
    @Operation(summary = "Crear nueva tarea")
    public ResponseEntity<GetTaskDto> create(
        @RequestBody EditTaskCommand cmd,
        // Con esto recuperamos el autor registrado
        @AuthenticationPrincipal User author) {
        return ResponseEntity.status(HttpStatus.CREATED).body(GetTaskDto.of(taskService.save(cmd, author)));
    }   
 
    // Si el autor de la tarea coincide con el usuario autenticado, se permite la edición
    @PreAuthorize("hasAnyRole('USER', 'GESTOR', 'ADMIN') and @ownerCheck.check(#id, principal.id)")
    @PutMapping("/{id}")
    @Operation(summary = "Editar una tarea")
    public GetTaskDto edit(@RequestBody EditTaskCommand cmd, @PathVariable Long id) {
        return GetTaskDto.of(taskService.edit(cmd, id));
    }   

    // Si el autor de la tarea coincide con el usuario autenticado, se permite la eliminación
    @PreAuthorize("hasAnyRole('USER', 'GESTOR', 'ADMIN') and @ownerCheck.check(#id, principal.id)")
    @DeleteMapping("/{id}")
    @Operation(summary = "Eliminar una tarea")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        taskService.delete(id);
        return ResponseEntity.noContent().build();
    }
    
    // DEVUELVE LISTA CON TASKS CON ESE TAGNAME
    @PreAuthorize("hasAnyRole('USER', 'GESTOR', 'ADMIN')")
    @GetMapping("/tag/{tagName}")
    @Operation(summary = "Buscar tareas por etiqueta")
    public List<GetTaskDto> getByTagName(@PathVariable String tagName) {
        return taskService.findByTag(tagName)
                .stream()
                .map(GetTaskDto::of)
                .toList();
    }

    // ASIGNAR UN TAG A TASK
    // NUEVO: Ruta adaptada a POST /task/{id}/tags según pide la lista de cotejo
    @PreAuthorize("hasAnyRole('USER', 'GESTOR', 'ADMIN') and @ownerCheck.check(#id, principal.id)")
    @PostMapping("/{id}/tags")
    @Operation(summary = "Asignar etiqueta a tarea")
    public GetTaskDto assignTag(@PathVariable Long id, @RequestParam String tagName) {
        return GetTaskDto.of(taskService.assignTagToTask(id, tagName));
    }

    // ELIMINAR TAG DE UN TASK
    // LE PASAMOS TAMBIÉN EL TAGNAME POR REQUESTPARAM PARA SABER CUÁL DE LAS ETIQUETAS QUEREMOS QUITAR DE LA LISTA
    // NUEVO: Cambiado a DELETE /task/{id}/tags y usa la lógica del servicio extrayendo el nombre
    @PreAuthorize("hasAnyRole('USER', 'GESTOR', 'ADMIN') and @ownerCheck.check(#id, principal.id)")
    @DeleteMapping("/{id}/tags")
    @Operation(summary = "Desasignar etiqueta de tarea")
    public GetTaskDto removeTag(@PathVariable Long id, @RequestParam String tagName) {
        return GetTaskDto.of(taskService.removeTagFromTask(id, tagName));
    }

    // FILTRAR POR TITULO ESTADO O PRIORIDAD (Search?)
    @PreAuthorize("hasAnyRole('USER', 'GESTOR', 'ADMIN')")
    @GetMapping("/search")
    @Operation(summary = "Buscador unificado y dinámico")
    public List<GetTaskDto> searchTasks(
            @RequestParam(required = false) String title,
            @RequestParam(required = false) enumStat status,
            @RequestParam(required = false) enumPrio priority) {
        
            List<Task> tasks;
        
            if (title != null) {
                tasks = taskService.findByTitleContaining(title);
            } else if (status != null) {
                tasks = taskService.findByStatus(status);
            } else if (priority != null) {
                tasks = taskService.findByPriority(priority); 
            } else {
                tasks = taskService.findAll();
            }

            return tasks.stream().map(GetTaskDto::of).toList();
    }

    // ENDPOINT PARA LAS ESTADÍSTICAS DEL USUARIO
    @PreAuthorize("hasAnyRole('USER', 'GESTOR', 'ADMIN')")
    @GetMapping("/dashboard")
    @Operation(summary = "Estadísticas del Dashboard")
    public ResponseEntity<com.oscar.todo_rest.dto.DashboardResponse> 
        getDashboard(@AuthenticationPrincipal User author) {
        return ResponseEntity.ok(taskService.getDashboardStats(author));
    }
}