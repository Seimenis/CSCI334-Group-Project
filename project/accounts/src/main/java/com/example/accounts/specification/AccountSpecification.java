package com.example.accounts.specification;

import java.time.LocalDateTime;

import org.springframework.data.jpa.domain.Specification;

import com.example.accounts.model.Account;
import com.example.accounts.util.Role;

public class AccountSpecification {

    public static Specification<Account> enabled(Boolean enabled) {
        return (root, query, cb) ->
            enabled == null ? null : cb.equal(root.get("enabled"), enabled);
    }

    public static Specification<Account> role(Role role) {
        return (root, query, cb) ->
            role == null ? null : cb.equal(root.get("role"), role);
    }

    public static Specification<Account> createdBetween(LocalDateTime start, LocalDateTime end) {
        return (root, query, cb) ->
            cb.between(root.get("createdAt"), start, end);
    }
}