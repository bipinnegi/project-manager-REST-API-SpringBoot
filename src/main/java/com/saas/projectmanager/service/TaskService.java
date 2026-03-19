package com.saas.projectmanager.service;

import com.saas.projectmanager.domain.valueobject.TaskStatus;
import com.saas.projectmanager.dto.TaskCreateRequest;
import com.saas.projectmanager.dto.TaskResponse;

import java.util.List;
import java.util.UUID;

public interface TaskService {

    TaskResponse createTask(UUID projectId, TaskCreateRequest request);

    List<TaskResponse> getTasksByProject(UUID projectId);

    TaskResponse getTaskById(UUID taskId);

    TaskResponse updateTask(UUID taskId, TaskCreateRequest request);
    TaskResponse updateTaskStatus(UUID taskId, TaskStatus status);
    void deleteTask(UUID taskId);

}