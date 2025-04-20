package com.example.app.service.impl;

import com.example.app.enums.ApplicationStatus;
import com.example.app.enums.FlatType;
import com.example.app.enums.MaritalStatus;
import com.example.app.enums.RegistrationStatus;
import com.example.app.models.*;
import com.example.app.service.*;

import java.io.IOException;
import java.util.*;

public class ManagerServiceImpl extends UserServiceImpl implements ManagerService {

    static ProjectService projectService = new ProjectServiceImpl();
    static ApplicationService applicationService = new ApplicationServiceImpl();
    static OfficerService officerService = new OfficerServiceImpl();
    static EnquiryService enquiryService = new EnquiryServiceImpl();
    static RegistrationService registrationService = new RegistrationServiceImpl();

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

        // Check if the project dates overlap with existing projects
        if (hasDateOverlap(manager, applicationOpenDate, applicationCloseDate)) {
            throw new IllegalArgumentException("Project dates overlap with existing projects.");
        }

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


    // Allow manager to edit project
    // Can retrieve entire project and then edit with fill ins
    public Project updateProject(
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
        return projectService.updateProject(
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


    // Check project ownership
    public boolean isProjectBelongToManager(Manager manager, int projectId) throws IOException {
        Project project = projectService.findById(projectId);
        if (project == null) return false;
        return Objects.equals(project.getManagerId(), manager.getId());
    }

    public Project toggleVisibility(Manager manager, int projectId) throws IOException {
        if (!isProjectBelongToManager(manager, projectId)) {
            throw new IllegalArgumentException("Project ID " + projectId + " does not belong to this manager.");
        }

        Project project = projectService.findById(projectId);
        project.setVisibility(!project.getVisibility());
        return projectService.save(project);
    }

    // Returns List of projects open to user group and "on" visibility
    public List<Project> getAllProjects() throws IOException {
        return projectService.findAll();
    }

    // View my projects
    public List<Project> getMyProjects(Manager manager) throws IOException {
        return projectService.findByManagerId(manager.getId());
    }

    // View project he is currently handling
    public Project getHandlingProject(Manager manager) throws IOException {
        return projectService.findByManagerIdAndIsActive(manager.getId());
    }

    public List<Registration> getRegistrationsOfCurrentProject(Manager manager) throws IOException {
        Project project = getHandlingProject(manager);
        if (project == null) {
            throw new IllegalArgumentException("No active project found for this manager.");
        }
        return registrationService.findByProjectId(project.getId());
    }

    public void updateRegistrationStatus(Manager manager, int registrationId, RegistrationStatus status) throws IOException {
        Registration registration = registrationService.findById(registrationId);

        if (!isProjectBelongToManager(manager, registration.getProjectId())) {
            throw new IllegalArgumentException("Registration ID " + registrationId + " does not belong to this manager.");
        }

        Officer officer = (Officer) this.findById(registration.getUserId());

        if (officer == null) {
            throw new IllegalArgumentException("User ID " + registration.getUserId() + " not found.");
        }

        registrationService.changeStatus(registrationId, status);
        if (status == RegistrationStatus.APPROVED) {
            officer.setProjectId(registration.getProjectId());
        }
        officerService.save(officer);
    }


    // Delete Project and clean up related records from users
    public void deleteProject(int projectId) throws IOException {
        projectService.deleteProject(projectId);
    }

    public List<Application> getApplicationsOfProject(Manager manager, Integer projectId) throws IOException {
        if (!isProjectBelongToManager(manager, projectId)) {
            throw new IllegalArgumentException("Project ID " + projectId + " does not belong to this manager.");
        }
        return applicationService.findByProjectId(projectId);
    }

    // Edit application status
    public Application updateApplicationStatus(int applicationId, boolean success) throws IOException {
        ApplicationStatus status = success ? ApplicationStatus.SUCCESSFUL : ApplicationStatus.UNSUCCESSFUL;
        return applicationService.updateStatus(applicationId, status);
    }


    public Application updateWithdrawalStatus(int applicationId, boolean success) throws IOException {
        return applicationService.updateWithdrawalStatus(applicationId, success);
    }

    public List<Enquiry> getAllEnquiries() throws IOException {
        return enquiryService.findAll();
    }

    // Get Enquiries
    public List<Enquiry> getEnquiriesOfProject(Manager manager, int projectId) throws IOException {
        if (!isProjectBelongToManager(manager, projectId)) {
            throw new IllegalArgumentException("Project ID " + projectId + " does not belong to this manager.");
        }
        return enquiryService.findByProjectId(projectId);
    }

    public Enquiry replyEnquiry(Manager manager, int enquiryId, String reply) throws IOException {
        return enquiryService.replyEnquiry(enquiryId, manager.getId(), reply);
    }

    public boolean hasDateOverlap(Manager manager, Date start, Date end) throws IOException {
        Collection<Project> myProjects = projectService.findByManagerId(manager.getId());
        return myProjects.stream()
                .anyMatch(p -> {
                    Date pStart = p.getApplicationOpenDate();
                    Date pEnd = p.getApplicationCloseDate();
                    return !(end.before(pStart) || start.after(pEnd));
                });
    }

}
