package com.example.app.repository;
import java.util.*;

import com.example.app.models.User;


public class UserRepository extends GeneralRepository<User> {
    private final Map<Integer, User> users = new HashMap<>();

    public User findByNric (String nric){
        return storage.values().stream()
            .filter(user -> user.getNric().equals(nric))
            .findFirst()
            .orElse(null);
    }
    //  Add this to return all users
    // public List<User> findAll() {
    //     System.out.println(users);
    //     return new ArrayList<>(users.values());
    // }
}
