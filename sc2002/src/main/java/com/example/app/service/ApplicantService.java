package com.example.app.service;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import com.example.app.models.Applicant;
import com.example.app.models.Application;
import com.example.app.models.ApplicationStatus;
import com.example.app.models.Enquiry;
import com.example.app.models.FlatType;
import com.example.app.models.MaritalStatus;
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

    public List<FlatType> getEligibleFlatTypesForProject(int projectId) {
        Project project = projectService.findById(projectId);
        if (project == null) {
            throw new IllegalArgumentException("Project with ID " + projectId + " not found.");
        }

        return project.getFlats().keySet().stream()
                .filter(flat -> {
                    if (user.getMaritalStatus() == MaritalStatus.SINGLE && user.getAge() >= 35) {
                        return flat == FlatType._2ROOM;
                    } else if (user.getMaritalStatus() == MaritalStatus.MARRIED && user.getAge() >= 21) {
                        return flat == FlatType._2ROOM || flat == FlatType._3ROOM;
                    }
                    return false;
                })
                .collect(Collectors.toList());
    }

    public void applyForProject(int projectId, FlatType preferredFlatType) {
        Project project = projectService.findById(projectId);
        if (project == null) {
            throw new IllegalArgumentException("Project with ID " + projectId + " does not exist.");
        }

        if (!project.getFlats().containsKey(preferredFlatType)) {
            throw new IllegalArgumentException("Selected flat type is not offered in the chosen project.");
        }

        List<FlatType> eligibleTypes = getEligibleFlatTypesForProject(projectId);
        if (!eligibleTypes.contains(preferredFlatType)) {
            throw new IllegalArgumentException("You are not eligible for the selected flat type.");
        }

        if (user.getApplicationId() != -1) {
            applicationService.deleteApplication(user.getApplicationId());
        }

        int applicationId = applicationService.applyForProject(user.getId(), projectId, project.getProjectName());
        user.setApplicationId(applicationId);
        user.setFlatType(preferredFlatType);
    }

    // Get current application safely
    public Application viewCurrentApplication() {
        if (user.getApplicationId() == -1)
            return null;
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

    public boolean isOfficerFor(int projectId) {
        Project project = projectService.findById(projectId);
        return project.getOfficers().contains(user.getId());
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
                .collect(Collectors.toList()); // ✅ this was missing
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
