package com.example.app.service;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import com.example.app.models.Application;
import com.example.app.enums.ApplicationStatus;
import com.example.app.repository.ApplicationRepository;

public class ApplicationService {
    private static ApplicationRepository applicationRepository = new ApplicationRepository();

    public ApplicationService() {

    }

    // Apply for a project using id for that user
    // Return application Id for user to save
    public int applyForProject(int userId ,int projectId, String projectName) {
        Application apply = new Application(
            userId, projectId, ApplicationStatus.PENDING, projectName
        );

        Application application = applicationRepository.save(apply);
        return application.getId();
    } 

    // Get Applied project by user Id
    public int getAppliedProjectId(int userId) {
        Application userApplication =  applicationRepository.findOneByUserId(userId);
        return userApplication.getProjectId();
    } 

    // Get available statuses
    // Meant to give options to change
    // ["PENDING", "BOOKED", "SUCCESSFUL", "UNSUCCESSFUL"]
    public List<String> getPossibleStatuses() {
        return Arrays.stream(ApplicationStatus.values())
                    .map(Enum::name)
                    .collect(Collectors.toList());
    }

    // Change status by finding application 
    // Used by officer
    public void changeStatus(int id, ApplicationStatus status) {
        Application application = applicationRepository.findById(id);
        application.setStatus(status);
    }

    // Withdraw Application 
    // Can be used as a withdraw from BTO as application is always there
    public void deleteApplication(int id) {
        applicationRepository.deleteById(id);
    }

    // Delete all applications related to a project
    public void deleteApplicationsByProjectId(int projectId) {
        List<Application> applications = applicationRepository.findByProjectId(projectId);
        for (Application application : applications) {
            applicationRepository.deleteById(application.getId());
        }
    }


    // Get all applications relating to a project 
    // Meant for officer and manager
    public List<Application> getApplicationsByProjectId(int projectId) {
        return applicationRepository.findByProjectId(projectId);
    }

    // Get application by application Id
    public Application getApplicationById(int id) {
        return applicationRepository.findById(id);
    }

    // Meant for officer to check if a user is an applicant for a specific project
    public boolean isApplicantFor(int userId, int projectId) { 
        List<Application> applications = applicationRepository.findByProjectId(projectId);
        return applications.stream().anyMatch(app -> app.getUserId() == userId);
    }

    

}

