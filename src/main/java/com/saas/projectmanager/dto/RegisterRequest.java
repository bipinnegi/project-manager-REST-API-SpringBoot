package com.saas.projectmanager.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RegisterRequest {
    private String tenantName;
    private String tenantSlug;
    private String name;
    private String email;
    private String password;
    private String slug;

}
