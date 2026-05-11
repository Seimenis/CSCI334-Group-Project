package com.example.accounts.service;

import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import com.example.accounts.dto.event.AccountCreatedEvent;
import com.example.accounts.dto.event.AccountDeletedEvent;
import com.example.accounts.dto.event.AccountUpdatedEvent;
import com.example.accounts.dto.event.LoginFailedEvent;
import com.example.accounts.dto.event.LoginSucceededEvent;
import com.example.accounts.dto.event.TokenIssuedEvent;

@Service
public class AccountProducerService {

    private final KafkaTemplate<String, Object> kafkaTemplate;

    public AccountProducerService(KafkaTemplate<String, Object> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void publishAccountCreatedEvent(AccountCreatedEvent event) {
        kafkaTemplate.send("account.created", event);
    }

    public void publishAccountUpdatedEvent(AccountUpdatedEvent event) {
        kafkaTemplate.send("account.updated", event);
    }

    public void publishAccountDeletedEvent(AccountDeletedEvent event) {
        kafkaTemplate.send("account.deleted", event);
    }

    public void publishLoginSucceededEvent(LoginSucceededEvent event) {
        kafkaTemplate.send("account.login.succeeded", event);
    }

    public void publishLoginFailedEvent(LoginFailedEvent event) {
        kafkaTemplate.send("account.login.failed", event);
    }

    public void publishTokenIssuedEvent(TokenIssuedEvent event) {
        kafkaTemplate.send("account.token.issued", event);
    }
}