package com.saas.projectmanager.service.impl;

import com.saas.projectmanager.domain.model.Project;
import com.saas.projectmanager.domain.model.Tenant;
import com.saas.projectmanager.repository.ProjectRepository;
import com.saas.projectmanager.repository.TenantRepository;
import com.saas.projectmanager.service.ProjectService;
import com.saas.projectmanager.tenant.TenantContext;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;


@Service
@RequiredArgsConstructor
public class ProjectServiceImpl implements ProjectService {
    private final ProjectRepository projectRepository;
    private final TenantRepository tenantRepository;


    @Override
    public Project createProject(String name, String description) {
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

        return projectRepository.save(project);
    }

    @Override
    public List<Project> getAllProjects() {
        UUID tenantId = TenantContext.getCurrentTenant();

        if (tenantId == null) {
            throw new RuntimeException("Tenant not found in context");
        }

        return projectRepository.findByTenantId(tenantId);
    }

    @Override
    public Project getProjectById(UUID projectId) {

        UUID tenantId = TenantContext.getCurrentTenant();

        Project project = projectRepository.findById(projectId)
                .orElseThrow(() -> new RuntimeException("Project not found"));

        if (!project.getTenant().getId().equals(tenantId)) {
            throw new RuntimeException("Access denied");
        }

        return project;
    }

    @Override
    public void deleteProject(UUID projectId) {

        Project project = getProjectById(projectId);

        projectRepository.delete(project);
    }
}
