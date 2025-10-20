package com.example.demo1.service;

public interface GeminiService {
    // Takes a natural-language query and returns Gemini's processed explanation
    String generateExplanation(String query);

    // Optional — handles structured analytics queries for the admin panel
    String handleAdminQuery(String query);
}
