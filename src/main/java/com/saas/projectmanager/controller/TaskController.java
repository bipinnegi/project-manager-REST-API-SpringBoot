package com.saas.projectmanager.controller;

import com.saas.projectmanager.dto.TaskCreateRequest;
import com.saas.projectmanager.dto.TaskResponse;
import com.saas.projectmanager.dto.TaskStatusUpdateRequest;
import com.saas.projectmanager.service.TaskService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class TaskController {

    private final TaskService taskService;

    @PostMapping("/projects/{projectId}/tasks")
    @PreAuthorize("hasRole('OWNER')")
    public TaskResponse createTask(
            @PathVariable UUID projectId,
            @RequestBody TaskCreateRequest request
    ) {
        return taskService.createTask(projectId, request);
    }

    @GetMapping("/projects/{projectId}/tasks")
    @PreAuthorize("hasAnyRole('OWNER','MEMBER')")
    public List<TaskResponse> getTasksByProject(@PathVariable UUID projectId) {
        return taskService.getTasksByProject(projectId);
    }

    @GetMapping("/tasks/{taskId}")
    @PreAuthorize("hasAnyRole('OWNER','MEMBER')")
    public TaskResponse getTaskById(@PathVariable UUID taskId) {
        return taskService.getTaskById(taskId);
    }

    @PutMapping("/tasks/{taskId}")
    @PreAuthorize("hasRole('OWNER')")
    public TaskResponse updateTask(
            @PathVariable UUID taskId,
            @RequestBody TaskCreateRequest request
    ) {
        return taskService.updateTask(taskId, request);
    }

    @PatchMapping("/tasks/{taskId}/status")
    @PreAuthorize("hasRole('OWNER')")
    public TaskResponse updateTaskStatus(
            @PathVariable UUID taskId,
            @RequestBody TaskStatusUpdateRequest request
    ) {
        return taskService.updateTaskStatus(taskId, request.getStatus());
    }

    @DeleteMapping("/tasks/{taskId}")
    @PreAuthorize("hasRole('OWNER')")
    public void deleteTask(@PathVariable UUID taskId) {
        taskService.deleteTask(taskId);
    }
}