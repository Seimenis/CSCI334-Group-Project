package com.example.accounts.dto.request;

public class AccountRequest {
    private Long id;
    private String email;

    public AccountRequest() {}

    public AccountRequest(Long id) {
        this.id = id;
    }

    public AccountRequest(String email) {
        this.email = email;
    }

    public AccountRequest(Long id, String email) {
        this.id = id;
        this.email = email;
    }
    
    public Long getId() {
        return id;
    }

    public String getEmail() {
        return email;
    }

    
}
