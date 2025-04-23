package com.example.app.models;

import com.example.app.enums.ApplicationStatus;
import com.example.app.enums.FlatType;

/**
 * Application class that represents an application made by a user for a project.
 * It implements the BaseEntity interface.
 * Provides methods to get and set application details.
 *
 * @see BaseEntity
 * @see ApplicationStatus
 * @see FlatType
 */
public class Application implements BaseEntity {

    /**
     * The ID of the application.
     */
    private Integer id;

    /**
     * The ID of the user who made the application.
     */
    private Integer userId;

    /**
     * The ID of the project for which the application is made.
     */
    private Integer projectId;

    /**
     * The status of the application.
     */
    private ApplicationStatus status;

    /**
     * Indicates whether the user has requested to withdraw the application.
     */
    private Boolean requestWithdrawal;

    /**
     * The type of flat the user is applying for.
     */
    private FlatType flatType;

    /**
     * Default constructor for the Application class.
     */
    public Application() {
    }

    /**
     * Constructor for the Application class with parameters.
     *
     * @param id               the ID of the application. This field should be null for new applications. After saving to the database, it will be set.
     * @param userId           the ID of the user who made the application
     * @param projectId        the ID of the project for which the application is made
     * @param status           the status of the application
     * @param requestWithdrawal indicates whether the user has requested to withdraw the application
     * @param flatType         the type of flat the user is applying for
     */
    public Application(Integer id, Integer userId, Integer projectId, ApplicationStatus status,
            Boolean requestWithdrawal, FlatType flatType) {
        this.id = id;
        this.userId = userId;
        this.projectId = projectId;
        this.status = status;
        this.requestWithdrawal = requestWithdrawal;
        this.flatType = flatType;
    }

    /**
     * Gets the ID of the application.
     *
     * @return the ID of the application
     */
    @Override
    public Integer getId() {
        return id;
    }

    /**
     * Sets the ID of the application.
     *
     * @param id the new ID of the application
     */
    @Override
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * Gets the ID of the user who made the application.
     *
     * @return the ID of the user who made the application
     */
    public int getUserId() {
        return userId;
    }

    /**
     * Sets the ID of the user who made the application.
     *
     * @param userId the new ID of the user who made the application
     */
    public void setUserId(int userId) {
        this.userId = userId;
    }

    /**
     * Gets the ID of the project for which the application is made.
     *
     * @return the ID of the project for which the application is made
     */
    public int getProjectId() {
        return projectId;
    }

    /**
     * Sets the ID of the project for which the application is made.
     *
     * @param projectId the new ID of the project for which the application is made
     */
    public void setProjectId(int projectId) {
        this.projectId = projectId;
    }

    /**
     * Gets the status of the application.
     *
     * @return the status of the application
     */
    public ApplicationStatus getStatus() {
        return status;
    }

    /**
     * Sets the status of the application.
     *
     * @param status the new status of the application
     */
    public void setStatus(ApplicationStatus status) {
        this.status = status;
    }

    /**
     * Indicates whether the user has requested to withdraw the application.
     *
     * @return true if the user has requested to withdraw the application, false otherwise
     */
    public boolean isRequestWithdrawal() {
        return requestWithdrawal;
    }

    /**
     * Sets whether the user has requested to withdraw the application.
     *
     * @param requestWithdrawal true if the user has requested to withdraw the application, false otherwise
     */
    public void setRequestWithdrawal(boolean requestWithdrawal) {
        this.requestWithdrawal = requestWithdrawal;
    }

    /**
     * Gets the type of flat the user is applying for.
     *
     * @return the type of flat the user is applying for
     */
    public FlatType getFlatType() {
        return flatType;
    }

    /**
     * Sets the type of flat the user is applying for.
     *
     * @param flatType the new type of flat the user is applying for
     */
    public void setFlatType(FlatType flatType) {
        this.flatType = flatType;
    }

    /**
     * Returns a string representation of the Application object.
     *
     * @return a string representation of the Application object
     */
    @Override
    public String toString() {
        return String.format("""
            [Application Id: %s]
            Project (%s):
            Application Status: %s
            Request Withdrawal: %s
            Flat Type: %s
            """, id , projectId, status, requestWithdrawal ? "Yes" : "No", flatType != null ? flatType.toString() : "None");
    }

}
