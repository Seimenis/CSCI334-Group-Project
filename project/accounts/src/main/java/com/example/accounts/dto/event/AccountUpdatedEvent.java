package com.example.accounts.dto.event;

import com.example.accounts.model.Account;
import com.example.accounts.util.Role;
import com.example.accounts.util.Subscription;

public class AccountUpdatedEvent {
    private final EventMetadata metadata = new EventMetadata();
    private Long accountId;
    private String email;
    private String username;
    private Role role;
    private boolean enabled;
    private Subscription subscription;

    public AccountUpdatedEvent() {}

    public AccountUpdatedEvent(Long accountId, String email, String username, Role role, boolean enabled, Subscription subscription) {
        this.accountId = accountId;
        this.email = email;
        this.username = username;
        this.role = role;
        this.enabled = enabled;
        this.subscription = subscription;
    }

    public AccountUpdatedEvent(Account account) {
        this(
            account.getId(),
            account.getEmail(),
            account.getUsername(),
            account.getRole(),
            account.isEnabled(),
            account.getSubscription()
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

    public Role getRole() {
        return role;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public EventMetadata getMetadata() {
        return metadata;
    }

    public Subscription getSubscription() {
        return subscription;
    }
}
