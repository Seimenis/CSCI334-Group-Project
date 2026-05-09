package com.example.accounts.service;

import java.time.LocalDateTime;
import java.util.Optional;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.accounts.dto.event.AccountCreatedEvent;
import com.example.accounts.dto.event.LoginFailedEvent;
import com.example.accounts.dto.event.LoginSucceededEvent;
import com.example.accounts.dto.event.TokenIssuedEvent;
import com.example.accounts.dto.request.LoginRequest;
import com.example.accounts.dto.request.RegisterRequest;
import com.example.accounts.dto.response.AuthResponse;
import com.example.accounts.dto.response.RegisterResponse;
import com.example.accounts.model.Account;
import com.example.accounts.repository.AccountRepository;
import com.example.accounts.util.Role;

@Service
public class AccountService {

    private final AccountRepository accountRepository;
    private final AccountProducerService accountEventProducer;
    private final PasswordEncoder passwordEncoder;
    private final JWTService jwtService;

    public AccountService(
        AccountRepository accountRepository,
        AccountProducerService accountEventProducer,
        PasswordEncoder passwordEncoder,
        JWTService jwtService) {

        this.accountRepository = accountRepository;
        this.accountEventProducer = accountEventProducer;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
    }

    public RegisterResponse register(RegisterRequest registerRequest) {

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
        account.setCreatedAt(LocalDateTime.now());
        account.setPassword(passwordEncoder.encode(registerRequest.getPassword()));
        account.setRole(Role.USER);
        Account savedAccount = accountRepository.save(account);

        
        // Publish kafka event
        AccountCreatedEvent event = new AccountCreatedEvent(savedAccount.getId(), savedAccount.getEmail(), savedAccount.getUsername());
        accountEventProducer.publishAccountCreatedEvent(event);

        // Return the response
        return new RegisterResponse(
            savedAccount.getId(),
            savedAccount.getUsername(),
            savedAccount.getEmail(),
            savedAccount.getCreatedAt(),
            "Account created successfully"
        );
    }

    public AuthResponse authenticate(LoginRequest loginRequest) {

        Optional<Account> optionalAccount = accountRepository.findByEmail(loginRequest.getEmail());

        // Check if user exists
        if (optionalAccount.isEmpty()) {
            LoginFailedEvent event = new LoginFailedEvent(loginRequest.getEmail(), "No account found with email: " + loginRequest.getEmail());

            accountEventProducer.publishLoginFailedEvent(event);
            throw new IllegalArgumentException("Invalid email or password");
        }

        Account account = optionalAccount.get();

        // Verify password
        boolean matches = passwordEncoder.matches(
            loginRequest.getPassword(), 
            account.getPassword()
        );

        if (!matches) {
            LoginFailedEvent event = new LoginFailedEvent(loginRequest.getEmail(), "Invalid password");

            accountEventProducer.publishLoginFailedEvent(event);
            throw new IllegalArgumentException("Invalid email or password");
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

        LoginSucceededEvent loginEvent = new LoginSucceededEvent(
            account.getId(),
            account.getEmail(),
            account.getUsername()
        );

        accountEventProducer.publishLoginSucceededEvent(loginEvent);

        // Return response
        return new AuthResponse(
            account.getId(),
            account.getUsername(),
            account.getEmail(),
            account.getRole(),
            token
        );
    }
}
