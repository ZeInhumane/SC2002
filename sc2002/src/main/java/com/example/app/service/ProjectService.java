package com.example.app.service;

import java.io.IOException;
import java.util.*;

import com.example.app.enums.FlatType;
import com.example.app.enums.MaritalStatus;
import com.example.app.models.Project;

/**
 * Service interface for managing projects.
 * 
 * This interface defines methods for creating, updating, deleting, and retrieving
 * projects. It also includes methods for checking project status and managing
 * project officers.
 */
public interface ProjectService {

    /**
     * Get a project by its ID.
     * 
     * @param id
     *            the ID of the project
     * @return the project object
     * @throws IOException
     *             if an I/O error occurs
     */
    Project findById(int id) throws IOException;

    /**
     * Get all projects for a specific manager.
     * 
     * @param managerId
     *            the ID of the manager
     * @return a list of projects for the manager
     * @throws IOException
     *             if an I/O error occurs
     */
    List<Project> findByManagerId(int managerId) throws IOException;

    /**
     * Get all projects for a specific manager that are active.
     * 
     * @param managerId
     *            the ID of the manager
     * @return the project object
     * @throws IOException
     *             if an I/O error occurs
     */
    Project findByManagerIdAndIsActive(int managerId) throws IOException;

    /**
     * Get all projects for a specific officer.
     * 
     * @param visibility
     *            the visibility of the project
     * @param date
     *            the date to compare
     * @return a list of projects for the officer
     * @throws IOException
     *             if an I/O error occurs
     */
    List<Project> findByVisibilityAndOpenDateGreaterThanAndCloseDateLessThan(boolean visibility, Date date)
            throws IOException;

    /**
     * Get all projects.
     * 
     * @return a list of all projects
     * @throws IOException
     *             if an I/O error occurs
     */
    List<Project> findAll() throws IOException;

    /**
     * Get all projects that are public and have a specific marital status.
     * 
     * @param userStatus
     *            the marital status of the user
     * @param visibility
     *            the visibility of the project
     * @param date
     *            the date to compare
     * @return a list of projects that are public and have the specified marital status
     * @throws IOException
     *             if an I/O error occurs
     */
    List<Project> getPublicProjects(MaritalStatus userStatus, boolean visibility, Date date) throws IOException;

    /**
     * Check if a project is active. For applicant to apply For officer to register Still, officer can decrement flat
     * count even if project is not active
     * 
     * @param projectId
     *            the ID of the project
     * @return true if the project is active, false otherwise
     * @throws IOException
     *             if an I/O error occurs
     */
    boolean isActive(int projectId) throws IOException;

    /**
     * Create a new project. Meant for manager to create a project.
     * 
     * @param projectName
     *            the name of the project
     * @param applicationOpenDate
     *            the date when applications open
     * @param applicationCloseDate
     *            the date when applications close
     * @param neighborhood
     *            the neighborhood of the project
     * @param managerId
     *            the ID of the manager
     * @param visibility
     *            the visibility of the project
     * @param officerLimit
     *            the limit of officers
     * @param officers
     *            the set of officer IDs
     * @param groups
     *            the set of marital status groups
     * @param flats
     *            the map of flat types and their counts
     * @return the created project object
     * @throws IOException
     *             if an I/O error occurs
     */
    Project createProject(String projectName, Date applicationOpenDate, Date applicationCloseDate, String neighborhood,
            int managerId, boolean visibility, Integer officerLimit, Set<Integer> officers, Set<MaritalStatus> groups,
            Map<FlatType, Integer> flats) throws IOException;

    /**
     * Update an existing project. Meant for manager to update a project.
     * 
     * @param projectId
     *            the ID of the project
     * @param projectName
     *            the name of the project
     * @param applicationOpenDate
     *            the date when applications open
     * @param applicationCloseDate
     *            the date when applications close
     * @param neighborhood
     *            the neighborhood of the project
     * @param managerId
     *            the ID of the manager
     * @param visibility
     *            the visibility of the project
     * @param groups
     *            the set of marital status groups
     * @param flats
     *            the map of flat types and their counts
     * @return the updated project object
     * @throws IOException
     *             if an I/O error occurs
     */
    Project updateProject(int projectId, String projectName, Date applicationOpenDate, Date applicationCloseDate,
            String neighborhood, int managerId, boolean visibility, Set<MaritalStatus> groups,
            Map<FlatType, Integer> flats) throws IOException;

    /**
     * Add an officer to a project. Meant for manager to add an officer.
     * 
     * @param projectId
     *            the ID of the project
     * @param officerId
     *            the ID of the officer
     * @return the updated project object
     * @throws IOException
     *             if an I/O error occurs
     */
    Project addOfficer(int projectId, int officerId) throws IOException;

    /**
     * When an officer book a flat for an applicant, decrement the flat count.
     * 
     * @param projectId
     *            the ID of the project
     * @param flatType
     *            the type of flat
     * @return the updated project object
     * @throws IOException
     *             if an I/O error occurs
     */
    Project decrementFlatCount(int projectId, FlatType flatType) throws IOException;

    /**
     * Delete a project. Meant for manager to delete a project.
     * 
     * @param projectId
     *            the ID of the project
     * @throws IOException
     *             if an I/O error occurs
     */
    void deleteProject(int projectId) throws IOException;

    /**
     * Save a project to the database.
     * 
     * @param project
     *            the project object to save
     * @return the saved project object
     * @throws IOException
     *             if an I/O error occurs
     */
    Project save(Project project) throws IOException;
}
