package com.oscar.todo_rest.dto;

import com.oscar.todo_rest.enums.enumStat;
import com.oscar.todo_rest.model.Task;
import com.oscar.todo_rest.model.Tag;
import com.oscar.todo_rest.users.NewUserResponse;
import java.time.Duration;
import java.util.List;

import java.time.LocalDateTime;

public record GetTaskDto(
        Long id,
        String title,
        String description,
        enumStat status,
        LocalDateTime createdAt,
        LocalDateTime deadline,
        NewUserResponse author,
        String category,  
        // AL SER RELACION MANYTO MANY DEVUELVE UNA LISTA DE STRINGS CON LOS NOMBRES DE LOS TAGS
        List<String> tags,
        // IMPORTANT EN TAREA ES BOOLEAN PERO AQUÍ DEVUELVE UNA PALABRA (SI/NO), POR LO QUE LO DECLARAMOS COMO STRING
        String important,
        // NO METEMOS A TASK.MODEL, PUES ES ALGO QUE SE MUESTRA PERO NO ES UN DATO QUE SE INTRODUCE
        long daysBet
){
    public static GetTaskDto of(Task t) {

        // HAYAMOS DURACIÓN ENTRE AHORA Y LA FECHA LIMITE Y LA ACTUAL
        long daysBet= Duration.between(LocalDateTime.now(), t.getDeadline()).toDays();

        // RECORREMOS LA LISTA DE TAGS DE LA TAREA PARA SACAR SOLO SUS NOMBRES Y PASARLOS AL DTO
        List<String> nombresTags = t.getTags() != null 
                ? t.getTags().stream().map(Tag::getName).toList() 
                : List.of();

        return new GetTaskDto(
                t.getId(),
                t.getTitle(),
                t.getDescription(),
                t.getStatus(),
                t.getCreatedAt(),
                t.getDeadline(),
                NewUserResponse.of(t.getAuthor()),
                t.getCategory() != null ? t.getCategory().getName() : "VACIO",
                // LE PASAMOS LA NUEVA LISTA CON LOS NOMBRES DE LAS ETIQUETAS
                nombresTags,
                t.isImportant()? "SI":"NO",
                daysBet
        );
    }
}