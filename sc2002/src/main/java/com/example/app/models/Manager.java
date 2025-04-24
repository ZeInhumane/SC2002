package com.example.app.models;

import com.example.app.enums.MaritalStatus;
import com.example.app.enums.Role;

/**
 * Manager class that represents a user with managerial privileges.
 * It extends the User class.
 * This class contains details specific to a manager.
 * Provides methods to get and set manager details.
 *
 * @see User
 */
public class Manager extends User {

    /**
     * Default constructor for the Manager class.
     * This constructor initializes a new instance of the Manager class.
     */
    public Manager() {
    }

    /**
     * Constructor for the Manager class with parameters.
     *
     * @param id             the ID of the manager. This field should be null for new managers. After saving to the database, it will be set.
     * @param username       the username of the manager
     * @param password       the password of the manager
     * @param email          the email of the manager
     * @param role           the role of the manager
     * @param nric           the NRIC of the manager
     * @param age            the age of the manager
     * @param maritalStatus  the marital status of the manager
     */
    public Manager(Integer id, String username, String password, String email, Role role, String nric, int age,
            MaritalStatus maritalStatus) {
        super(id, username, password, email, role, nric, age, maritalStatus);
    }

}
