package com.example.app.service;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import com.example.app.models.Application;
import com.example.app.models.ApplicationStatus;
import com.example.app.models.User;
import com.example.app.repository.ApplicationRepository;

public class ApplicationService extends GeneralService{
    private ApplicationRepository applicationRepository = new ApplicationRepository();
    
    public ApplicationService(User user) {
        super(user); 
    }

    // Apply for a project using id for that user
    // Return application Id for user to save
    public int applyForProject(int projectId) {
        Application apply = new Application(
            user.getId(), projectId, ApplicationStatus.PENDING
        );

        Application application = applicationRepository.save(apply);
        return application.getId();
    } 

    // Get Applied project by user Id
    public int getAppliedProjectId() {
        Application userApplication =  applicationRepository.findOneByUserId(user.getId());
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


    // Get all applications relating to a project 
    // Meant for officer and manager
    public List<Application> getApplicationsByProjectId(int projectId) {
        return applicationRepository.findByProjectId(projectId);
    }

    // Get application by application Id
    public Application getApplicationById(int id) {
        return applicationRepository.findById(id);
    }


}

