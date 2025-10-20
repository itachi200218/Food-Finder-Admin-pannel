package com.example.demo1.service.impl;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.json.*;

@Service
public class GeminiApiService {

    private static final String GEMINI_API_URL =
            "https://generativelanguage.googleapis.com/v1beta/models/gemini-2.0-flash:generateContent";

    @Value("${gemini.api.key}")
    private String geminiApiKey;

    // 🔹 Common Gemini call
    public String callGemini(String prompt, String geminiApiKey) {
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
            return parts.getJSONObject(0).getString("text").trim();
        } catch (Exception e) {
            return "Error calling Admin AI: " + e.getMessage();
        }
    }

    // 🍽️ Generate full recipe (Description + Ingredients + Steps)
    public String generateFullRecipe(String recipeName) {
        String prompt =
                "Generate a recipe for " + recipeName +
                        " in this exact format:\n\n" +
                        "Description:\n(2-line engaging summary)\n\n" +
                        "Ingredients:\n(List each ingredient on a new line without numbering)\n\n" +
                        "Steps:\n(Provide clear, numbered step-by-step instructions)\n\n" +
                        "Make sure the output strictly follows this format — no extra text, no markdown, no JSON.";
        return callGemini(prompt, geminiApiKey);
    }

    // 🧩 Get only Ingredients
    public String getIngredients(String recipeName) {
        String prompt = "List only the ingredients required for " + recipeName +
                " in this exact format:\n\nIngredients:\n(item1)\n(item2)\n(item3)...\n\n" +
                "No explanations or markdown.";
        return callGemini(prompt, geminiApiKey);
    }

    // 🧠 Get only Description
    public String getDescription(String recipeName) {
        String prompt = "Write a 2-line engaging description for the recipe " + recipeName +
                " in this exact format:\n\nDescription:\n(your answer)";
        return callGemini(prompt, geminiApiKey);
    }

    // 🍳 Get only Steps
    public String getSteps(String recipeName) {
        String prompt = "Write step-by-step preparation steps for " + recipeName +
                " in this exact format:\n\nSteps:\n1. Step one\n2. Step two\n3. Step three...\n" +
                "Do not include any other text.";
        return callGemini(prompt, geminiApiKey);
    }
}
