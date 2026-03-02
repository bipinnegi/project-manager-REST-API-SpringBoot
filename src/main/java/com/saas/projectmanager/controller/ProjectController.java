package com.saas.projectmanager.controller;

import com.saas.projectmanager.dto.ProjectCreateRequest;
import com.saas.projectmanager.dto.ProjectResponse;
import com.saas.projectmanager.service.ProjectService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/projects")
@RequiredArgsConstructor
public class ProjectController {

    private final ProjectService projectService;

    /**
     * Create new project (Tenant automatically injected from JWT)
     */
    @PostMapping
    public ProjectResponse createProject(@RequestBody ProjectCreateRequest request) {
        return projectService.createProject(
                request.getName(),
                request.getDescription()
        );
    }

    /**
     * Get all projects for current tenant
     */
    @GetMapping
    public List<ProjectResponse> getAllProjects() {
        return projectService.getAllProjects();
    }

    /**
     * Get single project by ID (Tenant ownership validated in service)
     */
    @GetMapping("/{id}")
    public ProjectResponse getProjectById(@PathVariable UUID id) {
        return projectService.getProjectById(id);
    }

    /**
     * Delete project by ID (Tenant ownership validated in service)
     */
    @DeleteMapping("/{id}")
    public void deleteProject(@PathVariable UUID id) {
        projectService.deleteProject(id);
    }
}