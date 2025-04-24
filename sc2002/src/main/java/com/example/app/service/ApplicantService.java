package com.example.app.service;

import java.io.IOException;
import java.util.List;

import com.example.app.enums.MaritalStatus;
import com.example.app.models.*;
import com.example.app.enums.FlatType;

public interface ApplicantService extends UserService {

    // View public projects based on marital status and visibility and within application period
    List<Project> getViewableProjects(Applicant applicant) throws IOException, NullPointerException;

    // View currently applied projects
    Project viewAppliedProjects(Applicant applicant) throws IOException, NullPointerException;

    // Check if the applicant is eligible to apply for a new project
    boolean isAbleToApply(Applicant applicant) throws IOException, NullPointerException;

    // Apply for a project
    Application applyForProject(Applicant applicant, int projectId, FlatType preferredFlatType) throws IOException, NullPointerException;

    // View the project that the applicant has applied for, regardless of status
    Project viewAppliedProject(Applicant applicant) throws IOException, NullPointerException;

    // Check the application status
    Application viewApplication(Applicant applicant) throws IOException, NullPointerException;

    // Withdraw application
    Application withdrawApplication(Applicant applicant) throws IOException, NullPointerException;

    Enquiry submitEnquiry(Applicant applicant, String message, int projectId) throws IOException, NullPointerException;

    // Get all enquiries made by the applicant
    List<Enquiry> getOwnEnquiries(Applicant applicant) throws IOException, NullPointerException;

    Enquiry updateEnquiry(Applicant applicant, int enquiryId, String message) throws IOException, NullPointerException;

    void deleteEnquiry(Applicant applicant, int enquiryId) throws IOException, NullPointerException;
    // Check if the applicant is eligible for a specific flat type in a project
    List<FlatType> getEligibleFlatTypesForProject(Applicant applicant, int projectId)
            throws IOException, NullPointerException;

    // Check types of flat applicant is eligible for
    List<FlatType> getEligibleFlatTypes(MaritalStatus userStatus, int userAge) throws
            IOException, NullPointerException;
}
