package com.kruger.kevaluacion.service.impl;

import com.kruger.kevaluacion.dto.task.TaskRequestDTO;
import com.kruger.kevaluacion.dto.task.TaskResponseDTO;
import com.kruger.kevaluacion.entity.Project;
import com.kruger.kevaluacion.entity.Task;
import com.kruger.kevaluacion.entity.User;
import com.kruger.kevaluacion.mapper.TaskMapper;
import com.kruger.kevaluacion.repository.ProjectRepository;
import com.kruger.kevaluacion.repository.TaskRepository;
import com.kruger.kevaluacion.repository.UserRepository;
import com.kruger.kevaluacion.service.interfaces.TaskService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class TaskServiceImpl implements TaskService {

    private final TaskRepository taskRepository;
    private final UserRepository userRepository;
    private final ProjectRepository projectRepository;
    private final TaskMapper taskMapper;

    @Override
    public TaskResponseDTO create(TaskRequestDTO dto, UserDetails currentUser) {
        User creator = userRepository.findByUsername(currentUser.getUsername())
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        User assignedTo = userRepository.findById(dto.assignedToUserId())
                .orElseThrow(() -> new RuntimeException("Usuario asignado no encontrado"));

        Project project = projectRepository.findById(dto.projectId())
                .orElseThrow(() -> new RuntimeException("Proyecto no encontrado"));

        if (!project.getOwner().getId().equals(creator.getId())) {
            throw new RuntimeException("No puedes crear tareas en proyectos que no son tuyos");
        }

        Task task = Task.builder()
                .title(dto.title())
                .description(dto.description())
                .status(dto.status())
                .dueDate(dto.dueDate())
                .createdAt(LocalDateTime.now())
                .assignedTo(assignedTo)
                .project(project)
                .build();

        return taskMapper.toDTO(taskRepository.save(task));
    }

    @Override
    public List<TaskResponseDTO> findByUser(UserDetails currentUser) {
        User user = userRepository.findByUsername(currentUser.getUsername())
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        return taskRepository.findByAssignedTo(user).stream()
                .map(taskMapper::toDTO)
                .toList();
    }

    @Override
    public List<TaskResponseDTO> findByProject(Long projectId, UserDetails currentUser) {
        Project project = projectRepository.findById(projectId)
                .orElseThrow(() -> new RuntimeException("Proyecto no encontrado"));

        if (!project.getOwner().getUsername().equals(currentUser.getUsername())) {
            throw new RuntimeException("No puedes ver tareas de proyectos que no son tuyos");
        }

        return taskRepository.findByProject(project).stream()
                .map(taskMapper::toDTO)
                .toList();
    }

    @Override
    public TaskResponseDTO update(Long id, TaskRequestDTO dto, UserDetails currentUser) {
        Task task = taskRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Tarea no encontrada"));

        if (!task.getProject().getOwner().getUsername().equals(currentUser.getUsername())) {
            throw new RuntimeException("No tienes permiso para modificar esta tarea");
        }

        task.setTitle(dto.title());
        task.setDescription(dto.description());
        task.setStatus(dto.status());
        task.setDueDate(dto.dueDate());

        // cambiar usuario asignado si se requiere
        User newAssigned = userRepository.findById(dto.assignedToUserId())
                .orElseThrow(() -> new RuntimeException("Usuario asignado no encontrado"));
        task.setAssignedTo(newAssigned);

        return taskMapper.toDTO(taskRepository.save(task));
    }

    @Override
    public void delete(Long id, UserDetails currentUser) {
        Task task = taskRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Tarea no encontrada"));

        if (!task.getProject().getOwner().getUsername().equals(currentUser.getUsername())) {
            throw new RuntimeException("No tienes permiso para eliminar esta tarea");
        }

        taskRepository.delete(task);
    }
}
