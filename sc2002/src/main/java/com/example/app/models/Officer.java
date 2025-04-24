package com.example.app.models;

import com.example.app.enums.FlatType;
import com.example.app.enums.MaritalStatus;
import com.example.app.enums.Role;

/**
 * Officer class that represents an officer in the system. It extends the Applicant class Officer can do what an
 * applicant can do, but also has additional properties Officer can register to be an officer for a project Officer can
 * book a flat upon successful application of an applicant Provides methods to get and set officer details.
 *
 * @see Applicant
 */
public class Officer extends Applicant {

    /**
     * The ID of the officer's registration. This field is null if the officer has not submitted any registration.
     */
    private Integer registrationId;

    /**
     * The ID of the project for which the officer is registered. This field is null if the officer has not registered
     * for any project.
     */
    private Integer projectId;

    /**
     * Default constructor for the Officer class.
     */
    public Officer() {
    }

    /**
     * Constructor for the Officer class with parameters.
     *
     * @param id
     *            the ID of the officer. This field should be null for new officers. After saving to the database, it
     *            will be set.
     * @param username
     *            the username of the officer
     * @param password
     *            the password of the officer
     * @param email
     *            the email of the officer
     * @param role
     *            the role of the officer
     * @param nric
     *            the NRIC of the officer
     * @param age
     *            the age of the officer
     * @param maritalStatus
     *            the marital status of the officer
     * @param flatType
     *            the flat type of the officer
     * @param applicationId
     *            the application ID of the officer
     * @param registeredId
     *            the registration ID of the officer
     * @param projectId
     *            the project ID for which the officer is registered
     */
    public Officer(Integer id, String username, String password, String email, Role role, String nric, int age,
            MaritalStatus maritalStatus, FlatType flatType, Integer applicationId, Integer registeredId,
            Integer projectId) {
        super(id, username, password, email, role, nric, age, maritalStatus, flatType, applicationId);
        this.registrationId = registeredId;
        this.projectId = projectId;
    }

    /**
     * Gets the ID of the officer's registration.
     *
     * @return the ID of the officer's registration
     */
    public Integer getRegistrationId() {
        return registrationId;
    }

    /**
     * Sets the ID of the officer's registration.
     *
     * @param registrationId
     *            the ID of the officer's registration
     */
    public void setRegistrationId(Integer registrationId) {
        this.registrationId = registrationId;
    }

    /**
     * Gets the ID of the project for which the officer is registered.
     *
     * @return the ID of the project for which the officer is registered
     */
    public Integer getProjectId() {
        return projectId;
    }

    /**
     * Sets the ID of the project for which the officer is registered.
     *
     * @param projectId
     *            the ID of the project for which the officer is registered
     */
    public void setProjectId(Integer projectId) {
        this.projectId = projectId;
    }

}
