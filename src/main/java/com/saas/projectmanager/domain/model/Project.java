package com.saas.projectmanager.domain.model;

import jakarta.persistence.*;

import lombok.*;
import org.hibernate.sql.results.graph.Fetch;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "projects")
public class Project extends BaseEntity {
    @Column(nullable = false)
    private String name;

    @Column(length = 1024)
    private String description;

    @ManyToOne(fetch = FetchType.LAZY)

    @JoinColumn(name ="tenant_id", nullable = false)
    private Tenant tenant;

}
