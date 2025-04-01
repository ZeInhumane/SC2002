package com.example.app.repository;

import com.example.app.models.User;

import java.util.HashMap;

public class UserRepository {
    private final HashMap<String, User> userList = new HashMap<>();

    public User findByNirc(String nirc) {
        return userList.get(nirc);
    }

    public void save(User user) {
        userList.put(user.getNirc(), user);
    }

}
