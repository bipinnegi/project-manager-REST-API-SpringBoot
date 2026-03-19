package com.saas.projectmanager.controller;

import com.saas.projectmanager.dto.ProjectCreateRequest;
import com.saas.projectmanager.dto.ProjectResponse;
import com.saas.projectmanager.service.ProjectService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

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
    @PreAuthorize("hasRole('OWNER')")
    public ProjectResponse createProject(@RequestBody ProjectCreateRequest request) {
       
        return projectService.createProject(
                request.getName(),
                request.getDescription()
        );
    }

    /**
     * Get paginated projects for current tenant
     * Example:
     * /api/projects?page=0&size=5&sort=name,asc
     */
    @GetMapping
    public Page<ProjectResponse> getAllProjects(Pageable pageable)
    {
        return projectService.getAllProjects(pageable);
    }

    /**
     * Get single project by ID (Tenant ownership validated in service)
     */
    @GetMapping("/{id}")
    public ProjectResponse getProjectById(@PathVariable UUID id)
    {
        return projectService.getProjectById(id);
    }

    /**
     * Delete project by ID (Tenant ownership validated in service)
     */
    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('OWNER')")
    public void deleteProject(@PathVariable UUID id) {
        projectService.deleteProject(id);
    }
}