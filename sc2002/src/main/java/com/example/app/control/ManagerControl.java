package com.example.app.control;

import com.example.app.enums.FlatType;
import com.example.app.enums.MaritalStatus;
import com.example.app.enums.RegistrationStatus;
import com.example.app.models.*;
import com.example.app.service.ManagerService;
import com.example.app.service.impl.ManagerServiceImpl;

import java.util.*;

public class ManagerControl {
    private final ManagerService managerService;
    private final Manager manager;

    public ManagerControl(ManagerService managerService, Manager manager) {
        this.managerService = managerService;
        this.manager = manager;
    }

    // Default constructor for production
    public ManagerControl(Manager manager) {
        this(new ManagerServiceImpl(), manager);
    }

    // Project Management
    public List<Project> getAllProjects() {
        try {
            return managerService.getAllProjects();
        } catch (Exception e) {
            throw new RuntimeException("Error getting all projects: " + e.getMessage(), e);
        }
    }

    public List<Project> getMyProjects() {
        try {
            return managerService.getMyProjects(manager);
        } catch (Exception e) {
            throw new RuntimeException("Error getting manager's projects: " + e.getMessage(), e);
        }
    }

    public Project createProject(String projectName, Date open, Date close, String neighborhood, boolean visibility, Integer officerLimit, Set<Integer> officers, Set<MaritalStatus> groups, Map<FlatType, Integer> flats) {
        try {
            return managerService.createProject(manager, projectName, open, close, neighborhood, visibility, officerLimit, officers, groups, flats);
        } catch (Exception e) {
            throw new RuntimeException("Error creating project: " + e.getMessage(), e);
        }
    }

    public Project updateProject(int projectId, String projectName, Date open, Date close, String neighborhood, boolean visibility, Set<MaritalStatus> groups, Map<FlatType, Integer> flats) {
        try {
            return managerService.updateProject(manager, projectId, projectName, open, close, neighborhood, visibility, groups, flats);
        } catch (Exception e) {
            throw new RuntimeException("Error updating project: " + e.getMessage(), e);
        }
    }

    public boolean isProjectBelongToManager(int projectId) {
        try {
            return managerService.isProjectBelongToManager(manager, projectId);
        } catch (Exception e) {
            throw new RuntimeException("Error checking project ownership: " + e.getMessage(), e);
        }
    }

    public Project toggleVisibility(int projectId) {
        try {
            return managerService.toggleVisibility(manager, projectId);
        } catch (Exception e) {
            throw new RuntimeException("Error toggling project visibility: " + e.getMessage(), e);
        }
    }

    public void deleteProject(int projectId) {
        try {
            managerService.deleteProject(projectId);
        } catch (Exception e) {
            throw new RuntimeException("Error deleting project: " + e.getMessage(), e);
        }
    }

    // Officer Registration Management
    public List<Registration> getRegistrationsOfCurrentProject() {
        try {
            return managerService.getRegistrationsOfCurrentProject(manager);
        } catch (Exception e) {
            throw new RuntimeException("Error getting registrations of current project: " + e.getMessage(), e);
        }
    }

    public void updateRegistrationStatus(int registrationId, RegistrationStatus status) {
        try {
            managerService.updateRegistrationStatus(manager, registrationId, status);
        } catch (Exception e) {
            throw new RuntimeException("Error updating registration status: " + e.getMessage(), e);
        }
    }

    // Application Management
    public List<Application> getApplicationsOfProject(int projectId) {
        try {
            return managerService.getApplicationsOfProject(manager, projectId);
        } catch (Exception e) {
            throw new RuntimeException("Error getting applications of project: " + e.getMessage(), e);
        }
    }

    public Application updateApplicationStatus(int applicationId, boolean status) {
        try {
            return managerService.updateApplicationStatus(applicationId, status);
        } catch (Exception e) {
            throw new RuntimeException("Error updating application status: " + e.getMessage(), e);
        }
    }

    public Application updateWithdrawalStatus(int applicationId, boolean status) {
        try {
            return managerService.updateWithdrawalStatus(applicationId, status);
        } catch (Exception e) {
            throw new RuntimeException("Error updating withdrawal status: " + e.getMessage(), e);
        }
    }

    public List<ApplicantBookingReportRow> getBookedApplicationsReport(
        MaritalStatus maritalStatus,
        FlatType flatType,
        String projectName,
        Integer minAge,
        Integer maxAge
    ) {
        try {
            return managerService.getBookedApplicationsReport(maritalStatus, flatType, projectName, minAge, maxAge);
        } catch (Exception e) {
            throw new RuntimeException("Error getting booked applications report: " + e.getMessage(), e);
        }
    }

    // Enquiry Management
    public List<Enquiry> getAllEnquiries() {
        try {
            return managerService.getAllEnquiries();
        } catch (Exception e) {
            throw new RuntimeException("Error getting all enquiries: " + e.getMessage(), e);
        }
    }

    public List<Enquiry> getEnquiriesOfProject(int projectId) {
        try {
            return managerService.getEnquiriesOfProject(manager, projectId);
        } catch (Exception e) {
            throw new RuntimeException("Error getting enquiries of project: " + e.getMessage(), e);
        }
    }

    public Enquiry replyEnquiry(int enquiryId, String message) {
        try {
            return managerService.replyEnquiry(manager, enquiryId, message);
        } catch (Exception e) {
            throw new RuntimeException("Error replying to enquiry: " + e.getMessage(), e);
        }
    }
}
