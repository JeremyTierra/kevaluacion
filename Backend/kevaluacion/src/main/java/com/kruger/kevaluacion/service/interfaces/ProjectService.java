package com.kruger.kevaluacion.service.interfaces;

import com.kruger.kevaluacion.dto.project.ProjectRequestDTO;
import com.kruger.kevaluacion.dto.project.ProjectResponseDTO;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.List;

public interface ProjectService {
    ProjectResponseDTO create(ProjectRequestDTO dto, UserDetails userDetails);
    List<ProjectResponseDTO> findAllByUser(UserDetails userDetails);
    ProjectResponseDTO update(Long id, ProjectRequestDTO dto, UserDetails userDetails);
    void delete(Long id, UserDetails userDetails);
}
