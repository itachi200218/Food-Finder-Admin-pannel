package com.example.demo1.service.impl;

import com.example.demo1.repository.AppUserRepository;
import com.example.demo1.repository.FlaskUserRepository;
import com.example.demo1.repository.DashboardRepository;
import com.example.demo1.service.DashboardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class DashboardServiceImpl implements DashboardService {

    @Autowired
    private DashboardRepository dashboardRepository;

    @Autowired
    private AppUserRepository appUserRepository;

    @Autowired
    private FlaskUserRepository flaskUserRepository;

    @Override
    public Map<String, Object> getDashboardStats() {
        Map<String, Object> stats = new HashMap<>();

        // ✅ Count from both tables
        long adminUsers = appUserRepository.count();
        long flaskUsers = flaskUserRepository.count();
        long totalUsers = adminUsers + flaskUsers;

        long totalRecipes = dashboardRepository.countTotalRecipes();
        Integer topCategory = dashboardRepository.findTopCategory();

        stats.put("totalRecipes", totalRecipes);
        stats.put("adminUsers", adminUsers);
        stats.put("flaskUsers", flaskUsers);
        stats.put("totalUsers", totalUsers);
        stats.put("topCategory", topCategory != null ? topCategory : "N/A");

        return stats;
    }
}
