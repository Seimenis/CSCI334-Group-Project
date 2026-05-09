package com.example.accounts.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.accounts.dto.request.LoginRequest;
import com.example.accounts.dto.request.RegisterRequest;
import com.example.accounts.dto.response.AuthResponse;
import com.example.accounts.dto.response.RegisterResponse;
import com.example.accounts.service.AccountAdminService;

@RestController
@RequestMapping("/admin")
public class AccountAdminController {

    private final AccountAdminService accountAdminService;

    public AccountAdminController(AccountAdminService accountAdminService) {
        this.accountAdminService = accountAdminService;
    }

    @PostMapping("/register-staff")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<RegisterResponse> registerStaff(@RequestBody RegisterRequest registerRequest) {
        RegisterResponse registerResponse = accountAdminService.registerStaff(registerRequest);
        return new ResponseEntity<>(registerResponse, HttpStatus.CREATED);
    }

    @PostMapping("/login")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<AuthResponse> login(@RequestBody LoginRequest loginRequest) {
        AuthResponse authResponse = accountAdminService.authenticateAdmin(loginRequest);
        return new ResponseEntity<>(authResponse, HttpStatus.OK);
    }
}