package com.saas.projectmanager.domain.model;

import com.saas.projectmanager.domain.valueobject.Role;
import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "users")
public class User extends BaseEntity {

  @Column(nullable = false, unique = true)
  private String email;

  @Column(nullable = false)
  private String name;

  @Column(nullable = false)
  private String password;

  @Enumerated(EnumType.STRING)
  @Column(nullable = false)
  private Role role;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "tenant_id", nullable = false)
  private Tenant tenant;
}
