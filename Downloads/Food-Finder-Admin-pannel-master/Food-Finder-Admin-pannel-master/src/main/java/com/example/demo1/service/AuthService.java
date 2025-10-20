package com.example.demo1.service;

import com.example.demo1.model.AppUser;

public interface AuthService {

    AppUser register(AppUser appUser);

    AppUser login(String username, String password);

    AppUser forgotUsername(String email);

    AppUser resetPassword(String username, String currentPassword, String newPassword);

    AppUser getProfile(String username);

    AppUser updatePasswordWithoutOld(String username, String newPassword);
}