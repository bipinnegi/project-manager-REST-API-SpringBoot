package com.saas.projectmanager.service.impl;

import com.saas.projectmanager.domain.model.Tenant;
import com.saas.projectmanager.domain.model.User;
import com.saas.projectmanager.domain.valueobject.Role;
import com.saas.projectmanager.dto.LoginRequest;
import com.saas.projectmanager.dto.RegisterRequest;
import com.saas.projectmanager.dto.AuthResponse;
import com.saas.projectmanager.repository.TenantRepository;
import com.saas.projectmanager.repository.UserRepository;
import com.saas.projectmanager.security.jwt.JwtService;
import com.saas.projectmanager.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final UserRepository userRepository;
    private final TenantRepository tenantRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    @Override
    public AuthResponse register(RegisterRequest request) {

        // Check if tenant slug already exists
        if (tenantRepository.findBySlug(request.getSlug()).isPresent()) {
            throw new RuntimeException("Tenant with this slug already exists");
        }

        // Create Tenant
        Tenant tenant = Tenant.builder()
                .name(request.getTenantName())
                .slug(request.getTenantName().toLowerCase().replace(" ", "-"))
                .createdAt(LocalDateTime.now())
                .build();

        tenantRepository.save(tenant);

        // Create Owner User
        User user = User.builder()
                .email(request.getEmail())
                .name(request.getName())
                .password(passwordEncoder.encode(request.getPassword()))
                .role(Role.OWNER)
                .tenant(tenant)
                .createdAt(LocalDateTime.now())
                .build();

        userRepository.save(user);

        // Generate token WITH tenantId
        String token = jwtService.generateToken(
                user.getEmail(),
                tenant.getId()
        );

        return new AuthResponse(token);
    }

    @Override
    public AuthResponse login(LoginRequest request) {

        // Authenticate using Spring Security
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getEmail(),
                        request.getPassword()
                )
        );

        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow();

        // Generate token WITH tenantId
        String token = jwtService.generateToken(
                user.getEmail(),
                user.getTenant().getId()
        );

        return new AuthResponse(token);
    }
}
