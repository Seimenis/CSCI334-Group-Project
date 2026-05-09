package com.example.accounts.dto.event;

import com.example.accounts.model.Account;

public class AccountDeletedEvent {
    private final EventMetadata metadata = new EventMetadata();
    private Long accountId;

    public AccountDeletedEvent() {}

    public AccountDeletedEvent(Long accountId) {
        this.accountId = accountId;
    }

    public AccountDeletedEvent(Account account) {
        this(account.getId());
    }

    public Long getAccountId() {
        return accountId;
    }

    public EventMetadata getMetadata() {
        return metadata;
    }
}
