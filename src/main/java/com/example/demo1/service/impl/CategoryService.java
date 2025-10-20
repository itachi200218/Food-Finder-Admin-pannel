package com.example.demo1.service.impl;

import com.example.demo1.model.Category;
import com.example.demo1.repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.*;

@Service
public class CategoryService {

    @Autowired private CategoryRepository categoryRepository;
    @Autowired private QueryParserService queryParserService;

    public String handleCategoryQueries(String query) {
        if (query.contains("add category")) {
            String name = queryParserService.extractWordAfter(query, "category");
            if (name == null || name.isBlank()) return "Please specify a category name.";
            Category c = new Category();
            c.setName(name);
            categoryRepository.save(c);
            return "✅ New category added: " + name;

        } else if (query.contains("update category")) {
            String name = queryParserService.extractWordAfter(query, "category");
            Optional<Category> cat = categoryRepository.findByName(name);
            if (cat.isEmpty()) return "Category not found: " + name;
            Category c = cat.get();
            c.setName(name + " Updated");
            categoryRepository.save(c);
            return "✅ Category updated successfully: " + c.getName();

        } else if (query.contains("delete category")) {
            String name = queryParserService.extractWordAfter(query, "category");
            Optional<Category> cat = categoryRepository.findByName(name);
            if (cat.isPresent()) {
                categoryRepository.delete(cat.get());
                return "🗑️ Category deleted successfully: " + name;
            } else {
                return "Category not found: " + name;
            }

        } else if (query.contains("list categories") || query.contains("all categories")) {
            List<Category> categories = categoryRepository.findAll();
            if (categories.isEmpty()) return "No categories found.";
            StringBuilder sb = new StringBuilder("📚 **All Categories:**\n");
            for (Category c : categories) sb.append("- ").append(c.getName()).append("\n");
            return sb.toString();
        }

        return "I'm not sure how to handle this category query.";
    }
}
