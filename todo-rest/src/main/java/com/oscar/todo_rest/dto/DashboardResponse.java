package com.oscar.todo_rest.dto;

//ESTADISTICAS GENERALES DE TAREAS
public record DashboardResponse(
    long totalTasks,
    long pendingTasks,
    long completedTasks,
    long processTasks,
    long passedTasks
) {}