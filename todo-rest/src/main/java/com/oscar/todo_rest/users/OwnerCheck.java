package com.oscar.todo_rest.users;

import com.oscar.todo_rest.repos.TaskRepository;

import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Component;

import com.oscar.todo_rest.model.Task;


@Component
@RequiredArgsConstructor
public class OwnerCheck {
    private final TaskRepository taskRepository;    

    public boolean check(Task task, Long UserId) {
       if (task!=null && task.getAuthor()!=null) {
            return task.getAuthor().getId().equals(UserId);
        }
        return false;
    }

    public boolean check(Long taskId, Long userId) {
        return taskRepository.findById(taskId)
                .map(t -> t.getAuthor().getId().equals(userId))
                .orElse(false);
    }
    
}
