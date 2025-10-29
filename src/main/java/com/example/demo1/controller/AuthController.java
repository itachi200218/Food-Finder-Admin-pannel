package com.example.demo1.controller;

import com.example.demo1.model.AppUser;
import com.example.demo1.repository.AppUserRepository;
import com.example.demo1.service.AuthService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin(origins = "http://localhost:8080", allowCredentials = "true")
public class AuthController {

    @Autowired
    private AuthService authService;

    @Autowired
    private AppUserRepository appUserRepository;

    // ✅ Register User
    @PostMapping("/register")
    public AppUser register(@RequestBody AppUser user) {
        return authService.register(user);
    }

    // ✅ Login
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

    // ✅ Forgot Username
    @PostMapping("/forgot-username")
    public ResponseEntity<?> forgotUsername(@RequestBody Map<String, String> request) {
        try {
            String email = request.get("email");
            AppUser user = authService.forgotUsername(email);
            return ResponseEntity.ok("Your username is: " + user.getUsername());
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    // ✅ Reset Password (with current password)
    @PostMapping("/reset-password")
    public ResponseEntity<?> resetPassword(@RequestBody Map<String, String> request) {
        try {
            String username = request.get("username");
            String currentPassword = request.get("currentPassword");
            String newPassword = request.get("newPassword");

            AppUser updatedUser = authService.resetPassword(username, currentPassword, newPassword);
            return ResponseEntity.ok("Password updated successfully for user: " + updatedUser.getUsername());
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    // ✅ Change Password (no old password — for profile page)
    @PutMapping("/updatePassword/{username}")
    public ResponseEntity<?> updatePassword(@PathVariable String username, @RequestBody Map<String, String> body) {
        try {
            String newPassword = body.get("password");
            AppUser updatedUser = authService.updatePasswordWithoutOld(username, newPassword);
            return ResponseEntity.ok("Password updated successfully for user: " + updatedUser.getUsername());
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    // ✅ Check login
    @GetMapping("/check")
    public boolean checkLogin(HttpSession session) {
        return session.getAttribute("user") != null;
    }

    // ✅ Logout
    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "Logged out";
    }

    // ✅ Profile
    @GetMapping("/profile/{username}")
    public ResponseEntity<?> getProfile(@PathVariable String username) {
        try {
            AppUser user = authService.getProfile(username);
            return ResponseEntity.ok(user);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    // ✅ NEW: Show All Users (for Admin only)
    @GetMapping("/users")
    public ResponseEntity<?> getAllUsers() {
        List<AppUser> users = appUserRepository.findAll();
        if (users.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body("No users found");
        }
        return ResponseEntity.ok(users);
    }
}
