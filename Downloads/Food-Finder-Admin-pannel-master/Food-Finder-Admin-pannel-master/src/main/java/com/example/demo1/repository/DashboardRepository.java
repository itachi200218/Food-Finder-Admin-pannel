package com.example.demo1.repository;

import com.example.demo1.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface DashboardRepository extends JpaRepository<User, Integer> {

    // Count total recipes
    @Query("SELECT COUNT(u) FROM User u")
    long countTotalRecipes();

    // Find most used category
    @Query("SELECT u.categoryId FROM User u GROUP BY u.categoryId ORDER BY COUNT(u.categoryId) DESC LIMIT 1")
    Integer findTopCategory();
}
