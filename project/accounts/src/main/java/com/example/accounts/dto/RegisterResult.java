package com.example.accounts.dto;

import java.time.LocalDateTime;

import com.example.accounts.model.Account;

public class RegisterResult {
    private Long id;
    private String username;
    private String email;
    private LocalDateTime createdAt;
    private String message;
    private String token;

    public RegisterResult() {}

    public RegisterResult(Long id, String username, String email, LocalDateTime createdAt, String message, String token) {
        this.id = id;
        this.username = username;
        this.email = email;
        this.message = message;
        this.createdAt = createdAt;
        this.token = token;
    }

    public RegisterResult(Account account, String message, String token) {
        this(
            account.getId(),
            account.getUsername(),
            account.getEmail(),
            account.getCreatedAt(),
            message,
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

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public String getMessage() {
        return message;
    }

    public String getToken() {
        return token;
    }
}
