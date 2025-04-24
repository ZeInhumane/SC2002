package com.example.app.service;

import java.io.IOException;
import java.util.List;
import com.example.app.enums.FlatType;
import com.example.app.models.Application;
import com.example.app.enums.ApplicationStatus;

/**
 * Service interface for managing applications.
 */
public interface ApplicationService {

    /**
     * Apply for a project.
     * 
     * @param applicantId
     *            the ID of the applicant
     * @param projectId
     *            the ID of the project
     * @param flatType
     *            the type of flat
     * @return the application object
     * @throws IOException
     *             if an I/O error occurs
     */
    Application applyForProject(int applicantId, int projectId, FlatType flatType) throws IOException;

    /**
     * View the current application of the applicant.
     * 
     * @param id
     *            the ID of the application
     * @return the application object after applying
     * @throws IOException
     *             if an I/O error occurs
     */
    Application findById(int id) throws IOException;

    /**
     * View all applications for a specific project.
     * 
     * @param projectId
     *            the ID of the project
     * @return a list of applications for the project
     * @throws IOException
     *             if an I/O error occurs
     */
    List<Application> findByProjectId(int projectId) throws IOException;

    /**
     * Update the status of an application. Meant for manager to update the status of an application. Meant for officer
     * to update the status of an application.
     * 
     * @param id
     *            the ID of the application
     * @param status
     *            the new status of the application
     * @return the updated application object
     * @throws IOException
     *             if an I/O error occurs
     */
    Application updateStatus(int id, ApplicationStatus status) throws IOException;

    /**
     * Withdraw an application.
     * 
     * @param id
     *            the ID of the application
     * @return the application object after withdrawal
     * @throws IOException
     *             if an I/O error occurs
     */
    Application withdrawApplication(int id) throws IOException;

    /**
     * Update the withdrawal status of an application.
     * 
     * @param id
     *            the ID of the application
     * @param status
     *            the new withdrawal status
     * @return the updated application object
     * @throws IOException
     *             if an I/O error occurs
     */
    Application updateWithdrawalStatus(int id, boolean status) throws IOException;

    /**
     * Get all applications that have been booked.
     * 
     * @return List of booked applications
     * @throws IOException
     *             if an I/O error occurs
     */
    List<Application> getAllBookedApplications() throws IOException;

    /**
     * Save an application to the database.
     * 
     * @param application
     *            the application object to save
     * @return the saved application object
     * @throws IOException
     *             if an I/O error occurs
     */
    Application save(Application application) throws IOException;
}
