package com.saas.projectmanager.service.impl;

import com.saas.projectmanager.domain.model.Project;
import com.saas.projectmanager.domain.model.Task;
import com.saas.projectmanager.domain.valueobject.TaskStatus;
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

        Project project = projectRepository.findById(projectId)
                .orElseThrow(() -> new RuntimeException("Project not found"));

        validateTenant(project, tenantId);

        Task task = Task.builder()
                .title(request.getTitle())
                .description(request.getDescription())
                .status(request.getStatus())
                .project(project)
                .build();

        return mapToResponse(taskRepository.save(task));
    }

    @Override
    public List<TaskResponse> getTasksByProject(UUID projectId) {

        UUID tenantId = TenantContext.getCurrentTenant();

        Project project = projectRepository.findById(projectId)
                .orElseThrow(() -> new RuntimeException("Project not found"));

        validateTenant(project, tenantId);

        return taskRepository.findByProjectId(projectId)
                .stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    @Override
    public TaskResponse getTaskById(UUID taskId) {

        UUID tenantId = TenantContext.getCurrentTenant();

        Task task = taskRepository.findById(taskId)
                .orElseThrow(() -> new RuntimeException("Task not found"));

        validateTenant(task.getProject(), tenantId);

        return mapToResponse(task);
    }

    @Override
    public TaskResponse updateTask(UUID taskId, TaskCreateRequest request) {

        UUID tenantId = TenantContext.getCurrentTenant();

        Task task = taskRepository.findById(taskId)
                .orElseThrow(() -> new RuntimeException("Task not found"));

        validateTenant(task.getProject(), tenantId);

        task.setTitle(request.getTitle());
        task.setDescription(request.getDescription());
        task.setStatus(request.getStatus());

        return mapToResponse(taskRepository.save(task));
    }
    @Override
    public TaskResponse updateTaskStatus(UUID taskId, TaskStatus status) {

        UUID tenantId = TenantContext.getCurrentTenant();

        Task task = taskRepository.findById(taskId)
                .orElseThrow(() -> new RuntimeException("Task not found"));

        // Tenant validation
        if (!task.getProject().getTenant().getId().equals(tenantId)) {
            throw new RuntimeException("Access denied");
        }

        task.setStatus(status);

        return mapToResponse(taskRepository.save(task));
    }

    @Override
    public void deleteTask(UUID taskId) {

        UUID tenantId = TenantContext.getCurrentTenant();

        Task task = taskRepository.findById(taskId)
                .orElseThrow(() -> new RuntimeException("Task not found"));

        validateTenant(task.getProject(), tenantId);

        taskRepository.delete(task);
    }

    /**
     * Tenant validation
     */
    private void validateTenant(Project project, UUID tenantId) {
        if (tenantId == null || !project.getTenant().getId().equals(tenantId)) {
            throw new RuntimeException("Access denied");
        }
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