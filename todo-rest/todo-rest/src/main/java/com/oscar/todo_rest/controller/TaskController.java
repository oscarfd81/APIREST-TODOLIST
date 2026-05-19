package com.oscar.todo_rest.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.oscar.todo_rest.service.TaskService;
import com.oscar.todo_rest.users.User;

import lombok.RequiredArgsConstructor;

import com.oscar.todo_rest.dto.EditTaskCommand;
import com.oscar.todo_rest.dto.GetTaskDto;
import com.oscar.todo_rest.model.Task;

import java.util.List;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PutMapping;




@RestController
@RequestMapping("/task/")
@RequiredArgsConstructor
public class TaskController {

    private final TaskService taskService;

    @GetMapping
    public List<GetTaskDto> getAll(
        @AuthenticationPrincipal User author
    ) {
        return taskService.findByAuthor(author)
                .stream()
                .map(GetTaskDto::of)
                .toList();
    }

    // Si el usuario autenticado es el autor de la tarea, se permite el acceso a los detalles de la tarea, si no se lanza una excepción
    @PostAuthorize("""
        returnObject.author.username == authentication.principal.username
    """)
    @GetMapping("/{id}")
    public Task getById(@PathVariable Long id) {
        return taskService.findById(id);
    }

    @PostMapping
    public ResponseEntity<GetTaskDto> create(
        @RequestBody EditTaskCommand cmd,
        // Con esto recuperamos el autor registrado
        @AuthenticationPrincipal User author) {
        return ResponseEntity.status(HttpStatus.CREATED).body(GetTaskDto.of(taskService.save(cmd, author)));
    }   
    
    // Si el autor de la tarea coincide con el usuario autenticado, se permite la edición
    @PreAuthorize("""
        @ownerCheck.check(#id, authentication.principal.getId())
    """)
    @PutMapping("/{id}")
    public GetTaskDto edit(@RequestBody EditTaskCommand cmd, @PathVariable Long id) {
        return GetTaskDto.of(taskService.edit(cmd, id));
    }   

    // Si el autor de la tarea coincide con el usuario autenticado, se permite la eliminación
    @PreAuthorize("""
        @ownerCheck.check(#id, authentication.principal.getId())
    """)
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) 
    {
        taskService.delete(id);
        return ResponseEntity.noContent().build();
    }
    
}