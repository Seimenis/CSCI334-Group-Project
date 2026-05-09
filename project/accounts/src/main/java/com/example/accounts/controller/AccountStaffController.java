package com.example.accounts.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.accounts.dto.request.RegisterRequest;
import com.example.accounts.dto.response.RegisterResponse;
import com.example.accounts.service.AccountStaffService;


@RestController
@RequestMapping("/staff")
public class AccountStaffController {
    
    private final AccountStaffService accountStaffService;

    public AccountStaffController(AccountStaffService accountStaffService) {
        this.accountStaffService = accountStaffService;
    }

    @PostMapping("/register")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<RegisterResponse> registerStaff(@RequestBody RegisterRequest registerRequest) {
        RegisterResponse registerResponse = accountStaffService.registerStaff(registerRequest);
        return new ResponseEntity<>(registerResponse, HttpStatus.NOT_IMPLEMENTED);
    }

}
