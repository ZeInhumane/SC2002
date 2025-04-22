package com.example.app.repository;
import java.io.IOException;
import java.util.Objects;

import com.example.app.models.User;
import com.example.app.serializer.SerializerDependency;


public class UserRepository extends GeneralRepository<User> {
    public UserRepository() {
        super(SerializerDependency.getUserSerializer(), "users.txt");
    }

    public User findByNric (String nric) throws IOException {
        return this.findAll().stream()
            .filter(user -> Objects.equals(user.getNric(), nric))
            .findFirst()
            .orElse(null);
    }
    //  Add this to return all users
    // public List<User> findAll() {
    //     System.out.println(users);
    //     return new ArrayList<>(users.values());
    // }
}
