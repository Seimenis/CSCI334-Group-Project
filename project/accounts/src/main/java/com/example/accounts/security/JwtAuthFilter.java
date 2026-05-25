package com.example.accounts.security;

import java.io.IOException;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class JwtAuthFilter extends OncePerRequestFilter {

    private final JwtService jwtService;
    private final AccountUserDetailsService accountUserDetailsService;

    public JwtAuthFilter(JwtService jwtService, AccountUserDetailsService accountUserDetailsService) {
        this.jwtService = jwtService;
        this.accountUserDetailsService = accountUserDetailsService;
    }

    @Override
    protected void doFilterInternal(
        HttpServletRequest request,
        HttpServletResponse response,
        FilterChain filterChain
    ) throws ServletException, IOException {

        String path = request.getRequestURI();

        // Skip auth endpoints
        if (path.contains("/login") || path.contains("/register")) {
            filterChain.doFilter(request, response);
            return;
        }

        String token = null;

        if (request.getCookies() != null) {
            for (Cookie cookie : request.getCookies()) {
                if ("jwt".equals(cookie.getName())) {
                    token = cookie.getValue();
                    break;
                }
            }
        }

        // No token -> continue chain
        if (token == null) {
            filterChain.doFilter(request, response);
            return;
        }

        // Extract identity
        String email = jwtService.extractEmail(token);


        // If valid and not already authenticated
        if (email != null && SecurityContextHolder.getContext().getAuthentication() == null) {

            try {
                
                if (jwtService.isTokenValid(token)) {
                    
                    UserDetails userDetails = accountUserDetailsService.loadUserByUsername(email);
                    
                    // Build authentication object
                    UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(
                        userDetails, null, userDetails.getAuthorities());
                        
                    auth.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                    SecurityContextHolder.getContext().setAuthentication(auth);
                }

            } catch (Exception e) {
                System.out.println("Exception caught here: " + e.getMessage());
                SecurityContextHolder.clearContext();
            }
        }

        filterChain.doFilter(request, response);
    }

}
