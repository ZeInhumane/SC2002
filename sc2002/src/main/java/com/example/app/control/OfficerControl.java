package com.example.app.control;

import com.example.app.enums.FlatType;
import com.example.app.models.*;
import com.example.app.service.OfficerService;
import com.example.app.service.impl.OfficerServiceImpl;

import java.util.List;

public class OfficerControl {
    private final OfficerService officerService;
    private Officer officer;

    public OfficerControl(OfficerService officerService, Officer officer) {
        this.officerService = officerService;
        this.officer = officer;
    }

    // Default constructor wiring for production
    public OfficerControl(Officer officer) {
        this(new OfficerServiceImpl(), officer);
    }

    /// Applicant methods ///
    public List<Project> getViewableProjects() {
        try {
            return officerService.getViewableProjects(this.officer);
        } catch (Exception e) {
            throw new RuntimeException("Error getting viewable projects: " + e.getMessage(), e);
        }
    }

    public boolean isAbleToApply() {
        try {
            return officerService.isAbleToApply(this.officer);
        } catch (Exception e) {
            throw new RuntimeException("Error checking application eligibility: " + e.getMessage(), e);
        }
    }

    public void applyForProject(int projectId, FlatType flatType) {
        try {
            officerService.applyForProject(this.officer, projectId, flatType);
        } catch (Exception e) {
            throw new RuntimeException("Error applying for project: " + e.getMessage(), e);
        }
    }

    public Project getAppliedProject() {
        try {
            return officerService.viewAppliedProject(this.officer);
        } catch (Exception e) {
            throw new RuntimeException("Error getting applied project: " + e.getMessage(), e);
        }
    }

    public Application getApplication() {
        try {
            return officerService.viewApplication(this.officer);
        } catch (Exception e) {
            throw new RuntimeException("Error getting application: " + e.getMessage(), e);
        }
    }

    public void submitEnquiry(String message, int projectId) {
        try {
            officerService.submitEnquiry(this.officer, message, projectId);
        } catch (Exception e) {
            throw new RuntimeException("Error submitting enquiry: " + e.getMessage(), e);
        }
    }

    public void withdrawApplication() {
        try {
            officerService.withdrawApplication(this.officer);
        } catch (Exception e) {
            throw new RuntimeException("Error withdrawing application: " + e.getMessage(), e);
        }
    }

    public List<Enquiry> getEnquiries() {
        try {
            return officerService.getOwnEnquiries(this.officer);
        } catch (Exception e) {
            throw new RuntimeException("Error getting enquiries: " + e.getMessage(), e);
        }
    }

    public void updateEnquiry(int enquiryId, String newMessage) {
        try {
            officerService.updateEnquiry(this.officer, enquiryId, newMessage);
        } catch (Exception e) {
            throw new RuntimeException("Error updating enquiry: " + e.getMessage(), e);
        }
    }

    public void deleteEnquiry(int enquiryId) {
        try {
            officerService.deleteEnquiry(this.officer, enquiryId);
        } catch (Exception e) {
            throw new RuntimeException("Error deleting enquiry: " + e.getMessage(), e);
        }
    }

    // Officer methods
    public List<Project> getRegistrableProjects() {
        try {
            return officerService.getRegistrableProjects(this.officer);
        } catch (Exception e) {
            throw new RuntimeException("Error getting registrable projects: " + e.getMessage(), e);
        }
    }

    public void registerForProject(int projectId) {
        try {
            officerService.registerForProject(this.officer, projectId);
        } catch (Exception e) {
            throw new RuntimeException("Error registering for project: " + e.getMessage(), e);
        }
    }

    public Registration getRegistration() {
        try {
            return officerService.viewCurrentRegistration(this.officer);
        } catch (Exception e) {
            throw new RuntimeException("Error getting registration: " + e.getMessage(), e);
        }
    }

    public Project getHandlingProject() {
        try {
            return officerService.viewHandlingProject(this.officer);
        } catch (Exception e) {
            throw new RuntimeException("Error getting handling project: " + e.getMessage(), e);
        }
    }

    public List<Enquiry> getHandlingEnquiries() {
        try {
            return officerService.getHandlingEnquiries(this.officer);
        } catch (Exception e) {
            throw new RuntimeException("Error getting handling enquiries: " + e.getMessage(), e);
        }
    }

    public Enquiry replyToEnquiry(int enquiryId, String message) {
        try {
            return officerService.replyEnquiry(this.officer, enquiryId, message);
        } catch (Exception e) {
            throw new RuntimeException("Error replying to enquiry: " + e.getMessage(), e);
        }
    }

    public List<Application> getBookingApplications() {
        try {
            return officerService.getBookingApplications(this.officer);
        } catch (Exception e) {
            throw new RuntimeException("Error getting booking applications: " + e.getMessage(), e);
        }
    }

    public List<Application> getBookedApplications() {
        try {
            return officerService.getBookedApplications(this.officer);
        } catch (Exception e) {
            throw new RuntimeException("Error getting booked applications: " + e.getMessage(), e);
        }
    }

    public void bookFlatForApplicant(int applicantId) {
        try {
            officerService.bookFlatForApplicant(applicantId);
        } catch (Exception e) {
            throw new RuntimeException("Error booking flat for applicant: " + e.getMessage(), e);
        }
    }

    public String generateBookingReceipt(int applicantId) {
        try {
            return officerService.generateBookingReceipt(applicantId);
        } catch (Exception e) {
            throw new RuntimeException("Error generating booking receipt: " + e.getMessage(), e);
        }
    }
}