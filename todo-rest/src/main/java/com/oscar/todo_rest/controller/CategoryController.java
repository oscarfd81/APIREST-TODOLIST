package com.oscar.todo_rest.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import com.oscar.todo_rest.model.Category;
import com.oscar.todo_rest.repos.CategoryRepository;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class CategoryController {

    private final CategoryRepository categoryRepository;

    // ---- ADMIN ----
    // PERMITE AL ADMINISTRADOR OBTENER LA LISTA COMPLETA DE TODAS LAS CATEGORÍAS EXISTENTES EN EL SISTEMA.
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/admin/categories")
    public List<Category> adminList() {
        return categoryRepository.findAll();
    }

    // PERMITE AL ADMINISTRADOR CREAR Y GUARDAR UNA NUEVA CATEGORÍA EN LA BASE DE DATOS.
    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/admin/categories")
    public ResponseEntity<Category> adminCreate(@RequestBody Category category) {
        return ResponseEntity.status(HttpStatus.CREATED).body(categoryRepository.save(category));
    }

    // PERMITE AL ADMINISTRADOR EDITAR EL NOMBRE DE UNA CATEGORÍA EXISTENTE BUSCÁNDOLA POR SU ID.
    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/admin/categories/{id}")
    public Category adminUpdate(@PathVariable Long id, @RequestBody Category newCat) {
        return categoryRepository.findById(id).map(c -> {
            c.setName(newCat.getName());
            return categoryRepository.save(c);
        }).orElseThrow(() -> new RuntimeException("Category not found"));
    }

    // PERMITE AL ADMINISTRADOR ELIMINAR COMPLETAMENTE UNA CATEGORÍA DEL SISTEMA UTILIZANDO SU ID.
    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/admin/categories/{id}")
    public ResponseEntity<Void> adminDelete(@PathVariable Long id) {
        categoryRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }


    // ---- GESTOR ----
    // PERMITE AL GESTOR LISTAR TODAS LAS CATEGORÍAS DISPONIBLES EN LA APLICACIÓN.
    @PreAuthorize("hasRole('GESTOR')")
    @GetMapping("/manager/categories")
    public List<Category> gestorList() {
        return categoryRepository.findAll();
    }

    // PERMITE AL GESTOR DAR DE ALTA UNA NUEVA CATEGORÍA EN EL SISTEMA.
    @PreAuthorize("hasRole('GESTOR')")
    @PostMapping("/manager/categories")
    public ResponseEntity<Category> gestorCreate(@RequestBody Category category) {
        return ResponseEntity.status(HttpStatus.CREATED).body(categoryRepository.save(category));
    }

    // PERMITE AL GESTOR MODIFICAR LOS DATOS O EL NOMBRE DE UNA CATEGORÍA ESPECÍFICA MEDIANTE SU ID.
    @PreAuthorize("hasRole('GESTOR')")
    @PutMapping("/manager/categories/{id}")
    public Category gestorUpdate(@PathVariable Long id, @RequestBody Category newCat) {
        return categoryRepository.findById(id).map(c -> {
            c.setName(newCat.getName());
            return categoryRepository.save(c);
        }).orElseThrow(() -> new RuntimeException("Category not found"));
    }

    // PERMITE AL GESTOR BORRAR UNA CATEGORÍA CONCRETA DE LA BASE DE DATOS PASANDO SU ID.
    @PreAuthorize("hasRole('GESTOR')")
    @DeleteMapping("/manager/categories/{id}")
    public ResponseEntity<Void> gestorDelete(@PathVariable Long id) {
        categoryRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}