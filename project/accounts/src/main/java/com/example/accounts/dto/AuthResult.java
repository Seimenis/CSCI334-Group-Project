package com.example.accounts.dto;

import com.example.accounts.model.Account;
import com.example.accounts.util.Role;

public class AuthResult {
    private Long id;
    private String username;
    private String email;
    private Role role;
    private String token;

    public AuthResult() {}

    public AuthResult(Long id, String username, String email, Role role, String token) {
        this.id = id;
        this.username = username;
        this.email = email;
        this.role = role;
        this.token = token;
    }

    public AuthResult(Account account, String token) {
        this(
            account.getId(),
            account.getUsername(),
            account.getEmail(),
            account.getRole(),
            token
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

    public String getToken() {
        return token;
    }
}
