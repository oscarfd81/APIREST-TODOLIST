package com.oscar.todo_rest.category;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.oscar.todo_rest.model.Task;

import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "cat")
public class Category {
    
    @Id
    @GeneratedValue
    private Long id;

    private String name;

    @OneToMany(mappedBy = "category")
    @JsonIgnore
    private List<Task> tasks;
}