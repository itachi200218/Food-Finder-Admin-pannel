package com.example.demo1.service.impl;
import com.example.demo1.model.AppUser;
import com.example.demo1.repository.AppUserRespository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import com.example.demo1.service.AuthService;
import org.springframework.stereotype.Service;

import java.beans.Encoder;
@Service
public class AuthServiceImp implements AuthService {
    private BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
@Autowired
private AppUserRespository appUserRespository;
    @Override
    public AppUser  register(AppUser user) {
        user.setPassword(encoder.encode(user.getPassword()));
        return appUserRespository.save(user);
    }
    @Override
    public AppUser login(String username, String password) {
        AppUser user = appUserRespository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found"));
        if(!encoder.matches(password, user.getPassword())) {
            throw new RuntimeException("Invalid credits");
        }
        return user;
    }
}
