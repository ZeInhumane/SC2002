package com.example.app.service.impl;

import com.example.app.enums.ApplicationStatus;
import com.example.app.enums.FlatType;
import com.example.app.enums.MaritalStatus;
import com.example.app.models.Applicant;
import com.example.app.models.Application;
import com.example.app.models.Enquiry;
import com.example.app.models.Project;
import com.example.app.service.ApplicantService;
import com.example.app.service.ApplicationService;
import com.example.app.service.EnquiryService;
import com.example.app.service.ProjectService;

import java.io.IOException;
import java.util.*;

/**
 * Service implementation for applicant service The ApplicantServiceImpl possesses methods from the UserServiceImpl and
 * implements the ApplicantService interface.
 *
 * @see ApplicantService
 * @see UserServiceImpl
 */
public class ApplicantServiceImpl extends UserServiceImpl implements ApplicantService {

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
     * EnquiryService instance for managing enquiries.
     * 
     * @see EnquiryService
     */
    static EnquiryService enquiryService = new EnquiryServiceImpl();

    /**
     * Get all projects that the applicant can view.
     * 
     * @param applicant
     *            the applicant
     * @return a list of projects that the applicant can view
     * @throws IOException
     *             if an I/O error occurs
     * @throws NullPointerException
     *             if the applicant is null
     */
    @Override
    public List<Project> getViewableProjects(Applicant applicant) throws IOException, NullPointerException {
        return projectService.getPublicProjects(applicant.getMaritalStatus(), true, new Date());
    }

    /**
     * Get all projects that the applicant has applied for.
     * 
     * @param applicant
     *            the applicant
     * @return the project that the applicant has applied or currently applying
     * @throws IOException
     *             if an I/O error occurs
     * @throws NullPointerException
     *             if the applicant is null
     */
    @Override
    public Project viewAppliedProjects(Applicant applicant) throws IOException, NullPointerException {
        Application application = applicationService.findById(applicant.getApplicationId());
        if (application == null) {
            return null;
        }
        return projectService.findById(application.getProjectId());
    }

    /**
     * The applicant is only able to apply if they have no application yet or the last one failed
     * 
     * @param applicant
     *            the applicant
     * @return true if the applicant is able to apply, false otherwise
     * @throws IOException
     *             if an I/O error occurs
     * @throws NullPointerException
     *             if the applicant is null
     */
    @Override
    public boolean isAbleToApply(Applicant applicant) throws IOException, NullPointerException {
        Application application = viewApplication(applicant);
        return application == null || application.getStatus() == ApplicationStatus.UNSUCCESSFUL
                || application.getStatus() == ApplicationStatus.WITHDRAWN;
    }

    /**
     * Apply an applicant for a project
     * 
     * @param applicant
     *            the applicant
     * @param projectId
     *            the project ID
     * @param preferredFlatType
     *            the preferred flat type
     * @return the application object
     * @throws IOException
     *             if an I/O error occurs
     * @throws NullPointerException
     *             if the applicant is null
     */
    @Override
    public Application applyForProject(Applicant applicant, int projectId, FlatType preferredFlatType)
            throws IOException, NullPointerException {
        Project project = projectService.findById(projectId);
        if (project == null) {
            throw new IllegalArgumentException("Project with ID " + projectId + " does not exist.");
        }

        if (!project.getFlats().containsKey(preferredFlatType)) {
            throw new IllegalArgumentException("Selected flat type is not offered in the chosen project.");
        }

        List<FlatType> eligibleTypes = getEligibleFlatTypesForProject(applicant, projectId);
        if (!eligibleTypes.contains(preferredFlatType)) {
            throw new IllegalArgumentException("You are not eligible for the selected flat type.");
        }

        Application application = applicationService.applyForProject(applicant.getId(), projectId, preferredFlatType);
        applicant.setApplicationId(application.getId());
        applicant.setFlatType(preferredFlatType);
        this.save(applicant);
        return application;
    }

    /**
     * View the current applied project of the applicant, regardless of project status.
     * 
     * @param applicant
     *            the applicant
     * @return the project object
     * @throws IOException
     *             if an I/O error occurs
     * @throws NullPointerException
     *             if the applicant is null
     */
    @Override
    public Project viewAppliedProject(Applicant applicant) throws IOException, NullPointerException {
        if (applicant.getApplicationId() == null) {
            return null;
        }
        Application application = applicationService.findById(applicant.getApplicationId());
        if (application == null) {
            return null;
        }

        return projectService.findById(application.getProjectId());
    }

    /**
     * View the current application of the applicant.
     * 
     * @param applicant
     *            the applicant
     * @return the application object
     * @throws IOException
     *             if an I/O error occurs
     * @throws NullPointerException
     *             if the applicant is null
     */
    @Override
    public Application viewApplication(Applicant applicant) throws IOException, NullPointerException {
        if (applicant.getApplicationId() == null)
            return null;
        return applicationService.findById(applicant.getApplicationId());
    }

    /**
     * Withdraw the current application of the applicant.
     * 
     * @param applicant
     *            the applicant
     * @return the application object after modifying
     * @throws IOException
     *             if an I/O error occurs
     * @throws NullPointerException
     *             if the applicant is null
     */
    @Override
    public Application withdrawApplication(Applicant applicant) throws IOException, NullPointerException {
        Application application = viewApplication(applicant);
        if (application == null) {
            throw new IllegalArgumentException("No application found to withdraw.");
        }
        if (application.isRequestWithdrawal()) {
            throw new IllegalArgumentException("Application withdrawal has already been requested.");
        }

        application.setRequestWithdrawal(true);
        return applicationService.save(application);
    }

    // Submit enquiry (stores ID back to user profile)

    /**
     * @param applicant
     *            the applicant
     * @param question
     *            the message to be sent
     * @param projectId
     *            the project ID
     * @return the enquiry object after being submitted
     * @throws IOException
     *             if an I/O error occurs
     * @throws NullPointerException
     *             if the applicant is null
     */
    @Override
    public Enquiry submitEnquiry(Applicant applicant, String question, int projectId)
            throws IOException, NullPointerException {
        return enquiryService.submitEnquiry(question, projectId, applicant.getId());
    }

    /**
     * Get all enquiries made by the applicant.
     * 
     * @param applicant
     *            the applicant
     * @return a list of enquiries made by the applicant
     * @throws IOException
     *             if an I/O error occurs
     * @throws NullPointerException
     *             if the applicant is null
     */
    @Override
    public List<Enquiry> getOwnEnquiries(Applicant applicant) throws IOException, NullPointerException {
        return enquiryService.findByEnquirerId(applicant.getId());
    }

    /**
     * Update an enquiry (update enquiry question only)
     * 
     * @param applicant
     *            the applicant
     * @param enquiryId
     *            the enquiry ID
     * @param newQuestion
     *            the new message
     * @return the updated enquiry object
     * @throws IOException
     *             if an I/O error occurs
     * @throws NullPointerException
     *             if the applicant is null
     */
    @Override
    public Enquiry updateEnquiry(Applicant applicant, int enquiryId, String newQuestion)
            throws IOException, NullPointerException {
        Enquiry enquiry = enquiryService.findById(enquiryId);

        if (enquiry == null || !Objects.equals(enquiry.getEnquirerId(), applicant.getId())
                || enquiry.getResponse() != null) {
            throw new IllegalArgumentException("You do not have permission to edit this enquiry.");
        }

        return enquiryService.updateEnquiryQuestion(enquiryId, newQuestion);
    }

    /**
     * Delete an enquiry.
     * 
     * @param applicant
     *            the applicant
     * @param enquiryId
     *            the enquiry ID
     * @throws IOException
     *             if an I/O error occurs
     * @throws NullPointerException
     *             if the applicant is null
     */
    @Override
    public void deleteEnquiry(Applicant applicant, int enquiryId) throws IOException, NullPointerException {

        Enquiry enquiry = enquiryService.findById(enquiryId);

        if (enquiry == null || !Objects.equals(enquiry.getEnquirerId(), applicant.getId())) {
            throw new IllegalArgumentException("You do not have permission to delete this enquiry.");
        }

        enquiryService.deleteEnquiry(enquiryId);

    }

    /**
     * Get all enquiries for a specific project.
     * 
     * @param applicant
     *            the applicant
     * @param projectId
     *            the project ID
     * @return a list of enquiries for the project
     * @throws IOException
     *             if an I/O error occurs
     * @throws NullPointerException
     *             if the applicant is null
     */
    @Override
    public List<FlatType> getEligibleFlatTypesForProject(Applicant applicant, int projectId)
            throws IOException, NullPointerException {
        Project project = projectService.findById(projectId);
        if (project == null) {
            throw new IllegalArgumentException("Project with ID " + projectId + " not found.");
        }

        List<FlatType> eligible = getEligibleFlatTypes(applicant.getMaritalStatus(), applicant.getAge());

        eligible.removeIf(flatType -> !project.getFlats().containsKey(flatType));
        return eligible;
    }

    /**
     * Get all eligible flat types for the applicant based on marital status and age.
     * 
     * @param userStatus
     *            the marital status of the user
     * @param userAge
     *            the age of the user
     * @return a list of eligible flat types
     * @throws IOException
     *             if an I/O error occurs
     * @throws NullPointerException
     *             if the user status is null
     */
    @Override
    public List<FlatType> getEligibleFlatTypes(MaritalStatus userStatus, int userAge) throws NullPointerException {
        List<FlatType> eligible = new ArrayList<>();
        if (userStatus == MaritalStatus.SINGLE && userAge >= 35) {
            eligible.add(FlatType._2ROOM);
        } else if (userAge >= 21) { // modified to remove MARRIED check
            eligible.add(FlatType._2ROOM);
            eligible.add(FlatType._3ROOM);
        }

        return eligible;
    }
}
