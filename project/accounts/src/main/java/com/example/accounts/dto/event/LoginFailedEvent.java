package com.example.accounts.dto.event;

public class LoginFailedEvent {
    private final EventMetadata metadata = new EventMetadata();
    private String email;
    private String reason;


    public LoginFailedEvent() {}

    public LoginFailedEvent(String email, String reason) {
        this.email = email;
        this.reason = reason;
    }

    public String getEmail() {
        return email;
    }

    public String getReason() {
        return reason;
    }

    public EventMetadata getMetadata() {
        return metadata;
    }
}
