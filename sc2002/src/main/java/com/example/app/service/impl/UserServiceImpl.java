package com.example.app.service.impl;

import com.example.app.RuntimeData;
import com.example.app.models.User;
import com.example.app.repository.RepositoryDependency;
import com.example.app.repository.UserRepository;
import com.example.app.service.UserService;

import java.io.IOException;

public class UserServiceImpl implements UserService {
    private final UserRepository userRepository = RepositoryDependency.getUserRepository();

    // singleton pattern
    private static UserServiceImpl instance;
    protected UserServiceImpl() {}
    public static UserServiceImpl getInstance() {
        return instance == null ? instance = new UserServiceImpl() : instance;
    }

    public User login(String nric, String password) throws IOException {
        User user = userRepository.findByNric(nric);
        if (user == null) return null;
        if (user.getPassword().equals(password)) {
            RuntimeData.setCurrentUser(user);
            return user;
        } else return null;
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
