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

@Service
public class GeminiServiceImpl implements GeminiService {

    @Value("${gemini.api.key}")
    private String geminiApiKey;

    @Autowired private AppUserRepository appUserRepository;
    @Autowired private UserRepository userRepository;
    @Autowired private CategoryRepository categoryRepository;

    private static final String GEMINI_API_URL =
            "https://generativelanguage.googleapis.com/v1beta/models/gemini-2.0-flash:generateContent";

    @Override
    public String generateExplanation(String query) {
        String dbResult = handleAdminQuery(query);
        if (dbResult.startsWith("I'm not sure")) {
            return callGemini("Answer conversationally as Admin AI: " + query);
        }
        return callGemini("Explain clearly as Admin AI: " + dbResult);
    }

    @Override
    public String handleAdminQuery(String query) {
        query = query.toLowerCase();

        // USER ANALYTICS
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

        // RECIPE CRUD
        else if (query.startsWith("add recipe") || query.contains("create recipe")) {
            try {
                String name = extractWordAfter(query, "recipe");
                if (name == null || name.isBlank()) return "Please specify a recipe name.";
                User recipe = new User();
                recipe.setName(name);
                recipe.setIngredients("Auto-added via Admin AI");
                recipe.setDescription("Created from Admin AI");
                recipe.setCategoryId(1);
                userRepository.save(recipe);
                return "✅ New recipe added successfully: " + name;
            } catch (Exception e) {
                return "❌ Error adding recipe: " + e.getMessage();
            }

        } else if (query.contains("update recipe")) {
            try {
                String name = extractWordAfter(query, "recipe");
                List<User> existing = userRepository.findByName(name);
                if (existing.isEmpty()) return "Recipe not found: " + name;
                User recipe = existing.get(0);
                recipe.setDescription("Updated by Admin AI");
                userRepository.save(recipe);
                return "✅ Recipe updated successfully: " + name;
            } catch (Exception e) {
                return "❌ Error updating recipe: " + e.getMessage();
            }

        } else if (query.contains("delete recipe")) {
            try {
                String name = extractWordAfter(query, "recipe");
                if (name == null) return "Please specify the recipe name to delete.";
                List<User> recipes = userRepository.findByName(name);
                if (!recipes.isEmpty()) {
                    userRepository.deleteAll(recipes);
                    return "🗑️ Recipe deleted successfully: " + name;
                } else {
                    return "Recipe not found: " + name;
                }
            } catch (Exception e) {
                return "❌ Error deleting recipe: " + e.getMessage();
            }

        } else if (query.contains("list recipes") || query.contains("all recipes")) {
            List<User> recipes = userRepository.findAll();
            if (recipes.isEmpty()) return "No recipes found.";
            StringBuilder sb = new StringBuilder("📋 **All Recipes:**\n");
            for (User u : recipes) {
                String catName = categoryRepository.findById(u.getCategoryId())
                        .map(Category::getName).orElse("Unknown");
                sb.append("- ").append(u.getName())
                        .append(" (").append(catName).append(")\n");
            }
            return sb.toString();

        } else if (query.contains("find recipe")) {
            String name = extractWordAfter(query, "recipe");
            List<User> recipes = userRepository.findByName(name);
            if (recipes.isEmpty()) return "Recipe not found: " + name;
            User u = recipes.get(0);
            return "🍲 Recipe Found: " + u.getName() +
                    " | Ingredients: " + u.getIngredients() +
                    " | Description: " + u.getDescription();
        }

        // CATEGORY CRUD
        else if (query.contains("add category")) {
            String name = extractWordAfter(query, "category");
            if (name == null || name.isBlank()) return "Please specify a category name.";
            Category c = new Category();
            c.setName(name);
            categoryRepository.save(c);
            return "✅ New category added: " + name;

        } else if (query.contains("update category")) {
            String name = extractWordAfter(query, "category");
            Optional<Category> cat = categoryRepository.findByName(name);
            if (cat.isEmpty()) return "Category not found: " + name;
            Category c = cat.get();
            c.setName(name + " Updated");
            categoryRepository.save(c);
            return "✅ Category updated successfully: " + c.getName();

        } else if (query.contains("delete category")) {
            String name = extractWordAfter(query, "category");
            Optional<Category> cat = categoryRepository.findByName(name);
            if (cat.isPresent()) {
                categoryRepository.delete(cat.get());
                return "🗑️ Category deleted successfully: " + name;
            } else {
                return "Category not found: " + name;
            }

        } else if (query.contains("list categories") || query.contains("all categories")) {
            List<Category> categories = categoryRepository.findAll();
            if (categories.isEmpty()) return "No categories found.";
            StringBuilder sb = new StringBuilder("📚 **All Categories:**\n");
            for (Category c : categories) sb.append("- ").append(c.getName()).append("\n");
            return sb.toString();
        }

        // DATABASE OVERVIEW
        else if (query.contains("database structure")) {
            return "Database structure:\n1️⃣ AppUser (users)\n2️⃣ User (recipes)\n3️⃣ Category (categories)\nEach recipe links to a category via categoryId.";
        }

        // DEFAULT
        else {
            return "I'm not sure how to handle this query directly in DB context.";
        }
    }

    private String extractWordAfter(String text, String keyword) {
        String[] words = text.split("\\s+");
        for (int i = 0; i < words.length - 1; i++) {
            if (words[i].equalsIgnoreCase(keyword)) return words[i + 1];
        }
        return null;
    }

    private String callGemini(String prompt) {
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        String apiUrl = GEMINI_API_URL + "?key=" + geminiApiKey;
        String requestBody = "{ \"contents\": [{\"parts\":[{\"text\": \"" +
                prompt.replace("\"", "\\\"") + "\"}]}]}";
        HttpEntity<String> entity = new HttpEntity<>(requestBody, headers);

        try {
            ResponseEntity<String> response = restTemplate.postForEntity(apiUrl, entity, String.class);
            JSONObject jsonResponse = new JSONObject(response.getBody());
            JSONArray candidates = jsonResponse.getJSONArray("candidates");
            JSONObject content = candidates.getJSONObject(0).getJSONObject("content");
            JSONArray parts = content.getJSONArray("parts");
            return parts.getJSONObject(0).getString("text");
        } catch (Exception e) {
            return "Error calling Admin AI: " + e.getMessage();
        }
    }
}
