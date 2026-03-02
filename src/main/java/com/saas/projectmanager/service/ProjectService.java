package com.saas.projectmanager.service;

import com.saas.projectmanager.dto.ProjectResponse;

import java.util.List;
import java.util.UUID;

public interface ProjectService {

    ProjectResponse createProject(String name, String description);

    List<ProjectResponse> getAllProjects();

    ProjectResponse getProjectById(UUID projectId);

    void deleteProject(UUID projectId);
}