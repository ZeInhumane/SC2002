package com.example.app.service;

import java.io.IOException;
import java.util.List;

import com.example.app.enums.MaritalStatus;
import com.example.app.models.*;
import com.example.app.enums.FlatType;

/**
 * Service interface for managing applicant-related operations.
 * This interface provides methods to interact with the applicant service and perform various actions related to applicants.
 */
public interface ApplicantService extends UserService {

    /**
     * Get all projects that the applicant can view.
     * 
     * @param applicant
     *            the applicant
     * @return a list of projects that the applicant can view
     * @throws IOException
     *             if an I/O error occurs
     * @throws NullPointerException
     *             if the applicant is null
     */
    List<Project> getViewableProjects(Applicant applicant) throws IOException, NullPointerException;

    /**
     * Get all projects that the applicant has applied for.
     * 
     * @param applicant
     *            the applicant
     * @return a list of projects that the applicant has applied for
     * @throws IOException
     *             if an I/O error occurs
     * @throws NullPointerException
     *             if the applicant is null
     */
    Project viewAppliedProjects(Applicant applicant) throws IOException, NullPointerException;

    /**
     * Check if the applicant is able to apply for a project.
     * 
     * @param applicant
     *            the applicant
     * @return true if the applicant is able to apply, false otherwise
     * @throws IOException
     *             if an I/O error occurs
     * @throws NullPointerException
     *             if the applicant is null
     */
    boolean isAbleToApply(Applicant applicant) throws IOException, NullPointerException;

    /**
     * Apply for a project.
     * 
     * @param applicant
     *            the applicant
     * @param projectId
     *            the project ID
     * @param preferredFlatType
     *            the preferred flat type
     * @return the application object
     * @throws IOException
     *             if an I/O error occurs
     * @throws NullPointerException
     *             if the applicant is null
     */
    Application applyForProject(Applicant applicant, int projectId, FlatType preferredFlatType)
            throws IOException, NullPointerException;

    /**
     * View the current applied project of the applicant, regardless of project status.
     * 
     * @param applicant
     * @return the project object
     * @throws IOException
     *             if an I/O error occurs
     * @throws NullPointerException
     *             if the applicant is null
     */
    Project viewAppliedProject(Applicant applicant) throws IOException, NullPointerException;

    /**
     * View the current application of the applicant.
     * 
     * @param applicant
     *            the applicant
     * @return the application object
     * @throws IOException
     *             if an I/O error occurs
     * @throws NullPointerException
     *             if the applicant is null
     */
    Application viewApplication(Applicant applicant) throws IOException, NullPointerException;

    /**
     * With draw an application.
     * 
     * @param applicant
     *            the applicant
     * @return the application object after withdrawal
     * @throws IOException
     *             if an I/O error occurs
     * @throws NullPointerException
     *             if the applicant is null, or the application is null
     */
    Application withdrawApplication(Applicant applicant) throws IOException, NullPointerException;

    /**
     * Submit an enquiry.
     * 
     * @param applicant
     *            the applicant
     * @param message
     *            the message to be sent
     * @param projectId
     *            the project ID
     * @return the enquiry object
     * @throws IOException
     *             if an I/O error occurs
     * @throws NullPointerException
     *             if the applicant is null
     */
    Enquiry submitEnquiry(Applicant applicant, String message, int projectId) throws IOException, NullPointerException;

    /**
     * Get all enquiries made by the applicant.
     * 
     * @param applicant
     *            the applicant
     * @return a list of enquiries made by the applicant
     * @throws IOException
     *             if an I/O error occurs
     * @throws NullPointerException
     *             if the applicant is null
     */
    List<Enquiry> getOwnEnquiries(Applicant applicant) throws IOException, NullPointerException;

    /**
     * Update an enquiry (update enquiry question only)
     * 
     * @param applicant
     *            the applicant
     * @param enquiryId
     *            the enquiry ID
     * @param message
     *            the new message
     * @return the updated enquiry object
     * @throws IOException
     *             if an I/O error occurs
     * @throws NullPointerException
     *             if the applicant is null
     */
    Enquiry updateEnquiry(Applicant applicant, int enquiryId, String message) throws IOException, NullPointerException;

    /**
     * Delete an enquiry.
     * 
     * @param applicant
     *            the applicant
     * @param enquiryId
     *            the enquiry ID
     * @throws IOException
     *             if an I/O error occurs
     * @throws NullPointerException
     *             if the applicant is null
     */
    void deleteEnquiry(Applicant applicant, int enquiryId) throws IOException, NullPointerException;

    /**
     * Get all enquiries for a specific project.
     * 
     * @param applicant
     *            the applicant
     * @param projectId
     *            the project ID
     * @return a list of enquiries for the project
     * @throws IOException
     *             if an I/O error occurs
     * @throws NullPointerException
     *             if the applicant is null
     */
    List<FlatType> getEligibleFlatTypesForProject(Applicant applicant, int projectId)
            throws IOException, NullPointerException;

    /**
     * Get all eligible flat types for the applicant based on marital status and age.
     * 
     * @param userStatus
     *            the marital status of the user
     * @param userAge
     *            the age of the user
     * @return a list of eligible flat types
     * @throws NullPointerException
     *             if the user status is null
     */
    List<FlatType> getEligibleFlatTypes(MaritalStatus userStatus, int userAge) throws NullPointerException;
}
