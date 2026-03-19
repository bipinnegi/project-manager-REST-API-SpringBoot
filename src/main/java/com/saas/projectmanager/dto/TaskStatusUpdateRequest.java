package com.saas.projectmanager.dto;

import com.saas.projectmanager.domain.valueobject.TaskStatus;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TaskStatusUpdateRequest {
    private TaskStatus status;
}