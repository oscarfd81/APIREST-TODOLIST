package com.oscar.todo_rest.repos;

import com.oscar.todo_rest.model.Task;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

import com.oscar.todo_rest.users.User;
import com.oscar.todo_rest.model.Category;
import com.oscar.todo_rest.model.Tag;




public interface TaskRepository extends JpaRepository<Task, Long> {
    
    List <Task>  findByAuthor(User author);

    List <Task>  findByTag(Tag tag);

    List<Task> findByCategory(Category category);
}
