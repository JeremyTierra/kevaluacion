package com.kruger.kevaluacion.mapper;

import com.kruger.kevaluacion.dto.project.ProjectRequestDTO;
import com.kruger.kevaluacion.dto.project.ProjectResponseDTO;
import com.kruger.kevaluacion.entity.Project;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ProjectMapper {

    ProjectResponseDTO toDTO(Project entity);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "owner", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    Project toEntity(ProjectRequestDTO dto);
}
