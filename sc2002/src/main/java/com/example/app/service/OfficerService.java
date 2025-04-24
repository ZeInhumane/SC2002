package com.example.app.service;

import java.io.IOException;
import java.util.List;
import com.example.app.models.Application;
import com.example.app.models.Enquiry;
import com.example.app.models.Officer;
import com.example.app.models.Registration;
import com.example.app.models.Project;

/**
 * Service interface for managing officers.
 * This interface extends the ApplicantService interface.
 * It provides methods for managing officer-related operations,
 * including project registration, enquiry handling,
 * and application management.
 *
 */
public interface OfficerService extends ApplicantService {

    /**
     * Check if the officer is currently handling a project.
     * @param officer the officer
     * @param projectId the project ID
     * @return true if the officer is handling the project, false otherwise
     * @throws IOException if an I/O error occurs
     * @throws NullPointerException if the officer is null
     */
    boolean isHandling(Officer officer, int projectId) throws IOException, NullPointerException;

    /**
     * Check if the officer is able to register for a project.
     * @param officer the officer
     * @param projectId the project ID
     * @return true if the officer is able to register, false otherwise
     * @throws IOException if an I/O error occurs
     * @throws NullPointerException if the officer is null
     */
    boolean isRegistrable(Officer officer, int projectId) throws IOException, NullPointerException;

    /**
     *
     * @param officer
     * @return
     * @throws IOException
     * @throws NullPointerException
     */
    boolean isAbleToApply(Officer officer) throws IOException, NullPointerException;

    /**
     * Get all projects that the officer can register for.
     * Can contain projects that are not open for application for the officer
     * Registration is different from application
     * @param officer the officer
     * @return a list of projects that the officer can register for
     * @throws IOException if an I/O error occurs
     * @throws NullPointerException if the officer is null
     */
    List<Project> getRegistrableProjects(Officer officer) throws IOException, NullPointerException;

    /**
     * Register the officer for a project.
     * @param officer the officer
     * @param projectId the project ID
     * @return the registration object
     * @throws IOException if an I/O error occurs
     * @throws NullPointerException if the officer is null
     */
    Officer registerForProject(Officer officer, int projectId) throws IOException, NullPointerException;

    /**
     * View the current registration of the officer.
     * @param officer the officer
     * @return the registration object
     * @throws IOException if an I/O error occurs
     * @throws NullPointerException if the officer is null
     */
    Registration viewCurrentRegistration(Officer officer) throws IOException, NullPointerException;

    /**
     * View the current project the officer is handling.
     * @param officer the officer
     * @return the project object
     * @throws IOException if an I/O error occurs
     * @throws NullPointerException if the officer is null
     */
    Project viewHandlingProject(Officer officer) throws IOException, NullPointerException;

    /**
     * Get all enquiries that the officer is handling.
     * @param officer the officer
     * @return a list of enquiries that the officer is handling
     * @throws IOException if an I/O error occurs
     * @throws NullPointerException if the officer is null
     */
    List<Enquiry> getHandlingEnquiries(Officer officer) throws IOException, NullPointerException;

    /**
     * Reply to an enquiry.
     * @param officer the officer
     * @param enquiryId the ID of the enquiry
     * @param message the message to be sent
     * @return the updated enquiry object
     * @throws IOException if an I/O error occurs
     * @throws NullPointerException if the officer is null
     */
    Enquiry replyEnquiry(Officer officer, int enquiryId, String message) throws IOException, NullPointerException;

    /**
     * Get all applications that the officer is handling with {@code Application.status = SUCCESSFUL}.
     * @param officer the officer
     * @return a list of applications that the officer is handling
     * @throws IOException if an I/O error occurs
     * @throws NullPointerException if the officer is null
     */
    List<Application> getBookingApplications(Officer officer) throws IOException, NullPointerException;

    /**
     * Get all applications that the officer is handling with {@code Application.status = BOOKED}
     * @param officer the officer
     * @throws IOException if an I/O error occurs
     * @throws NullPointerException if the NRIC is null
     */
    List<Application> getBookedApplications(Officer officer) throws IOException, NullPointerException;

    /**
     * Book a flat for the applicant.
     * @param applicantId the NRIC of the applicant
     * @throws IOException if an I/O error occurs
     * @throws NullPointerException if the NRIC is null, or if the project is not found
     */
    void bookFlatForApplicant(int applicantId) throws IOException, NullPointerException;

    /**
     * Generate a booking receipt for the applicant.
     * @param applicantId the NRIC of the applicant
     * @return the booking receipt
     * @throws IOException if an I/O error occurs
     * @throws IllegalStateException if the applicant has not booked a flat,
     * @throws NullPointerException if the NRIC is null, or if the project is not found
     */
    String generateBookingReceipt(int applicantId) throws IOException, NullPointerException;
}
