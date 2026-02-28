package com.saas.projectmanager.service;

import com.saas.projectmanager.domain.model.Project;

import java.util.List;
import java.util.UUID;

public interface ProjectService {
    Project createProject(String name, String description);

    List<Project> getAllProjects();

    Project getProjectById(UUID projectId);

    void deleteProject(UUID projectId);
}

