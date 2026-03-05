package com.saas.projectmanager.repository;

import com.saas.projectmanager.domain.model.Project;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface ProjectRepository extends JpaRepository<Project, UUID> {

    Page<Project> findByTenantId(UUID tenantId, Pageable pageable);

}