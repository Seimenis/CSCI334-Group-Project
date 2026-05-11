package com.example.accounts.controller;

import java.time.LocalDate;
import java.util.List;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.accounts.dto.response.AccountResponse;
import com.example.accounts.model.Account;
import com.example.accounts.repository.AccountRepository;
import com.example.accounts.service.AccountStaffService;
import com.example.accounts.util.Role;


@RestController
@RequestMapping("/staff/accounts")
@PreAuthorize("hasRole('STAFF') or hasRole('ADMIN')")
public class AccountStaffController {
    
    private final AccountStaffService accountStaffService;
    private final AccountRepository accountRepository;

    public AccountStaffController(AccountStaffService accountStaffService, AccountRepository accountRepository) {
        this.accountStaffService = accountStaffService;
        this.accountRepository = accountRepository;
    }

    @GetMapping()
    public List<AccountResponse> getAccounts(
        @RequestParam(required = false) Boolean enabled,
        @RequestParam(required = false) Role role,
        @RequestParam(required = false) LocalDate startDate,
        @RequestParam(required = false) LocalDate endDate
    ) {

        return accountStaffService.getAccounts(enabled, role, startDate, endDate);
    }

    @GetMapping("/staff")
    public List<Account> test() {
        return accountRepository.findAll();
    }
    
}
