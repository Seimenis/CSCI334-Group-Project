package com.example.accounts.dto.response;

import java.time.LocalDateTime;

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