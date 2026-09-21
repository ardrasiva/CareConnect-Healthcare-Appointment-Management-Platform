package com.careconnect.service;

import com.careconnect.dto.LoginRequest;
import com.careconnect.dto.RegisterRequest;
import com.careconnect.entity.AuthToken;
import com.careconnect.entity.Role;
import com.careconnect.entity.User;
import com.careconnect.repository.AuthTokenRepository;
import com.careconnect.repository.UserRepository;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
public class AuthService {

    private final UserRepository userRepository;

    private final AuthTokenRepository authTokenRepository;

    private final PasswordEncoder passwordEncoder;

    public AuthService(
            UserRepository userRepository,
            AuthTokenRepository authTokenRepository,
            PasswordEncoder passwordEncoder) {

        this.userRepository = userRepository;
        this.authTokenRepository =
                authTokenRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Transactional
    public void register(RegisterRequest request) {

        if (userRepository
                .findByEmail(request.getEmail())
                .isPresent()) {

            throw new IllegalArgumentException(
                "A user with this email already exists."
            );
        }

        User user = new User();

        user.setFirstName(
            request.getFirstName()
        );

        user.setLastName(
            request.getLastName()
        );

        user.setEmail(
            request.getEmail()
        );

        user.setPassword(
            passwordEncoder.encode(
                request.getPassword()
            )
        );

        user.setPhoneNumber(
            request.getPhoneNumber()
        );

        user.setDateOfBirth(
            request.getDateOfBirth()
        );

        user.setGender(
            request.getGender()
        );

        user.setAddress(
            request.getAddress()
        );

        user.setRole(Role.PATIENT);

        user.setIsActive(true);

        userRepository.save(user);
    }

    public String login(LoginRequest request) {

        User user = userRepository
                .findByEmail(request.getEmail())
                .orElseThrow(() ->
                    new IllegalArgumentException(
                        "Invalid email or password."
                    )
                );

        if (!passwordEncoder.matches(
                request.getPassword(),
                user.getPassword())) {

            throw new IllegalArgumentException(
                "Invalid email or password."
            );
        }

        if (!user.getIsActive()) {

            throw new IllegalArgumentException(
                "This account is inactive."
            );
        }

        AuthToken authToken =
                authTokenRepository
                    .findByUser(user)
                    .orElseGet(() -> {

                        AuthToken token =
                                new AuthToken();

                        token.setUser(user);

                        token.setToken(
                            UUID.randomUUID()
                                .toString()
                        );

                        return token;
                    });

        authTokenRepository.save(authToken);

        return authToken.getToken();
    }

    @Transactional
    public void logout(String token) {

        authTokenRepository.deleteByToken(token);
    }
}