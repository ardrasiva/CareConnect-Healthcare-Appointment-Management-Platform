package com.careconnect.controller.api;

import com.careconnect.dto.LoginRequest;
import com.careconnect.dto.RegisterRequest;
import com.careconnect.service.AuthService;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api")
public class AuthController {

    private final AuthService authService;

    public AuthController(
            AuthService authService) {

        this.authService = authService;
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(
            @RequestBody RegisterRequest request) {

        try {

            authService.register(request);

            return ResponseEntity
                    .status(HttpStatus.CREATED)
                    .body(
                        Map.of(
                            "message",
                            "Registration successful."
                        )
                    );

        } catch (IllegalArgumentException e) {

            return ResponseEntity
                    .badRequest()
                    .body(
                        Map.of(
                            "error",
                            e.getMessage()
                        )
                    );
        }
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(
            @RequestBody LoginRequest request) {

        try {

            String token =
                    authService.login(request);

            return ResponseEntity.ok(
                Map.of(
                    "token",
                    token
                )
            );

        } catch (IllegalArgumentException e) {

            return ResponseEntity
                    .badRequest()
                    .body(
                        Map.of(
                            "error",
                            e.getMessage()
                        )
                    );
        }
    }

    @PostMapping("/logout")
    public ResponseEntity<?> logout(
            @RequestHeader("Authorization")
            String authorizationHeader) {

        if (authorizationHeader == null
                || !authorizationHeader
                    .startsWith("Bearer ")) {

            return ResponseEntity
                    .status(HttpStatus.UNAUTHORIZED)
                    .body(
                        Map.of(
                            "error",
                            "Authentication required."
                        )
                    );
        }

        String token =
                authorizationHeader.substring(7);

        authService.logout(token);

        return ResponseEntity.ok(
            Map.of(
                "message",
                "Logout successful."
            )
        );
    }
}