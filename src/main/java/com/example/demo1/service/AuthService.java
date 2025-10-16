package com.example.demo1.service;

import com.example.demo1.model.AppUser;

public interface AuthService {
    AppUser register(AppUser user);
    AppUser login(String username, String password);
}
