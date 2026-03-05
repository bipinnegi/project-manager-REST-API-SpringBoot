package com.saas.projectmanager.service.impl;

import com.saas.projectmanager.domain.model.Project;
import com.saas.projectmanager.domain.model.Tenant;
import com.saas.projectmanager.dto.ProjectResponse;
import com.saas.projectmanager.repository.ProjectRepository;
import com.saas.projectmanager.repository.TenantRepository;
import com.saas.projectmanager.service.ProjectService;
import com.saas.projectmanager.tenant.TenantContext;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ProjectServiceImpl implements ProjectService {

    private final ProjectRepository projectRepository;
    private final TenantRepository tenantRepository;

    @Override
    public ProjectResponse createProject(String name, String description) {

        UUID tenantId = TenantContext.getCurrentTenant();

        if (tenantId == null) {
            throw new RuntimeException("Tenant not found in context");
        }

        Tenant tenant = tenantRepository.findById(tenantId)
                .orElseThrow(() -> new RuntimeException("Tenant not found"));

        Project project = Project.builder()
                .name(name)
                .description(description)
                .tenant(tenant)
                .build();

        Project savedProject = projectRepository.save(project);

        return mapToResponse(savedProject);
    }

    @Override
    public Page<ProjectResponse> getAllProjects(Pageable pageable) {

        UUID tenantId = TenantContext.getCurrentTenant();

        if (tenantId == null) {
            throw new RuntimeException("Tenant not found in context");
        }

        Page<Project> projectPage =
                projectRepository.findByTenantId(tenantId, pageable);

        return projectPage.map(this::mapToResponse);
    }

    @Override
    public ProjectResponse getProjectById(UUID projectId) {

        UUID tenantId = TenantContext.getCurrentTenant();

        Project project = projectRepository.findById(projectId)
                .orElseThrow(() -> new RuntimeException("Project not found"));

        if (!project.getTenant().getId().equals(tenantId)) {
            throw new RuntimeException("Access denied");
        }

        return mapToResponse(project);
    }

    @Override
    public void deleteProject(UUID projectId) {

        UUID tenantId = TenantContext.getCurrentTenant();

        Project project = projectRepository.findById(projectId)
                .orElseThrow(() -> new RuntimeException("Project not found"));

        if (!project.getTenant().getId().equals(tenantId)) {
            throw new RuntimeException("Access denied");
        }

        projectRepository.delete(project);
    }

    /**
     * Entity → DTO mapper
     */
    private ProjectResponse mapToResponse(Project project) {
        return ProjectResponse.builder()
                .id(project.getId())
                .name(project.getName())
                .description(project.getDescription())
                .createdAt(project.getCreatedAt())
                .build();
    }
}