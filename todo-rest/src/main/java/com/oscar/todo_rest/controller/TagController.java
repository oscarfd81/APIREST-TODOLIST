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

import com.oscar.todo_rest.model.Tag;
import com.oscar.todo_rest.repos.TagRepository;

@RestController
@RequestMapping("/tag/")
public class TagController {

    private final TagRepository tagRepository;

    public TagController(TagRepository tagRepository) {
        this.tagRepository = tagRepository;
    }

    @PreAuthorize("hasAnyRole('USER')")
    @GetMapping
    public List<Tag> getAll() {
        return tagRepository.findAll();
    }
    
    @PreAuthorize("hasAnyRole('USER')")
    @PostMapping
    public Tag create(@RequestBody Tag tag) {
        return tagRepository.save(tag);
    }

    @PreAuthorize("hasAnyRole('USER')")
    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        tagRepository.deleteById(id);
    }

    // ---------------- NO ES FINAL ----------------
    @PreAuthorize("hasAnyRole('USER')")
    @PutMapping("/{id}")
    public Tag update(@PathVariable Long id, @RequestBody Tag tagDetails) {

        return tagRepository.findById(id)
                .map(tag -> {
                    tag.setName(tagDetails.getName());
                    return tagRepository.save(tag);
                })
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Tag not found: " + id
                ));
    }

}