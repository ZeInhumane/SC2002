package com.example.app.service;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import com.example.app.models.Applicant;
import com.example.app.models.Application;
import com.example.app.models.ApplicationStatus;
import com.example.app.models.Enquiry;
import com.example.app.models.FlatType;
import com.example.app.models.Project;


public class ApplicantService {
    
    static ProjectService projectService = new ProjectService();
    static ApplicationService applicationService = new ApplicationService();
    static EnquiryService enquiryService = new EnquiryService();

    protected Applicant user;

    public ApplicantService(Applicant user) {
        this.user = user;
    }

    public Applicant getUser() {
        return user;
    }


    // Returns List of projects open to user group and "on" visibility
    public Collection<Project> viewProjects() {
        return projectService.findByMaritalStatusAndVisibility(user.getMaritalStatus(), true);
    }

    // Get eligible flat types based on age and marital status
    public List<FlatType> getEligibleFlatTypes() {
        return projectService.getEligibleFlatTypes(user.getMaritalStatus(), user.getAge());
    }

    // Apply for project (removes existing application if necessary)
    public void applyForProject(int projectId) {
        Project project = projectService.findById(projectId);
        if (project == null) {
            throw new IllegalArgumentException("Project with ID " + projectId + " does not exist.");
        }

        if (user.getApplicationId() != -1) {
            applicationService.deleteApplication(user.getApplicationId());
        }

        int applicationId = applicationService.applyForProject(user.getId(), projectId, project.getProjectName());
        user.setApplicationId(applicationId);
    }

    // Get current application safely
    public Application viewCurrentApplication() {
        if (user.getApplicationId() == -1) return null;
        try {
            return applicationService.getApplicationById(user.getApplicationId());
        } catch (IllegalArgumentException e) {
            // Application not found, possibly deleted
            return null;
        }
    }

    // Can only apply if no app exists or the last one failed
    public boolean isAbleToApply() {
        Application current = viewCurrentApplication();
        return current == null || current.getStatus() == ApplicationStatus.UNSUCCESSFUL;
    }

    // Submit enquiry (stores ID back to user profile)
    public int submitEnquiry(String question, int projectId) {
        Project project = projectService.findById(projectId);
        if (project == null) {
            throw new IllegalArgumentException("Project ID " + projectId + " not found.");
        }

        int enquiryId = enquiryService.submitEnquiry(question, projectId, user.getId(), project.getProjectName());
        user.addToPastEnquiries(enquiryId);
        return enquiryId;
    }

    // View one enquiry
    public Enquiry getPastEnquiry(int enquiryId) {
        Enquiry e = enquiryService.getEnquiry(enquiryId);
        if (e == null || !user.getPastEnquiries().contains(enquiryId)) {
            throw new IllegalArgumentException("Enquiry not found or does not belong to this user.");
        }
        return e;
    }

    // List all enquiries for this applicant
    public List<Enquiry> getAllPastEnquiries() {
        return user.getPastEnquiries().stream()
                .map(enquiryService::getEnquiry)
                .filter(e -> e != null)
                .collect(Collectors.toList());
    }

    // Edit enquiry (if it belongs to this user)
    public void updateEnquiry(int enquiryId, String newQuestion) {
        if (!user.getPastEnquiries().contains(enquiryId)) {
            throw new IllegalArgumentException("You do not have permission to edit this enquiry.");
        }

        enquiryService.editEnquiry(enquiryId, newQuestion);
    }

    // Delete enquiry (if it belongs to this user)
    public void deleteEnquiry(int enquiryId) {
        if (!user.getPastEnquiries().contains(enquiryId)) {
            throw new IllegalArgumentException("You do not have permission to delete this enquiry.");
        }

        enquiryService.deleteEnquiry(enquiryId);
        user.removeFromPastEnquiries(enquiryId);
    }
    
    
}
