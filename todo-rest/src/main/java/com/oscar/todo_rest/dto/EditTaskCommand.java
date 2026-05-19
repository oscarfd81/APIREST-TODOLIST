package com.oscar.todo_rest.dto;


import com.fasterxml.jackson.annotation.JsonFormat;
import com.oscar.todo_rest.enums.enumStat;

import java.time.LocalDateTime;

public record EditTaskCommand(
    String title,
    String description,
    enumStat status,
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    LocalDateTime deadline,
    String categoryName,
    String statusName,
    String tagName
){}