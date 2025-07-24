package com.kruger.kevaluacion.dto.project;

import java.time.LocalDateTime;

public record ProjectResponseDTO(
        Long id,
        String name,
        String description,
        LocalDateTime createdAt
) {}
