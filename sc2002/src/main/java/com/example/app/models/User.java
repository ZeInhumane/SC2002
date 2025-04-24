package com.example.app.models;

import com.example.app.enums.MaritalStatus;
import com.example.app.enums.Role;

/**
 * User class representing a user in the system. It implements the BaseEntity interface. This class contains user
 * details such as ID, name, password, email, role, However, this is the base class for all users. The system will
 * prefer direct subclasses like Applicant, Officer, and Manager Provides methods to get and set user details.
 *
 * @see BaseEntity
 * @see Applicant
 * @see Officer
 * @see Manager
 * @see Role
 * @see MaritalStatus
 */
public class User implements BaseEntity {

    /**
     * The ID of the user.
     */
    private Integer id;

    /**
     * The name of the user.
     */
    private String name;

    /**
     * The password of the user.
     */
    private String password;

    /**
     * The email of the user.
     */
    private String email;

    /**
     * The role of the user.
     */
    private Role role;

    /**
     * The NRIC of the user.
     */
    private String nric;

    /**
     * The age of the user.
     */
    private Integer age;

    /**
     * The marital status of the user.
     */
    private MaritalStatus maritalStatus;

    /**
     * Default constructor for User class.
     */
    public User() {
    }

    /**
     * Constructor for User class with parameters.
     * 
     * @param id
     *            the ID of the user. This field should be null for new users. After saving to the database, it will be
     *            set.
     * @param name
     *            the name of the user
     * @param password
     *            the password of the user
     * @param email
     *            the email of the user
     * @param role
     *            the role of the user
     * @param nric
     *            the NRIC of the user
     * @param age
     *            the age of the user
     * @param maritalStatus
     *            the marital status of the user
     */
    public User(Integer id, String name, String password, String email, Role role, String nric, Integer age,
            MaritalStatus maritalStatus) {
        this.id = id;
        this.password = password;
        this.name = name;
        this.email = email;
        this.role = role;
        this.nric = nric;
        this.age = age;
        this.maritalStatus = maritalStatus;
    }

    /**
     * Gets the ID of the user.
     * 
     * @return the ID of the user
     */
    @Override
    public Integer getId() {
        return id;
    }

    /**
     * Sets the ID of the user.
     * 
     * @param id
     *            the new ID of the user
     */
    @Override
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * Gets the NRIC of the user.
     * 
     * @return the NRIC of the user
     */
    public String getNric() {
        return nric;
    }

    /**
     * Sets the NRIC of the user.
     * 
     * @param nric
     *            the NRIC of the user
     */
    public void setNric(String nric) {
        this.nric = nric;
    }

    /**
     * Gets the name of the user.
     * 
     * @return the name of the user
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the name of the user.
     * 
     * @param name
     *            the name of the user
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Gets the password of the user.
     * 
     * @return the password of the user
     */
    public String getPassword() {
        return password;
    }

    /**
     * Sets the password of the user.
     * 
     * @param password
     *            the password of the user
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * Gets the email of the user.
     * 
     * @return the email of the user
     */
    public String getEmail() {
        return email;
    }

    /**
     * Sets the email of the user.
     * 
     * @param email
     *            the email of the user
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * Gets the role of the user.
     * 
     * @return the role of the user
     */
    public Role getRole() {
        return role;
    }

    /**
     * Sets the role of the user.
     * 
     * @param role
     *            the role of the user
     */
    public void setRole(Role role) {
        this.role = role;
    }

    /**
     * Gets the marital status of the user.
     * 
     * @return the marital status of the user
     */
    public MaritalStatus getMaritalStatus() {
        return maritalStatus;
    }

    /**
     * Sets the marital status of the user.
     * 
     * @param maritalStatus
     *            the marital status of the user
     */
    public void setMaritalStatus(MaritalStatus maritalStatus) {
        this.maritalStatus = maritalStatus;
    }

    /**
     * Gets the age of the user.
     * 
     * @return the age of the user
     */
    public Integer getAge() {
        return age;
    }

    /**
     * Sets the age of the user.
     * 
     * @param age
     *            the age of the user
     */
    public void setAge(int age) {
        this.age = age;
    }

    /**
     * Returns a string representation of the user.
     * @return a string representation of the user
     */
    @Override
    public String toString() {
        return String.format("""
                [User ID: %d]
                Name: %s
                NRIC: %s
                Age: %d
                Marital Status: %s
                Email: %s
                """,
                id,
                name,
                nric,
                age,
                maritalStatus,
                email
        );
    }
}
