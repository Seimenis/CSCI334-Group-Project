package com.example.accounts.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import com.example.accounts.dto.response.AuthResponse;
import com.example.accounts.dto.request.LoginRequest;
import org.springframework.web.bind.annotation.RestController;

import com.example.accounts.dto.request.RegisterRequest;
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
        System.out.println("REGISTER HIT");
        return new ResponseEntity<>(registerResponse, HttpStatus.CREATED);
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@RequestBody LoginRequest loginRequest) {
        AuthResponse authResponse = accountService.authenticate(loginRequest);
        return new ResponseEntity<>(authResponse, HttpStatus.OK);
    }
}
