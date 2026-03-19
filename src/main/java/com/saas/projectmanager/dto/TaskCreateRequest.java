package com.saas.projectmanager.dto;

import com.saas.projectmanager.domain.valueobject.TaskStatus;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TaskCreateRequest {

    private String title;
    private String description;
    private TaskStatus status;

}