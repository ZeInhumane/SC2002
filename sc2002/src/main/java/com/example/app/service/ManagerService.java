package com.example.app.service;

import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Map;

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


import java.util.Comparator;


public class ManagerService {
    
    static ProjectService projectService = new ProjectService();
    static ApplicationService applicationService = new ApplicationService();
    static EnquiryService enquiryService = new EnquiryService();
    static RegistrationService registrationService = new RegistrationService();
    static UserService userService = new UserService();

    private Manager admin;

    public  ManagerService(Manager admin) {
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

    // Check project ownership
    public boolean isProjectBelongToManager(int projectId) {
        Project project = projectService.findById(projectId);
        if (project == null) return false;
        return project.getManagerId() == admin.getId();
    }

    // view applications relating to project 
    public List<Application> getApplicationsByProjectId(int projectId) {
        return applicationService.findByProjectId(projectId);
    }


    // Reassign the managers current project handling to another project if date limit is over or if manager has no project yet 
    public void reassignManaging() {
        int currentId = admin.getCurrentProjectId();

        // Case 1: Still managing current project
        if (projectService.isProjectStillApplying(currentId)) {
            return;
        }

        // Case 2: Not managing, find next project that has started today or earlier
        Collection<Project> myProjects = viewMyProjects();
        Date today = projectService.stripTime(new Date());

        Project nextProject = myProjects.stream()
            .filter(p -> {
                Date projectDate = projectService.stripTime(p.getApplicationOpenDate());
                return !projectDate.after(today); // projectDate <= today
            })
            .min(Comparator.comparing(p -> projectService.stripTime(p.getApplicationOpenDate())))
            .orElse(null);

        if (nextProject != null) {
            admin.setCurrentProjectId(nextProject.getId());
        } else {
            admin.setCurrentProjectId(-1); // reset if none found
        }
    }


   // Returns List of projects open to user group and "on" visibility
    public Collection<Project> viewProjects() {
        return projectService.findAll();        
    }

    // View my projects
    public Collection<Project> viewMyProjects() {
        return projectService.findByManagerId(admin.getId());        
    }

    // View project he is currently handling
    public Project viewHandlingProject() {
        return projectService.findById(admin.getCurrentProjectId());
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


    // Toggle Visibility for project
    public void toggleVisibility(int projectId) {
        Project project = projectService.findById(projectId);
        project.setVisibility(!project.getVisibility());
    }

    // Delete Project and clean up related records from users
    public void deleteProject(int projectId) {
        // 1. Delete related applications and remove from applicants
        List<Application> applications = applicationService.findByProjectId(projectId);
        for (Application app : applications) {
            int userId = app.getUserId();
            User user = userService.findById(userId);
            if (user instanceof Applicant applicant) {
                applicant.setApplicationId(-1); 
            }
            applicationService.deleteApplication(app.getId());
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
