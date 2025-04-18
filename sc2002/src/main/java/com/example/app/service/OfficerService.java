package com.example.app.service;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import com.example.app.models.Applicant;
import com.example.app.models.Application;
import com.example.app.models.ApplicationStatus;
import com.example.app.models.Enquiry;
import com.example.app.models.FlatType;
import com.example.app.models.Officer;
import com.example.app.models.Registration;
import com.example.app.models.User;
import com.example.app.models.Project;

public class OfficerService extends ApplicantService {

    static RegistrationService registrationService = new RegistrationService();
    static UserManagementService userManagementService = new UserManagementService();

    public OfficerService(Officer user) {
        super(user);
    }

    // Check if Officer is officer for a project (After Officer Application)
    public boolean isOfficerFor(int projectId) {
        return projectService.isOfficerFor(user.getId(), projectId);
    }

    // Check if is applicant for HDB (Before Officer Application)
    public boolean isApplicantFor(int projectId) {
        return applicationService.isApplicantFor(user.getId(), projectId);
    }

    // Check if Project is still running
    public boolean isProjectStillApplying(int projectId) {
        return projectService.isProjectStillApplying(projectId);
    }

    // Checks if cannot register as officer (An applicant for the hdb or has a
    // projhect somewhere before deadline)
    public boolean isNotRegisterableAsOfficer(int projectId) {
        Officer officer = (Officer) user;
        int oldProjectId = officer.getRegisteredProject();

        boolean isAlreadyApplicant = isApplicantFor(projectId);
        boolean isAlreadyHandling = oldProjectId != -1 && isProjectStillApplying(oldProjectId);

        return isAlreadyApplicant || isAlreadyHandling;
    }

    // Register for project
    public void registerAsOfficer(int projectId) {
        Officer officer = (Officer) user; // Cast explicitly since user is declared as Applicant

        Project project = projectService.findById(projectId);
        if (project == null) {
            throw new IllegalArgumentException("Project ID " + projectId + " not found.");
        }

        if (officer.getRegisteredProject() != -1) {
            registrationService.deleteOfficerRegistration(officer.getRegisteredProject());
        }

        // Override the registration to the latest one
        int registrationId = registrationService.registerAsOfficerForProject(officer.getId(), projectId,
                project.getProjectName());
        officer.setRegisteredProject(registrationId);

    }

    // Retrieve curren registration even if it is turned off
    public Registration viewCurrentRegistration() {
        Officer officer = (Officer) user; // Cast explicitly since user is declared as Applicant
        return registrationService.getRegistrationByRegistrationId(officer.getRegisteredProject());
    }

    // get Enquiries regarding a project
    public List<Enquiry> getProjectEnquiries() {

        Officer officer = (Officer) user;
        int projectId = officer.getRegisteredProject();
        if (projectId == -1) {
            throw new IllegalStateException("Officer is not assigned to any project.");
        }

        return enquiryService.getEnquiriesByProjectId(projectId);
    }

    // get Enquiry using enquiry id
    public Enquiry getEnquiry(int id) {
        return enquiryService.getEnquiry(id);
    }

    // reply Enquiry
    public void replyEnquiry(int id, String reply) {
        enquiryService.replyEnquiry(id, reply, user.getId());
    }

    public List<Project> getRegisterableProjects() {
        Collection<Project> allProjects = projectService.findByMaritalStatusAndVisibility(user.getMaritalStatus(),
                true);
        return allProjects.stream()
                .filter(p -> !isNotRegisterableAsOfficer(p.getId()))
                .collect(Collectors.toList());
    }

    // ------------------------------
    // Additionally Required functions
    // ------------------------------
    // === Flat selection responsibilities ===

    public void bookFlatForApplicant(String applicantNric, FlatType chosenFlatType) {
        User user = userManagementService.findByNric(applicantNric);
        if (!(user instanceof Applicant applicant)) {
            throw new IllegalArgumentException("NRIC does not belong to an applicant.");
        }

        Application app = applicationService.getApplicationById(applicant.getApplicationId());
        if (app == null || app.getStatus() != ApplicationStatus.SUCCESSFUL) {
            throw new IllegalStateException("Applicant has no successful application.");
        }

        // Update project flat count
        projectService.decrementFlatCount(app.getProjectId(), chosenFlatType);

        // Update application status
        app.setStatus(ApplicationStatus.BOOKED);

        // Assign flat type to applicant
        applicant.setFlatType(chosenFlatType);
    }

    public String generateBookingReceipt(String applicantNric) {
        User user = userManagementService.findByNric(applicantNric);
        if (!(user instanceof Applicant applicant)) {
            throw new IllegalArgumentException("NRIC does not belong to an applicant.");
        }

        Application app = applicationService.getApplicationById(applicant.getApplicationId());
        if (app == null || app.getStatus() != ApplicationStatus.BOOKED) {
            throw new IllegalStateException("Applicant has not booked a flat.");
        }

        Project project = projectService.findById(app.getProjectId());

        return String.format("""
                === Booking Receipt ===
                Name: %s
                NRIC: %s
                Age: %d
                Marital Status: %s
                Booked Flat Type: %s
                Project Name: %s
                Neighborhood: %s
                """,
                applicant.getName(),
                applicant.getNric(),
                applicant.getAge(),
                applicant.getMaritalStatus(),
                applicant.getFlatType(),
                project.getProjectName(),
                project.getNeighborhood());
    }

    // Maybe get all bookings

    // bookUser
    public List<String> getAllBookingsForHandledProject() {
        Officer officer = (Officer) user;
        int projectId = officer.getRegisteredProject();

        if (projectId == -1) {
            throw new IllegalStateException("Officer is not assigned to any project.");
        }

        List<Application> applications = applicationService.getApplicationsByProjectId(projectId);
        return applications.stream()
                .filter(app -> app.getStatus() == ApplicationStatus.BOOKED)
                .map(app -> {
                    User u = userManagementService.findById(app.getUserId());
                    if (u instanceof Applicant applicant) {
                        return String.format("""
                                Name: %s | NRIC: %s | Age: %d | Marital Status: %s | Flat Type: %s | Project: %s (%s)
                                """,
                                applicant.getName(),
                                applicant.getNric(),
                                applicant.getAge(),
                                applicant.getMaritalStatus(),
                                applicant.getFlatType(),
                                projectService.findById(projectId).getProjectName(),
                                projectService.findById(projectId).getNeighborhood());
                    }
                    return null;
                })
                .filter(s -> s != null)
                .toList();
    }

}
