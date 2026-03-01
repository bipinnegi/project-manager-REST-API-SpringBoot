package com.saas.projectmanager.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProjectCreateRequest {

    private String name;
    private String description;
}