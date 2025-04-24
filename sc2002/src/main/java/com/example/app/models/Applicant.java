package com.example.app.models;

import com.example.app.enums.FlatType;
import com.example.app.enums.MaritalStatus;
import com.example.app.enums.Role;

/**
 * Applicant class that represents a user who is applying for a project. It extends the User class. This class contains
 * details specific to an applicant such as flat type and application ID. Provides methods to get and set applicant
 * details.
 *
 * @see User
 */
public class Applicant extends User {

    /**
     * The type of flat the applicant is applying for. This field is null if the applicant has not applied for any flat.
     */
    private FlatType flatType;

    /**
     * The ID of the application made by the applicant. This field is null if the applicant has not made any
     * application.
     */
    private Integer applicationId;

    /**
     * Default constructor for the Applicant class.
     */
    public Applicant() {
        super();
    }

    /**
     * Constructor for the Applicant class with parameters.
     *
     * @param id
     *            the ID of the applicant. This field should be null for new applicants. After saving to the database,
     *            it will be set.
     * @param name
     *            the name of the applicant
     * @param password
     *            the password of the applicant
     * @param email
     *            the email of the applicant
     * @param role
     *            the role of the applicant
     * @param nric
     *            the NRIC of the applicant
     * @param age
     *            the age of the applicant
     * @param maritalStatus
     *            the marital status of the applicant
     * @param flatType
     *            the flat type of the applicant
     * @param applicationId
     *            the application ID of the applicant
     */
    public Applicant(Integer id, String name, String password, String email, Role role, String nric, int age,
            MaritalStatus maritalStatus, FlatType flatType, Integer applicationId) {
        super(id, name, password, email, role, nric, age, maritalStatus);
        this.flatType = flatType;
        this.applicationId = applicationId;
    }

    /**
     * Gets the flat type of the applicant.
     * 
     * @return the flat type of the applicant
     */
    public FlatType getFlatType() {
        return flatType;
    }

    /**
     * Sets the flat type of the applicant.
     * 
     * @param flatType
     *            the flat type to set
     */
    public void setFlatType(FlatType flatType) {
        this.flatType = flatType;
    }

    /**
     * Gets the application ID of the applicant.
     * 
     * @return the application ID of the applicant
     */
    public Integer getApplicationId() {
        return applicationId;
    }

    /**
     * Sets the application ID of the applicant.
     * 
     * @param applicationId
     *            the application ID to set
     */
    public void setApplicationId(Integer applicationId) {
        this.applicationId = applicationId;
    }

    /**
     * Returns a string representation of the Applicant object.
     * @return a string representation of the Applicant object
     */
    @Override
    public String toString() {
        return String.format("""
                [Applicant ID: %d]
                Name: %s
                NRIC: %s
                Age: %d
                Marital Status: %s
                Email: %s
                Application ID: %d
                Flat Type: %s
                """,
                getId(),
                getName(),
                getNric(),
                getAge(),
                getMaritalStatus(),
                getEmail(),
                applicationId,
                flatType != null ? flatType.name() : "None"
        );
    }

}
