package com.saas.projectmanager.repository;

import com.saas.projectmanager.domain.model.Invite;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface InviteRepository extends JpaRepository<Invite, UUID> {

    Optional<Invite> findByToken(String token);

    Optional<Invite> findByEmail(String email);
}