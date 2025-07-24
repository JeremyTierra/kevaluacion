package com.kruger.kevaluacion.dto.project;

import jakarta.validation.constraints.NotBlank;

public record ProjectRequestDTO(
        @NotBlank String name,
        String description
) {}
