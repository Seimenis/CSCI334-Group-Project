package com.example.accounts.dto.response;

import com.example.accounts.model.Account;
import com.example.accounts.util.Role;

public class AuthResponse {
    private Long id;
    private String username;
    private String email;
    private Role role;

    public AuthResponse() {}

    public AuthResponse(Long id, String username, String email, Role role) {
        this.id = id;
        this.username = username;
        this.email = email;
        this.role = role;
    }

    public AuthResponse(Account account) {
        this(
            account.getId(),
            account.getUsername(),
            account.getEmail(),
            account.getRole()
        );
        
    }

    public Long getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public String getEmail() {
        return email;
    }

    public Role getRole() {
        return role;
    }
}
