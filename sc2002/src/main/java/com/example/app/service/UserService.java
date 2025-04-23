package com.example.app.service;

import java.io.IOException;

import com.example.app.models.User;

/**
 * Service interface for managing user operations.
 */
public interface UserService {

    /**
     * Login a user with the given nirc and password.
     * @param nirc the nirc of the user
     * @param password the password of the user
     * @return the logged in user
     * @throws IOException if an I/O error occurs
     */
    User login(String nirc, String password) throws IOException;

    /**
     * Register a new user with the given details.
     * @param user the user to register
     * @return the registered user
     * @throws IOException if an I/O error occurs
     */
    User changePassword(User user, String newPassword) throws IOException;

    /**
     * Find a user by their ID.
     * @param id the ID of the user
     * @return the found user
     * @throws IOException if an I/O error occurs
     */
    User findById(int id) throws IOException;

    /**
     * Find a user by their NRIC.
     * @param nric the NRIC of the user
     * @return the found user
     * @throws IOException if an I/O error occurs
     */
    User findByNric(String nric) throws IOException;

    /**
     * Save a user to the database.
     * @param user the user to save
     * @return the saved user
     * @throws IOException if an I/O error occurs
     */
    User save(User user) throws IOException;
}
