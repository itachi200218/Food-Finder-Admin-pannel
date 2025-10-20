package com.example.demo1.service.impl;

import com.example.demo1.model.AppUser;
import com.example.demo1.repository.AppUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class UserAnalyticsService {

    @Autowired
    private AppUserRepository userRepository;

    // 🧍‍♂️ 1. Show all usernames
    public String getAllUsernames() {
        List<AppUser> users = userRepository.findAll();
        if (users.isEmpty()) return "No users found in the database.";

        StringBuilder sb = new StringBuilder("Usernames:\n");
        users.forEach(u -> sb.append("- ").append(u.getUsername()).append("\n"));
        return sb.toString();
    }

    // 🔢 2. Show total user count
    public String getUserCount() {
        long count = userRepository.count();
        return "Total users: " + count;
    }

    // 🧠 3. Find most active user (example by most recipes or last login)
    public String getMostActiveUser() {
        // Assuming AppUser has a field like `activityCount` or `lastLogin`
        List<AppUser> users = userRepository.findAll();

        if (users.isEmpty()) return "No users found to analyze activity.";

        AppUser mostActive = users.stream()
                .max(Comparator.comparing(AppUser::getId)) // replace with getActivityCount() if available
                .orElse(null);

        return mostActive != null
                ? "Most active user: " + mostActive.getUsername()
                : "Unable to determine the most active user.";
    }

    // 🧍‍♀️ 4. Find duplicate usernames
    public String findDuplicateUsers() {
        List<AppUser> users = userRepository.findAll();
        Map<String, Long> nameCount = users.stream()
                .collect(Collectors.groupingBy(AppUser::getUsername, Collectors.counting()));

        List<String> duplicates = nameCount.entrySet().stream()
                .filter(e -> e.getValue() > 1)
                .map(Map.Entry::getKey)
                .toList();

        if (duplicates.isEmpty()) return "No duplicate usernames found.";
        return "Duplicate usernames:\n" + String.join("\n", duplicates);
    }

    // 🔍 5. Dynamic query handling (used by GeminiServiceImpl)
    public String handleUserQueries(String query) {
        query = query.toLowerCase();

        if (query.contains("all usernames") || query.contains("user list")) {
            return getAllUsernames();
        } else if (query.contains("user count") || query.contains("total users")) {
            return getUserCount();
        } else if (query.contains("most active user")) {
            return getMostActiveUser();
        } else if (query.contains("duplicate users")) {
            return findDuplicateUsers();
        } else {
            return "I'm not sure how to process this user-related query.";
        }
    }
}
