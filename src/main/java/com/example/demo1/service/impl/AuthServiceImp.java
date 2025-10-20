package com.example.demo1.service.impl;

import com.example.demo1.model.AppUser;
import com.example.demo1.repository.AppUserRepository; // ✅ corrected import
import com.example.demo1.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthServiceImp implements AuthService {

    private final BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

    @Autowired
    private AppUserRepository appUserRepository; // ✅ corrected variable name

    @Override
    public AppUser register(AppUser user) {
        user.setPassword(encoder.encode(user.getPassword()));
        return appUserRepository.save(user);
    }

    @Override
    public AppUser login(String username, String password) {
        if (username == null || password == null) {
            throw new RuntimeException("Username or password cannot be null");
        }

        AppUser user = appUserRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found"));

        if (!encoder.matches(password, user.getPassword())) {
            throw new RuntimeException("Wrong password");
        }

        return user;
    }

    @Override
    public AppUser forgotUsername(String email) {
        return appUserRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));
    }

    @Override
    public AppUser resetPassword(String username, String currentPassword, String newPassword) {
        AppUser user = appUserRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found"));

        if (!encoder.matches(currentPassword, user.getPassword())) {
            throw new RuntimeException("Current password is incorrect");
        }

        user.setPassword(encoder.encode(newPassword));
        return appUserRepository.save(user);
    }

    @Override
    public AppUser getProfile(String username) {
        return appUserRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found"));
    }

    @Override
    public AppUser updatePasswordWithoutOld(String username, String newPassword) {
        AppUser user = appUserRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found"));

        user.setPassword(encoder.encode(newPassword));
        return appUserRepository.save(user);
    }
}
