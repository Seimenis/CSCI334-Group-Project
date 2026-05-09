package com.example.accounts.dto.event;

public class AccountDeletedEvent {
    private final EventMetadata metadata = new EventMetadata();
    private Long accountId;

    public AccountDeletedEvent() {}

    public AccountDeletedEvent(Long accountId) {
        this.accountId = accountId;
    }

    public Long getAccountId() {
        return accountId;
    }

    public EventMetadata getMetadata() {
        return metadata;
    }
}
