package com.example.app.control;

import java.io.IOException;
import java.util.List;

import com.example.app.enums.FlatType;
import com.example.app.models.Applicant;
import com.example.app.models.Project;
import com.example.app.service.ApplicantService;

public class ApplicantControl {
    private ApplicantService applicantService;

    public ApplicantControl(ApplicantService applicantService) {
        this.applicantService = applicantService;
    }

    public List<Project> getViewableProjects(Applicant applicant) {
        try {
            return applicantService.getViewableProjects(applicant);
        } catch (IOException e) {
            throw new RuntimeException("Error retrieving viewable projects", e);
        }
    }

    public boolean isAbleToApply(Applicant applicant) {
        try {
            return applicantService.isAbleToApply(applicant);
        } catch (IOException e) {
            throw new RuntimeException("Error checking application eligibility", e);
        }
    }

    public void applyForProject(Applicant applicant, int projectId, FlatType flatType) {
        try {
            applicantService.applyForProject(applicant, projectId, flatType);
        } catch (IOException e) {
            throw new RuntimeException("Error applying for project", e);
        }
    }

    public void submitEnquiry(Applicant applicant, String message, int projectId) {
        try {
            applicantService.submitEnquiry(applicant, message, projectId);
        } catch (IOException e) {
            throw new RuntimeException("Error submitting enquiry", e);
        }
    }
}
