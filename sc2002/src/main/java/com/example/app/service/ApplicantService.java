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
    
    ProjectService projectService = new ProjectService();
    ApplicationService applicationService = new ApplicationService();
    EnquiryService enquiryService = new EnquiryService();

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

    // Get Eligible flat types. Used for UI to check if application is allowed for user
    public List<FlatType> getEligibleFlatTypes() {
        return projectService.getEligibleFlatTypes(user.getMaritalStatus(), user.getAge());
    }
    
    // Create an application for a project and set my user application to applicationId
    // delete the old application
    public void applyForProject(int projectId) {
        if (user.getApplicationId() != -1 ) {
            applicationService.deleteApplication(user.getApplicationId());
        }    
        int applicationId = applicationService.applyForProject(user.getId(), projectId);
        user.setApplicationId(applicationId);

    }

    // Retrieve curren application even if it is turned off
    public Application viewCurrentApplication() {
        return applicationService.getApplicationById(user.getApplicationId());
    }

    // Check if user can apply by seeing if his object has an application inside
    // or if old application is unsuccessful 
    public boolean isAbleToApply() {
        Application currentApplication = viewCurrentApplication();
        
        if (currentApplication == null) {
            return true;
        }

        return currentApplication.getStatus() == ApplicationStatus.UNSUCCESSFUL; 
    }

    // Submit enquiry, adds to enquiry repo but also to user object to fast track enquiries
    public int submitEnquiry(String question, int projectId) {
        int enquiryId = enquiryService.submitEnquiry(question, projectId, user.getId());
        user.addToPastEnquiries(enquiryId);
        return enquiryId;
    }
    
    // get past enquiry using id
    public Enquiry getPastEnquiry(int enquiryId) {
        return enquiryService.getEnquiry(enquiryId);
    }

    // Get a list of all past enquiry objects
    public List<Enquiry> getAllPastEnquiries() {
        return user.getPastEnquiries().stream()
                .map(enquiryService::getEnquiry)
                .collect(Collectors.toList());
    }

    // Update an existing enquiry's question text
    public void updateEnquiry(int enquiryId, String newQuestion) {
        enquiryService.editEnquiry(enquiryId, newQuestion);
    }


    // Delete Enquiry
    public void deleteEnquiry(int enquiryId) {
        enquiryService.deleteEnquiry(enquiryId);
        user.removeFromPastEnquiries(enquiryId);
    }

    
}
