package com.oscar.todo_rest.controller;

import org.springframework.web.bind.annotation.*;
import org.springframework.security.access.prepost.PreAuthorize;
import com.oscar.todo_rest.model.Category;
import com.oscar.todo_rest.repos.CategoryRepository;

import java.util.List;

@RestController
@RequestMapping("/category/")
public class CategoryController {

    private final CategoryRepository categoryRepository;

    public CategoryController(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    // COMANDO HASANYROLE SIRVE PARA COMPROBAR SI EL USUARIO TIENE ESOS ROLES
    @PreAuthorize("hasAnyRole('ADMIN', 'GESTOR','USER')")
    @GetMapping
    public List<Category> getAll() {
        return categoryRepository.findAll();
    }
    
    @PreAuthorize("hasAnyRole('ADMIN', 'GESTOR')")
    @GetMapping("/{id}")
    public Category getById(@PathVariable Long id) {
        return categoryRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Category not found"));
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'GESTOR')")
    @PostMapping
    public Category create(@RequestBody Category category) {
        return categoryRepository.save(category);
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'GESTOR')")
    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        categoryRepository.deleteById(id);
    }
    
    // ---------------- NO ES FINAL ----------------
    @PreAuthorize("hasAnyRole('ADMIN', 'GESTOR')")
    @PutMapping("/{id}")
    public Category update(@PathVariable Long id, @RequestBody Category categoryDetails) {

        return categoryRepository.findById(id)
                .map(category -> {
                    category.setName(categoryDetails.getName()); // ajusta campos si tienes más
                    return categoryRepository.save(category);
                })
                .orElseThrow(() -> new RuntimeException("Category not found"));
    }
}