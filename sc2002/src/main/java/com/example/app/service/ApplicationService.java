package com.example.app.service;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import com.example.app.enums.FlatType;
import com.example.app.models.Application;
import com.example.app.enums.ApplicationStatus;
import com.example.app.repository.ApplicationRepository;

public class ApplicationService {
    private static ApplicationRepository applicationRepository = new ApplicationRepository();

    public ApplicationService() {

    }

    // Apply for a project using id for that user
    // Return application Id for user to save
    public Application applyForProject(Integer userId, Integer projectId, FlatType flatType) throws IOException {
        Application apply = new Application(
            null, userId, projectId, ApplicationStatus.PENDING, false, flatType
        );

        return applicationRepository.save(apply);
    }

    // Get Applied project by user Id
    // Can include past applications
    public List<Application> findByUserId(int userId) throws IOException {
        return applicationRepository.findByUserId(userId);
    }

    // Get applied project by user Id and status
    public List<Application> findByUserIdAndStatus(int userId) throws IOException {
        return applicationRepository.findByUserId(userId);
    }

    public List<Application> getRequestWithdrawalByProjectId(int projectId) throws IOException {
        return applicationRepository.findByProjectIdAndRequestWithdrawal(projectId, true);
    }


    public Application findById(int id) throws IOException {
        return applicationRepository.findById(id);
    }


    // Change status by finding application 
    // Used by officer
    public void updateStatus(int id, ApplicationStatus status) throws IOException, NullPointerException {
        Application application = applicationRepository.findById(id);
        application.setStatus(status);
    }


    // Get all applications relating to a project
    // Meant for officer and manager
    public List<Application> findByProjectId(int projectId) throws IOException {
        return applicationRepository.findByProjectId(projectId);
    }


    // Meant for officer to check if a user is an applicant for a specific project
    public boolean isCurrentlyApplyFor(int userId, int projectId) throws IOException {
        List<Application> applications = applicationRepository.findByUserIdAndStatus(userId, ApplicationStatus.PENDING);
        return applications.stream().anyMatch(app -> app.getProjectId() == projectId);
    }

}

