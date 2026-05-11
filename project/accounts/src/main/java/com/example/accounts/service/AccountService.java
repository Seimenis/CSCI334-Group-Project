package com.example.accounts.service;

import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.example.accounts.dto.event.AccountCreatedEvent;
import com.example.accounts.dto.event.AccountUpdatedEvent;
import com.example.accounts.dto.event.LoginFailedEvent;
import com.example.accounts.dto.event.LoginSucceededEvent;
import com.example.accounts.dto.event.TokenIssuedEvent;
import com.example.accounts.dto.request.LoginRequest;
import com.example.accounts.dto.request.RegisterRequest;
import com.example.accounts.dto.request.UpdateRequest;
import com.example.accounts.dto.response.AccountResponse;
import com.example.accounts.dto.response.AuthResponse;
import com.example.accounts.dto.response.RegisterResponse;
import com.example.accounts.model.Account;
import com.example.accounts.repository.AccountRepository;
import com.example.accounts.security.JwtService;
import com.example.accounts.util.Role;

@Service
public class AccountService {

    private final AccountRepository accountRepository;
    private final AccountProducerService accountEventProducer;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;

    public AccountService(
        AccountRepository accountRepository,
        AccountProducerService accountEventProducer,
        PasswordEncoder passwordEncoder,
        JwtService jwtService) {

        this.accountRepository = accountRepository;
        this.accountEventProducer = accountEventProducer;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
    }

    // Create

    public RegisterResponse register(RegisterRequest registerRequest) {
        return register(registerRequest, Role.USER);
    }

    public RegisterResponse register(RegisterRequest registerRequest, Role role) {

        // Check if email or username already exists
        if (accountRepository.existsByEmail(registerRequest.getEmail())) {
            throw new IllegalArgumentException("Email already in use");
        }
        if (accountRepository.existsByUsername(registerRequest.getUsername())) {
            throw new IllegalArgumentException("Username already in use");
        }

        // Create and save the new account
        Account account = new Account();
        account.setUsername(registerRequest.getUsername());
        account.setEmail(registerRequest.getEmail());
        account.setPassword(passwordEncoder.encode(registerRequest.getPassword()));
        account.setRole(role);
        account.setEnabled(true);
        account = accountRepository.save(account);

        // Publish kafka event
        AccountCreatedEvent event = new AccountCreatedEvent(account);
        accountEventProducer.publishAccountCreatedEvent(event);

        // Return the response
        return new RegisterResponse(account, "Account created successfully");
    }

    // Authenticate (Login)

    public AuthResponse authenticate(LoginRequest loginRequest) {

        Optional<Account> optionalAccount = accountRepository.findByEmail(loginRequest.getEmail());

        // Check if user exists
        if (optionalAccount.isEmpty()) {
            LoginFailedEvent event = new LoginFailedEvent(loginRequest.getEmail(), "No account found with email: " + loginRequest.getEmail());

            accountEventProducer.publishLoginFailedEvent(event);
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Invalid email or password");
        }

        Account account = optionalAccount.get();

        // Verify password
        boolean matches = passwordEncoder.matches(
            loginRequest.getPassword(), 
            account.getPassword()
        );

        if (!matches) {
            LoginFailedEvent event = new LoginFailedEvent(account, "Invalid password");

            accountEventProducer.publishLoginFailedEvent(event);
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Invalid email or password");
        }

        // Generate JWT token
        String token = jwtService.generateToken(account);
        
        TokenIssuedEvent tokenEvent = new TokenIssuedEvent(
            account.getId(),
            jwtService.extractIssuedAt(token),
            jwtService.extractExpiration(token)
        );

        accountEventProducer.publishTokenIssuedEvent(tokenEvent);

        // Publish login success event
        LoginSucceededEvent loginEvent = new LoginSucceededEvent(account);
        accountEventProducer.publishLoginSucceededEvent(loginEvent);

        // Return response
        return new AuthResponse(account, token);
    }

    // Read

    public AccountResponse getAccount(String email) {
        Account account = accountRepository.findByEmail(email)
            .orElseThrow(() -> new RuntimeException("Account not found"));

        return new AccountResponse(account);
    }


    // Update
    public void update(UpdateRequest updateRequest, String email) {
        Account account = accountRepository.findByEmail(email)
            .orElseThrow(() -> new RuntimeException("Account not found"));

        if (updateRequest.getUsername() != null)
            account.setUsername(updateRequest.getUsername());

        if (updateRequest.getEmail() != null)
            account.setEmail(updateRequest.getEmail());

        if (updateRequest.getPassword() != null)
            account.setPassword(passwordEncoder.encode(updateRequest.getPassword()));

        accountRepository.save(account);

        // Publish kafka event
        AccountUpdatedEvent event = new AccountUpdatedEvent(account);
        accountEventProducer.publishAccountUpdatedEvent(event);
    }
    
}
