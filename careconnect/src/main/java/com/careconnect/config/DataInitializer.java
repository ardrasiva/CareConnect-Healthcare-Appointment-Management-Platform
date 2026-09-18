package com.careconnect.config;

import com.careconnect.entity.Role;
import com.careconnect.entity.User;
import com.careconnect.repository.UserRepository;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class DataInitializer {

    @Bean
    CommandLineRunner createAdministrator(
            UserRepository userRepository,
            PasswordEncoder passwordEncoder) {

        return args -> {

            if (userRepository
                    .findByEmail("admin@careconnect.com")
                    .isEmpty()) {

                User user = new User();

                user.setFirstName("Administrator");

                user.setEmail(
                    "admin@careconnect.com"
                );

                user.setPassword(
                    passwordEncoder.encode("admin123")
                );

                user.setRole(Role.ADMIN);

                userRepository.save(user);
            }
        };
    }
}