package com.kruger.kevaluacion.dto.task;

import com.kruger.kevaluacion.entity.TaskStatus;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;

/**
 * DTO para crear o actualizar una tarea.
 */
public record TaskRequestDTO(
        @NotBlank String title,
        String description,
        @NotNull TaskStatus status,
        @NotNull Long assignedToUserId,
        @NotNull Long projectId,
        @FutureOrPresent LocalDateTime dueDate
) {}
