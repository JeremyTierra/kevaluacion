package com.kruger.kevaluacion.mapper;

import com.kruger.kevaluacion.dto.task.TaskResponseDTO;
import com.kruger.kevaluacion.entity.Task;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface TaskMapper {

    @Mapping(source = "assignedTo.username", target = "assignedToUsername")
    @Mapping(source = "project.id", target = "projectId")
    TaskResponseDTO toDTO(Task task);
}
