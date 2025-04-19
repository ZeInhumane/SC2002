package com.example.app.service;

import java.util.Collection;

import com.example.app.enums.FlatType;
import com.example.app.models.ProjectAssignable;
import com.example.app.models.User;
import com.example.app.repository.UserRepository;

public class UserService {
    private static UserRepository userRepository = new UserRepository();

    public User login(String nirc, String password) throws Exception {
        User user = userRepository.findByNric(nirc);
        if (user == null) {
            System.out.println("User not found");
            return user;
        }
        if (user.getPassword().equals(password)) {
            System.out.println("Login successful");
            return user;
        } else {
            System.out.println("Invalid password");
            return null;
        }
    }

    public User changePassword(User user, String newPassword) throws Exception {
        user.setPassword(newPassword);
        userRepository.save(user);
        return user;
    }
}
