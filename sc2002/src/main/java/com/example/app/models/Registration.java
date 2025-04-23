package com.example.app.models;

import com.example.app.enums.RegistrationStatus;

/**
 * Registration class that represents a registration made by an officer for a project.
 * It implements the BaseEntity interface.
 * Provides methods to get and set registration details.
 *
 * @see BaseEntity
 * @see RegistrationStatus
 */
public class Registration implements BaseEntity {

    /**
     * The ID of the registration.
     */
    private Integer id;

    /**
     * The ID of the officer who made the registration.
     */
    private Integer userId;

    /**
     * The ID of the project for which the registration is made.
     */
    private Integer projectId;

    /**
     * The status of the registration.
     */
    private RegistrationStatus status;

    /**
     * Default constructor for the Registration class.
     */
    public Registration() {
    }

    /**
     * Constructor for the Registration class with parameters.
     *
     * @param id        the ID of the registration. This field should be null for new registrations. After saving to the database, it will be set.
     * @param userId    the ID of the officer who made the registration
     * @param projectId the ID of the project for which the registration is made
     * @param status    the status of the registration
     */
    public Registration(Integer id, Integer userId, Integer projectId, RegistrationStatus status) {
        this.id = id;
        this.userId = userId;
        this.projectId = projectId;
        this.status = status;
    }


    /**
     * Gets the ID of the registration.
     *
     * @return the ID of the registration
     */
    @Override
    public Integer getId() {
        return id;
    }

    /**
     * Sets the ID of the registration.
     *
     * @param id the new ID of the registration
     */
    @Override
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * Gets the ID of the officer who made the registration.
     *
     * @return the ID of the officer
     */
    public Integer getUserId() {
        return userId;
    }

    /**
     * Sets the ID of the officer who made the registration.
     *
     * @param userId the new ID of the officer
     */
    public void setUserId(int userId) {
        this.userId = userId;
    }

    /**
     * Gets the ID of the project for which the registration is made.
     *
     * @return the ID of the project
     */
    public Integer getProjectId() {
        return projectId;
    }

    /**
     * Sets the ID of the project for which the registration is made.
     *
     * @param projectId the new ID of the project
     */
    public void setProjectId(int projectId) {
        this.projectId = projectId;
    }

    /**
     * Gets the status of the registration.
     *
     * @return the status of the registration
     */
    public RegistrationStatus getStatus() {
        return status;
    }

    /**
     * Sets the status of the registration.
     *
     * @param status the new status of the registration
     */
    public void setStatus(RegistrationStatus status) {
        this.status = status;
    }

    /**
     * Returns a string representation of the registration.
     *
     * @return a string representation of the registration
     */
    @Override
    public String toString() {
        return String.format("""
                [Registration Id: %s]
                Project (%s):
                Application Status: %s
                """, id, projectId, status);
    }
}
