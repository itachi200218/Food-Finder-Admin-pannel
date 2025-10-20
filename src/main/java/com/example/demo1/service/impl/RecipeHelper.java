package com.example.demo1.service.impl;

import com.example.demo1.model.User;
import com.example.demo1.repository.UserRepository;
import com.example.demo1.repository.CategoryRepository;
import com.example.demo1.service.impl.GeminiApiService;
import com.example.demo1.service.impl.QueryParserService;

public class RecipeHelper {

    // Main static method called from controller
    public static String addRecipe(String query,
                                   UserRepository userRepository,
                                   CategoryRepository categoryRepository,
                                   QueryParserService queryParserService,
                                   GeminiApiService geminiApiService) {
        try {
            // Example query: "create recipe wada at category 5"
            String lowerQuery = query.toLowerCase();

            String recipeName = extractRecipeName(lowerQuery);
            int categoryId = extractCategoryId(lowerQuery);

            if (recipeName == null || categoryId == -1) {
                return "❌ Invalid format. Try: create recipe wada at category 5";
            }

            // Get AI generated details using Gemini
            String ingredients = geminiApiService.getIngredients(recipeName);
            String description = geminiApiService.getDescription(recipeName);
            String steps = geminiApiService.getSteps(recipeName);

            // Create and save recipe
            User recipe = new User();
            recipe.setName(recipeName);
            recipe.setIngredients(ingredients);
            recipe.setDescription(description);
            recipe.setSteps(steps);
            recipe.setCategoryId(categoryId);
            recipe.setUrl(null);

            userRepository.save(recipe);
            return "✅ Recipe '" + recipeName + "' created successfully in category " + categoryId + ".";
        } catch (Exception e) {
            e.printStackTrace();
            return "⚠️ Error creating recipe: " + e.getMessage();
        }
    }

    private static String extractRecipeName(String query) {
        try {
            if (query.contains("create recipe")) {
                query = query.substring(query.indexOf("create recipe") + 13).trim();
            } else if (query.contains("add recipe")) {
                query = query.substring(query.indexOf("add recipe") + 10).trim();
            }

            if (query.contains("at category")) {
                return query.substring(0, query.indexOf("at category")).trim();
            }
            return null;
        } catch (Exception e) {
            return null;
        }
    }

    private static int extractCategoryId(String query) {
        try {
            if (query.contains("category")) {
                String[] parts = query.split("category");
                return Integer.parseInt(parts[1].trim());
            }
            return -1;
        } catch (Exception e) {
            return -1;
        }
    }
}
