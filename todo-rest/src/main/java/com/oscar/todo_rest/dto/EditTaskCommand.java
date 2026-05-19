package com.oscar.todo_rest.dto;


import com.fasterxml.jackson.annotation.JsonFormat;
import com.oscar.todo_rest.enums.enumPrio;
import com.oscar.todo_rest.enums.enumStat;

import java.time.LocalDateTime;
import java.util.List;

// DTO DE ENTRADA: SIRVE PARA EDITAR TAREA
public record EditTaskCommand(
    String title,
    String description,
    enumStat status,
    enumPrio priority,
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    LocalDateTime deadline,
    String categoryName,
    String statusName,
    List<String> tagNames
){}