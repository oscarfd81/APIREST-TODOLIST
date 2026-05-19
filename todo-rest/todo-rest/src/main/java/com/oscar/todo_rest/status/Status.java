package com.oscar.todo_rest.status;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.oscar.todo_rest.model.Task;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table (name = "statu")
public class Status {
    @GeneratedValue
    @Id
    private long id;
    
    private String name;

    @OneToMany(mappedBy = "status")
    @JsonIgnore
    private List<Task> tasks;
}
