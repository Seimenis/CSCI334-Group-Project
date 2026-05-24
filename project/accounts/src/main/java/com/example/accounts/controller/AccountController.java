package com.example.accounts.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.accounts.dto.request.LoginRequest;
import com.example.accounts.dto.request.RegisterRequest;
import com.example.accounts.dto.request.UpdateRequest;
import com.example.accounts.dto.response.AccountResponse;
import com.example.accounts.dto.response.AuthResponse;
import com.example.accounts.dto.response.RegisterResponse;
import com.example.accounts.service.AccountService;

@RestController
@RequestMapping("/accounts")
public class AccountController {

    private final AccountService accountService;

    public AccountController(AccountService accountService) {
        this.accountService = accountService;
    }

    // Creating account (registration) and authentication (login)

    @PostMapping("/register")
    public ResponseEntity<RegisterResponse> register(@RequestBody RegisterRequest registerRequest) {
        RegisterResponse registerResponse = accountService.register(registerRequest);
        return new ResponseEntity<>(registerResponse, HttpStatus.CREATED);
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@RequestBody LoginRequest loginRequest) {
        AuthResponse authResponse = accountService.authenticate(loginRequest);
        return new ResponseEntity<>(authResponse, HttpStatus.OK);
    }

    // Reading their own account details

    @GetMapping()
    public ResponseEntity<AccountResponse> getAccount() {

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String email = auth.getName();

        AccountResponse accountResponse = accountService.getAccount(email);
        return new ResponseEntity<>(accountResponse, HttpStatus.OK);

    }

    // Updating their account details

    @PatchMapping()
    public ResponseEntity<Void> update(@RequestBody UpdateRequest updateRequest) {

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String email = auth.getName();

        accountService.update(updateRequest, email);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    // updating subscription

    @PatchMapping("/subscription/upgrade")
    public ResponseEntity<Void> upgradeSubscription() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String email = auth.getName();
    
        accountService.upgradeSubscription(email);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PatchMapping("/subscription/downgrade")
    public ResponseEntity<Void> downgradeSubscription() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String email = auth.getName();

        accountService.downgradeSubscription(email);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
