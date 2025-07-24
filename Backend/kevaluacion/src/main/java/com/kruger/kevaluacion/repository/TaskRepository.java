package com.kruger.kevaluacion.repository;

import com.kruger.kevaluacion.entity.Task;
import com.kruger.kevaluacion.entity.User;
import com.kruger.kevaluacion.entity.Project;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TaskRepository extends JpaRepository<Task, Long> {

    List<Task> findByAssignedTo(User user);

    List<Task> findByProject(Project project);
}
