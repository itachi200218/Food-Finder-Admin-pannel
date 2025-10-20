package com.example.demo1.service;
import com.example.demo1.model.Category;
import com.example.demo1.model.User;
import java.util.ArrayList;
import java.util.List;
public interface UserService {
    List<User> getAllUsers();
    List<User> getUsersByCategoryId(int categoryId);
    List<User> getUsersByCategoryName(String categoryName);
    User getUserByid(int id);
   User CreateUser(User user);
   User UpdateUser(int id,User user);
   void DeleteUser(int id);
}
