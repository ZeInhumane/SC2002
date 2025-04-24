package com.example.app.control;

import java.util.List;

import com.example.app.enums.FlatType;
import com.example.app.models.Applicant;
import com.example.app.models.Application;
import com.example.app.models.Enquiry;
import com.example.app.models.Project;
import com.example.app.service.ApplicantService;
import com.example.app.service.impl.ApplicantServiceImpl;

/**
 * Control class for managing applicant-related operations.
 * This class provides methods to interact with the applicant service and perform various actions related to applicants.
 */
public class ApplicantControl {
    private final ApplicantService applicantService;
    private Applicant applicant;

    public ApplicantControl(ApplicantService applicantService, Applicant applicant) {
        this.applicantService = applicantService;
        this.applicant = applicant;
    }

    // Default constructor wiring for production
    public ApplicantControl(Applicant applicant) {
        this(new ApplicantServiceImpl(), applicant);
    }

    public List<Project> getViewableProjects() {
        try {
            return applicantService.getViewableProjects(this.applicant);
        } catch (Exception e) {
            throw new RuntimeException("Error getting viewable projects: " + e.getMessage(), e);
        }
    }

    public boolean isAbleToApply() {
        try {
            return applicantService.isAbleToApply(this.applicant);
        } catch (Exception e) {
            throw new RuntimeException("Error checking application eligibility: " + e.getMessage(), e);
        }
    }

    public void applyForProject(int projectId, FlatType flatType) {
        try {
            applicantService.applyForProject(this.applicant, projectId, flatType);
        } catch (Exception e) {
            throw new RuntimeException("Error applying for project: " + e.getMessage(), e);
        }
    }

    public void submitEnquiry(String message, int projectId) {
        try {
            applicantService.submitEnquiry(this.applicant, message, projectId);
        } catch (Exception e) {
            throw new RuntimeException("Error submitting enquiry: " + e.getMessage(), e);
        }
    }

    public Application getApplication() {
        try {
            return applicantService.viewApplication(this.applicant);
        } catch (Exception e) {
            throw new RuntimeException("Error getting application: " + e.getMessage(), e);
        }
    }

    public Project getAppliedProject() {
        try {
            return applicantService.viewAppliedProject(this.applicant);
        } catch (Exception e) {
            throw new RuntimeException("Error getting applied project: " + e.getMessage(), e);
        }
    }

    public void withdrawApplication() {
        try {
            applicantService.withdrawApplication(this.applicant);
        } catch (Exception e) {
            throw new RuntimeException("Error withdrawing application: " + e.getMessage(), e);
        }
    }

    public List<Enquiry> getEnquiries() {
        try {
            return applicantService.getOwnEnquiries(this.applicant);
        } catch (Exception e) {
            throw new RuntimeException("Error getting enquiries: " + e.getMessage(), e);
        }
    }

    public void updateEnquiry(int enquiryId, String newMessage) {
        try {
            applicantService.updateEnquiry(this.applicant, enquiryId, newMessage);
        } catch (Exception e) {
            throw new RuntimeException("Error updating enquiry: " + e.getMessage(), e);
        }
    }

    public void deleteEnquiry(int enquiryId) {
        try {
            applicantService.deleteEnquiry(this.applicant, enquiryId);
        } catch (Exception e) {
            throw new RuntimeException("Error deleting enquiry: " + e.getMessage(), e);
        }
    }
}
