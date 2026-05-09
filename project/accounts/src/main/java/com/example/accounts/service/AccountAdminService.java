package com.example.accounts.service;

import org.springframework.stereotype.Service;

import com.example.accounts.dto.event.AccountDeletedEvent;
import com.example.accounts.dto.response.AccountResponse;
import com.example.accounts.model.Account;
import com.example.accounts.repository.AccountRepository;


@Service
public class AccountAdminService {

    private final AccountRepository accountRepository;
    private final AccountService accountService;
    private final AccountProducerService accountProducerService;

    public AccountAdminService(
        AccountService accountService, 
        AccountRepository accountRepository,
        AccountProducerService accountProducerService) {

        this.accountService = accountService;
        this.accountRepository = accountRepository;
        this.accountProducerService = accountProducerService;
    }

    // Delete

    public void deleteAccount(Long accountId) {
        accountRepository.deleteById(accountId);
        accountProducerService.publishAccountDeletedEvent(new AccountDeletedEvent(accountId));
    }

    public void deleteAccountByEmail(String email) {
        accountRepository.findByEmail(email).ifPresent(account -> {
            accountRepository.delete(account);
            accountProducerService.publishAccountDeletedEvent(new AccountDeletedEvent(account.getId()));
        });
    }

    // Read

    public AccountResponse getAccountById(Long accountId) {
        Account account = accountRepository.findById(accountId)
            .orElseThrow(() -> new RuntimeException("Account not found"));
        
        return new AccountResponse(account);
    }

}
