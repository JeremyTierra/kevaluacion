package com.kruger.kevaluacion.service.interfaces;

import com.kruger.kevaluacion.dto.task.TaskRequestDTO;
import com.kruger.kevaluacion.dto.task.TaskResponseDTO;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.List;

public interface TaskService {
    TaskResponseDTO create(TaskRequestDTO dto, UserDetails currentUser);
    List<TaskResponseDTO> findByUser(UserDetails currentUser);
    List<TaskResponseDTO> findByProject(Long projectId, UserDetails currentUser);
    TaskResponseDTO update(Long id, TaskRequestDTO dto, UserDetails currentUser);
    void delete(Long id, UserDetails currentUser);
}
