package com.example.demo1.repository;

import com.example.demo1.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface UserRepository extends JpaRepository<User,Integer> {
    List<User> findByCategoryId(int categoryId);
    @Query("SELECT u FROM User u JOIN Category c ON u.categoryId = c.id WHERE c.name = :categoryName")
    List<User> findByCategoryName(@Param("categoryName") String categoryName);
}
