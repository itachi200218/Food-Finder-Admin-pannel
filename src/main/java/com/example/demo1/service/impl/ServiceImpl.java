package com.example.demo1.service.impl;

import com.example.demo1.model.User;
import com.example.demo1.repository.UserRepository;
import com.example.demo1.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    @Override
    public User getUserByid(int id) {
        return userRepository.findById(id).orElse(null);
    }
    @Override
    public List<User> getUsersByCategoryId(int categoryId) {
        return userRepository.findByCategoryId(categoryId);
    }
    @Override
    public List<User> getUsersByCategoryName(String categoryName) {
        return userRepository.findByCategoryName(categoryName);
    }
    @Override
    public User CreateUser(User user) {
        user.setId(0);
        return userRepository.save(user);
    }

    @Override
    public User UpdateUser(int id, User user) {
        User u = userRepository.findById(id).orElse(null);
        u.setName(user.getName());
        u.setIngredients(user.getIngredients());
        u.setDescription(user.getDescription());
        u.setSteps(user.getSteps());
        u.setUrl(user.getUrl());
        u.setCategoryId(user.getCategoryId());
        return userRepository.save(u);
    }

    @Override
    public void DeleteUser(int id) {
        userRepository.deleteById(id);
        System.out.println("Recipe deleted successfully");
    }

}