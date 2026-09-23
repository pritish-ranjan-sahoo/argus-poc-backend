package com.ecommerce.user;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.management.relation.Role;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface UserRepository extends JpaRepository<AppUser, UUID> {
    Optional<AppUser> findById(String id);
    Optional<AppUser> findByUsernameOrEmail(String username, String email);
    Page<AppUser> findAll(Pageable pageable);
    Page<AppUser> findByRole(RoleType role, Pageable pageable);
    Page<AppUser> findByIsActive(boolean activeOrNot, Pageable pageable);
}