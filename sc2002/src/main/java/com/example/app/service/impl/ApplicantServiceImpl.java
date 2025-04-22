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

public class ApplicantServiceImpl extends UserServiceImpl implements ApplicantService {
    static ProjectService projectService = new ProjectServiceImpl();
    static ApplicationService applicationService = new ApplicationServiceImpl();
    static EnquiryService enquiryService = new EnquiryServiceImpl();

    public List<Project> getViewableProjects(Applicant applicant) throws IOException, NullPointerException {
        return projectService.getPublicProjects(applicant.getMaritalStatus(), true, new Date());
    }

    public Project viewAppliedProjects(Applicant applicant) throws IOException, NullPointerException {
        Application application = applicationService.findById(applicant.getApplicationId());
        if (application == null) {
            return null;
        }
        return projectService.findById(application.getProjectId());
    }

    public Application applyForProject(Applicant applicant, int projectId, FlatType preferredFlatType) throws IOException, NullPointerException {
        Project project = projectService.findById(projectId);
        if (project == null) {
            throw new IllegalArgumentException("Project with ID " + projectId + " does not exist.");
        }

        if (!project.getFlats().containsKey(preferredFlatType)) {
            throw new IllegalArgumentException("Selected flat type is not offered in the chosen project.");
        }

        List<FlatType> eligibleTypes = getEligibleFlatTypesForProject(applicant, projectId);
        System.err.println("Eligible types: " + eligibleTypes);
        System.err.println(preferredFlatType);
        if (!eligibleTypes.contains(preferredFlatType)) {
            throw new IllegalArgumentException("You are not eligible for the selected flat type.");
        }

        if (!isAbleToApply(applicant)) {
            throw new IllegalArgumentException("You are not eligible to apply for this project.");
        }
        Application application = applicationService.applyForProject(applicant.getId(), projectId, preferredFlatType);
        applicant.setApplicationId(application.getId());
        applicant.setFlatType(preferredFlatType);
        this.save(applicant);
        return application;
    }

    public Project viewCurrentProject(Applicant applicant) throws IOException, NullPointerException{
        if (applicant.getApplicationId() == null) {
            return null;
        }
        Application application = applicationService.findById(applicant.getApplicationId());
        if (application == null) {
            return null;
        }

        return projectService.findById(application.getProjectId());
    }

    // Get current application safely
    public Application viewCurrentApplication(Applicant applicant) throws IOException, NullPointerException {
        if (applicant.getApplicationId() == null)
            return null;
        return applicationService.findById(applicant.getApplicationId());
    }

    // Can only apply if no app exists or the last one failed
    public boolean isAbleToApply(Applicant applicant) throws IOException, NullPointerException {
        Application application = viewCurrentApplication(applicant);
        return application == null || application.getStatus() == ApplicationStatus.UNSUCCESSFUL || application.getStatus() == ApplicationStatus.WITHDRAWN;
    }

    public Application withdrawApplication(Applicant applicant) throws IOException, NullPointerException {
        Application application = viewCurrentApplication(applicant);
        if (application == null) {
            throw new IllegalArgumentException("No application found to withdraw.");
        }

        application.setRequestWithdrawal(true);
        return applicationService.save(application);
    }

    // Submit enquiry (stores ID back to user profile)
    public Enquiry submitEnquiry(Applicant applicant, String question, int projectId) throws IOException, NullPointerException {
        return enquiryService.submitEnquiry(question, projectId, applicant.getId());
    }

    // get all enquiries made by the applicant
    public List<Enquiry> getAllEnquiries(Applicant applicant) throws IOException, NullPointerException {
        return enquiryService.findByEnquirerId(applicant.getId());
    }

    // Edit enquiry (if it belongs to this user)
    public Enquiry updateEnquiry(Applicant applicant, int enquiryId, String newQuestion) throws IOException, NullPointerException {
        Enquiry enquiry = enquiryService.findById(enquiryId);

        if (enquiry == null || !Objects.equals(enquiry.getEnquirerId(), applicant.getId()) || !enquiry.getResponse().isEmpty()) {
            throw new IllegalArgumentException("You do not have permission to edit this enquiry.");
        }

        return enquiryService.updateEnquiryQuestion(enquiryId, newQuestion);
    }

    // Delete enquiry (if it belongs to this user)
    public void deleteEnquiry(Applicant applicant, int enquiryId) throws IOException, NullPointerException {

        Enquiry enquiry = enquiryService.findById(enquiryId);

        if (enquiry == null || !Objects.equals(enquiry.getEnquirerId(), applicant.getId())) {
            throw new IllegalArgumentException("You do not have permission to delete this enquiry.");
        }

        enquiryService.deleteEnquiry(enquiryId);

    }


    public List<FlatType> getEligibleFlatTypesForProject(Applicant applicant, int projectId) throws IOException, NullPointerException {
        Project project = projectService.findById(projectId);
        if (project == null) {
            throw new IllegalArgumentException("Project with ID " + projectId + " not found.");
        }

        List<FlatType> eligible = getEligibleFlatTypes(applicant.getMaritalStatus(), applicant.getAge());

        eligible.removeIf(flatType -> !project.getFlats().containsKey(flatType));
        return eligible;
    }

    public List<FlatType> getEligibleFlatTypes(MaritalStatus userStatus, int userAge) {
        List<FlatType> eligible = new ArrayList<>();
        if (userStatus == MaritalStatus.SINGLE && userAge >= 35) {
            eligible.add(FlatType._2ROOM);
        } else if (userStatus == MaritalStatus.MARRIED && userAge >= 21) {
            eligible.add(FlatType._2ROOM);
            eligible.add(FlatType._3ROOM);
        }

        return eligible;
    }
}
