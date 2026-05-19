package com.oscar.todo_rest.repos;

import org.springframework.data.jpa.repository.JpaRepository;
import com.oscar.todo_rest.model.Task;
import com.oscar.todo_rest.model.Tag;
import com.oscar.todo_rest.model.Category;
import com.oscar.todo_rest.users.User;
import java.util.List;

public interface TaskRepository extends JpaRepository<Task, Long> {
    
    List<Task> findByAuthor(User author);
    
    List<Task> findByCategory(Category category);
    
    // NUEVO: SE DECLARA ESTE METODO PARA QUE SPRING JPA BUSQUE TAREAS QUE TENGAN EL TAG DENTRO DE SU LISTA MANYTOMANY
    List<Task> findByTagsContains(Tag tag);
}