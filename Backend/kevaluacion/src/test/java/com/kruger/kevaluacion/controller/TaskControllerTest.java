package com.kruger.kevaluacion.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.kruger.kevaluacion.dto.auth.RegisterRequest;
import com.kruger.kevaluacion.dto.project.ProjectRequestDTO;
import com.kruger.kevaluacion.dto.task.TaskRequestDTO;
import com.kruger.kevaluacion.entity.TaskStatus;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.time.LocalDateTime;

import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.ANY)
class TaskControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    private String jwtToken;

    @BeforeEach
    void setup() throws Exception {
        RegisterRequest registerRequest = new RegisterRequest("taskuser", "task@kruger.com", "task123");
        mockMvc.perform(post("/auth/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(registerRequest)));

        String loginJson = "{ \"username\": \"taskuser\", \"password\": \"task123\" }";
        MvcResult result = mockMvc.perform(post("/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(loginJson))
                .andExpect(status().isOk())
                .andReturn();

        jwtToken = objectMapper.readTree(result.getResponse().getContentAsString()).get("token").asText();
    }

    @Test
    void shouldCreateTask() throws Exception {
        Long projectId = createProject();
        TaskRequestDTO request = new TaskRequestDTO(
                "Task 1", "First task", TaskStatus.PENDING, projectId, null, LocalDateTime.now().plusDays(3)
        );

        mockMvc.perform(post("/tasks")
                        .header("Authorization", "Bearer " + jwtToken)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.title", is("Task 1")));
    }

    @Test
    void shouldListTasks() throws Exception {
        Long projectId = createProject();
        TaskRequestDTO request = new TaskRequestDTO(
                "Task 2", "Second task", TaskStatus.PENDING, projectId, 1L, LocalDateTime.now().plusDays(3)
        );

        mockMvc.perform(post("/tasks")
                        .header("Authorization", "Bearer " + jwtToken)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated());

        mockMvc.perform(get("/tasks")
                        .header("Authorization", "Bearer " + jwtToken))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)));
    }

    @Test
    void shouldUpdateTask() throws Exception {
        Long projectId = createProject();
        TaskRequestDTO createRequest = new TaskRequestDTO(
                "Old Task", "To update", TaskStatus.PENDING, projectId, 1L, LocalDateTime.now().plusDays(3)
        );

        MvcResult result = mockMvc.perform(post("/tasks")
                        .header("Authorization", "Bearer " + jwtToken)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(createRequest)))
                .andExpect(status().isCreated())
                .andReturn();

        Long taskId = objectMapper.readTree(result.getResponse().getContentAsString()).get("id").asLong();

        TaskRequestDTO updateRequest = new TaskRequestDTO(
                "Updated Task", "Updated Desc", TaskStatus.IN_PROGRESS, projectId, 1L, LocalDateTime.now().plusDays(5)
        );

        mockMvc.perform(put("/tasks/" + taskId)
                        .header("Authorization", "Bearer " + jwtToken)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updateRequest)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status", is("IN_PROGRESS")));
    }

    @Test
    void shouldDeleteTask() throws Exception {
        Long projectId = createProject();
        TaskRequestDTO request = new TaskRequestDTO(
                "Delete Task", "To be deleted", TaskStatus.PENDING, projectId, null, LocalDateTime.now().plusDays(3)
        );

        MvcResult result = mockMvc.perform(post("/tasks")
                        .header("Authorization", "Bearer " + jwtToken)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andReturn();

        Long taskId = objectMapper.readTree(result.getResponse().getContentAsString()).get("id").asLong();

        mockMvc.perform(delete("/tasks/" + taskId)
                        .header("Authorization", "Bearer " + jwtToken))
                .andExpect(status().isNoContent());
    }

    @Test
    void shouldFailWithoutToken() throws Exception {
        mockMvc.perform(get("/tasks"))
                .andExpect(status().isUnauthorized());
    }

    private Long createProject() throws Exception {
        ProjectRequestDTO projectRequest = new ProjectRequestDTO("Task Project", "Project with tasks");

        MvcResult result = mockMvc.perform(post("/projects")
                        .header("Authorization", "Bearer " + jwtToken)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(projectRequest)))
                .andExpect(status().isCreated())
                .andReturn();

        return objectMapper.readTree(result.getResponse().getContentAsString()).get("id").asLong();
    }
}
