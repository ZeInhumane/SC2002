package com.example.app.repository;

import java.io.IOException;
import java.util.Objects;

import com.example.app.models.User;
import com.example.app.serializer.SerializerDependency;

/**
 * Repository class for User. This class extends the GeneralRepository class to provide CRUD operations for User
 * objects.
 *
 * @see GeneralRepository
 * @see User
 */
public class UserRepository extends GeneralRepository<User> {

    /**
     * Constructor for UserRepository. Initializes the repository with the User serializer and the file name.
     *
     * @see SerializerDependency
     * @see GeneralRepository
     */
    public UserRepository() {
        super(SerializerDependency.getUserSerializer(), "users.txt");
    }

    /**
     * Finds a user by their NRIC.
     * 
     * @param nric
     *            the NRIC of the user
     * @return the User object if found, null otherwise
     * @throws IOException
     *             if there is an error reading the file
     */
    public User findByNric(String nric) throws IOException {
        return this.findAll().stream().filter(user -> Objects.equals(user.getNric(), nric)).findFirst().orElse(null);
    }
}
