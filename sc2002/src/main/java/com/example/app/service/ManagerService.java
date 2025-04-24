package com.example.app.service;

import java.io.IOException;
import java.util.*;

import com.example.app.models.*;
import com.example.app.enums.FlatType;
import com.example.app.enums.MaritalStatus;
import com.example.app.enums.RegistrationStatus;

/**
 * Service interface for managing projects and registrations.
 * This interface extends the UserService interface.
 * It provides methods for managing project creation,
 * updating, visibility toggling,
 * registration status updates,
 * and application management.
 *
 */
public interface ManagerService extends UserService {

    /**
     * Create a new project.
     * @param manager the manager creating the project
     * @param projectName the name of the project
     * @param applicationOpenDate the date when applications open
     * @param applicationCloseDate the date when applications close
     * @param neighborhood the neighborhood of the project
     * @param visibility the visibility of the project
     * @param officerLimit the limit on the number of officers
     * @param officers the set of officer IDs
     * @param groups the set of marital status groups
     * @param flats the map of flat types and their quantities
     * @return the created project object
     * @throws IOException if an I/O error occurs
     * @throws IllegalStateException if project creation fails due to time overlap
     */
    Project createProject(Manager manager, String projectName, Date applicationOpenDate, Date applicationCloseDate,
            String neighborhood, boolean visibility, Integer officerLimit, Set<Integer> officers, Set<MaritalStatus> groups, Map<FlatType, Integer> flats)
            throws IOException, IllegalStateException;

    /**
     * Update an existing project.
     * @param manager the manager updating the project
     * @param projectId the ID of the project
     * @param projectName the name of the project
     * @param applicationOpenDate the date when applications open
     * @param applicationCloseDate the date when applications close
     * @param neighborhood the neighborhood of the project
     * @param visibility the visibility of the project
     * @param groups the set of marital status groups
     * @param flats the map of flat types and their quantities
     * @return the updated project object
     * @throws IOException if an I/O error occurs
     */
    Project updateProject(Manager manager, int projectId, String projectName, Date applicationOpenDate,
            Date applicationCloseDate, String neighborhood, boolean visibility, Set<MaritalStatus> groups,
            Map<FlatType, Integer> flats) throws IOException;

    /**
     * Check if a project belongs to the manager.
     * Since the manager can own multiple projects
     *
     * @param manager the manager
     * @param projectId the ID of the project
     * @return true if the project belongs to the manager, false otherwise
     * @throws IOException if an I/O error occurs
     */
    boolean isProjectBelongToManager(Manager manager, int projectId) throws IOException;

    /**
     * Toggle the visibility of a project.
     * @param manager the manager toggling the visibility
     * @param projectId the ID of the project
     * @return the updated project object
     * @throws IOException if an I/O error occurs
     * @throws IllegalStateException if the project does not belong to the manager
     */
    Project toggleVisibility(Manager manager, int projectId) throws IOException, IllegalStateException;

    /**
     * Get all projects. Since manager can view all projects, including the one they are not handling
     * @return a list of all projects
     * @throws IOException if an I/O error occurs
     */
    List<Project> getAllProjects() throws IOException;

    /**
     * Get all projects that the manager is handling.
     * @param manager the manager
     * @return a list of projects that the manager is handling
     * @throws IOException if an I/O error occurs
     */
    List<Project> getMyProjects(Manager manager) throws IOException;

    /**
     * Get the project that the manager is currently handling.
     * @param manager the manager
     * @return the project object
     * @throws IOException if an I/O error occurs
     */
    Project getHandlingProject(Manager manager) throws IOException;

    /**
     * Get all registrations of the current project that the manager is handling.
     * @param manager the manager
     * @return a list of registrations
     * @throws IOException if an I/O error occurs
     * @throws NullPointerException if the project is not found
     */
    List<Registration> getRegistrationsOfCurrentProject(Manager manager) throws IOException, NullPointerException;

    /**
     * Update the status of a registration.
     * @param manager the manager updating the registration
     * @param registrationId the ID of the registration
     * @param status the new status of the registration
     * @throws IOException if an I/O error occurs
     * @throws IllegalStateException if the project does not belong to the manager
     * @throws NullPointerException if the registration or officer not found
     */
    void updateRegistrationStatus(Manager manager, int registrationId, RegistrationStatus status) throws IOException, IllegalStateException, NullPointerException;

    /**
     * Delete a project.
     * @param projectId the ID of the project to delete
     * @throws IOException if an I/O error occurs
     */
    void deleteProject(int projectId) throws IOException;

    /**
     * Get all applications of the current project that the manager is handling.
     * @param manager the manager
     * @return a list of applications
     * @throws IOException if an I/O error occurs
     * @throws IllegalStateException if the project does not belong to the manager
     */
    List<Application> getApplicationsOfProject(Manager manager, Integer projectId) throws IOException, IllegalStateException;

    /**
     * Update the status of an application for an applicant
     * @param applicationId the ID of the application
     * @param status the new status of the application
     * @return the updated application object
     * @throws IOException if an I/O error occurs
     */
    Application updateApplicationStatus(int applicationId, boolean status) throws IOException;

    /**
     * Update the withdrawal status of an application.
     * @param applicationId the ID of the application
     * @param status the new withdrawal status
     * @return the updated application object
     * @throws IOException if an I/O error occurs
     */
    Application updateWithdrawalStatus(int applicationId, boolean status) throws IOException;

    /**
     * Get all the enquiries made by applicants
     * @return a list of enquiries
     * @throws IOException if an I/O error occurs
     */
    List<Enquiry> getAllEnquiries() throws IOException;

    /**
     * Get all the enquiries of a specific project
     * @param manager the manager
     * @param projectId the ID of the project
     * @return a list of enquiries for the project
     * @throws IOException if an I/O error occurs
     * @throws IllegalStateException if the project does not belong to the manager
     */
    List<Enquiry> getEnquiriesOfProject(Manager manager, int projectId) throws IOException, IllegalStateException;

    /**
     * Reply to an enquiry.
     * @param manager the manager replying to the enquiry
     * @param enquiryId the ID of the enquiry
     * @param message the message to be sent
     * @return the updated enquiry object
     * @throws IOException if an I/O error occurs
     */
    Enquiry replyEnquiry(Manager manager, int enquiryId, String message) throws IOException;

    /**
     * Generate a list of booked applications matching given filters
     * @return a list of booked applications
     * @param maritalStatus the marital status of the applicant
     * @param flatType the type of flat applied for
     * @param projectName the name of the project
     * @param minAge the minimum age of the applicant
     * @param maxAge the maximum age of the applicant
     * @throws IOException if an I/O error occurs
     */
    List<ApplicantBookingReportRow> getBookedApplicationsReport(
        MaritalStatus maritalStatus,
        FlatType flatType,
        String projectName,
        Integer minAge,
        Integer maxAge
    ) throws IOException;
}
