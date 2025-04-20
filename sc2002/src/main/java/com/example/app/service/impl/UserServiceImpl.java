package com.example.app.service.impl;

import com.example.app.models.User;
import com.example.app.repository.RepositoryDependency;
import com.example.app.repository.UserRepository;
import com.example.app.service.UserService;

import java.io.IOException;

public class UserServiceImpl implements UserService {
    private final UserRepository userRepository = RepositoryDependency.getUserRepository();

    public User login(String nirc, String password) throws IOException {
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

    public User changePassword(User user, String newPassword) throws IOException {
        user.setPassword(newPassword);
        userRepository.save(user);
        return user;
    }

    public User findById(int id) throws IOException {
        return userRepository.findById(id);
    }

    public User findByNric(String nric) throws IOException {
        return userRepository.findByNric(nric);
    }

    public User save(User user) throws IOException {
        return userRepository.save(user);
    }
}
