package com.example.app.service.impl;

import com.example.app.enums.ApplicationStatus;
import com.example.app.enums.FlatType;
import com.example.app.models.Applicant;
import com.example.app.models.Application;
import com.example.app.repository.ApplicationRepository;
import com.example.app.repository.RepositoryDependency;
import com.example.app.repository.UserRepository;
import com.example.app.service.ApplicationService;

import java.io.IOException;
import java.util.List;

public class ApplicationServiceImpl implements ApplicationService {

    private final ApplicationRepository applicationRepository = RepositoryDependency.getApplicationRepository();

    // Apply for a project using id for that user
    // Return application Id for user to save
    @Override
    public Application applyForProject(int userId, int projectId, FlatType flatType) throws IOException {
        Application apply = new Application(null, userId, projectId, ApplicationStatus.PENDING, false, flatType);

        return applicationRepository.save(apply);
    }

    @Override
    public Application findById(int id) throws IOException {
        return applicationRepository.findById(id);
    }

    // Get all applications relating to a project
    // Meant for officer and manager
    @Override
    public List<Application> findByProjectId(int projectId) throws IOException {
        return applicationRepository.findByProjectId(projectId);
    }

    // Change status by finding application
    // Used by officer
    @Override
    public Application updateStatus(int id, ApplicationStatus status) throws IOException, NullPointerException {
        Application application = applicationRepository.findById(id);
        application.setStatus(status);
        return applicationRepository.save(application);
    }

    @Override
    public Application withdrawApplication(int id) throws IOException {
        Application application = applicationRepository.findById(id);
        application.setRequestWithdrawal(true);
        return applicationRepository.save(application);
    }

    @Override
    public Application updateWithdrawalStatus(int id, boolean status) throws IOException {
        Application application = applicationRepository.findById(id);
        application.setRequestWithdrawal(false);
        if (status) {
            application.setStatus(ApplicationStatus.WITHDRAWN);
        }
        return applicationRepository.save(application);
    }

    @Override
    public Application save(Application application) throws IOException {
        return applicationRepository.save(application);
    }

}
