package com.oscar.todo_rest.service;

import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.stereotype.Service;

import com.oscar.todo_rest.category.Category;
import com.oscar.todo_rest.category.CategoryRepository;
import com.oscar.todo_rest.dto.EditTaskCommand;
import com.oscar.todo_rest.error.TaskNotFoundException;
import com.oscar.todo_rest.model.Task;
import com.oscar.todo_rest.repos.TaskRepository;
import com.oscar.todo_rest.status.Status;
import com.oscar.todo_rest.status.StatusRepository;
import com.oscar.todo_rest.tag.Tag;
import com.oscar.todo_rest.tag.TagRepository;
import com.oscar.todo_rest.users.User;

@Service
@RequiredArgsConstructor
public class TaskService {
    
    private final TaskRepository taskRepository;
    private final CategoryRepository categoryRepository;
    private final StatusRepository statusRepository;
    private final TagRepository tagRepository;

    public List<Task> findAll() {
        List<Task> result = taskRepository.findAll();
        
        if (result.isEmpty()) {
            throw new TaskNotFoundException();
        }
        
        return result;
    }

    public List<Task> findByAuthor(User author) {
        List<Task> result = taskRepository.findByAuthor(author);
        
        if (result.isEmpty()) {
            throw new TaskNotFoundException();
        }
        
        return result;
    }


    public Task findById(Long id) {
        return taskRepository.findById(id)
                .orElseThrow(() -> new TaskNotFoundException(id));
    }


    // GUARDAR CAMBIOS TAREA
    public Task save(EditTaskCommand cmd, User author) {
        Category category = categoryRepository.findByName(cmd.categoryName())
                .orElseThrow();

        Status status = statusRepository.findByName(cmd.statusName())
                .orElseThrow();

        Tag tag= tagRepository.findByName(cmd.tagName())
                .orElseThrow();

        return taskRepository.save(
            Task.builder()
                .title(cmd.title())
                .description(cmd.description())
                .deadline(cmd.deadline())
                .author(author)
                .category(category)
                .status(status)
                .tag(tag)
                .updatedAt(LocalDateTime.now())
                .build()
        );
    }

    public Task edit(EditTaskCommand cmd, Long id) {
        return taskRepository.findById(id).map(t -> {

            Category category = categoryRepository.findByName(cmd.categoryName())
                .orElseThrow();

            Status status = statusRepository.findByName(cmd.statusName())
                    .orElseThrow();

            Tag tag= tagRepository.findByName(cmd.tagName())
                    .orElseThrow();

            t.setTitle(cmd.title());
            t.setDescription(cmd.description());
            t.setDeadline(cmd.deadline());
            t.setUpdatedAt(LocalDateTime.now());
            t.setCategory(category);
            t.setStatus(status);
            t.setTag(tag);

            return taskRepository.save(t);

        }).orElseThrow(() -> new TaskNotFoundException(id));
    }

    
    public void delete(Long id) {
        taskRepository.deleteById(id);
    }   


}
