package com.oscar.todo_rest.model;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table (name = "tagd")
public class Tag {
    
    @Id
    @GeneratedValue
    private Long id;

    private String name;

    @OneToMany(mappedBy = "tag")
    @JsonIgnore
    private List<Task> tasks;
}

