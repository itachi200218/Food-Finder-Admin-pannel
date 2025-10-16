package com.example.demo1.controller;

import com.example.demo1.model.AppUser;
import com.example.demo1.service.AuthService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin(origins = "http://localhost:8080", allowCredentials = "true")public class AuthController {

    @Autowired
    private AuthService authService;

    @PostMapping("/register")
    public AppUser register(@RequestBody AppUser user) {
        return authService.register(user);
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody AppUser user, HttpSession session) {
        AppUser loggedInUser = authService.login(user.getUsername(), user.getPassword());

        if (loggedInUser != null) {
            session.setAttribute("user", loggedInUser);
            return ResponseEntity.ok(loggedInUser);
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid credentials");
        }
    }

    @GetMapping("/check")
    public boolean checkLogin(HttpSession session) {
        return session.getAttribute("user") != null; // ✅ true if logged in
    }

    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate(); // ✅ clear session
        return "Logged out";
    }
}
