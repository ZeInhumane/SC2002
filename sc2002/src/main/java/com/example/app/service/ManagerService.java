package com.example.app.service;

import java.io.IOException;
import java.util.*;

import com.example.app.models.Applicant;
import com.example.app.enums.ApplicationStatus;
import com.example.app.models.Enquiry;
import com.example.app.enums.FlatType;
import com.example.app.models.Manager;
import com.example.app.enums.MaritalStatus;
import com.example.app.models.Officer;
import com.example.app.models.Project;
import com.example.app.models.Registration;
import com.example.app.enums.RegistrationStatus;
import com.example.app.models.User;
import com.example.app.models.Application;


public class ManagerService extends UserService{
    
    static ProjectService projectService = new ProjectService();
    static ApplicationService applicationService = new ApplicationService();
    static EnquiryService enquiryService = new EnquiryService();
    static RegistrationService registrationService = new RegistrationService();

    // Allow manager to create project
    public Project createProject(
        Manager manager,
        String projectName, 
        Date applicationOpenDate, 
        Date applicationCloseDate, 
        String neighborhood,
        boolean visibility,
        Set<MaritalStatus> groups,
        Map<FlatType, Integer> flats
    ) throws IOException {
        return projectService.createProject(
            projectName,
            applicationOpenDate,
            applicationCloseDate,
            neighborhood,
            manager.getId(),
            visibility,
            groups,
            flats
        );
    }

    // Check project ownership
    public boolean isProjectBelongToManager(Manager manager, int projectId) throws IOException {
        Project project = projectService.findById(projectId);
        if (project == null) return false;
        return Objects.equals(project.getManagerId(), manager.getId());
    }

    // view applications relating to project 
    public List<Application> getApplicationsByProjectId(int projectId) throws IOException {
        return applicationService.findByProjectId(projectId);
    }




   // Returns List of projects open to user group and "on" visibility
    public Collection<Project> viewProjects() throws IOException {
        return projectService.findAll();        
    }

    // View my projects
    public Collection<Project> viewMyProjects(Manager manager) throws IOException {
        return projectService.findByManagerId(manager.getId());
    }

    // View project he is currently handling
    public Project viewHandlingProject(Manager manager) throws IOException {
        return projectService.findById(manager.getCurrentProjectId());
    }


    // Allow manager to edit project
    // Can retrieve entire project and then edit with fill ins 
    public void editProject(
        Manager manager,
        int projectId,
        String projectName, 
        Date applicationOpenDate, 
        Date applicationCloseDate, 
        String neighborhood, 
        boolean visibility,
        Set<MaritalStatus> groups,
        Map<FlatType, Integer> flats
    ) throws IOException {
        Project project = projectService.editProject(
            projectId,
            projectName,
            applicationOpenDate,
            applicationCloseDate,
            neighborhood,
            manager.getId(),
            visibility,
            groups,
            flats
        );
    }


    // Toggle Visibility for project
    public void toggleVisibility(Manager manager, int projectId) throws IOException {

        if (!isProjectBelongToManager(manager, projectId)) {
            throw new IllegalArgumentException("Project ID " + projectId + " does not belong to this manager.");
        }

        Project project = projectService.findById(projectId);
        project.setVisibility(!project.getVisibility());
    }

    // Delete Project and clean up related records from users
    public void deleteProject(int projectId) throws IOException {
        // 1. Delete related applications and remove from applicants
        List<Application> applications = applicationService.findByProjectId(projectId);
        for (Application app : applications) {
            int userId = app.getUserId();
            User user = this.findById(userId);
            if (user instanceof Applicant applicant) {
                applicant.setApplicationId(-1); 
            }
        }

        // 2. Delete related registrations and remove from officers
        List<Registration> registrations = registrationService.getRegistrationsByProjectId(projectId);
        for (Registration reg : registrations) {
            int userId = reg.getUserId();
            User user = userService.findById(userId);
            if (user instanceof Officer officer) {
                officer.setRegisteredProject(-1);
            }
            registrationService.deleteRegistration(reg.getId());
        }

        // 3. Delete related enquiries and remove from applicants
        List<Enquiry> enquiries = enquiryService.getEnquiriesByProjectId(projectId);
        for (Enquiry enquiry : enquiries) {
            int enquirerId = enquiry.getEnquirerId();
            User user = userService.findById(enquirerId);
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
        userService.assignProjectToOfficer(userId, projectId);
    }

    public void rejectRegistration(int registrationId) {
        Registration registration = registrationService.getRegistrationByRegistrationId(registrationId);
        registration.setStatus(RegistrationStatus.REJECTED);
    }
    
    // Edit application status
    public boolean approveApplication(int applicationId, boolean success) {
        ApplicationStatus status = success ? ApplicationStatus.SUCCESSFUL : ApplicationStatus.UNSUCCESSFUL;
        applicationService.updateStatus(applicationId, status);
        return success;
    }    


    // Get Enquiries
    public  List<Enquiry> viewEnquiriesOfProject(int projectId) {
        return enquiryService.getEnquiriesByProjectId(projectId);
    }

    // Get project by id
    public Project getProjectById(int projectId) {
        return projectService.findById(projectId);
    }

    public  boolean hasDateOverlap(Date start, Date end) {
        Collection<Project> myProjects = projectService.findByManagerId(admin.getId());
        return myProjects.stream()
            .filter(p -> p.getId() != admin.getCurrentProjectId())
            .anyMatch(p -> {
                Date pStart = p.getApplicationOpenDate();
                Date pEnd = p.getApplicationCloseDate();
                return !(end.before(pStart) || start.after(pEnd));
            });
    }


}
