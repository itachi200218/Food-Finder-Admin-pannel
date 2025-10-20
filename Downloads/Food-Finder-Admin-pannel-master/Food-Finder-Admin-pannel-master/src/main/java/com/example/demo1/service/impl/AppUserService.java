package com.example.demo1.service.impl;

import com.example.demo1.model.AppUser;
import com.example.demo1.repository.AppUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class AppUserService {

    @Autowired
    private AppUserRepository appUserRepository;

    private static final String ADMIN_PASSWORD = "admin123";

    public String handleUserQueries(String query) {
        query = query.toLowerCase().trim();

        if (query.contains("user list") || query.contains("all users") || query.contains("show all usernames")) {
            String[] parts = query.split("password:");
            if (parts.length < 2) {
                return """
                        Admin AI:
                        I can show you all the users in the system. However, for security reasons, 
                        please provide the admin password in your message.
                        
                        Example:
                        👉 "Show all users password:admin123"
                        """;
            }

            String enteredPassword = parts[1].trim();
            if (!enteredPassword.equals(ADMIN_PASSWORD)) {
                return "❌ Access Denied: Incorrect password.";
            }

            // ✅ Fetch actual users from database
            List<AppUser> users = appUserRepository.findAll();
            if (users.isEmpty()) {
                return "No users found in the database.";
            }

            // Format data
            StringBuilder sb = new StringBuilder("""
                    Admin AI:
                    Okay, here are the real users from the database 👇

                    Username\t\tLast Updated
                    -----------------------------------------
                    """);

            for (AppUser user : users) {
                sb.append(user.getUsername()).append("\t\t");
                sb.append(user.getUpdatedAt() != null ? user.getUpdatedAt() : "N/A");
                sb.append("\n");
            }

            sb.append("\nIf you need more details, please use the admin dashboard.");
            return sb.toString();
        }

        else if (query.contains("user count")) {
            long count = appUserRepository.count();
            return "There are " + count + " registered users.";
        }

        return "I'm not sure how to handle this user query.";
    }
}
