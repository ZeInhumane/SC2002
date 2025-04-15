package com.example.app.controller;

import com.example.app.models.User;
import com.example.app.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/users")
public class UserController {
    
    @Autowired
    private UserService userService;
    
    @GetMapping
    public List<User> getAllUsers() {
       return userService.findAllUsers();
    }
    
    @GetMapping("/{id}")
    public User getUser(@PathVariable Long id) {
       Optional<User> userOpt = userService.findById(id);
       return userOpt.orElse(null);
    }
    
    @PostMapping
    public User createUser(@RequestBody User user) {
       return userService.createUser(user);
    }
    
    @DeleteMapping("/{id}")
    public void deleteUser(@PathVariable Long id) {
       userService.deleteUser(id);
    }
}
