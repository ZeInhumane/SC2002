package com.example.app.repository;
import com.example.app.models.User;


public class UserRepository extends GeneralRepository<User> {
    public User findByNric (String nric){
        return storage.values().stream()
            .filter(user -> user.getNric().equals(nric))
            .findFirst()
            .orElse(null);
    }
}
