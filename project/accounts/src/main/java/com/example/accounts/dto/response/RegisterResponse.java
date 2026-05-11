package com.example.accounts.dto.response;

import java.time.LocalDateTime;

import com.example.accounts.model.Account;

public class RegisterResponse {
    private Long id;
    private String username;
    private String email;
    private LocalDateTime createdAt;
    private String message;

    public RegisterResponse() {}

    public RegisterResponse(Long id, String username, String email, LocalDateTime createdAt, String message) {
        this.id = id;
        this.username = username;
        this.email = email;
        this.message = message;
    }

    public RegisterResponse(Account account, String message) {
        this(
            account.getId(),
            account.getUsername(),
            account.getEmail(),
            account.getCreatedAt(),
            message
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
}