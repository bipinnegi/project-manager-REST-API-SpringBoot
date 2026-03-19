package com.saas.projectmanager.controller.auth;

import com.saas.projectmanager.dto.*;
import com.saas.projectmanager.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {
    private final AuthService authService;

    @PostMapping("/register")
    public ResponseEntity<AuthResponse> register(@RequestBody RegisterRequest request) {
        return ResponseEntity.ok(authService.register(request));
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@RequestBody LoginRequest request) {
        return ResponseEntity.ok(authService.login(request));
    }

    @PostMapping("/invite")
    @PreAuthorize("hasRole('OWNER')")
    public String sendInvite(@RequestBody InviteRequest request) {
        return authService.sendInvite(request.getEmail());
    }

    @PostMapping("/accept-invite")
    public AuthResponse acceptInvite(@RequestBody AcceptInviteRequest request) {
        return authService.acceptInvite(request);
    }


}
