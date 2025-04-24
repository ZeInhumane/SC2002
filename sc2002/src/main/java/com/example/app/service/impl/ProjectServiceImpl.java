package com.example.app.service.impl;

import com.example.app.enums.FlatType;
import com.example.app.enums.MaritalStatus;
import com.example.app.models.Project;
import com.example.app.repository.ProjectRepository;
import com.example.app.repository.RepositoryDependency;
import com.example.app.service.ProjectService;

import java.io.IOException;
import java.util.*;

/**
 * Implementation of the ProjectService interface.
 * Provides functionality to manage housing projects including creating, updating, deleting,
 * retrieving by various conditions, and assigning officers or updating flat counts.
 *
 * @see ProjectService
 */
public class ProjectServiceImpl implements ProjectService {

    /**
     * Repository for accessing and managing project data.
     */
    private final ProjectRepository projectRepository = RepositoryDependency.getProjectRepository();

    /**
     * Finds a project by its ID.
     *
     * @param id the ID of the project
     * @return the Project object or null if not found
     * @throws IOException if an I/O error occurs
     */
    @Override
    public Project findById(int id) throws IOException {
        return projectRepository.findById(id);
    }

    /**
     * Finds all projects managed by a specific manager.
     *
     * @param managerId the manager's user ID
     * @return a list of Project objects
     * @throws IOException if an I/O error occurs
     */
    @Override
    public List<Project> findByManagerId(int managerId) throws IOException {
        return projectRepository.findByManagerId(managerId);
    }

    /**
     * Finds an active project managed by the given manager.
     *
     * @param managerId the manager's user ID
     * @return an active Project object or null if not found
     * @throws IOException if an I/O error occurs
     */
    @Override
    public Project findByManagerIdAndIsActive(int managerId) throws IOException {
        return projectRepository.findByManagerIdAndOpenDateGreaterThanAndCloseDateLessThan(managerId, new Date());
    }

    /**
     * Retrieves projects based on visibility and date range (open < now < close).
     *
     * @param visibility visibility of the project
     * @param date       current date for filtering
     * @return a list of available Project objects
     * @throws IOException if an I/O error occurs
     */
    @Override
    public List<Project> findByVisibilityAndOpenDateGreaterThanAndCloseDateLessThan(boolean visibility, Date date) throws IOException {
        return projectRepository.findByVisibilityAndOpenDateGreaterThanAndCloseDateLessThan(visibility, date);
    }

    /**
     * Returns all projects regardless of visibility.
     *
     * @return a list of all Project objects
     * @throws IOException if an I/O error occurs
     */
    @Override
    public List<Project> findAll() throws IOException {
        return projectRepository.findAll();
    }

    /**
     * Gets projects available to the public, filtered by marital status, visibility, and date.
     *
     * @param userStatus marital status of the applicant
     * @param visibility whether the project is public
     * @param date       current date
     * @return a list of publicly visible Project objects
     * @throws IOException if an I/O error occurs
     */
    @Override
    public List<Project> getPublicProjects(MaritalStatus userStatus, boolean visibility, Date date) throws IOException {
        return projectRepository.findByMaritalStatusAndVisibilityAndOpenDateGreaterThanAndCloseDateLessThan(userStatus, visibility, date);
    }

    /**
     * Checks if a project is currently open for application.
     *
     * @param projectId the ID of the project
     * @return true if the project is active (within open-close date range), false otherwise
     * @throws IOException if an I/O error occurs
     */
    @Override
    public boolean isActive(int projectId) throws IOException {
        Project project = projectRepository.findById(projectId);
        if (project == null) {
            return false;
        }
        Date now = new Date();
        Date openDate = project.getApplicationOpenDate();
        Date closeDate = project.getApplicationCloseDate();
        return now.compareTo(openDate) >= 0 && now.compareTo(closeDate) <= 0;
    }

    /**
     * Creates a new housing project.
     *
     * @param projectName           name of the project
     * @param applicationOpenDate   opening date for applications
     * @param applicationCloseDate  closing date for applications
     * @param neighborhood          project location
     * @param managerId             manager ID
     * @param visibility            public visibility flag
     * @param officerLimit          maximum number of officers
     * @param officers              assigned officer IDs
     * @param groups                target marital status groups
     * @param flats                 available flat types and counts
     * @return the saved Project object
     * @throws IOException if an I/O error occurs
     */
    @Override
    public Project createProject(String projectName, Date applicationOpenDate, Date applicationCloseDate,
                                 String neighborhood, int managerId, boolean visibility, Integer officerLimit,
                                 Set<Integer> officers, Set<MaritalStatus> groups,
                                 Map<FlatType, Integer> flats) throws IOException {
        Project project = new Project(null, projectName, stripTime(applicationOpenDate),
                stripTime(applicationCloseDate), neighborhood, managerId, visibility, officerLimit, officers, groups, flats);
        return projectRepository.save(project);
    }

    /**
     * Updates an existing housing project.
     *
     * @param projectId             ID of the project to update
     * @param projectName           new name
     * @param applicationOpenDate   new open date
     * @param applicationCloseDate  new close date
     * @param neighborhood          new neighborhood
     * @param managerId             manager ID
     * @param visibility            visibility flag
     * @param groups                marital groups
     * @param flats                 flat details
     * @return the updated Project object
     * @throws IOException              if an I/O error occurs
     * @throws IllegalArgumentException if the project is not found
     */
    @Override
    public Project updateProject(int projectId, String projectName, Date applicationOpenDate, Date applicationCloseDate,
                                 String neighborhood, int managerId, boolean visibility,
                                 Set<MaritalStatus> groups, Map<FlatType, Integer> flats) throws IOException {
        Project project = projectRepository.findById(projectId);
        if (project == null) {
            throw new IllegalArgumentException("Project with ID " + projectId + " not found.");
        }

        project.setProjectName(projectName);
        project.setApplicationOpenDate(stripTime(applicationOpenDate));
        project.setApplicationCloseDate(stripTime(applicationCloseDate));
        project.setNeighborhood(neighborhood);
        project.setManagerId(managerId);
        project.setVisibility(visibility);
        project.setGroups(groups);
        project.setFlats(flats);

        return projectRepository.save(project);
    }

    /**
     * Assigns an officer to a project, ensuring the officer limit is not exceeded.
     *
     * @param projectId the project ID
     * @param officerId the officer ID to add
     * @return the updated Project object
     * @throws IOException              if an I/O error occurs
     * @throws IllegalArgumentException if project is not found or officer limit is exceeded
     */
    @Override
    public Project addOfficer(int projectId, int officerId) throws IOException {
        Project project = projectRepository.findById(projectId);
        if (project == null) {
            throw new IllegalArgumentException("Project with ID " + projectId + " not found.");
        }

        Set<Integer> officers = project.getOfficers();
        if (officers == null) {
            officers = new HashSet<>();
        }

        if (officers.size() >= project.getOfficerLimit()) {
            throw new IllegalArgumentException("Officer limit reached for this project.");
        }

        officers.add(officerId);
        project.setOfficers(officers);

        return projectRepository.save(project);
    }

    /**
     * Decrements the flat count for a specific flat type in the project.
     *
     * @param projectId the project ID
     * @param flatType  the type of flat to decrement
     * @return the updated Project object
     * @throws IOException if an I/O error occurs
     */
    @Override
    public Project decrementFlatCount(int projectId, FlatType flatType) throws IOException {
        Project project = projectRepository.findById(projectId);
        project.decrementFlatCount(flatType);
        return projectRepository.save(project);
    }

    /**
     * Deletes a project by its ID.
     *
     * @param projectId the project ID
     * @throws IOException              if an I/O error occurs
     * @throws IllegalArgumentException if project is not found
     */
    @Override
    public void deleteProject(int projectId) throws IOException {
        Project project = projectRepository.findById(projectId);
        if (project == null) {
            throw new IllegalArgumentException("Project with ID " + projectId + " not found.");
        }

        projectRepository.deleteById(projectId);
    }

    /**
     * Saves a project instance to the repository.
     *
     * @param project the project to save
     * @return the saved Project object
     * @throws IOException if an I/O error occurs
     */
    @Override
    public Project save(Project project) throws IOException {
        return projectRepository.save(project);
    }

    /**
     * Strips the time component from a Date object (sets it to midnight).
     *
     * @param date the input date
     * @return the same date with time set to 00:00:00
     */
    private Date stripTime(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MILLISECOND, 0);
        return cal.getTime();
    }
}