package com.example.demo1.model;

import jakarta.persistence.*;

@Entity
@Table(name ="categories")
public class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String name;

    // Getters & Setters
    public int getId() {
        return id; }
    public void setId(int id) {
        this.id = id; }

    public String getName() {
        return name; }
    public void setName(String name) {
        this.name = name; }
}
