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

/**
 * OfficerServiceImpl is a service class that implements the OfficerService interface. It provides methods for managing
 * officer registrations, handling enquiries,
 *
 * @see OfficerService
 * @see ApplicantServiceImpl
 */
public class OfficerServiceImpl extends ApplicantServiceImpl implements OfficerService {

    /**
     * Service for managing registration.
     *
     * @see RegistrationService
     */
    static RegistrationService registrationService = new RegistrationServiceImpl();

    /**
     * Service for managing users
     *
     * @see UserService
     */
    static UserService userService = new UserServiceImpl();

    @Override
    public boolean isHandling(Officer officer, int projectId) throws IOException, NullPointerException {
        return Objects.equals(projectId, officer.getProjectId()) && projectService.isActive(projectId);
    }

    /**
     * Check if the officer is able to register for a project.
     * 
     * @param officer
     *            the officer
     * @param projectId
     *            the project ID
     * @return true if the officer is able to register, false otherwise
     * @throws IOException
     *             if an I/O error occurs
     * @throws NullPointerException
     *             if the officer is null
     */
    @Override
    public boolean isRegistrable(Officer officer, int projectId) throws IOException, NullPointerException {
        boolean isAlreadyApplicant = officer.getApplicationId() != null && officer.getApplicationId() == projectId;

        boolean isAlreadyHandling = officer.getProjectId() != null && projectService.isActive(officer.getProjectId());

        return !isAlreadyApplicant && !isAlreadyHandling;
    }

    /**
     * Check if the officer is able to apply for a project. This is an extension from applicant.isAbleToAplly() since
     * the officer cannot apply for the project they are handling After this check, the officer will be treated as an
     * applicant for the next check
     * 
     * @param officer
     *            the officer
     * @return true if the officer is able to apply, false otherwise
     * @throws IOException
     *             if an I/O error occurs
     */
    @Override
    public boolean isAbleToApply(Officer officer) throws IOException {
        if (officer.getProjectId() != null && projectService.isActive(officer.getProjectId())) {
            return false;
        }
        return super.isAbleToApply(officer);
    }

    /**
     * Get all projects that the officer can register for.
     * 
     * @param officer
     *            the officer
     * @return a list of projects that the officer can register for
     * @throws IOException
     *             if an I/O error occurs
     * @throws NullPointerException
     *             if the officer is null
     */
    @Override
    public List<Project> getRegistrableProjects(Officer officer) throws IOException, NullPointerException {
        Collection<Project> allProjects = projectService
                .findByVisibilityAndOpenDateGreaterThanAndCloseDateLessThan(true, new Date());

        return allProjects.stream().filter(p -> {
            try {
                return isRegistrable(officer, p.getId());
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }).collect(Collectors.toList());
    }

    /**
     * Registers the officer for the specified project.
     *
     * @param officer
     *            the officer registering for the project
     * @param projectId
     *            the ID of the project
     * @return the updated officer after registration
     * @throws IOException
     *             if an I/O error occurs
     * @throws IllegalStateException
     *             if the officer is not eligible to register
     */
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

    /**
     * Retrieves the officer's current registration, even if the project is closed.
     *
     * @param officer
     *            the officer requesting to view registration
     * @return the current registration of the officer
     * @throws IOException
     *             if an I/O error occurs
     */
    @Override
    public Registration viewCurrentRegistration(Officer officer) throws IOException {
        Registration registration = registrationService.findById(officer.getRegistrationId());
        if (registration == null) {
            throw new IllegalArgumentException("Registration ID " + officer.getRegistrationId() + " not found.");
        }
        return registration;
    }

    /**
     * Retrieves the project that the officer is currently assigned to handle.
     *
     * @param officer
     *            the officer handling the project
     * @return the project being handled
     * @throws IOException
     *             if an I/O error occurs
     * @throws IllegalStateException
     *             if the officer is not assigned to any project
     */
    @Override
    public Project viewHandlingProject(Officer officer) throws IOException {
        if (officer.getProjectId() == null) {
            throw new IllegalStateException("Officer is not assigned to any project.");
        }
        return projectService.findById(officer.getProjectId());
    }

    /**
     * Retrieves all enquiries related to the project the officer is currently handling.
     *
     * @param officer
     *            the officer handling the project
     * @return a list of enquiries related to the project
     * @throws IOException
     *             if an I/O error occurs
     * @throws IllegalStateException
     *             if the officer is not assigned to any project
     */
    @Override
    public List<Enquiry> getHandlingEnquiries(Officer officer) throws IOException {
        if (officer.getProjectId() == null) {
            throw new IllegalStateException("Officer is not assigned to any project.");
        }
        return enquiryService.findByProjectId(officer.getProjectId());
    }

    /**
     * Replies to an enquiry made for the project the officer is handling.
     *
     * @param officer
     *            the officer replying to the enquiry
     * @param enquiryId
     *            the ID of the enquiry to reply to
     * @param reply
     *            the reply content
     * @return the updated enquiry after reply
     * @throws IOException
     *             if an I/O error occurs
     */
    @Override
    public Enquiry replyEnquiry(Officer officer, int enquiryId, String reply) throws IOException {
        return enquiryService.replyEnquiry(enquiryId, officer.getId(), reply);
    }

    /**
     * Retrieves all applications for the officer's assigned project that have been marked as SUCCESSFUL.
     *
     * @param officer
     *            the officer requesting the applications
     * @return a list of successful applications
     * @throws IOException
     *             if an I/O error occurs
     * @throws IllegalStateException
     *             if the officer is not assigned to any project
     */
    @Override
    public List<Application> getBookingApplications(Officer officer) throws IOException {
        if (officer.getProjectId() == null) {
            throw new IllegalStateException("Officer is not assigned to any project.");
        }

        return applicationService.findByProjectId(officer.getProjectId()).stream()
                .filter(application -> application.getStatus() == ApplicationStatus.SUCCESSFUL)
                .collect(Collectors.toList());
    }

    /**
     * Retrieves all applications for the officer's assigned project that have been marked as BOOKED.
     *
     * @param officer
     *            the officer requesting the applications
     * @return a list of booked applications
     * @throws IOException
     *             if an I/O error occurs
     * @throws IllegalStateException
     *             if the officer is not assigned to any project
     */
    @Override
    public List<Application> getBookedApplications(Officer officer) throws IOException {
        if (officer.getProjectId() == null) {
            throw new IllegalStateException("Officer is not assigned to any project.");
        }

        return applicationService.findByProjectId(officer.getProjectId()).stream()
                .filter(application -> application.getStatus() == ApplicationStatus.BOOKED)
                .collect(Collectors.toList());
    }

    /**
     * Books a flat for the specified applicant. The application must be in SUCCESSFUL status before booking.
     *
     * @param applicantId
     *            the ID of the applicant
     * @throws IOException
     *             if an I/O error occurs
     * @throws IllegalStateException
     *             if the applicant does not have a successful application
     * @throws NullPointerException
     *             if the applicant does not exist
     */
    @Override
    public void bookFlatForApplicant(int applicantId) throws IOException {
        Applicant applicant = (Applicant) userService.findById(applicantId);

        if (applicant == null) {
            throw new NullPointerException("NRIC does not belong to an applicant.");
        }

        Application application = applicationService.findById(applicant.getApplicationId());

        if (application == null || application.getStatus() != ApplicationStatus.SUCCESSFUL) {
            throw new IllegalStateException("Applicant has no successful application.");
        }

        projectService.decrementFlatCount(application.getProjectId(), application.getFlatType());
        application.setStatus(ApplicationStatus.BOOKED);
        applicationService.save(application);
        applicant.setFlatType(application.getFlatType());
        userService.save(applicant);
    }

    /**
     * Generates a booking receipt for the applicant who has successfully booked a flat.
     *
     * @param applicantId the ID of the applicant
     * @return a formatted booking receipt string
     * @throws IOException           if an I/O error occurs
     * @throws IllegalStateException if the applicant has not booked a flat
     * @throws NullPointerException  if the project cannot be found
     */
    @Override
    public String generateBookingReceipt(int applicantId) throws IOException {
        Applicant applicant = (Applicant) userService.findById(applicantId);

        Application app = applicationService.findById(applicant.getApplicationId());
        if (app == null || app.getStatus() != ApplicationStatus.BOOKED) {
            throw new IllegalStateException("Applicant has not booked a flat.");
        }

        Project project = projectService.findById(app.getProjectId());

        if (project == null) {
            throw new NullPointerException("Project not found.");
        }

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
