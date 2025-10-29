package com.example.demo1.service.impl;

import org.springframework.stereotype.Service;

@Service
public class QueryParserService {

    public String extractWordAfter(String text, String keyword) {
        String[] words = text.split("\\s+");
        for (int i = 0; i < words.length - 1; i++) {
            if (words[i].equalsIgnoreCase(keyword)) return words[i + 1];
        }
        return null;
    }

    public String extractAfter(String text, String key) {
        int index = text.indexOf(key);
        if (index == -1) return null;
        return text.substring(index + key.length()).split(",|\\.|and")[0].trim();
    }

    public Integer extractCategoryId(String text) {
        try {
            String lower = text.toLowerCase();
            if (lower.contains("category")) {
                String after = lower.substring(lower.indexOf("category"));
                String digits = after.replaceAll("[^0-9]", "").trim();
                if (!digits.isEmpty()) {
                    return Integer.parseInt(digits);
                }
            }
        } catch (Exception e) {
            System.out.println("Error extracting category ID: " + e.getMessage());
        }
        return null;
    }

    // -------------------------------------------------------
    // 🔹 Extended Query Detection (for all your URLs)
    // -------------------------------------------------------
    public String parseQueryType(String query) {
        query = query.toLowerCase();

        // 👥 USER QUERIES
        if (query.contains("show all usernames") || query.contains("list all usernames")) return "USER_LIST";
        if (query.contains("show user count") || query.contains("how many users")) return "USER_COUNT";
        if (query.contains("most active user") || query.contains("which user is most active")) return "MOST_ACTIVE_USER";
        if (query.contains("most recipes by user")) return "USER_TOP_RECIPE_COUNT";

        // 🍳 RECIPE ANALYTICS
        if (query.contains("list duplicate recipes") || query.contains("same name")) return "DUPLICATE_RECIPES";
        if (query.contains("category has the most recipes") || query.contains("top category by recipes")) return "TOP_CATEGORY";
        if (query.contains("total recipes per category")) return "RECIPES_PER_CATEGORY";
        if (query.contains("summary per category")) return "SUMMARY_PER_CATEGORY";
        if (query.contains("total recipes") || query.contains("how many recipes")) return "TOTAL_RECIPES";
        if (query.contains("missing ingredients") || query.contains("without ingredients")) return "RECIPES_MISSING_INGREDIENTS";

        // 🧠 DATABASE INSIGHTS
        if (query.contains("database structure") || query.contains("explain database tables")) return "DB_STRUCTURE";
        if (query.contains("relationships") || query.contains("foreign key")) return "DB_RELATIONSHIPS";
        if (query.contains("list tables") || query.contains("all tables")) return "DB_TABLES";

        // 🧩 FALLBACK
        return "UNKNOWN_QUERY";
    }
}
