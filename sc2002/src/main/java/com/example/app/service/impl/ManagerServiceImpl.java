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
    @Override
    public Project createProject(Manager manager, String projectName, Date applicationOpenDate,
            Date applicationCloseDate, String neighborhood, boolean visibility, Integer officerLimit, Set<Integer> officers, Set<MaritalStatus> groups,
            Map<FlatType, Integer> flats) throws IOException {

        // Check if the project dates overlap with existing projects
        if (hasDateOverlap(manager, applicationOpenDate, applicationCloseDate)) {
            throw new IllegalArgumentException("Project dates overlap with existing projects.");
        }

        return projectService.createProject(projectName, applicationOpenDate, applicationCloseDate, neighborhood,
                manager.getId(), visibility, officerLimit, officers, groups, flats);
    }

    // Allow manager to edit project
    // Can retrieve entire project and then edit with fill ins
    @Override
    public Project updateProject(Manager manager, int projectId, String projectName, Date applicationOpenDate,
            Date applicationCloseDate, String neighborhood, boolean visibility, Set<MaritalStatus> groups,
            Map<FlatType, Integer> flats) throws IOException {
        Project project = projectService.findById(projectId);
        if (project == null) {
            throw new IllegalArgumentException("Project not found.");
        }
        Date now = new Date();
        if (!now.before(project.getApplicationOpenDate())) {
            throw new IllegalArgumentException("Cannot edit project after application open date.");
        }
        // Check for date overlap with other projects (exclude this project)
        if (hasDateOverlapExcludingProject(manager, applicationOpenDate, applicationCloseDate, projectId)) {
            throw new IllegalArgumentException("Project dates overlap with existing projects.");
        }
        return projectService.updateProject(projectId, projectName, applicationOpenDate, applicationCloseDate,
                neighborhood, manager.getId(), visibility, groups, flats);
    }

    // Check project ownership
    @Override
    public boolean isProjectBelongToManager(Manager manager, int projectId) throws IOException {
        Project project = projectService.findById(projectId);
        if (project == null)
            return false;
        return Objects.equals(project.getManagerId(), manager.getId());
    }

    @Override
    public Project toggleVisibility(Manager manager, int projectId) throws IOException {
        if (!isProjectBelongToManager(manager, projectId)) {
            throw new IllegalArgumentException("Project ID " + projectId + " does not belong to this manager.");
        }

        Project project = projectService.findById(projectId);
        project.setVisibility(!project.getVisibility());
        return projectService.save(project);
    }

    // Returns List of projects open to user group and "on" visibility
    @Override
    public List<Project> getAllProjects() throws IOException {
        return projectService.findAll();
    }

    // View my projects
    @Override
    public List<Project> getMyProjects(Manager manager) throws IOException {
        return projectService.findByManagerId(manager.getId());
    }

    // View project he is currently handling
    @Override
    public Project getHandlingProject(Manager manager) throws IOException {
        return projectService.findByManagerIdAndIsActive(manager.getId());
    }

    @Override
    public List<Registration> getRegistrationsOfCurrentProject(Manager manager) throws IOException {
        Project project = getHandlingProject(manager);
        if (project == null) {
            throw new IllegalArgumentException("No active project found for this manager.");
        }
        return registrationService.findByProjectId(project.getId());
    }

    @Override
    public void updateRegistrationStatus(Manager manager, int registrationId, RegistrationStatus status)
            throws IOException {
        Registration registration = registrationService.findById(registrationId);

        if (!isProjectBelongToManager(manager, registration.getProjectId())) {
            throw new IllegalArgumentException(
                    "Registration ID " + registrationId + " does not belong to this manager.");
        }

        Officer officer = (Officer) this.findById(registration.getUserId());

        if (officer == null) {
            throw new IllegalArgumentException("User ID " + registration.getUserId() + " not found.");
        }

        if (status == RegistrationStatus.APPROVED) {
            projectService.addOfficer(registration.getProjectId(), officer.getId());
            officer.setProjectId(registration.getProjectId());
        }
        registrationService.changeStatus(registrationId, status);
        officerService.save(officer);
    }

    // Delete Project and clean up related records from users
    @Override
    public void deleteProject(int projectId) throws IOException {
        projectService.deleteProject(projectId);
    }

    @Override
    public List<Application> getApplicationsOfProject(Manager manager, Integer projectId) throws IOException {
        if (!isProjectBelongToManager(manager, projectId)) {
            throw new IllegalArgumentException("Project ID " + projectId + " does not belong to this manager.");
        }
        return applicationService.findByProjectId(projectId);
    }

    // Edit application status
    @Override
    public Application updateApplicationStatus(int applicationId, boolean success) throws IOException {
        ApplicationStatus status = success ? ApplicationStatus.SUCCESSFUL : ApplicationStatus.UNSUCCESSFUL;
        return applicationService.updateStatus(applicationId, status);
    }

    @Override
    public Application updateWithdrawalStatus(int applicationId, boolean success) throws IOException {
        return applicationService.updateWithdrawalStatus(applicationId, success);
    }

    @Override
    public List<Enquiry> getAllEnquiries() throws IOException {
        return enquiryService.findAll();
    }

    // Get Enquiries
    @Override
    public List<Enquiry> getEnquiriesOfProject(Manager manager, int projectId) throws IOException {
        if (!isProjectBelongToManager(manager, projectId)) {
            throw new IllegalArgumentException("Project ID " + projectId + " does not belong to this manager.");
        }
        return enquiryService.findByProjectId(projectId);
    }

    @Override
    public Enquiry replyEnquiry(Manager manager, int enquiryId, String reply) throws IOException {
        return enquiryService.replyEnquiry(enquiryId, manager.getId(), reply);
    }

    @Override
    public List<ApplicantBookingReportRow> getBookedApplicationsReport(MaritalStatus maritalStatus, FlatType flatType, String projectName, Integer minAge, Integer maxAge) throws IOException {
        List<Application> applications = applicationService.getAllBookedApplications();
        List<ApplicantBookingReportRow> reportRows = new ArrayList<>();
        for (Application app : applications) {
            if (app.getStatus() != com.example.app.enums.ApplicationStatus.SUCCESSFUL) continue;
            User user = this.findById(app.getUserId());
            if (!(user instanceof Applicant)) continue;
            Applicant applicant = (Applicant) user;
            Project project = projectService.findById(app.getProjectId());
            if (project == null) continue;
            // Filter by marital status
            if (maritalStatus != null && applicant.getMaritalStatus() != maritalStatus) continue;
            // Filter by flat type
            if (flatType != null && app.getFlatType() != flatType) continue;
            // Filter by project name (case-insensitive contains)
            if (projectName != null && !project.getProjectName().toLowerCase().contains(projectName.toLowerCase())) continue;
            // Filter by age range
            int age = applicant.getAge();
            if (minAge != null && age < minAge) continue;
            if (maxAge != null && age > maxAge) continue;
            reportRows.add(new ApplicantBookingReportRow(
                applicant.getName(),
                applicant.getAge(),
                applicant.getMaritalStatus(),
                project.getProjectName(),
                app.getFlatType()
            ));
        }
        return reportRows;
    }

    private boolean hasDateOverlap(Manager manager, Date start, Date end) throws IOException {
        Collection<Project> myProjects = projectService.findByManagerId(manager.getId());
        return myProjects.stream().anyMatch(p -> {
            Date pStart = p.getApplicationOpenDate();
            Date pEnd = p.getApplicationCloseDate();
            return !(end.before(pStart) || start.after(pEnd));
        });
    }

    private boolean hasDateOverlapExcludingProject(Manager manager, Date start, Date end, int excludeProjectId) throws IOException {
        Collection<Project> myProjects = projectService.findByManagerId(manager.getId());
        return myProjects.stream()
            .filter(p -> p.getId() != excludeProjectId)
            .anyMatch(p -> {
                Date pStart = p.getApplicationOpenDate();
                Date pEnd = p.getApplicationCloseDate();
                return !(end.before(pStart) || start.after(pEnd));
            });
    }

}
