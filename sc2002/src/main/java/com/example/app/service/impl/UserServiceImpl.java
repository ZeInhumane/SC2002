package com.example.app.service.impl;

import com.example.app.RuntimeData;
import com.example.app.models.User;
import com.example.app.repository.RepositoryDependency;
import com.example.app.repository.UserRepository;
import com.example.app.service.UserService;

import java.io.IOException;

/**
 * Implementation of the UserService interface. Provides user management functionalities such as login, password change,
 * and retrieval and persistence of user data.
 *
 * @see UserService
 */
public class UserServiceImpl implements UserService {

    /**
     * Repository for accessing and managing user data.
     */
    private final UserRepository userRepository = RepositoryDependency.getUserRepository();

    /**
     * Logs in a user with the given NRIC and password. If login is successful, sets the current user in RuntimeData.
     *
     * @param nric
     *            the user's NRIC
     * @param password
     *            the user's password
     * @return the authenticated User object, or null if login fails
     * @throws IOException
     *             if an I/O error occurs
     */
    @Override
    public User login(String nric, String password) throws IOException {
        User user = userRepository.findByNric(nric);
        if (user == null)
            return null;
        if (user.getPassword().equals(password)) {
            RuntimeData.setCurrentUser(user);
            return user;
        } else
            return null;
    }

    /**
     * Changes the password of the given user.
     *
     * @param user
     *            the user whose password is to be changed
     * @param newPassword
     *            the new password
     * @return the updated User object
     * @throws IOException
     *             if an I/O error occurs
     */
    @Override
    public User changePassword(User user, String newPassword) throws IOException {
        user.setPassword(newPassword);
        userRepository.save(user);
        return user;
    }

    /**
     * Finds a user by their ID.
     *
     * @param id
     *            the user ID
     * @return the User object, or null if not found
     * @throws IOException
     *             if an I/O error occurs
     */
    @Override
    public User findById(int id) throws IOException {
        return userRepository.findById(id);
    }

    /**
     * Finds a user by their NRIC.
     *
     * @param nric
     *            the user's NRIC
     * @return the User object, or null if not found
     * @throws IOException
     *             if an I/O error occurs
     */
    @Override
    public User findByNric(String nric) throws IOException {
        return userRepository.findByNric(nric);
    }

    /**
     * Saves or updates a user in the repository.
     *
     * @param user
     *            the user to save
     * @return the saved User object
     * @throws IOException
     *             if an I/O error occurs
     */
    @Override
    public User save(User user) throws IOException {
        return userRepository.save(user);
    }
}