package com.saas.projectmanager.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TaskCreateRequest {

    private String title;
    private String description;

}