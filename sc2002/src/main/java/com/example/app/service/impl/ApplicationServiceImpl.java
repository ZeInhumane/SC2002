package com.example.app.service.impl;

import com.example.app.enums.ApplicationStatus;
import com.example.app.enums.FlatType;
import com.example.app.models.Application;
import com.example.app.repository.ApplicationRepository;
import com.example.app.repository.RepositoryDependency;
import com.example.app.service.ApplicationService;

import java.io.IOException;
import java.util.List;

/**
 * Implementation of the ApplicationService interface. This class handles the application-related operations.
 *
 * @see ApplicationService
 */
public class ApplicationServiceImpl implements ApplicationService {

    /**
     * Repository for managing applications.
     *
     * @see ApplicationRepository
     */
    private final ApplicationRepository applicationRepository = RepositoryDependency.getApplicationRepository();

    /**
     * Create new application of an applicant for a project
     * 
     * @param userId
     *            the ID of the applicant
     * @param projectId
     *            the ID of the project
     * @param flatType
     *            the type of flat
     * @return the new application object
     * @throws IOException
     *             if an I/O error occurs
     */
    @Override
    public Application applyForProject(int userId, int projectId, FlatType flatType) throws IOException {
        Application apply = new Application(null, userId, projectId, ApplicationStatus.PENDING, false, flatType);

        return applicationRepository.save(apply);
    }

    /**
     * Find application by ID
     * 
     * @param id
     *            the ID of the application
     * @return the application object
     * @throws IOException
     *             if an I/O error occurs
     */
    @Override
    public Application findById(int id) throws IOException {
        return applicationRepository.findById(id);
    }

    /**
     * Get all applications relating to a project Should be used by officer and manager only
     * 
     * @param projectId
     *            the ID of the project
     * @return the list of applications
     * @throws IOException
     *             if an I/O error occurs
     */
    @Override
    public List<Application> findByProjectId(int projectId) throws IOException {
        return applicationRepository.findByProjectId(projectId);
    }

    /**
     * Update the status of an application
     * 
     * @param id
     *            the ID of the application
     * @param status
     *            the new status of the application
     * @return the updated application object
     * @throws IOException
     *             if an I/O error occurs
     * @throws NullPointerException
     *             if the application is null
     */
    @Override
    public Application updateStatus(int id, ApplicationStatus status) throws IOException, NullPointerException {
        Application application = applicationRepository.findById(id);
        application.setStatus(status);
        return applicationRepository.save(application);
    }

    /**
     * Withdraw an application
     * 
     * @param id
     *            the ID of the application
     * @return the updated application object
     * @throws IOException
     *             if an I/O error occurs
     */
    @Override
    public Application withdrawApplication(int id) throws IOException {
        Application application = applicationRepository.findById(id);
        application.setRequestWithdrawal(true);
        return applicationRepository.save(application);
    }

    /**
     * Update the withdrawal status of an application
     * 
     * @param id
     *            the ID of the application
     * @param status
     *            the new withdrawal status
     * @return the updated application object
     * @throws IOException
     *             if an I/O error occurs
     */
    @Override
    public Application updateWithdrawalStatus(int id, boolean status) throws IOException {
        Application application = applicationRepository.findById(id);
        application.setRequestWithdrawal(false);
        if (status) {
            application.setStatus(ApplicationStatus.WITHDRAWN);
        }
        return applicationRepository.save(application);
    }

    /**
     * Get all applications of an applicant
     * 
     * @param application
     *            the application object to save
     * @return the saved application object
     * @throws IOException
     *             if an I/O error occurs
     */
    @Override
    public List<Application> getAllBookedApplications() throws IOException {
        return applicationRepository.findByStatus(ApplicationStatus.BOOKED);
    }

    @Override
    public Application save(Application application) throws IOException {
        return applicationRepository.save(application);
    }

}
