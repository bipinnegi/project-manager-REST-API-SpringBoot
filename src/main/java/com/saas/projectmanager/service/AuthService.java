package com.saas.projectmanager.service;

import com.saas.projectmanager.dto.AcceptInviteRequest;
import com.saas.projectmanager.dto.AuthResponse;
import com.saas.projectmanager.dto.LoginRequest;
import com.saas.projectmanager.dto.RegisterRequest;

public interface AuthService {
    AuthResponse register(RegisterRequest request);
    AuthResponse login(LoginRequest request);
    String sendInvite(String email);
    AuthResponse acceptInvite(AcceptInviteRequest request);
}
