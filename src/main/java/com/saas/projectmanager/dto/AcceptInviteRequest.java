package com.saas.projectmanager.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AcceptInviteRequest {
    private String token;
    private String name;
    private String password;
}