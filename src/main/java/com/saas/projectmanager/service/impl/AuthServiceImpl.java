package com.saas.projectmanager.service.impl;

import com.saas.projectmanager.domain.model.Invite;
import com.saas.projectmanager.domain.model.Tenant;
import com.saas.projectmanager.domain.model.User;
import com.saas.projectmanager.domain.valueobject.Role;
import com.saas.projectmanager.dto.AcceptInviteRequest;
import com.saas.projectmanager.dto.LoginRequest;
import com.saas.projectmanager.dto.RegisterRequest;
import com.saas.projectmanager.dto.AuthResponse;
import com.saas.projectmanager.repository.InviteRepository;
import com.saas.projectmanager.repository.TenantRepository;
import com.saas.projectmanager.repository.UserRepository;
import com.saas.projectmanager.security.jwt.JwtService;
import com.saas.projectmanager.service.AuthService;
import com.saas.projectmanager.tenant.TenantContext;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final UserRepository userRepository;
    private final TenantRepository tenantRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;
    private final InviteRepository inviteRepository;
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

    @Override
    public String sendInvite(String email) {

        UUID tenantId = TenantContext.getCurrentTenant();

        Tenant tenant = tenantRepository.findById(tenantId)
                .orElseThrow(() -> new RuntimeException("Tenant not found"));

        String token = UUID.randomUUID().toString();

        Invite invite = Invite.builder()
                .email(email)
                .token(token)
                .tenant(tenant)
                .accepted(false)
                .expiresAt(LocalDateTime.now().plusDays(1))
                .build();

        inviteRepository.save(invite);

        return token; // later this becomes email link
    }

    @Override
    public AuthResponse acceptInvite(AcceptInviteRequest request) {

        Invite invite = inviteRepository.findByToken(request.getToken())
                .orElseThrow(() -> new RuntimeException("Invalid invite"));

        if (invite.isAccepted()) {
            throw new RuntimeException("Invite already used");
        }

        if (invite.getExpiresAt().isBefore(LocalDateTime.now())) {
            throw new RuntimeException("Invite expired");
        }

        Tenant tenant = invite.getTenant();

        User user = User.builder()
                .email(invite.getEmail())
                .name(request.getName())
                .password(passwordEncoder.encode(request.getPassword()))
                .role(Role.MEMBER)
                .tenant(tenant)
                .build();

        userRepository.save(user);

        invite.setAccepted(true);
        inviteRepository.save(invite);

        String token = jwtService.generateToken(user.getEmail(), tenant.getId());

        return new AuthResponse(token);
    }
}
