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
}
