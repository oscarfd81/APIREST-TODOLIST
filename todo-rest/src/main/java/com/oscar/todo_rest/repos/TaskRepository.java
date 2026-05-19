package com.oscar.todo_rest.repos;

import org.springframework.data.jpa.repository.JpaRepository;
import com.oscar.todo_rest.model.Task;
import com.oscar.todo_rest.model.User;
import com.oscar.todo_rest.model.Tag;
import com.oscar.todo_rest.enums.enumPrio;
import com.oscar.todo_rest.enums.enumStat;
import com.oscar.todo_rest.model.Category;

import java.util.List;

public interface TaskRepository extends JpaRepository<Task, Long> {
    
    List<Task> findByAuthor(User author);
    
    List<Task> findByCategory(Category category);
    
    // CON CONTAINING BUSCA TITULOS QUE LO CONTENGAN NO QUE SEAN NECESARIAMENTE IGUALES
    List<Task> findByTitleContainingIgnoreCase(String title);

    // SE DECLARA ESTE METODO PARA QUE BUSQUE TAREAS QUE TENGAN EL TAG DENTRO DE SU LISTA 
    List<Task> findByTagsContains(Tag tag);

    // BUSCAR POR ESTADO (ENUM)
    List<Task> findByStatus(enumStat status);

    // BUSCAR POR PRIORIDAD (ENUM)
    List<Task> findByPriority(enumPrio priority);
}