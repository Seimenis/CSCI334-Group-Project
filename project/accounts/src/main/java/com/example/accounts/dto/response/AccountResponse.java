package com.example.accounts.dto.response;

import java.time.LocalDateTime;

import com.example.accounts.model.Account;
import com.example.accounts.util.Role;

public class AccountResponse {
    private Long id;
    private String email;
    private String username;
    private Role role;
    private boolean enabled;
    private LocalDateTime createdAt;

    public AccountResponse() {}

    public AccountResponse(Long id, String email, String username, Role role, boolean enabled, LocalDateTime createdAt) {
        this.id = id;
        this.email = email;
        this.username = username;
        this.role = role;
        this.enabled = enabled;
        this.createdAt = createdAt;
    }

    public AccountResponse(Account account) {
        this(
            account.getId(), 
            account.getEmail(), 
            account.getUsername(), 
            account.getRole(), 
            account.isEnabled(), 
            account.getCreatedAt()
        );
    }

    public Long getId() {
        return id;
    }

    public String getEmail() {
        return email;
    }

    public String getUsername() {
        return username;
    }

    public Role getRole() {
        return role;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }
}
