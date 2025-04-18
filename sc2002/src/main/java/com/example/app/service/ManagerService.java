package com.example.app.service;

import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Map;

import com.example.app.models.Applicant;
import com.example.app.models.Application;
import com.example.app.models.ApplicationStatus;
import com.example.app.models.Enquiry;
import com.example.app.models.FlatType;
import com.example.app.models.Manager;
import com.example.app.models.MaritalStatus;
import com.example.app.models.Officer;
import com.example.app.models.Project;
import com.example.app.models.Registration;
import com.example.app.models.RegistrationStatus;
import com.example.app.models.User;

public class ManagerService {
    
    static ProjectService projectService = new ProjectService();
    static ApplicationService applicationService = new ApplicationService();
    static EnquiryService enquiryService = new EnquiryService();
    static RegistrationService registrationService = new RegistrationService();
    static UserManagementService userManagementService = new UserManagementService();

    private Manager admin;

    ManagerService(Manager admin) {
        this.admin = admin;
    }
    

    // Allow manager to create project
    public void createProject(
        String projectName, 
        Date applicationOpenDate, 
        Date applicationCloseDate, 
        String neighborhood, 
        MaritalStatus group, 
        Map<FlatType, Integer> flats
    ) 
    {
        int projectId = projectService.createProject(projectName, applicationOpenDate, applicationCloseDate, neighborhood, group, flats, admin.getId());
        admin.addProject(projectId);
    }


    // Allow manager to edit project
    // Can retrieve entire project and then edit with fill ins 
    public void editProject(
        int projectId,
        String projectName, 
        Date applicationOpenDate, 
        Date applicationCloseDate, 
        String neighborhood, 
        MaritalStatus group, 
        Map<FlatType, Integer> flats,
        boolean visibility
    ) 
    {
        projectService.editProject(projectId, projectName, applicationOpenDate, applicationCloseDate, neighborhood, group, flats, visibility);
    }

   // Delete Project
    // Delete Project and clean up related records from users
    public void deleteProject(int projectId) {
        // 1. Delete related applications and remove from applicants
        List<Application> applications = applicationService.getApplicationsByProjectId(projectId);
        for (Application app : applications) {
            int userId = app.getUserId();
            User user = userManagementService.findById(userId);
            if (user instanceof Applicant applicant) {
                applicant.setApplicationId(-1); 
            }
            applicationService.deleteApplication(app.getId());
        }

        // 2. Delete related registrations and remove from officers
        List<Registration> registrations = registrationService.getRegistrationsByProjectId(projectId);
        for (Registration reg : registrations) {
            int userId = reg.getUserId();
            User user = userManagementService.findById(userId);
            if (user instanceof Officer officer) {
                officer.setRegisteredProject(-1);
            }
            registrationService.deleteRegistration(reg.getId());
        }

        // 3. Delete related enquiries and remove from applicants
        List<Enquiry> enquiries = enquiryService.getEnquiriesByProjectId(projectId);
        for (Enquiry enquiry : enquiries) {
            int enquirerId = enquiry.getEnquirerId();
            User user = userManagementService.findById(enquirerId);
            if (user instanceof Applicant applicant) {
                applicant.getPastEnquiries().remove(enquiry.getId());
            }
            enquiryService.deleteEnquiry(enquiry.getId());
        }

        // 4. Delete the project itself
        projectService.deleteProject(projectId);

        // Manager delete project also 
        admin.removeProject(projectId);

    }

   // Returns List of projects open to user group and "on" visibility
    public Collection<Project> viewProjects() {
        return projectService.findAll();        
    }


    // View registrations
    public List<Registration> viewRegistrations(int projectId) {
        return registrationService.getRegistrationsByProjectId(projectId);
    }


    // Approve the registration
    public void approveRegistration(int registrationId) {
        Registration registration = registrationService.getRegistrationByRegistrationId(registrationId);
        registration.setStatus(RegistrationStatus.APPROVED);

        int userId = registration.getUserId();
        int projectId = registration.getProjectId();

        // Add officer to the project
        
        projectService.addOfficer(userId,projectId);

        // Change officer current portfolio project to this one
        userManagementService.assignProjectToOfficer(userId, projectId);
    }
    
    // Edit application status
    public void approveBTOStatus(int applicationId, boolean success) {
        ApplicationStatus status = success ? ApplicationStatus.SUCCESSFUL : ApplicationStatus.UNSUCCESSFUL;
        applicationService.changeStatus(applicationId, status);
    }


    // Get Enquiries
    public  List<Enquiry> viewEnquiriesOfProject(int projectId) {
        return enquiryService.getEnquiriesByProjectId(projectId);
    }
}
