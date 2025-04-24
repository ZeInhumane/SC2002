package com.example.app.service.impl;

import com.example.app.enums.ApplicationStatus;
import com.example.app.models.*;
import com.example.app.service.OfficerService;
import com.example.app.service.RegistrationService;
import com.example.app.service.UserService;

import java.io.IOException;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class OfficerServiceImpl extends ApplicantServiceImpl implements OfficerService {
    static RegistrationService registrationService = new RegistrationServiceImpl();
    static UserService userService = new UserServiceImpl();

    public boolean isHandling(Officer officer, int projectId) throws IOException {
        return Objects.equals(projectId, officer.getProjectId()) && projectService.isActive(projectId);
    }

    // Checks if cannot register as officer (An applicant for the hdb or has a
    // project somewhere before deadline)
    @Override
    public boolean isRegistrable(Officer officer, int projectId) throws IOException {
        boolean isAlreadyApplicant = officer.getApplicationId() != null && officer.getApplicationId() == projectId;

        boolean isAlreadyHandling = officer.getProjectId() != null && projectService.isActive(officer.getProjectId());

        return !isAlreadyApplicant && !isAlreadyHandling;
    }

    // Check if officer can apply for project
    // Can only apply if 1, able to apply as applicant and 2, not handling any active project
    public boolean isAbleToApply(Officer officer) throws IOException {
        if (officer.getProjectId() != null && projectService.isActive(officer.getProjectId())) {
            return false;
        }
        return super.isAbleToApply(officer);
    }

    @Override
    public List<Project> getRegistrableProjects(Officer officer) throws IOException, NullPointerException {
        Collection<Project> allProjects = projectService.findByVisibilityAndOpenDateGreaterThanAndCloseDateLessThan(true, new Date());

        return allProjects.stream().filter(p -> {
            try {
                return isRegistrable(officer, p.getId());
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }).collect(Collectors.toList());
    }

    // Register for project
    @Override
    public Officer registerForProject(Officer officer, int projectId) throws IOException {
        Project project = projectService.findById(projectId);
        if (project == null) {
            throw new IllegalArgumentException("Project ID " + projectId + " not found.");
        }

        if (!isRegistrable(officer, projectId)) {
            throw new IllegalStateException("You are not eligible to register for this project.");
        }

        // Override the registration to the latest one
        Registration registration = registrationService.registerForProject(officer.getId(), projectId);
        officer.setRegistrationId(registration.getId());
        return (Officer) this.save(officer);
    }

    // Retrieve current registration even if it is turned off
    @Override
    public Registration viewCurrentRegistration(Officer officer) throws IOException {
        Registration registration = registrationService.findById(officer.getRegistrationId());
        if (registration == null) {
            throw new IllegalArgumentException("Registration ID " + officer.getRegistrationId() + " not found.");
        }
        return registration;
    }

    @Override
    public Project viewHandlingProject(Officer officer) throws IOException {
        if (officer.getProjectId() == null) {
            throw new IllegalStateException("Officer is not assigned to any project.");
        }

        return projectService.findById(officer.getProjectId());
    }

    // get Enquiries regarding handling project
    @Override
    public List<Enquiry> getHandlingEnquiries(Officer officer) throws IOException {
        if (officer.getProjectId() == null) {
            throw new IllegalStateException("Officer is not assigned to any project.");
        }

        return enquiryService.findByProjectId(officer.getProjectId());
    }

    // reply Enquiry
    @Override
    public Enquiry replyEnquiry(Officer officer, int enquiryId, String reply) throws IOException {
        return enquiryService.replyEnquiry(enquiryId, officer.getId(), reply);
    }

    @Override
    public List<Application> getBookingApplications(Officer officer) throws IOException {
        if (officer.getProjectId() == null) {
            throw new IllegalStateException("Officer is not assigned to any project.");
        }

        return applicationService.findByProjectId(officer.getProjectId()).stream()
                .filter(application -> application.getStatus() == ApplicationStatus.SUCCESSFUL)
                .collect(Collectors.toList());
    }

    @Override
    public List<Application> getBookedApplications(Officer officer) throws IOException {
        if (officer.getProjectId() == null) {
            throw new IllegalStateException("Officer is not assigned to any project.");
        }

        return applicationService.findByProjectId(officer.getProjectId()).stream()
                .filter(application -> application.getStatus() == ApplicationStatus.BOOKED)
                .collect(Collectors.toList());
    }

    @Override
    public void bookFlatForApplicant(int applicantId) throws IOException {
        Applicant applicant = (Applicant) userService.findById(applicantId);
        Application application = applicationService.findById(applicant.getApplicationId());

        // CHANGE BACK TO SUCCESSFUL
        if (application == null || application.getStatus() != ApplicationStatus.SUCCESSFUL) {
            throw new IllegalStateException("Applicant has no successful application.");
        }

        // Update project flat count
        projectService.decrementFlatCount(application.getProjectId(), application.getFlatType());

        // Update application status
        application.setStatus(ApplicationStatus.BOOKED);
        applicationService.save(application);

        // Assign flat type to applicant
        applicant.setFlatType(application.getFlatType());
        userService.save(applicant);
    }

    @Override
    public String generateBookingReceipt(int applicantId) throws IOException {
        Applicant applicant = (Applicant) userService.findById(applicantId);

        Application app = applicationService.findById(applicant.getApplicationId());
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

}
