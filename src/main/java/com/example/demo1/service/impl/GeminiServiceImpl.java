package com.example.demo1.service.impl;

import com.example.demo1.model.AppUser;
import com.example.demo1.model.User;
import com.example.demo1.model.Category;
import com.example.demo1.repository.AppUserRepository;
import com.example.demo1.repository.UserRepository;
import com.example.demo1.repository.CategoryRepository;
import com.example.demo1.service.GeminiService;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.http.*;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class GeminiServiceImpl implements GeminiService {

    @Value("${gemini.api.key}")
    private String geminiApiKey;

    @Autowired private AppUserRepository appUserRepository;
    @Autowired private UserRepository userRepository;
    @Autowired private CategoryRepository categoryRepository;

    private static final String GEMINI_API_URL =
            "https://generativelanguage.googleapis.com/v1beta/models/gemini-2.0-flash:generateContent";

    // 🧠 Main entry for Gemini queries
    @Override
    public String generateExplanation(String query) {
        // Step 1: Try to generate DB-based logic if possible
        String dbResult = handleAdminQuery(query);

        // Step 2: If no DB-related info found, directly use Gemini conversationally
        if (dbResult.startsWith("I'm not sure")) {
            return callGemini("Answer conversationally and naturally: " + query);
        }

        // Step 3: If we got a DB result, ask Gemini to make it clear for the admin
        return callGemini("Explain this clearly and naturally for admin: " + dbResult);
    }

    // 🧩 Handles admin DB-related queries
    @Override
    public String handleAdminQuery(String query) {
        query = query.toLowerCase();

        // 👥 USER ANALYTICS
        if (query.contains("username") || query.contains("user list")) {
            List<AppUser> users = appUserRepository.findAll();
            if (users.isEmpty()) return "No users found in the system.";
            String usernames = users.stream()
                    .map(AppUser::getUsername)
                    .collect(Collectors.joining(", "));
            return "List of all registered usernames: " + usernames;

        } else if (query.contains("user") && query.contains("count")) {
            return "There are " + appUserRepository.count() + " registered users.";

        } else if (query.contains("most active user") || query.contains("most uploads")) {
            return "Feature to identify most active user will be added later.";

        }

        // 🍳 RECIPE ANALYTICS
        else if (query.contains("duplicate") && query.contains("recipe")) {
            List<String> duplicates = userRepository.findAll().stream()
                    .collect(Collectors.groupingBy(User::getName, Collectors.counting()))
                    .entrySet().stream()
                    .filter(e -> e.getValue() > 1)
                    .map(Map.Entry::getKey)
                    .collect(Collectors.toList());

            return duplicates.isEmpty()
                    ? "No duplicate recipe names found."
                    : "Duplicate recipes: " + String.join(", ", duplicates);

        } else if (query.contains("category") && query.contains("most")) {
            Map<Integer, Long> countPerCat = userRepository.findAll().stream()
                    .collect(Collectors.groupingBy(User::getCategoryId, Collectors.counting()));

            if (countPerCat.isEmpty()) return "No categories found.";

            Map.Entry<Integer, Long> max = Collections.max(countPerCat.entrySet(), Map.Entry.comparingByValue());
            String catName = categoryRepository.findById(max.getKey())
                    .map(Category::getName)
                    .orElse("Unknown");
            return "Category with the most recipes: " + catName + " (" + max.getValue() + " recipes).";

        } else if (query.contains("summary per category") || query.contains("recipes per category") || query.contains("category summary")) {
            Map<Integer, Long> summary = userRepository.findAll().stream()
                    .collect(Collectors.groupingBy(User::getCategoryId, Collectors.counting()));

            if (summary.isEmpty()) return "No recipe data found.";

            StringBuilder sb = new StringBuilder("📊 Recipe count per category:\n");
            for (Map.Entry<Integer, Long> e : summary.entrySet()) {
                String catName = categoryRepository.findById(e.getKey())
                        .map(Category::getName).orElse("Unknown");
                sb.append("- ").append(catName).append(": ").append(e.getValue()).append("\n");
            }
            return sb.toString();

        } else if (query.contains("total recipes") || query.contains("recipe count")) {
            return "Total recipes in the system: " + userRepository.count();

        } else if (query.contains("missing ingredients")) {
            long missing = userRepository.findAll().stream()
                    .filter(u -> u.getIngredients() == null || u.getIngredients().isBlank())
                    .count();
            return missing == 0
                    ? "All recipes have ingredient details."
                    : missing + " recipes are missing ingredient details.";

        }

        // 🧠 DATABASE OVERVIEW
        else if (query.contains("database structure") || query.contains("tables")) {
            return "Database structure: AppUser (users), User (recipes), Category (categories). Recipes link to categories by categoryId.";

        } else if (query.contains("relationship") && query.contains("database")) {
            return "Each user can upload multiple recipes. Each recipe belongs to one category.";

        }

        // 📈 AI INSIGHTS or CONVERSATION
        else if (query.contains("trend") || query.contains("growth") || query.contains("report") || query.contains("predict")) {
            return "This is an AI-based insight. Gemini will generate the response.";

        }

        // 💬 Fallback for normal talk
        else {
            return "I'm not sure how to handle this query directly in DB context.";
        }
    }

    // ⚡ Gemini API call
    private String callGemini(String prompt) {
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        String apiUrl = GEMINI_API_URL + "?key=" + geminiApiKey;
        String requestBody = "{ \"contents\": [{\"parts\":[{\"text\": \"" + prompt.replace("\"", "\\\"") + "\"}]}]}";
        HttpEntity<String> entity = new HttpEntity<>(requestBody, headers);

        try {
            ResponseEntity<String> response = restTemplate.postForEntity(apiUrl, entity, String.class);
            JSONObject jsonResponse = new JSONObject(response.getBody());
            JSONArray candidates = jsonResponse.getJSONArray("candidates");
            JSONObject content = candidates.getJSONObject(0).getJSONObject("content");
            JSONArray parts = content.getJSONArray("parts");
            return parts.getJSONObject(0).getString("text");
        } catch (Exception e) {
            return "Error calling Gemini API: " + e.getMessage();
        }
    }
}
