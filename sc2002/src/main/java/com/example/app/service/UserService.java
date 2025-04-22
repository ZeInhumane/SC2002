package com.example.app.service;

import java.io.IOException;

import com.example.app.models.User;

public interface UserService {
    User login(String nirc, String password) throws IOException;

    User changePassword(User user, String newPassword) throws IOException;

    User findById(int id) throws IOException;

    User findByNric(String nric) throws IOException;

    User save(User user) throws IOException;
}
