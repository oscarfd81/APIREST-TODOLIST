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

import java.util.List;

@RestController
@RequestMapping("/task")
@RequiredArgsConstructor
public class TaskController {

    private final TaskService taskService;
    
    // COMANDO HASANYROLE SIRVE PARA COMPROBAR SI EL USUARIO TIENE ESOS ROLES
    @PreAuthorize("hasAnyRole('USER', 'GESTOR', 'ADMIN')")
    @GetMapping
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
    public Task getById(@PathVariable Long id) {
        return taskService.findById(id);
    }

    @PostMapping
    @PreAuthorize("hasAnyRole('USER', 'GESTOR', 'ADMIN')")
    public ResponseEntity<GetTaskDto> create(
        @RequestBody EditTaskCommand cmd,
        // Con esto recuperamos el autor registrado
        @AuthenticationPrincipal User author) {
        return ResponseEntity.status(HttpStatus.CREATED).body(GetTaskDto.of(taskService.save(cmd, author)));
    }   
 
    // Si el autor de la tarea coincide con el usuario autenticado, se permite la edición
    @PreAuthorize("hasAnyRole('USER', 'GESTOR', 'ADMIN') and @ownerCheck.check(#id, principal.id)")
    @PutMapping("/{id}")
    public GetTaskDto edit(@RequestBody EditTaskCommand cmd, @PathVariable Long id) {
        return GetTaskDto.of(taskService.edit(cmd, id));
    }   

    // Si el autor de la tarea coincide con el usuario autenticado, se permite la eliminación
    @PreAuthorize("hasAnyRole('USER', 'GESTOR', 'ADMIN') and @ownerCheck.check(#id, principal.id)")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        taskService.delete(id);
        return ResponseEntity.noContent().build();
    }
    
    // DEVUELVE LISTA CON TASKS CON ESE TAGNAME
    @PreAuthorize("hasAnyRole('USER', 'GESTOR', 'ADMIN')")
    @GetMapping("/tag/{tagName}")
    public List<GetTaskDto> getByTagName(@PathVariable String tagName) {
        return taskService.findByTag(tagName)
                .stream()
                .map(GetTaskDto::of)
                .toList();
    }

    // ASIGNAR UN TAG A TASK
    @PreAuthorize("hasAnyRole('USER', 'GESTOR', 'ADMIN') and @ownerCheck.check(#id, principal.id)")
    @PutMapping("/{id}/tag")
    public GetTaskDto assignTag(@PathVariable Long id, @RequestParam String tagName) {
        return GetTaskDto.of(taskService.assignTagToTask(id, tagName));
    }

    // ELIMINAR TAG DE UN TASK
    @PreAuthorize("hasAnyRole('USER', 'GESTOR', 'ADMIN') and @ownerCheck.check(#id, principal.id)")
    @PutMapping("/{id}/tag/remove")
    // LE PASAMOS TAMBIÉN EL TAGNAME POR REQUESTPARAM PARA SABER CUÁL DE LAS ETIQUETAS QUEREMOS QUITAR DE LA LISTA
    public GetTaskDto removeTag(@PathVariable Long id, @RequestParam String tagName) {
        return GetTaskDto.of(taskService.removeTagFromTask(id, tagName));
    }
}