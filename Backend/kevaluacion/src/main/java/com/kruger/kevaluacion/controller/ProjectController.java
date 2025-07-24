package com.kruger.kevaluacion.controller;

import com.kruger.kevaluacion.dto.project.ProjectRequestDTO;
import com.kruger.kevaluacion.dto.project.ProjectResponseDTO;
import com.kruger.kevaluacion.service.interfaces.ProjectService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/projects")
public class ProjectController {

    private final ProjectService projectService;

    public ProjectController(ProjectService projectService) {
        this.projectService = projectService;
    }

    @PostMapping
    public ResponseEntity<ProjectResponseDTO> create(@RequestBody @Valid ProjectRequestDTO dto,
                                                     @AuthenticationPrincipal UserDetails userDetails) {
        return ResponseEntity.status(201).body(projectService.create(dto, userDetails));
    }

    @GetMapping
    public ResponseEntity<List<ProjectResponseDTO>> getAll(@AuthenticationPrincipal UserDetails userDetails) {
        return ResponseEntity.ok(projectService.findAllByUser(userDetails));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProjectResponseDTO> update(@PathVariable Long id,
                                                     @RequestBody @Valid ProjectRequestDTO dto,
                                                     @AuthenticationPrincipal UserDetails userDetails) {
        return ResponseEntity.ok(projectService.update(id, dto, userDetails));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id,
                                       @AuthenticationPrincipal UserDetails userDetails) {
        projectService.delete(id, userDetails);
        return ResponseEntity.noContent().build();
    }
}
