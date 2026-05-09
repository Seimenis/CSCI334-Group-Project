package com.example.accounts.service;

import org.springframework.stereotype.Service;

import com.example.accounts.dto.request.LoginRequest;
import com.example.accounts.dto.response.AuthResponse;
import com.example.accounts.repository.AccountRepository;

@Service
public class AccountStaffService {
    private final AccountRepository accountRepository;
    private final AccountService accountService;

    public AccountStaffService(
        AccountRepository accountRepository, 
        AccountService accountService) {

        this.accountRepository = accountRepository;
        this.accountService = accountService;
    }

    public AuthResponse authenticateStaff(LoginRequest loginRequest) {
        AuthResponse authResponse = accountService.authenticate(loginRequest);

        // Additional checks for staff role can be added here if needed

        return authResponse;

    }
}
