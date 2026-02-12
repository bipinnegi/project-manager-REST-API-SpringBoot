package com.saas.projectmanager.domain.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.UUID;


@Entity
@Table(name = "tenants")
public class Tenant {
    @Id
    @GeneratedValue
    private UUID Id;

    @Column(nullable = false, unique = true)
    private String name;

    @Column(nullable = false, unique = true)
    private String slug;

    @Column(nullable = false)
    private LocalDateTime createdAt;

    protected Tenant() {}

    public Tenant(String name, String slug){
        this.name = name;
        this.slug = slug;
        this.createdAt = LocalDateTime.now();
    }

    public UUID getId() {
        return Id;
    }

    public String getName() {
        return name;
    }

    public String getSlug() {
        return slug;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }
}
