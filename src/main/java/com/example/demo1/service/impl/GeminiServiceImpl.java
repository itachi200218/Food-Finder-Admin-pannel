package com.example.demo1.service.impl;

import com.example.demo1.service.GeminiService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service

public class GeminiServiceImpl implements GeminiService {
    @Autowired
    private UserAnalyticsService userAnalyticsService;

    @Value("${gemini.api.key}")
    private String geminiApiKey;

    @Autowired private AppUserService appUserService;
    @Autowired private RecipeService recipeService;
    @Autowired private CategoryService categoryService;
    @Autowired private GeminiApiService geminiApiService;
    @Autowired private QueryParserService queryParserService;

    @Override
    public String generateExplanation(String query) {
        String dbResult = handleAdminQuery(query);
        if (dbResult.startsWith("I'm not sure")) {
            return geminiApiService.callGemini("Answer conversationally as Admin AI: " + query, geminiApiKey);
        }
        return geminiApiService.callGemini("Explain clearly as Admin AI: " + dbResult, geminiApiKey);
    }

    @Override
    public String handleAdminQuery(String query) {
        query = query.toLowerCase();

        if (query.contains("user list") || query.contains("all users") || query.contains("user count")) {
            return appUserService.handleUserQueries(query);
        } else if (query.contains("recipe")) {
            return recipeService.handleRecipeQueries(query);
        } else if (query.contains("category")) {
            return categoryService.handleCategoryQueries(query);
        } else if (query.contains("database structure")) {
            return "Database structure:\n1️⃣ AppUser (users)\n2️⃣ User (recipes)\n3️⃣ Category (categories)\nEach recipe links to a category via categoryId.";
        } else {
            return "I'm not sure how to handle this query directly in DB context.";
        }
    }
}
