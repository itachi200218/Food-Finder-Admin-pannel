package com.example.demo1.service.impl;

import com.example.demo1.model.AppUser;
import com.example.demo1.repository.AppUserRespository;
import com.example.demo1.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthServiceImp implements AuthService {

    BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

    @Autowired
    AppUserRespository appUserRespository;

    @Override
    public AppUser register(AppUser user) {
        user.setPassword(encoder.encode(user.getPassword()));
        return appUserRespository.save(user);
    }

    @Override
    public AppUser login(String username, String password) {
        if (username == null || password == null) {
            throw new RuntimeException("Username or password cannot be null");
        }

        AppUser user = appUserRespository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found"));

        if (!encoder.matches(password, user.getPassword())) {
            throw new RuntimeException("Wrong password");
        }

        return user;
    }

    @Override
    public AppUser forgotUsername(String email) {
        return appUserRespository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));
    }

    @Override
    public AppUser resetPassword(String username, String currentPassword, String newPassword) {
        AppUser user = appUserRespository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found"));

        // ✅ Check if the current password matches
        if (!encoder.matches(currentPassword, user.getPassword())) {
            throw new RuntimeException("Current password is incorrect");
        }

        // ✅ Encode and save the new password
        user.setPassword(encoder.encode(newPassword));
        return appUserRespository.save(user);
    }
    @Override
    public AppUser getProfile(String username) {
        return appUserRespository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found"));
    }
    public AppUser updatePasswordWithoutOld(String username, String newPassword) {
        AppUser user = appUserRespository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found"));

        String encodedPassword = new org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder().encode(newPassword);
        user.setPassword(encodedPassword);
        return appUserRespository.save(user);
    }

}
