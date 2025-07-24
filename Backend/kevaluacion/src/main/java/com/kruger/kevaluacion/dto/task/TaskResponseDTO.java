package com.kruger.kevaluacion.dto.task;

import com.kruger.kevaluacion.entity.TaskStatus;

import java.time.LocalDateTime;

/**
 * DTO de salida para una tarea.
 */
public record TaskResponseDTO(
        Long id,
        String title,
        String description,
        TaskStatus status,
        LocalDateTime dueDate,
        LocalDateTime createdAt,
        Long projectId,
        String assignedToUsername
) {}
