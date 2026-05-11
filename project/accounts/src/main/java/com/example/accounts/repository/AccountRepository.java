package com.example.accounts.repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.accounts.model.Account;
import com.example.accounts.util.Role;

@Repository
public interface AccountRepository extends JpaRepository<Account, Long> {
    List<Account> findByEnabledAndRoleAndCreatedAtBetween(boolean enabled, Role role, LocalDateTime start, LocalDateTime end);

    Optional<Account> findByEmail(String email);
    Optional<Account> findByUsername(String username);

    boolean existsByEmail(String email);
    boolean existsByUsername(String username);
    boolean existsByRole(Role role);

}
