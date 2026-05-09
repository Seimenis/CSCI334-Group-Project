package com.example.accounts.dto.event;

import com.example.accounts.model.Account;

public class LoginSucceededEvent {
    private final EventMetadata metadata = new EventMetadata();
    private Long accountId;
    private String email;
    private String username;

    public LoginSucceededEvent() {}

    public LoginSucceededEvent(Long accountId, String email, String username) {
        this.accountId = accountId;
        this.email = email;
        this.username = username;
    }

    public LoginSucceededEvent(Account account) {
        this(
            account.getId(),
            account.getEmail(),
            account.getUsername()
        );
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
