package com.oscar.todo_rest.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

// IMPORTAMOS SOLO TU MODELO DE TAG
import com.oscar.todo_rest.model.Tag;
import com.oscar.todo_rest.repos.TagRepository;

// IMPORTAMOS SOLO LA OPERACIÓN DE SWAGGER (EL DE ANOTACIONES.TAG LO QUITAMOS PARA QUE NO CHOQUE)
import io.swagger.v3.oas.annotations.Operation;

@RestController
@RequestMapping("/tag/")
// PONEMOS LA RUTA COMPLETA DE LA ANOTACIÓN AQUÍ PARA EVITAR EL CHOQUE
@io.swagger.v3.oas.annotations.tags.Tag(name = "4. Gestión de Etiquetas (Tags)", description = "Operaciones CRUD sobre las etiquetas asignables a las tareas")
public class TagController {

    private final TagRepository tagRepository;

    public TagController(TagRepository tagRepository) {
        this.tagRepository = tagRepository;
    }

    @PreAuthorize("hasAnyRole('USER')")
    @GetMapping
    @Operation(summary = "Listar todas las etiquetas")
    public List<Tag> getAll() {
        return tagRepository.findAll();
    }
    
    @PreAuthorize("hasAnyRole('USER')")
    @PostMapping
    @Operation(summary = "Crear etiqueta")
    public Tag create(@RequestBody Tag tag) {
        return tagRepository.save(tag);
    }

    @PreAuthorize("hasAnyRole('USER')")
    @DeleteMapping("/{id}")
    @Operation(summary = "Eliminar etiqueta")
    public void delete(@PathVariable Long id) {
        tagRepository.deleteById(id);
    }

    @PreAuthorize("hasAnyRole('USER')")
    @PutMapping("/{id}")
    @Operation(summary = "Actualizar etiqueta")
    public Tag update(@PathVariable Long id, @RequestBody Tag tagDetails) {

        return tagRepository.findById(id)
                .map(tag -> {
                    // SELECCIONA TAG Y CAMBIA EL NOMBRE ANTERIOR POR EL DE LOS DATOS AÑADIDOS
                    tag.setName(tagDetails.getName());
                    return tagRepository.save(tag);
                })
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Tag not found: " + id
                ));
    }
}