package com.example.app.models;

import com.example.app.enums.FlatType;

/**
 * Holds filter criteria for project listing UIs.
 */
public class ProjectFilter {
    private String neighborhood; // Location
    private FlatType flatType;
    private String projectName;

    public ProjectFilter() {}

    public ProjectFilter(String neighborhood, FlatType flatType, String projectName) {
        this.neighborhood = neighborhood;
        this.flatType = flatType;
        this.projectName = projectName;
    }

    public String getNeighborhood() {
        return neighborhood;
    }

    public void setNeighborhood(String neighborhood) {
        this.neighborhood = neighborhood;
    }

    public FlatType getFlatType() {
        return flatType;
    }

    public void setFlatType(FlatType flatType) {
        this.flatType = flatType;
    }

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public void clear() {
        this.neighborhood = null;
        this.flatType = null;
        this.projectName = null;
    }
}
