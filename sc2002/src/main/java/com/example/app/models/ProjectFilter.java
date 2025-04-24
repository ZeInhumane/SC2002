package com.example.app.models;

import com.example.app.enums.FlatType;

/**
 * Represents a filter for project listings, allowing users to filter projects
 * based on neighborhood, flat type, and project name.
 */
public class ProjectFilter {
    /**
     * The neighborhood or location of the project.
     */
    private String neighborhood; // Location

    /**
     * The type of flat associated with the project.
     */
    private FlatType flatType;

    /**
     * The name of the project.
     */
    private String projectName;

    /**
     * Default constructor for creating an empty ProjectFilter.
     */
    public ProjectFilter() {}

    /**
     * Constructs a ProjectFilter with the specified neighborhood, flat type, and project name.
     *
     * @param neighborhood the neighborhood or location of the project
     * @param flatType the type of flat associated with the project
     * @param projectName the name of the project
     */
    public ProjectFilter(String neighborhood, FlatType flatType, String projectName) {
        this.neighborhood = neighborhood;
        this.flatType = flatType;
        this.projectName = projectName;
    }

    /**
     * Gets the neighborhood or location of the project.}
     *     * @return the neighborhood     */    public String getNeighborhood() {        return neighborhood;    }
    /**
     * Sets the neighborhood or location of the project.
     *
     * @param neighborhood the neighborhood to set
     */
    public void setNeighborhood(String neighborhood) {
        this.neighborhood = neighborhood;
    }

    /**
     * Gets the type of flat associated with the project.
     *
     * @return the flat type
     */
    public FlatType getFlatType() {
        return flatType;
    }

    /**
     * Sets the type of flat associated with the project.
     *
     * @param flatType the flat type to set
     */
    public void setFlatType(FlatType flatType) {
        this.flatType = flatType;
    }

    /**
     * Gets the name of the project.
     *
     * @return the project name
     */
    public String getProjectName() {
        return projectName;
    }

    /**
     * Sets the name of the project.
     *
     * @param projectName the project name to set
     */
    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }
}
