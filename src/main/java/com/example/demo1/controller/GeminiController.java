package com.example.demo1.controller;

import com.example.demo1.service.GeminiService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/admin/gemini")
public class GeminiController {

    @Autowired
    private GeminiService geminiService;

    // Accept both GET and POST
    @RequestMapping(value = "/ask", method = {RequestMethod.GET, RequestMethod.POST})
    public String askGemini(@RequestParam String query) {
        return geminiService.generateExplanation(query);
    }
}
