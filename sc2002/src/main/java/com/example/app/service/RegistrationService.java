package com.example.app.service;

import java.io.IOException;
import java.util.List;

import com.example.app.models.Registration;
import com.example.app.enums.RegistrationStatus;

/**
 * Service interface for managing registrations.
 */
public interface RegistrationService {

    /**
     * Register an officer for a project.
     * 
     * @param userId
     *            the ID of the officer
     * @param projectId
     *            the ID of the project
     * @return the registration object
     * @throws IOException
     *             if an I/O error occurs
     */
    Registration registerForProject(int userId, int projectId) throws IOException;

    /**
     * Update the status of a registration.
     * 
     * @param registerId
     *            the ID of the registration
     * @param status
     *            the new status of the registration
     * @return the updated registration object
     * @throws IOException
     *             if an I/O error occurs
     */
    Registration changeStatus(int registerId, RegistrationStatus status) throws IOException;

    /**
     * Get all registrations for a specific project.
     * 
     * @param id
     *            the ID of the project
     * @return a list of registrations for the project
     * @throws IOException
     *             if an I/O error occurs
     */
    Registration findById(int id) throws IOException;

    /**
     * Get all registrations for a specific project.
     * 
     * @param projectId
     *            the ID of the project
     * @return a list of registrations for the project
     * @throws IOException
     *             if an I/O error occurs
     */
    List<Registration> findByProjectId(int projectId) throws IOException;

    /**
     * Delete a registration by its ID.
     * 
     * @param id
     *            the ID of the registration
     * @throws IOException
     *             if an I/O error occurs
     */
    void deleteById(int id) throws IOException;

}
