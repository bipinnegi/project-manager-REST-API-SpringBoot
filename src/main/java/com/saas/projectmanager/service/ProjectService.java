package com.saas.projectmanager.service;

import com.saas.projectmanager.dto.ProjectResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.UUID;

public interface ProjectService {

    ProjectResponse createProject(String name, String description);

    Page<ProjectResponse> getAllProjects(Pageable pageable);

    ProjectResponse getProjectById(UUID projectId);

    void deleteProject(UUID projectId);
}