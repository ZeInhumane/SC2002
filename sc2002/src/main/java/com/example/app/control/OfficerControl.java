package com.example.app.control;

import com.example.app.enums.FlatType;
import com.example.app.models.*;
import com.example.app.service.OfficerService;
import com.example.app.service.impl.OfficerServiceImpl;

import java.io.IOException;
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
        } catch (IOException e) {
            throw new RuntimeException("Error retrieving viewable projects", e);
        }
    }

    public boolean isAbleToApply() {
        try {
            return officerService.isAbleToApply(this.officer);
        } catch (IOException e) {
            throw new RuntimeException("Error checking application eligibility", e);
        }
    }

    public void applyForProject(int projectId, FlatType flatType) {
        try {
            officerService.applyForProject(this.officer, projectId, flatType);
        } catch (IOException e) {
            throw new RuntimeException("Error applying for project", e);
        }
    }

    public Project getAppliedProject() {
        try {
            return officerService.viewAppliedProject(this.officer);
        } catch (IOException e) {
            throw new RuntimeException("Error retrieving applied project", e);
        }
    }

    public Application getApplication() {
        try {
            return officerService.viewApplication(this.officer);
        } catch (IOException e) {
            throw new RuntimeException("Error retrieving application", e);
        }
    }

    public void submitEnquiry(String message, int projectId) {
        try {
            officerService.submitEnquiry(this.officer, message, projectId);
        } catch (IOException e) {
            throw new RuntimeException("Error submitting enquiry", e);
        }
    }

    public void withdrawApplication() {
        try {
            officerService.withdrawApplication(this.officer);
        } catch (IOException e) {
            throw new RuntimeException("Error withdrawing application", e);
        }
    }

    public List<Enquiry> getEnquiries() {
        try {
            return officerService.getOwnEnquiries(this.officer);
        } catch (IOException e) {
            throw new RuntimeException("Error retrieving enquiries", e);
        }
    }

    public void updateEnquiry(int enquiryId, String newMessage) {
        try {
            officerService.updateEnquiry(this.officer, enquiryId, newMessage);
        } catch (IOException e) {
            throw new RuntimeException("Error editing enquiry", e);
        }
    }

    public void deleteEnquiry(int enquiryId) {
        try {
            officerService.deleteEnquiry(this.officer, enquiryId);
        } catch (IOException e) {
            throw new RuntimeException("Error deleting enquiry", e);
        }
    }

    /// Officer methods ///
    public List<Project> getRegistrableProjects() {
        try {
            return officerService.getRegistrableProjects(this.officer);
        } catch (IOException e) {
            throw new RuntimeException("Error retrieving registrable projects", e);
        }
    }

    public void registerForProject(int projectId) {
        try {
            officerService.registerForProject(this.officer, projectId);
        } catch (IOException e) {
            throw new RuntimeException("Error registering for project", e);
        }
    }

    public Registration getRegistration() {
        try {
            return officerService.viewCurrentRegistration(this.officer);
        } catch (IOException e) {
            throw new RuntimeException("Error retrieving current registration", e);
        }
    }

    public Project getHandlingProject() {
        try {
            return officerService.viewHandlingProject(this.officer);
        } catch (IOException e) {
            throw new RuntimeException("Error retrieving current project", e);
        }
    }

    public List<Enquiry> getHandlingEnquiries() {
        try {
            return officerService.getHandlingEnquiries(this.officer);
        } catch (IOException e) {
            throw new RuntimeException("Error retrieving handling enquiries", e);
        }
    }

    public Enquiry replyToEnquiry(int enquiryId, String message) {
        try {
            return officerService.replyEnquiry(this.officer, enquiryId, message);
        } catch (IOException e) {
            throw new RuntimeException("Error replying to enquiry", e);
        }
    }

    public List<Application> getBookingApplications() {
        try {
            return officerService.getBookingApplications(this.officer);
        } catch (IOException e) {
            throw new RuntimeException("Error retrieving handling applications", e);
        }
    }

    public List<Application> getBookedApplications() {
        try {
            return officerService.getBookedApplications(this.officer);
        } catch (IOException e) {
            throw new RuntimeException("Error retrieving booked applications", e);
        }
    }

    public void bookFlatForApplicant(int applicantId) {
        try {
            officerService.bookFlatForApplicant(applicantId);
        } catch (IOException e) {
            throw new RuntimeException("Error booking flat for applicant", e);
        }
    }

    public String generateBookingReceipt(int applicantId) {
        try {
            return officerService.generateBookingReceipt(applicantId);
        } catch (IOException e) {
            throw new RuntimeException("Error generating booking receipt", e);
        }
    }
}