package com.example.demo1.model;

import jakarta.persistence.*;

@Entity
@Table(name = "app_user")
public class AppUser {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    // Using TEXT for everything else
    @Lob
    @Column(columnDefinition = "TEXT", nullable = false, unique = true)
    private String username;

    @Lob
    @Column(columnDefinition = "TEXT", nullable = false)
    private String password;

    @Lob
    @Column(columnDefinition = "TEXT", nullable = false, unique = true)
    private String email;

    @Lob
    @Column(columnDefinition = "TEXT")
    private String updatedAt;

    public AppUser() {}

    public AppUser(String username, String password, String email) {
        this.username = username;
        this.password = password;
        this.email = email;
        this.updatedAt = String.valueOf(System.currentTimeMillis());
    }

    // Getters & Setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }

    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getUpdatedAt() { return updatedAt; }
    public void setUpdatedAt(String updatedAt) { this.updatedAt = updatedAt; }
}
