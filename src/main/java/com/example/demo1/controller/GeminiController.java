package com.example.demo1.controller;

import com.example.demo1.model.AppUser;
import com.example.demo1.repository.AppUserRepository;
import com.example.demo1.service.GeminiService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/admin/gemini")
public class GeminiController {

    @Autowired
    private GeminiService geminiService;

    @Autowired
    private AppUserRepository appUserRepository;

    // Accept both GET and POST for Gemini queries
    @RequestMapping(value = "/ask", method = {RequestMethod.GET, RequestMethod.POST})
    public String askGemini(@RequestParam String query) {
        return geminiService.generateExplanation(query);
    }

    // ✅ Fixed: Return all AppUser objects, not User
    @GetMapping("/users")
    public List<AppUser> getAllUsers() {
        return appUserRepository.findAll();
    }
}
