package com.example.app.service.impl;

import com.example.app.enums.ApplicationStatus;
import com.example.app.enums.FlatType;
import com.example.app.enums.MaritalStatus;
import com.example.app.enums.RegistrationStatus;
import com.example.app.models.*;
import com.example.app.service.*;

import java.io.IOException;
import java.util.*;

/**
 * Service implementation for manager service The ManagerServiceImpl possesses methods from the UserServiceImpl and
 * implements the ManagerService interface.
 *
 * @see ManagerService
 * @see UserServiceImpl
 */
public class ManagerServiceImpl extends UserServiceImpl implements ManagerService {

    /**
     * ProjectService instance for managing projects.
     *
     * @see ProjectService
     */
    static ProjectService projectService = new ProjectServiceImpl();

    /**
     * ApplicationService instance for managing applications.
     *
     * @see ApplicationService
     */
    static ApplicationService applicationService = new ApplicationServiceImpl();

    /**
     * OfficerService instance for managing officers.
     *
     * @see OfficerService
     */
    static OfficerService officerService = new OfficerServiceImpl();

    /**
     * EnquiryService instance for managing enquiries.
     *
     * @see EnquiryService
     */
    static EnquiryService enquiryService = new EnquiryServiceImpl();

    /**
     * RegistrationService instance for managing registrations.
     *
     * @see RegistrationService
     */
    static RegistrationService registrationService = new RegistrationServiceImpl();

    // Allow manager to create project

    /**
     * Allow a manager to create a new project. The project is created only if the project dates do not overlap with
     * existing projects.
     * 
     * @param manager
     *            the manager creating the project
     * @param projectName
     *            the name of the project
     * @param applicationOpenDate
     *            the date when applications open
     * @param applicationCloseDate
     *            the date when applications close
     * @param neighborhood
     *            the neighborhood of the project
     * @param visibility
     *            the visibility of the project
     * @param officerLimit
     *            the limit on the number of officers
     * @param officers
     *            the set of officer IDs
     * @param groups
     *            the set of marital status groups
     * @param flats
     *            the map of flat types and their quantities
     * @return the created project
     * @throws IOException
     *             if an I/O error occurs
     * @throws IllegalStateException
     *             if the project dates overlap with existing projects
     */
    @Override
    public Project createProject(Manager manager, String projectName, Date applicationOpenDate,
            Date applicationCloseDate, String neighborhood, boolean visibility, Integer officerLimit,
            Set<Integer> officers, Set<MaritalStatus> groups, Map<FlatType, Integer> flats)
            throws IOException, IllegalStateException {

        // Check if the project dates overlap with existing projects
        if (hasDateOverlap(manager, applicationOpenDate, applicationCloseDate)) {
            throw new IllegalStateException("Project dates overlap with existing projects.");
        }

        return projectService.createProject(projectName, applicationOpenDate, applicationCloseDate, neighborhood,
                manager.getId(), visibility, officerLimit, officers, groups, flats);
    }

    /**
     * Allow a manager to update an existing project. Cannot edit the project after the application end date.
     * 
     * @param manager
     *            the manager updating the project
     * @param projectId
     *            the ID of the project
     * @param projectName
     *            the name of the project
     * @param applicationOpenDate
     *            the date when applications open
     * @param applicationCloseDate
     *            the date when applications close
     * @param neighborhood
     *            the neighborhood of the project
     * @param visibility
     *            the visibility of the project
     * @param groups
     *            the set of marital status groups
     * @param flats
     *            the map of flat types and their quantities
     * @return the updated project
     * @throws IOException
     *             if an I/O error occurs
     */
    @Override
    public Project updateProject(Manager manager, int projectId, String projectName, Date applicationOpenDate,
            Date applicationCloseDate, String neighborhood, boolean visibility, Set<MaritalStatus> groups,
            Map<FlatType, Integer> flats) throws IOException, IllegalStateException {
        Project project = projectService.findById(projectId);
        if (project == null) {
            throw new IllegalStateException("Project not found.");
        }
        Date now = new Date();
        if (!now.before(project.getApplicationOpenDate())) {
            throw new IllegalStateException("Cannot edit project after application open date.");
        }
        // Check for date overlap with other projects (exclude this project)
        if (hasDateOverlapExcludingProject(manager, applicationOpenDate, applicationCloseDate, projectId)) {
            throw new IllegalStateException("Project dates overlap with existing projects.");
        }
        return projectService.updateProject(projectId, projectName, applicationOpenDate, applicationCloseDate,
                neighborhood, manager.getId(), visibility, groups, flats);
    }

    /**
     * Check for project ownership
     * 
     * @param manager
     *            the manager
     * @param projectId
     *            the ID of the project
     * @return true if the project belongs to the manager, false otherwise
     * @throws IOException
     *             if an I/O error occurs
     */
    @Override
    public boolean isProjectBelongToManager(Manager manager, int projectId) throws IOException {
        Project project = projectService.findById(projectId);
        if (project == null)
            return false;
        return Objects.equals(project.getManagerId(), manager.getId());
    }

    /**
     * Toggle the visibility of a project, from false to true or vice versa.
     * 
     * @param manager
     *            the manager toggling the visibility
     * @param projectId
     *            the ID of the project
     * @return the updated project
     * @throws IOException
     * @throws IllegalStateException
     */
    @Override
    public Project toggleVisibility(Manager manager, int projectId) throws IOException, IllegalStateException {
        if (!isProjectBelongToManager(manager, projectId)) {
            throw new IllegalArgumentException("Project ID " + projectId + " does not belong to this manager.");
        }

        Project project = projectService.findById(projectId);
        project.setVisibility(!project.getVisibility());
        return projectService.save(project);
    }

    /**
     * Get all projects, including the one which the manager is not handling or the one that is not active.
     * 
     * @return
     * @throws IOException
     */
    @Override
    public List<Project> getAllProjects() throws IOException {
        return projectService.findAll();
    }

    /**
     * View own projects (created andm managing)
     * 
     * @param manager
     *            the manager
     * @return a list of projects that the manager is handling
     * @throws IOException
     *             if an I/O error occurs
     */
    @Override
    public List<Project> getMyProjects(Manager manager) throws IOException {
        return projectService.findByManagerId(manager.getId());
    }

    /**
     * Get the project that the manager is currently handling. This should return a single project As the manager can
     * only handle one project at a time
     *
     * @param manager
     *            the manager
     * @return the project object
     * @throws IOException
     *             if an I/O error occurs
     */
    @Override
    public Project getHandlingProject(Manager manager) throws IOException {
        return projectService.findByManagerIdAndIsActive(manager.getId());
    }

    /**
     * Get all registrations for the current project that the manager is handling The registrations are made from
     * officers
     * 
     * @param manager
     *            the manager
     * @return a list of registration
     * @throws IOException
     *             if an I/O error occurs
     * @throws NullPointerException
     *             if the project is not found
     */
    @Override
    public List<Registration> getRegistrationsOfCurrentProject(Manager manager)
            throws IOException, NullPointerException {
        Project project = getHandlingProject(manager);
        if (project == null) {
            throw new NullPointerException("No active project found for this manager.");
        }
        return registrationService.findByProjectId(project.getId());
    }

    /**
     * Update the status of a registration.
     * 
     * @param manager
     *            the manager updating the registration
     * @param registrationId
     *            the ID of the registration
     * @param status
     *            the new status of the registration
     * @throws IOException
     *             if an I/O error occurs
     * @throws IllegalStateException
     *             if the project does not belong to the manager
     * @throws NullPointerException
     *             if the registration or officer not found
     */
    @Override
    public void updateRegistrationStatus(Manager manager, int registrationId, RegistrationStatus status)
            throws IOException, IllegalStateException, NullPointerException {
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

    /**
     * Delete a project.
     * 
     * @param projectId
     *            the ID of the project to delete
     * @throws IOException
     *             if an I/O error occurs
     */
    @Override
    public void deleteProject(int projectId) throws IOException {
        projectService.deleteProject(projectId);
    }

    /**
     * Get all applications of the current project that the manager is handling. The applications are made by applicants
     * 
     * @param manager
     *            the manager
     * @param projectId
     *            the ID of the project
     * @return a list of applications
     * @throws IOException
     *             if an I/O error occurs
     * @throws IllegalStateException
     *             if the project does not belong to the manager
     */
    @Override
    public List<Application> getApplicationsOfProject(Manager manager, Integer projectId)
            throws IOException, IllegalStateException {
        if (!isProjectBelongToManager(manager, projectId)) {
            throw new IllegalArgumentException("Project ID " + projectId + " does not belong to this manager.");
        }
        return applicationService.findByProjectId(projectId);
    }

    /**
     * Edit the status of an application for an applicant The applicationStatus will be set to either SUCCESSFUL or
     * UNSUCCESSFUL
     * 
     * @param applicationId
     *            the ID of the application
     * @param success
     *            the new status of the application
     * @return the updated application object
     * @throws IOException
     *             if an I/O error occurs
     */
    @Override
    public Application updateApplicationStatus(int applicationId, boolean success) throws IOException {
        ApplicationStatus status = success ? ApplicationStatus.SUCCESSFUL : ApplicationStatus.UNSUCCESSFUL;
        return applicationService.updateStatus(applicationId, status);
    }

    /**
     * Update the withdrawal status of an application. The application will be set to WITHDRAWN if the success parameter
     * is true In either case of status, the request for withdrawal will still be marked as processed and hence, set
     * back to false
     * 
     * @param applicationId
     *            the ID of the application
     * @param success
     *            the new withdrawal status
     * @return the update application object
     * @throws IOException
     *             if an I/O error occurs
     */
    @Override
    public Application updateWithdrawalStatus(int applicationId, boolean success) throws IOException {
        return applicationService.updateWithdrawalStatus(applicationId, success);
    }

    /**
     * Get all enquiries made by applicants The manager can view all enquiries regardless if they are owning the project
     * or not
     * 
     * @return a list of enquiries
     * @throws IOException
     *             if an I/O error occurs
     */
    @Override
    public List<Enquiry> getAllEnquiries() throws IOException {
        return enquiryService.findAll();
    }

    /**
     * Get all enquiries of a specific project
     * 
     * @param manager
     *            the manager
     * @param projectId
     *            the ID of the project
     * @return a list of enquiries for the project
     * @throws IOException
     *             if an I/O error occurs
     * @throws IllegalStateException
     *             if the project does not belong to the manager
     */
    @Override
    public List<Enquiry> getEnquiriesOfProject(Manager manager, int projectId)
            throws IOException, IllegalStateException {
        if (!isProjectBelongToManager(manager, projectId)) {
            throw new IllegalArgumentException("Project ID " + projectId + " does not belong to this manager.");
        }
        return enquiryService.findByProjectId(projectId);
    }

    /**
     * Reply to an enquiry.
     * 
     * @param manager
     *            the manager replying to the enquiry
     * @param enquiryId
     *            the ID of the enquiry
     * @param reply
     *            the message to be sent
     * @return the updated enquiry object
     * @throws IOException
     *             if an I/O error occurs
     */
    @Override
    public Enquiry replyEnquiry(Manager manager, int enquiryId, String reply) throws IOException {
        return enquiryService.replyEnquiry(enquiryId, manager.getId(), reply);
    }

    /**
     * Get a report of booked applications based on various filters.
     * 
     * @param maritalStatus
     *            the marital status to filter by (can be null)
     * @param flatType
     *            the flat type to filter by (can be null)
     * @param projectName
     *            the project name to filter by (can be null)
     * @param minAge
     *            the minimum age to filter by (can be null)
     * @param maxAge
     *            the maximum age to filter by (can be null)
     * @return a list of booked applications matching the filters
     * @throws IOException
     *             if an I/O error occurs
     */
    @Override
    public List<ApplicantBookingReportRow> getBookedApplicationsReport(MaritalStatus maritalStatus, FlatType flatType,
            String projectName, Integer minAge, Integer maxAge) throws IOException {
        List<Application> applications = applicationService.getAllBookedApplications();
        List<ApplicantBookingReportRow> reportRows = new ArrayList<>();
        for (Application app : applications) {
            if (app.getStatus() != com.example.app.enums.ApplicationStatus.SUCCESSFUL)
                continue;
            User user = this.findById(app.getUserId());
            if (!(user instanceof Applicant))
                continue;
            Applicant applicant = (Applicant) user;
            Project project = projectService.findById(app.getProjectId());
            if (project == null)
                continue;
            // Filter by marital status
            if (maritalStatus != null && applicant.getMaritalStatus() != maritalStatus)
                continue;
            // Filter by flat type
            if (flatType != null && app.getFlatType() != flatType)
                continue;
            // Filter by project name (case-insensitive contains)
            if (projectName != null && !project.getProjectName().toLowerCase().contains(projectName.toLowerCase()))
                continue;
            // Filter by age range
            int age = applicant.getAge();
            if (minAge != null && age < minAge)
                continue;
            if (maxAge != null && age > maxAge)
                continue;
            reportRows.add(new ApplicantBookingReportRow(applicant.getName(), applicant.getAge(),
                    applicant.getMaritalStatus(), project.getProjectName(), app.getFlatType()));
        }
        return reportRows;
    }

    /**
     * Check if the project dates overlap with existing projects.
     * 
     * @param manager
     *            the manager
     * @param start
     *            the start date of the new project
     * @param end
     *            the end date of the new project
     * @return true if there is an overlap, false otherwise
     * @throws IOException
     *             if an I/O error occurs
     */
    private boolean hasDateOverlap(Manager manager, Date start, Date end) throws IOException {
        Collection<Project> myProjects = projectService.findByManagerId(manager.getId());
        return myProjects.stream().anyMatch(p -> {
            Date pStart = p.getApplicationOpenDate();
            Date pEnd = p.getApplicationCloseDate();
            return !(end.before(pStart) || start.after(pEnd));
        });
    }

    private boolean hasDateOverlapExcludingProject(Manager manager, Date start, Date end, int excludeProjectId)
            throws IOException {
        Collection<Project> myProjects = projectService.findByManagerId(manager.getId());
        return myProjects.stream().filter(p -> p.getId() != excludeProjectId).anyMatch(p -> {
            Date pStart = p.getApplicationOpenDate();
            Date pEnd = p.getApplicationCloseDate();
            return !(end.before(pStart) || start.after(pEnd));
        });
    }

}
