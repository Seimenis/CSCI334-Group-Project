package com.example.accounts.dto.event;

public class TokenIssuedEvent {
    private final EventMetadata metadata = new EventMetadata();
    private Long accountId;
    private Long issuedAt;
    private Long expiresAt;

    public TokenIssuedEvent() {}

    public TokenIssuedEvent(Long accountId, Long issuedAt, Long expiresAt) {
        this.accountId = accountId;
        this.issuedAt = issuedAt;
        this.expiresAt = expiresAt;
    }

    public Long getAccountId() {
        return accountId;
    }

    public Long getIssuedAt() {
        return issuedAt;
    }

    public Long getExpiresAt() {
        return expiresAt;
    }

    public EventMetadata getMetadata() {
        return metadata;
    }
    
}
