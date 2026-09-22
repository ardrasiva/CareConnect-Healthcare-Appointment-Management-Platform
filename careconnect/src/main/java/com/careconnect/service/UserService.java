package com.careconnect.service;

import com.careconnect.entity.Role;
import com.careconnect.entity.User;
import com.careconnect.repository.UserRepository;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

     private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserService(
            UserRepository userRepository,
            PasswordEncoder passwordEncoder) {

        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
     }

    public List<User> getAllPatients() {

        return userRepository.findByRole(
            Role.PATIENT
        );
    }

    public User getUserById(Long id) {

        return userRepository
                .findById(id)
                .orElseThrow();
    }
    //UserService.java

public long getPatientCount() {
    return userRepository.countByRole(Role.PATIENT);
}
public void changePassword(
        User user,
        String currentPassword,
        String newPassword) {

    if (!passwordEncoder.matches(
            currentPassword,
            user.getPassword())) {

        throw new RuntimeException(
                "Current password is incorrect."
        );
    }

    user.setPassword(
            passwordEncoder.encode(newPassword)
    );

    userRepository.save(user);
}
}