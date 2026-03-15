package com.saas.projectmanager.service.impl;

import com.saas.projectmanager.domain.model.Project;
import com.saas.projectmanager.domain.model.Task;
import com.saas.projectmanager.dto.TaskCreateRequest;
import com.saas.projectmanager.dto.TaskResponse;
import com.saas.projectmanager.repository.ProjectRepository;
import com.saas.projectmanager.repository.TaskRepository;
import com.saas.projectmanager.service.TaskService;
import com.saas.projectmanager.tenant.TenantContext;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TaskServiceImpl implements TaskService {

    private final TaskRepository taskRepository;
    private final ProjectRepository projectRepository;

    @Override
    public TaskResponse createTask(UUID projectId, TaskCreateRequest request) {

        UUID tenantId = TenantContext.getCurrentTenant();

        if (tenantId == null) {
            throw new RuntimeException("Tenant not found in context");
        }

        Project project = projectRepository.findById(projectId)
                .orElseThrow(() -> new RuntimeException("Project not found"));

        // Tenant ownership validation
        if (!project.getTenant().getId().equals(tenantId)) {
            throw new RuntimeException("Access denied");
        }

        Task task = Task.builder()
                .title(request.getTitle())
                .description(request.getDescription())
                .project(project)
                .build();

        Task savedTask = taskRepository.save(task);

        return mapToResponse(savedTask);
    }

    @Override
    public List<TaskResponse> getTasksByProject(UUID projectId) {

        UUID tenantId = TenantContext.getCurrentTenant();

        Project project = projectRepository.findById(projectId)
                .orElseThrow(() -> new RuntimeException("Project not found"));

        if (!project.getTenant().getId().equals(tenantId)) {
            throw new RuntimeException("Access denied");
        }

        return taskRepository.findByProjectId(projectId)
                .stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    private TaskResponse mapToResponse(Task task) {
        return TaskResponse.builder()
                .id(task.getId())
                .title(task.getTitle())
                .description(task.getDescription())
                .status(task.getStatus())
                .createdAt(task.getCreatedAt())
                .build();
    }
}