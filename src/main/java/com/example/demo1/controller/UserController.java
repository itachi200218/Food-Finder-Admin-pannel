package com.example.demo1.controller;

import com.example.demo1.model.User;
import com.example.demo1.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
@CrossOrigin(origins = "http://localhost:8080", allowCredentials = "true")
public class UserController {
    @Autowired
    private UserService userService;

   @GetMapping
    public  List<User> getAllUsers(){
       return userService.getAllUsers();
   }
   @GetMapping("/{id}")
   public User getUserByid(@PathVariable int id){
       return userService.getUserByid(id);
   }
   @GetMapping("/category/id/{categoryId}")
   public List<User> getUsersByCategoryId(@PathVariable int categoryId){
       return userService.getUsersByCategoryId(categoryId);
   }
    @GetMapping("/category/name/{categoryName}")
    public List<User> getUsersByCategoryName(@PathVariable String categoryName) {
        return userService.getUsersByCategoryName(categoryName);
    }

   @PostMapping
    public User createUser(@RequestBody User user){
       return userService.CreateUser(user);
   }
   @PutMapping("/{id}")
    public User updateuser(@PathVariable int id , @RequestBody User user){
       return userService.UpdateUser(id , user);
   }
   @DeleteMapping("/{id}")
    public void deleteuser(@PathVariable int id){
        userService.DeleteUser(id);
   }
//    @Controller
//    public class HomeController {
//        @GetMapping("/")
//        public String home() {
//            return "forward:/index.html";
//        }
//    }

}
