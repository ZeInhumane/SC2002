package com.example.app.repository;

import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import com.example.app.enums.MaritalStatus;
import com.example.app.models.Project;
import com.example.app.serializer.SerializerDependency;

/**
 * Repository class for Project.
 * This class extends the GeneralRepository class to provide CRUD operations for Project objects.
 *
 * @see GeneralRepository
 * @see Project
 */
public class ProjectRepository extends GeneralRepository<Project> {

    /**
     * Constructor for ProjectRepository.
     * Initializes the repository with the Project serializer and the file name.
     *
     * @see SerializerDependency
     * @see GeneralRepository
     */
    public ProjectRepository() {
        super(SerializerDependency.getProjectSerializer(), "projects.txt");
    }

    /**
     * Finds all projects by the marital status and visibility.
     *
     * @param status    the marital status
     * @param visibility the visibility of the project
     * @return a list of projects that match the criteria
     * @throws IOException if there is an error reading the file
     */
    public List<Project> findByMaritalStatusAndVisibility(MaritalStatus status, boolean visibility) throws IOException {
        return this.findAll().stream()
                .filter(p -> p.getGroups().contains(status) && Objects.equals(p.getVisibility(), visibility))
                .collect(Collectors.toList());
    }

    /**
     * Finds all projects by the marital status, visibility, and open/close date.
     * @param status  the marital status
     * @param visibility the visibility of the project
     * @param date the date to compare with the open and close dates
     * @return a list of projects that match the criteria
     * @throws IOException if there is an error reading the file
     */
    public List<Project> findByMaritalStatusAndVisibilityAndOpenDateGreaterThanAndCloseDateLessThan(
            MaritalStatus status, boolean visibility, Date date) throws IOException {
        return this.findAll().stream()
                .filter(p -> p.getGroups().contains(status) && Objects.equals(p.getVisibility(), visibility)
                        && p.getApplicationOpenDate().compareTo(date) <= 0
                        && p.getApplicationCloseDate().compareTo(date) >= 0)
                .collect(Collectors.toList());
    }

    /**
     * Finds all projects by the visibility.
     *
     * @param visibility the visibility of the project
     * @return a list of projects that match the criteria
     * @throws IOException if there is an error reading the file
     */
    public List<Project> findByVisibility(boolean visibility) throws IOException {
        return this.findAll().stream().filter(p -> Objects.equals(p.getVisibility(), visibility))
                .collect(Collectors.toList());
    }

    /**
     * Finds all projects by the visibility and open/close date.
     * @param visibility the visibility of the project
     * @param date the date to compare with the open and close dates
     * @return a list of projects that match the criteria
     * @throws IOException if there is an error reading the file
     */
    public List<Project> findByVisibilityAndOpenDateGreaterThanAndCloseDateLessThan(boolean visibility, Date date)
            throws IOException {
        return this.findAll().stream()
                .filter(p -> Objects.equals(p.getVisibility(), visibility)
                        && p.getApplicationOpenDate().compareTo(date) <= 0
                        && p.getApplicationCloseDate().compareTo(date) >= 0)
                .collect(Collectors.toList());
    }

    /**
     * Finds all projects by the manager ID.
     *
     * @param managerId the ID of the manager
     * @return a list of projects that match the criteria
     * @throws IOException if there is an error reading the file
     */
    public List<Project> findByManagerId(Integer managerId) throws IOException {
        return this.findAll().stream().filter(p -> Objects.equals(p.getManagerId(), managerId))
                .collect(Collectors.toList());
    }

    /**
     * Finds a project by the manager ID and open/close date.
     *
     * @param managerId the ID of the manager
     * @param date      the date to compare with the open and close dates
     * @return a project that matches the criteria
     * @throws IOException if there is an error reading the file
     */
    public Project findByManagerIdAndOpenDateGreaterThanAndCloseDateLessThan(Integer managerId, Date date)
            throws IOException {
        return this.findAll().stream()
                .filter(p -> Objects.equals(p.getManagerId(), managerId)
                        && p.getApplicationOpenDate().compareTo(date) <= 0
                        && p.getApplicationCloseDate().compareTo(date) >= 0)
                .findFirst().orElse(null);
    }

}
