package com.example.demo1.service.impl;

import com.example.demo1.model.User;
import com.example.demo1.model.Category;
import com.example.demo1.repository.UserRepository;
import com.example.demo1.repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.*;

@Service
public class RecipeService {

    @Autowired private UserRepository userRepository;
    @Autowired private CategoryRepository categoryRepository;
    @Autowired private QueryParserService queryParserService;
    @Autowired private GeminiApiService geminiApiService;

    public String handleRecipeQueries(String query) {
        try {
            // --- Add Recipe ---
            if (query.startsWith("add recipe") || query.contains("create recipe")) {
                return RecipeHelper.addRecipe(query, userRepository, categoryRepository, queryParserService, geminiApiService);
            }

            // --- Update Recipe ---
            else if (query.contains("update recipe")) {
                String name = queryParserService.extractWordAfter(query, "recipe");
                List<User> existing = userRepository.findByName(name);
                if (existing.isEmpty()) return "Recipe not found: " + name;
                User recipe = existing.get(0);
                recipe.setDescription("Updated by Admin AI");
                userRepository.save(recipe);
                return "✅ Recipe updated successfully: " + name;
            }

            // --- Delete Recipe ---
            else if (query.contains("delete recipe")) {
                String name = queryParserService.extractWordAfter(query, "recipe");
                if (name == null) return "Please specify the recipe name to delete.";
                List<User> recipes = userRepository.findByName(name);
                if (!recipes.isEmpty()) {
                    userRepository.deleteAll(recipes);
                    return "🗑️ Recipe deleted successfully: " + name;
                } else {
                    return "Recipe not found: " + name;
                }
            }

            // --- List Recipes ---
            else if (query.contains("list recipes") || query.contains("all recipes")) {
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
            }

            // --- Find Recipe ---
            else if (query.contains("find recipe")) {
                String name = queryParserService.extractWordAfter(query, "recipe");
                List<User> recipes = userRepository.findByName(name);
                if (recipes.isEmpty()) return "Recipe not found: " + name;
                User u = recipes.get(0);
                return "🍲 Recipe Found: " + u.getName() +
                        " | Ingredients: " + u.getIngredients() +
                        " | Description: " + u.getDescription();
            }

        } catch (Exception e) {
            return "❌ Error processing recipe query: " + e.getMessage();
        }

        return "I'm not sure how to handle this recipe query.";
    }
}
