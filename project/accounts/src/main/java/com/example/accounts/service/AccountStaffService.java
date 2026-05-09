package com.example.accounts.service;

import org.springframework.stereotype.Service;

import com.example.accounts.dto.request.RegisterRequest;
import com.example.accounts.dto.response.RegisterResponse;
import com.example.accounts.repository.AccountRepository;
import com.example.accounts.util.Role;

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

    public RegisterResponse registerStaff(RegisterRequest registerRequest) {
        RegisterResponse registerResponse = accountService.register(registerRequest);

        accountRepository.findByEmail(registerRequest.getEmail()).ifPresent(account -> {
            account.setRole(Role.STAFF);
            accountRepository.save(account);
        });

        // Additional logic to set staff role can be added here if needed

        return registerResponse;
    }
}
