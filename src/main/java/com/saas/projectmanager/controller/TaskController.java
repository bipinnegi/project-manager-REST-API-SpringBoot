package com.saas.projectmanager.controller;

import com.saas.projectmanager.dto.TaskCreateRequest;
import com.saas.projectmanager.dto.TaskResponse;
import com.saas.projectmanager.service.TaskService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/projects/{projectId}/tasks")
@RequiredArgsConstructor
public class TaskController {
    private final TaskService taskService;

    @PostMapping
    @PreAuthorize("hasRole('OWNER')")
    public TaskResponse createTask(@PathVariable UUID projectId,
                                   @RequestBody TaskCreateRequest request
    ) {
        return taskService.createTask(projectId, request);
    }


}
