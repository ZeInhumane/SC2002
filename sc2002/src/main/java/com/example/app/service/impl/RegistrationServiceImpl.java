package com.example.app.service.impl;

import com.example.app.enums.RegistrationStatus;
import com.example.app.models.Registration;
import com.example.app.repository.RegistrationRepository;
import com.example.app.repository.RepositoryDependency;
import com.example.app.service.RegistrationService;

import java.io.IOException;
import java.util.List;

/**
 * RegistrationServiceImpl provides implementation for managing project registration by users. It supports operations
 * such as registering for a project, changing registration status, retrieving registrations by ID, project, or user,
 * and deleting registrations.
 *
 * @see RegistrationService
 */
public class RegistrationServiceImpl implements RegistrationService {

    /**
     * Repository for accessing and managing registration data.
     */
    private final RegistrationRepository registrationRepository = RepositoryDependency.getRegistrationRepository();

    /**
     * Registers a user for a given project. This method is typically used when an applicant registers to become an
     * officer.
     *
     * @param userId
     *            the ID of the user applying
     * @param projectId
     *            the ID of the project to register for
     * @return the saved Registration object with PENDING status
     * @throws IOException
     *             if an I/O error occurs
     */
    @Override
    public Registration registerForProject(int userId, int projectId) throws IOException {
        Registration register = new Registration(null, userId, projectId, RegistrationStatus.PENDING);
        return registrationRepository.save(register);
    }

    /**
     * Retrieves a registration by its ID. This can be used by officers to view their own registration.
     *
     * @param id
     *            the registration ID
     * @return the corresponding Registration object
     * @throws IOException
     *             if an I/O error occurs
     */
    @Override
    public Registration findById(int id) throws IOException {
        return registrationRepository.findById(id);
    }

    /**
     * Retrieves all registrations associated with a specific project.
     *
     * @param projectId
     *            the project ID
     * @return a list of registrations for the given project
     * @throws IOException
     *             if an I/O error occurs
     */
    @Override
    public List<Registration> findByProjectId(int projectId) throws IOException {
        return registrationRepository.findByProjectId(projectId);
    }

    /**
     * Deletes a registration by its ID.
     *
     * @param id
     *            the registration ID to delete
     * @throws IOException
     *             if an I/O error occurs
     */
    @Override
    public void deleteById(int id) throws IOException {
        registrationRepository.deleteById(id);
    }

    /**
     * Changes the status of a registration (e.g., from PENDING to APPROVED or REJECTED). Typically used by managers to
     * approve officer registrations.
     *
     * @param registerId
     *            the ID of the registration to update
     * @param status
     *            the new status to assign
     * @return the updated Registration object
     * @throws IOException
     *             if an I/O error occurs
     */
    @Override
    public Registration changeStatus(int registerId, RegistrationStatus status) throws IOException {
        Registration registration = registrationRepository.findById(registerId);
        registration.setStatus(status);
        return registrationRepository.save(registration);
    }
}