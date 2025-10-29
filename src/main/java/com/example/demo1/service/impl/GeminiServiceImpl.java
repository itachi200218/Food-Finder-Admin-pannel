package com.example.demo1.service.impl;

import com.example.demo1.service.GeminiService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class GeminiServiceImpl implements GeminiService {

    @Value("${gemini.api.key}")
    private String geminiApiKey;

    @Autowired private UserAnalyticsService userAnalyticsService;
    @Autowired private AppUserService appUserService;
    @Autowired private RecipeService recipeService;
    @Autowired private CategoryService categoryService;
    @Autowired private GeminiApiService geminiApiService;
    @Autowired private QueryParserService queryParserService;

    @Override
    public String generateExplanation(String query) {
        String dbResult = handleAdminQuery(query);

        // Only use Gemini if it’s not a DB answer
        if (dbResult.startsWith("I'm not sure")) {
            return geminiApiService.callGemini("Answer conversationally as Admin AI: " + query, geminiApiKey);
        }

        // ✅ Directly return DB result if it came from database
        if (dbResult.startsWith("👥") || dbResult.contains("All Users") || dbResult.contains("Total users")) {
            return dbResult;
        }

        // Else, explain it normally
        return geminiApiService.callGemini("Explain clearly as Admin AI: " + dbResult, geminiApiKey);
    }


    @Override
    public String handleAdminQuery(String query) {
        query = query.toLowerCase();

        // 🔍 Step 1: Detect the intent
        String queryType = queryParserService.parseQueryType(query);

        // 🔹 Step 2: Route based on detected query
        switch (queryType) {

            // 🧑‍💻 USER ANALYTICS
            case "USER_LIST":
            case "USER_COUNT":
            case "MOST_ACTIVE_USER":
            case "TOP_USERS":
                return appUserService.handleUserQueries(query);

            // 🍳 RECIPE ANALYTICS
            case "DUPLICATE_RECIPES":
            case "RECIPE_COUNT_BY_CATEGORY":
            case "MISSING_DESCRIPTION":
            case "AI_SUMMARIZE_CATEGORIES":
            case "TOP_CATEGORIES":
            case "LARGE_RECIPES":
            case "LEAST_CATEGORY":
            case "AI_TRENDING_DISHES":
            case "AI_SUGGEST_CATEGORIES":
            case "SEARCH_BY_INGREDIENT":
            case "ADD_RECIPE":
            case "DELETE_RECIPE":
            case "UPDATE_RECIPE":
            case "RECIPE_SEARCH":
            case "RECENT_RECIPES":
                return recipeService.handleRecipeQueries(query);

            // 🗂️ CATEGORY MANAGEMENT
            case "CATEGORY_LIST":
            case "ADD_CATEGORY":
            case "DELETE_CATEGORY":
            case "UPDATE_CATEGORY":
            case "HELP_ADD_CATEGORY":
            case "HELP_DUPLICATE_RECIPES":
            case "HELP_SEO":
                return categoryService.handleCategoryQueries(query);

            // 🧠 DATABASE INSIGHTS
            case "DATABASE_STRUCTURE":
            case "SHOW_RELATIONSHIPS":
            case "LIST_TABLES":
                return getDatabaseStructure();

            // 📈 ADVANCED USER ANALYTICS
            case "USER_STATS":
            case "USER_ENGAGEMENT":
                return userAnalyticsService.generateUserReport();

            // 💬 DEFAULT CASE: Let Gemini handle it
            default:
                return "I'm not sure how to handle this query directly in DB context.";
        }
    }

    // 🧾 Step 3: Database Structure Details
    private String getDatabaseStructure() {
        return """
        📘 DATABASE STRUCTURE OVERVIEW

        🧑‍💻 Table: AppUser
        • id (Long)
        • username (String)
        • email (String)
        • password (String)
        • createdAt (Timestamp)

        🍽️ Table: Recipe
        • id (Long)
        • name (String)
        • ingredients (Text)
        • description (Text)
        • steps (Text)
        • url (String)
        • categoryId (Foreign Key → Category.id)
        • createdAt (Timestamp)

        🗂️ Table: Category
        • id (Long)
        • name (String)
        • description (String)
        • createdAt (Timestamp)

        ⚙️ Relationships:
        • One Category → Many Recipes
        • One User → Can Add Many Recipes
        """;
    }
}
