package com.example.demo1.service.impl;

import com.example.demo1.model.AppUser;
import com.example.demo1.repository.AppUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.*;

@Service
public class AppUserService {

    @Autowired private AppUserRepository appUserRepository;

    public String handleUserQueries(String query) {
        if (query.contains("user list") || query.contains("all users")) {
            List<AppUser> users = appUserRepository.findAll();
            if (users.isEmpty()) return "No users found.";
            StringBuilder sb = new StringBuilder("👥 **All Users:**\n");
            for (AppUser u : users) {
                sb.append("- ID: ").append(u.getId())
                        .append(", Username: ").append(u.getUsername()).append("\n");
            }
            return sb.toString();

        } else if (query.contains("user count")) {
            return "There are " + appUserRepository.count() + " registered users.";
        }

        return "I'm not sure how to handle this user query.";
    }
}
