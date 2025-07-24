package com.kruger.kevaluacion.controller;

import com.kruger.kevaluacion.dto.task.TaskRequestDTO;
import com.kruger.kevaluacion.dto.task.TaskResponseDTO;
import com.kruger.kevaluacion.service.interfaces.TaskService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/tasks")
@RequiredArgsConstructor
public class TaskController {

    private final TaskService taskService;

    @PostMapping
    public ResponseEntity<TaskResponseDTO> create(@RequestBody @Valid TaskRequestDTO dto,
                                                  @AuthenticationPrincipal UserDetails userDetails) {
        return ResponseEntity.ok(taskService.create(dto, userDetails));
    }

    @GetMapping
    public ResponseEntity<List<TaskResponseDTO>> findAll(@AuthenticationPrincipal UserDetails userDetails) {
        return ResponseEntity.ok(taskService.findByUser(userDetails));
    }

    @GetMapping("/project/{projectId}")
    public ResponseEntity<List<TaskResponseDTO>> findByProject(@PathVariable Long projectId,
                                                               @AuthenticationPrincipal UserDetails userDetails) {
        return ResponseEntity.ok(taskService.findByProject(projectId, userDetails));
    }

    @PutMapping("/{id}")
    public ResponseEntity<TaskResponseDTO> update(@PathVariable Long id,
                                                  @RequestBody @Valid TaskRequestDTO dto,
                                                  @AuthenticationPrincipal UserDetails userDetails) {
        return ResponseEntity.ok(taskService.update(id, dto, userDetails));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id,
                                       @AuthenticationPrincipal UserDetails userDetails) {
        taskService.delete(id, userDetails);
        return ResponseEntity.noContent().build();
    }
}
