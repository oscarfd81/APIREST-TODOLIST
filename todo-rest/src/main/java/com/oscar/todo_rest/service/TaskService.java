package com.oscar.todo_rest.service;

import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;


import org.springframework.stereotype.Service;

import com.oscar.todo_rest.dto.EditTaskCommand;
import com.oscar.todo_rest.enums.enumPrio;
import com.oscar.todo_rest.enums.enumStat;
import com.oscar.todo_rest.error.TaskNotFoundException;
import com.oscar.todo_rest.model.Category;
import com.oscar.todo_rest.model.Tag;
import com.oscar.todo_rest.model.Task;
import com.oscar.todo_rest.model.User;
import com.oscar.todo_rest.repos.CategoryRepository;
import com.oscar.todo_rest.repos.TagRepository;
import com.oscar.todo_rest.repos.TaskRepository;

@Service
@RequiredArgsConstructor
public class TaskService {
    
    private final TaskRepository taskRepository;
    private final CategoryRepository categoryRepository;
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

        // BUSCAMOS LA LISTA DE TAGS COMPLETA QUE VIENE DESDE EL DTO
        List<Tag> tags = cmd.tagNames().stream()
                .map(name -> tagRepository.findByName(name)
                        .orElseThrow(() -> new RuntimeException("Tag no encontrado: " + name)))
                .toList();

        return taskRepository.save(
            Task.builder()
                .title(cmd.title())
                .description(cmd.description())
                .deadline(cmd.deadline())
                .author(author)
                .category(category)
                .tags(tags)
                .updatedAt(LocalDateTime.now())
                .build()
        );
    }

    public Task edit(EditTaskCommand cmd, Long id) {
        return taskRepository.findById(id).map(t -> {

            Category category = categoryRepository.findByName(cmd.categoryName())
                .orElseThrow();

            // BUSCAMOS LA NUEVA LISTA DE TAGS PARA REEMPLAZAR LA ANTERIOR EN LA EDICION
            List<Tag> tags = cmd.tagNames().stream()
                .map(name -> tagRepository.findByName(name)
                        .orElseThrow(() -> new RuntimeException("Tag no encontrado: " + name)))
                .toList();

            t.setTitle(cmd.title());
            t.setDescription(cmd.description());
            t.setDeadline(cmd.deadline());
            t.setUpdatedAt(LocalDateTime.now());
            t.setCategory(category);
            // ACTUALIZAMOS LA LISTA DE TAGS DE LA TAREA
            t.setTags(tags);

            return taskRepository.save(t);

        }).orElseThrow(() -> new TaskNotFoundException(id));
    }

    
    public void delete(Long id) {
        taskRepository.deleteById(id);
    }   

    // BUSCAR POR TAG
    public List<Task> findByTag(String nameTag) {
        Tag tag= tagRepository.findByName(nameTag).orElseThrow(()-> new RuntimeException( "Tag not found"));

        // USAMOS EL METODO CONTAINS DEL REPOSITORIO PARA BUSCAR DENTRO DE LA LISTA DE LA TABLA INTERMEDIA
        List <Task> listTask = taskRepository.findByTagsContains(tag);

        if (listTask.isEmpty()) {
            throw new RuntimeException("Tasks with these TagName not found");
        } else{
            return listTask;
        }
    }

    // BUSCAR POR CATEGORIA 
    public List <Task> findByCategory(String categoryName){
        Category cat= categoryRepository.findByName(categoryName).orElseThrow(()-> new RuntimeException( "Category not found"));
        
        List <Task> listTask= taskRepository.findByCategory(cat);

        if(listTask.isEmpty()){
            throw new RuntimeException("Tasks with these Category not found");
        } else{
            return listTask;
        }
    }
    
    // METODO PARA ASIGNAR TAG A UNA TAREA
    public Task assignTagToTask(Long taskId, String tagName) {
        Task task = taskRepository.findById(taskId)
                .orElseThrow(() -> new RuntimeException("Task not found"));

        Tag tag = tagRepository.findByName(tagName)
                .orElseThrow(() -> new RuntimeException("Tag not found"));

        // EN LUGAR DE REEMPLAZAR, AÑADIMOS EL NUEVO TAG A LA LISTA QUE YA TIENE LA TAREA
        task.getTags().add(tag);

        return taskRepository.save(task);
    }

    // METODO PARA ELIMINAR TAG DE UN TASK
    // PASAMOS EL TAGNAME PARA SABER EXACTAMENTE QUE TAG QUEREMOS QUITAR DE LA LISTA
    public Task removeTagFromTask(Long taskId, String tagName) {
        Task task = taskRepository.findById(taskId)
                .orElseThrow(() -> new RuntimeException("Task not found"));

        Tag tag = tagRepository.findByName(tagName)
                .orElseThrow(() -> new RuntimeException("Tag not found"));

        // ELIMINAMOS SOLO EL TAG SELECCIONADO DE LA LISTA DE LA TAREA
        task.getTags().remove(tag);

        return taskRepository.save(task);
    }

    // BUSCA TITULO QUE CONTENGA
    public List<Task> findByTitleContaining(String title) {
        List<Task> result = taskRepository.findByTitleContainingIgnoreCase(title);
        if (result.isEmpty()) {
            throw new RuntimeException("Not task found with this title");
        }
        return result;
    }

    // FILTRA POR ESTADO
    public List<Task> findByStatus(enumStat status) {
        List<Task> result = taskRepository.findByStatus(status);
        if (result.isEmpty()) {
            throw new RuntimeException("Not task found with this status");
        }
        return result;
    }

    //---------------NUEVO-------------------
    // NUEVO: Conecta con el repositorio para filtrar por la prioridad (enumPrio)
    public List<Task> findByPriority(enumPrio priority) {
        List<Task> result = taskRepository.findByPriority(priority);
        if (result.isEmpty()) {
            throw new RuntimeException("Not task found with this priority");
        }
        return result;
    }

    // NUEVO: CALCULA LAS ESTADÍSTICAS PARA EL DASHBOARD DEL USUARIO
    public com.oscar.todo_rest.dto.DashboardResponse getDashboardStats(User author) {
        List<Task> userTasks = taskRepository.findByAuthor(author);
        
        long total = userTasks.size();
        
        long pending = userTasks.stream().filter(t -> t.getStatus() == com.oscar.todo_rest.enums.enumStat.PENDIENTE).count();
        long process = userTasks.stream().filter(t -> t.getStatus() == com.oscar.todo_rest.enums.enumStat.EN_PROCESO).count();
        long completed = userTasks.stream().filter(t -> t.getStatus() == com.oscar.todo_rest.enums.enumStat.HECHO).count();
        long passed = userTasks.stream().filter(t -> t.getStatus() == com.oscar.todo_rest.enums.enumStat.NO_HECHO).count();

        
        return new com.oscar.todo_rest.dto.DashboardResponse(total, pending, completed,process,passed);
    }
}