package com.saas.projectmanager.domain.model;

import com.saas.projectmanager.domain.valueobject.Role;
import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.Locale;
import java.util.UUID;

@Entity
@Table(name = "users")
public class User {
  @Id
   @GeneratedValue
   private UUID Id;

  @Column(nullable = false)
  private String name;

  @Column(nullable = false, unique= true)
  private String email;

  @Column(nullable = false)
  private String password;

  @Enumerated(EnumType.STRING)
  @Column(nullable = false)
  private Role role;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "tenant_id", nullable = false)
  private Tenant tenant;

  @Column(nullable = false)
  private LocalDateTime createdAt;

  protected User() {}

    public User(String name, String email, String password, Role role, Tenant tenant) {
      this.name = name;
      this.email = email;
      this.password = password;
      this.role = role;
      this.tenant = tenant;
      this.createdAt = LocalDateTime.now();
    }

    public UUID getId() {
        return Id;
    }

    public String getEmail() {
        return email;
    }

    public Role getRole() {
        return role;
    }

    public Tenant getTenant() {
        return tenant;
    }
}
