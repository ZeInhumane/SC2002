package com.example.app.service;

import java.io.IOException;
import java.util.Collection;

import com.example.app.enums.FlatType;
import com.example.app.models.User;
import com.example.app.repository.RepositoryDependency;
import com.example.app.repository.UserRepository;

public interface UserService {

    User login(String nirc, String password) throws IOException;

    User changePassword(User user, String newPassword) throws IOException;

    User findById(int id) throws IOException;

    User findByNric(String nric) throws IOException;

    User save(User user) throws IOException;
}
