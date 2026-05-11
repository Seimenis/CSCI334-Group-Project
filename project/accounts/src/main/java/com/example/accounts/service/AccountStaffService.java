package com.example.accounts.service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import org.springframework.stereotype.Service;

import com.example.accounts.dto.response.AccountResponse;
import com.example.accounts.repository.AccountRepository;
import com.example.accounts.util.DateRange;
import com.example.accounts.util.Role;

@Service
public class AccountStaffService {
    private final AccountRepository accountRepository;

    public AccountStaffService(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    // Read
    
    public List<AccountResponse> getAccounts(boolean enabled, Role role, LocalDate startDate, LocalDate endDate) {
        LocalDateTime[] dateRange = DateRange.resolveRange(startDate, endDate);

        return accountRepository.findByEnabledAndRoleAndCreatedAtBetween(enabled, role, dateRange[0], dateRange[1])
            .stream()
            .map(AccountResponse::new)
            .toList();
    }
}
