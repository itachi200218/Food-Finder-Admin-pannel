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

        StringBuilder sb = new StringBuilder("👥 Usernames:\n");
        users.forEach(u -> sb.append("- ").append(u.getUsername()).append("\n"));
        return sb.toString();
    }

    // 🔢 2. Show total user count
    public String getUserCount() {
        long count = userRepository.count();
        return "📊 Total users: " + count;
    }

    // 🧠 3. Find most active user (example: based on ID or activity count)
    public String getMostActiveUser() {
        List<AppUser> users = userRepository.findAll();
        if (users.isEmpty()) return "No users found to analyze activity.";

        // You can replace getId() with a real metric like getActivityCount() if you have it
        AppUser mostActive = users.stream()
                .max(Comparator.comparing(AppUser::getId))
                .orElse(null);

        return mostActive != null
                ? "🔥 Most active user: " + mostActive.getUsername()
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

        if (duplicates.isEmpty()) return "✅ No duplicate usernames found.";
        return "⚠️ Duplicate usernames:\n" + String.join("\n", duplicates);
    }

    // 🔍 5. Handle user-related natural language queries
    public String handleUserQueries(String query) {
        query = query.toLowerCase();

        if (query.contains("all usernames") || query.contains("user list") || query.contains("list all users")) {
            return getAllUsernames();

        } else if (query.contains("user count") || query.contains("total users") || query.contains("how many users")) {
            return getUserCount();

        } else if (query.contains("most active user") || query.contains("top user")) {
            return getMostActiveUser();

        } else if (query.contains("duplicate users") || query.contains("same username")) {
            return findDuplicateUsers();

        } else {
            return "I'm not sure how to process this user-related query.";
        }
    }

    // 📈 6. Generate full user analytics report for Admin AI
    public String generateUserReport() {
        List<AppUser> users = userRepository.findAll();
        long count = users.size();
        String mostActive = users.isEmpty() ? "N/A" :
                users.stream()
                        .max(Comparator.comparing(AppUser::getId))
                        .map(AppUser::getUsername)
                        .orElse("N/A");

        Map<String, Long> nameCount = users.stream()
                .collect(Collectors.groupingBy(AppUser::getUsername, Collectors.counting()));

        long duplicateCount = nameCount.values().stream().filter(v -> v > 1).count();

        return """
        🧾 USER ANALYTICS SUMMARY

        👥 Total Users: %d
        🔥 Most Active User: %s
        ⚠️ Duplicate Username Count: %d

        ✅ User Insights:
        - Helps identify top contributors
        - Detects naming duplicates
        - Useful for user engagement tracking
        """.formatted(count, mostActive, duplicateCount);
    }
}
