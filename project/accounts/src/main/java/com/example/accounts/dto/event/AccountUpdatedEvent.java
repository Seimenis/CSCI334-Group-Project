package com.example.accounts.dto.event;

public class AccountUpdatedEvent {
    private final EventMetadata metadata = new EventMetadata();
    private Long accountId;
    private String email;
    private String username;

    public AccountUpdatedEvent() {}

    public AccountUpdatedEvent(Long accountId, String email, String username) {
        this.accountId = accountId;
        this.email = email;
        this.username = username;
    }

    public Long getAccountId() {
        return accountId;
    }
    
    public String getEmail() {
        return email;
    }

    public String getUsername() {
        return username;
    }

    public EventMetadata getMetadata() {
        return metadata;
    }
}
