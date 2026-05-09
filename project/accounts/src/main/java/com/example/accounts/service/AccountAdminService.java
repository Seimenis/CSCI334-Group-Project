package com.example.accounts.service;

import org.springframework.stereotype.Service;

import com.example.accounts.dto.request.LoginRequest;
import com.example.accounts.dto.request.RegisterRequest;
import com.example.accounts.dto.response.AuthResponse;
import com.example.accounts.dto.response.RegisterResponse;
import com.example.accounts.repository.AccountRepository;
import com.example.accounts.util.Role;


@Service
public class AccountAdminService {

    private final AccountRepository accountRepository;
    private final AccountService accountService;

    public AccountAdminService(
        AccountService accountService, 
        AccountRepository accountRepository) {

        this.accountService = accountService;
        this.accountRepository = accountRepository;
    }

    public RegisterResponse registerStaff(RegisterRequest registerRequest) {
        RegisterResponse registerResponse = accountService.register(registerRequest);

        accountRepository.findByEmail(registerRequest.getEmail()).ifPresent(account -> {
            account.setRole(Role.STAFF);
            accountRepository.save(account);
        });

        return registerResponse;
    }

    public AuthResponse authenticateAdmin(LoginRequest loginRequest) {
        AuthResponse authResponse = accountService.authenticate(loginRequest);

        // Additional checks for admin role can be added here if needed

        return authResponse;
    }

    // add enable user, disable user, delete user, etc. admin functions here

}
