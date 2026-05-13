package com.example.accounts.dto.response;

import com.example.accounts.model.Account;
import com.example.accounts.util.Role;

public class AuthResponse {
    private Long id;
    private String username;
    private String email;
    private Role role;
    private String token;

    public AuthResponse() {}

    public AuthResponse(Long id, String username, String email, Role role, String token) {
        this.id = id;
        this.username = username;
        this.email = email;
        this.role = role;
        this.token = token;
    }

    public AuthResponse(Account account, String token) {
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
