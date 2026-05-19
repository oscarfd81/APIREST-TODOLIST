package com.oscar.todo_rest.model;

import java.time.LocalDateTime;

import com.oscar.todo_rest.enums.enumPrio;
import com.oscar.todo_rest.enums.enumStat;
import com.oscar.todo_rest.users.User;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Lob;
import jakarta.persistence.ManyToOne;
import lombok.*;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
public class Task {
    
    @Id @GeneratedValue
    private Long id;

    @Builder.Default
    private LocalDateTime createdAt = LocalDateTime.now();

    private String title;   

    @Lob
    private String description;

    private LocalDateTime deadline;

    private boolean important;

    private LocalDateTime updatedAt;

    @Enumerated(EnumType.STRING)
    private enumStat status;

    @Enumerated(EnumType.STRING)
    private enumPrio priority;

    

    @ManyToOne
    private User author;

    @ManyToOne
    private Category category;

    @ManyToOne
    private Tag tag;

}


