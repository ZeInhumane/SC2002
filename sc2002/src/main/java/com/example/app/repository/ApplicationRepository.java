package com.example.app.repository;

import java.io.IOException;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import com.example.app.models.Application;
import com.example.app.enums.ApplicationStatus;
import com.example.app.serializer.SerializerDependency;

/**
 * Repository for managing Application objects.
 * This class extends the GeneralRepository class to provide CRUD operations for Application objects.
 *
 * @see GeneralRepository
 * @see Application
 */
public class ApplicationRepository extends GeneralRepository<Application> {

    /**
     * Constructor for ApplicationRepository.
     * Initializes the repository with the Application serializer and the file name.
     *
     * @see SerializerDependency
     * @see GeneralRepository
     */
    public ApplicationRepository() {
        super(SerializerDependency.getApplicationSerializer(), "applications.txt");
    }

    /**
     * Finds all applications by the user ID.
     * @param userId the ID of the user
     * @return a list of applications made by the user
     * @throws IOException if there is an error reading the file
     */
    public List<Application> findByUserId(Integer userId) throws IOException {
        return this.findAll().stream().filter(a -> Objects.equals(a.getUserId(), userId)).collect(Collectors.toList());
    }

    /**
     * Finds all applications by the user ID and status.
     * @param userId the ID of the user
     * @param status the status of the application
     * @return a list of applications made by the user with the specified status
     * @throws IOException if there is an error reading the file
     */
    public List<Application> findByUserIdAndStatus(Integer userId, ApplicationStatus status) throws IOException {
        return this.findAll().stream()
                .filter(a -> Objects.equals(a.getUserId(), userId) && Objects.equals(a.getStatus(), status))
                .collect(Collectors.toList());
    }

    /**
     * Finds all applications by the project ID.
     * @param projectId the ID of the project
     * @return a list of applications related to the project
     * @throws IOException if there is an error reading the file
     */
    public List<Application> findByProjectId(Integer projectId) throws IOException {
        return this.findAll().stream().filter(a -> Objects.equals(a.getProjectId(), projectId))
                .collect(Collectors.toList());
    }

    /**
     * Finds all applications by the project ID and withdrawal status.
     * @param projectId the ID of the project
     * @param requestWithdrawal the withdrawal status of the application
     * @return a list of applications related to the project with the specified withdrawal status
     * @throws IOException if there is an error reading the file
     */
    public List<Application> findByProjectIdAndRequestWithdrawal(Integer projectId, boolean requestWithdrawal)
            throws IOException {
        return this.findAll().stream().filter(a -> Objects.equals(a.getProjectId(), projectId)
                && Objects.equals(a.isRequestWithdrawal(), requestWithdrawal)).collect(Collectors.toList());
    }

    /**
     * Finds all applications by the status.
     * @param status the status of the application
     * @return a list of applications with the specified status
     * @throws IOException if there is an error reading the file
     */
    public List<Application> findByStatus(ApplicationStatus status) throws IOException {
        return this.findAll().stream().filter(a -> Objects.equals(a.getStatus(), status)).collect(Collectors.toList());
    }
}
