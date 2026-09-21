package com.careconnect.security;

import com.careconnect.entity.AuthToken;
import com.careconnect.entity.User;
import com.careconnect.repository.AuthTokenRepository;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;

import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.List;

public class TokenAuthenticationFilter
        extends OncePerRequestFilter {

    private final AuthTokenRepository
            authTokenRepository;

    public TokenAuthenticationFilter(
            AuthTokenRepository authTokenRepository) {

        this.authTokenRepository =
                authTokenRepository;
    }

    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain)
            throws ServletException, IOException {

        String authorization =
                request.getHeader("Authorization");

        if (authorization != null
                && authorization.startsWith("Bearer ")) {

            String token =
                    authorization.substring(7);

            AuthToken authToken =
                    authTokenRepository
                        .findByToken(token)
                        .orElse(null);

            if (authToken != null && authToken.getUser() != null) {

                User user =
                        authToken.getUser();

                SimpleGrantedAuthority authority =
                        new SimpleGrantedAuthority(
                            "ROLE_" +
                            user.getRole().name()
                        );

                UsernamePasswordAuthenticationToken
                        authentication =
                            new UsernamePasswordAuthenticationToken(
                                user,
                                null,
                                List.of(authority)
                            );

                SecurityContextHolder
                        .getContext()
                        .setAuthentication(
                            authentication
                        );
            }
        }

        filterChain.doFilter(
            request,
            response
        );
    }
}