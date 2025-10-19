package com.example.demo1.service.impl;

import com.example.demo1.repository.AppUserRespository;
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
    private AppUserRespository userRepository;

    @Override
    public Map<String, Object> getDashboardStats() {
        Map<String, Object> stats = new HashMap<>();

        long totalRecipes = dashboardRepository.countTotalRecipes();
        long totalUsers = userRepository.count();
        Integer topCategory = dashboardRepository.findTopCategory();

        stats.put("totalRecipes", totalRecipes);
        stats.put("totalUsers", totalUsers);
        stats.put("topCategory", topCategory != null ? topCategory : "N/A");

        return stats;
    }
}
