package com.kruger.kevaluacion.service.impl;

import com.kruger.kevaluacion.dto.project.ProjectRequestDTO;
import com.kruger.kevaluacion.dto.project.ProjectResponseDTO;
import com.kruger.kevaluacion.entity.Project;
import com.kruger.kevaluacion.entity.User;
import com.kruger.kevaluacion.mapper.ProjectMapper;
import com.kruger.kevaluacion.repository.ProjectRepository;
import com.kruger.kevaluacion.repository.UserRepository;
import com.kruger.kevaluacion.service.interfaces.ProjectService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ProjectServiceImpl implements ProjectService {

    private final ProjectRepository projectRepository;
    private final UserRepository userRepository;
    private final ProjectMapper projectMapper;

    @Override
    public ProjectResponseDTO create(ProjectRequestDTO dto, UserDetails userDetails) {
        User user = userRepository.findByUsername(userDetails.getUsername())
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        Project project = projectMapper.toEntity(dto);
        project.setOwner(user);
        project.setCreatedAt(LocalDateTime.now());

        return projectMapper.toDTO(projectRepository.save(project));
    }

    @Override
    public List<ProjectResponseDTO> findAllByUser(UserDetails userDetails) {
        User user = userRepository.findByUsername(userDetails.getUsername())
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        return projectRepository.findByOwner(user)
                .stream()
                .map(projectMapper::toDTO)
                .toList();
    }

    @Override
    public ProjectResponseDTO update(Long id, ProjectRequestDTO dto, UserDetails userDetails) {
        Project project = projectRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Proyecto no encontrado"));

        if (!project.getOwner().getUsername().equals(userDetails.getUsername())) {
            throw new RuntimeException("No tienes permiso para editar este proyecto");
        }

        project.setName(dto.name());
        project.setDescription(dto.description());

        return projectMapper.toDTO(projectRepository.save(project));
    }

    @Override
    public void delete(Long id, UserDetails userDetails) {
        Project project = projectRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Proyecto no encontrado"));

        if (!project.getOwner().getUsername().equals(userDetails.getUsername())) {
            throw new RuntimeException("No tienes permiso para eliminar este proyecto");
        }

        projectRepository.delete(project);
    }
}
