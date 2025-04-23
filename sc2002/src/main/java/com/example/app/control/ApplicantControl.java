package com.example.app.control;

import java.io.IOException;
import java.util.List;

import com.example.app.enums.FlatType;
import com.example.app.models.Applicant;
import com.example.app.models.Application;
import com.example.app.models.Enquiry;
import com.example.app.models.Project;
import com.example.app.service.ApplicantService;

public class ApplicantControl {
    private ApplicantService applicantService;
    private Applicant applicant;

    public ApplicantControl(ApplicantService applicantService, Applicant applicant) {
        this.applicantService = applicantService;
        this.applicant = applicant;
    }

    public List<Project> getViewableProjects() {
        try {
            return applicantService.getViewableProjects(this.applicant);
        } catch (IOException e) {
            throw new RuntimeException("Error retrieving viewable projects", e);
        }
    }

    public boolean isAbleToApply() {
        try {
            return applicantService.isAbleToApply(this.applicant);
        } catch (IOException e) {
            throw new RuntimeException("Error checking application eligibility", e);
        }
    }

    public void applyForProject(int projectId, FlatType flatType) {
        try {
            applicantService.applyForProject(this.applicant, projectId, flatType);
        } catch (IOException e) {
            throw new RuntimeException("Error applying for project", e);
        }
    }

    public void submitEnquiry(String message, int projectId) {
        try {
            applicantService.submitEnquiry(this.applicant, message, projectId);
        } catch (IOException e) {
            throw new RuntimeException("Error submitting enquiry", e);
        }
    }

    public Application getApplication() {
        try {
            return applicantService.viewCurrentApplication(this.applicant);
        } catch (IOException e) {
            throw new RuntimeException("Error retrieving application", e);
        }
    }

    public Project getAppliedProject() {
        try {
            return applicantService.viewCurrentProject(this.applicant);
        } catch (IOException e) {
            throw new RuntimeException("Error retrieving applied project", e);
        }
    }

    public void withdrawApplication() {
        try {
            applicantService.withdrawApplication(this.applicant);
        } catch (IOException e) {
            throw new RuntimeException("Error withdrawing application", e);
        }
    }

    public List<Enquiry> getEnquiries() {
        try {
            return applicantService.getAllEnquiries(this.applicant);
        } catch (IOException e) {
            throw new RuntimeException("Error retrieving enquiries", e);
        }
    }

    public void updateEnquiry(int enquiryId, String newMessage) {
        try {
            applicantService.updateEnquiry(this.applicant, enquiryId, newMessage);
        } catch (IOException e) {
            throw new RuntimeException("Error editing enquiry", e);
        }
    }

    public void deleteEnquiry(int enquiryId) {
        try {
            applicantService.deleteEnquiry(this.applicant, enquiryId);
        } catch (IOException e) {
            throw new RuntimeException("Error deleting enquiry", e);
        }
    }
}
