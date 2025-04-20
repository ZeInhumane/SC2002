package com.example.app.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import com.example.app.models.*;
import com.example.app.enums.ApplicationStatus;
import com.example.app.enums.FlatType;
import com.example.app.enums.MaritalStatus;

public class ApplicantService extends UserService{

    static ProjectService projectService = new ProjectService();
    static ApplicationService applicationService = new ApplicationService();
    static EnquiryService enquiryService = new EnquiryService();

    public Collection<Project> viewPublicProjects(Applicant applicant) throws IOException, NullPointerException {
        return projectService.findByMaritalStatusAndVisibility(applicant.getMaritalStatus(), true);
    }

    public Project viewAppliedProjects(Applicant applicant) throws IOException, NullPointerException {

        Application application = applicationService.findById(applicant.getApplicationId());
        if (application == null) {
            return null;
        }
        return projectService.findById(application.getProjectId());
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

    public void applyForProject(Applicant applicant, int projectId, FlatType preferredFlatType) throws IOException, NullPointerException {
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

        if (!isAbleToApply(applicant)) {
            throw new IllegalArgumentException("You are not eligible to apply for a new project.");
        }

        Application application = applicationService.applyForProject(applicant.getId(), projectId, preferredFlatType);
        applicant.setApplicationId(application.getId());
        applicant.setFlatType(preferredFlatType);
        this.save(applicant);
    }

    // Get current application safely
    public Application viewCurrentApplication(Applicant applicant) throws IOException, NullPointerException {
        if (applicant.getApplicationId() == null)
            return null;
        return applicationService.findById(applicant.getApplicationId());
    }

    // Can only apply if no app exists or the last one failed
    public boolean isAbleToApply(Applicant applicant) throws IOException, NullPointerException {
        Application current = viewCurrentApplication(applicant);
        return current == null || current.getStatus() == ApplicationStatus.UNSUCCESSFUL || current.getStatus() == ApplicationStatus.WITHDRAWN;
    }

    public void withdrawApplication(Applicant applicant) throws IOException, NullPointerException {
        Application application = viewCurrentApplication(applicant);
        if (application == null) {
            throw new IllegalArgumentException("No application found to withdraw.");
        }

        application.setStatus(ApplicationStatus.WITHDRAWN);
        applicationService.updateStatus(application.getId(), ApplicationStatus.WITHDRAWN);
        applicant.setApplicationId(null);
        this.save(applicant);
    }

    // Submit enquiry (stores ID back to user profile)
    public Enquiry submitEnquiry(Applicant applicant, String question, int projectId) throws IOException, NullPointerException {

        return enquiryService.submitEnquiry(question, projectId, applicant.getId());
    }

    // View one enquiry
    public List<Enquiry> getAllPastEnquiries(Applicant applicant) throws IOException, NullPointerException {
        return enquiryService.getEnquiriesByUserId(applicant.getId());
    }

    // Edit enquiry (if it belongs to this user)
    public void updateEnquiry(int enquiryId, String newQuestion) throws IOException, NullPointerException {

        Enquiry enquiry = enquiryService.getEnquiry(enquiryId);

        if (enquiry == null || !Objects.equals(enquiry.getEnquirerId(), user.getId())) {
            throw new IllegalArgumentException("You do not have permission to edit this enquiry.");
        }

        enquiryService.editEnquiryQuestion(enquiryId, newQuestion);
    }

    // Delete enquiry (if it belongs to this user)
    public void deleteEnquiry(Applicant applicant, int enquiryId) throws IOException, NullPointerException {

        Enquiry enquiry = enquiryService.getEnquiry(enquiryId);

        if (enquiry == null || !Objects.equals(enquiry.getEnquirerId(), applicant.getId())) {
            throw new IllegalArgumentException("You do not have permission to delete this enquiry.");
        }

        enquiryService.deleteEnquiry(enquiryId);

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
