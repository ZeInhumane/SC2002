package com.example.app.models;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Project {

    private static long idCounter = 1; // for auto-incrementing IDs
    private Long id;

    private String projectName;
    private Date applicationOpenDate;
    private Date applicationCloseDate;
    private String neighborhood;
    private MaritalStatus group;
    private Boolean visibility;

    private User manager;
    private List<User> officers = new ArrayList<>();

    public Project() {
        this.id = idCounter++;
    }

    public Project(String projectName, Date applicationOpenDate, Date applicationCloseDate, String neighborhood, Manager manager, Officer officer, MaritalStatus group, Boolean visibility) {
        this();
        this.projectName = projectName;
        this.applicationOpenDate = applicationOpenDate;
        this.applicationCloseDate = applicationCloseDate;
        this.neighborhood = neighborhood;
        this.manager = manager;
        this.group = group;
        this.visibility = visibility;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getProjectName() { return projectName; }
    public void setProjectName(String projectName) { this.projectName = projectName; }

    public Date getApplicationOpenDate() { return applicationOpenDate; }
    public void setApplicationOpenDate(Date applicationOpenDate) { this.applicationOpenDate = applicationOpenDate; }

    public Date getApplicationCloseDate() { return applicationCloseDate; }
    public void setApplicationCloseDate(Date applicationCloseDate) { this.applicationCloseDate = applicationCloseDate; }

    public String getNeighborhood() { return neighborhood; }
    public void setNeighborhood(String neighborhood) { this.neighborhood = neighborhood; }

    public MaritalStatus getGroup() { return group; }
    public void setGroup(MaritalStatus group) { this.group = group; }

    public Boolean getVisibility() { return visibility; }
    public void setVisibility(Boolean visibility) { this.visibility = visibility; }

    public User getManager() { return manager; }
    public void setManager(User manager) { this.manager = manager; }

    public List<User> getOfficers() { return officers; }
    public void setOfficers(List<User> officers) { this.officers = officers; }

    public void addOfficer(Officer officer) {
        if (!officers.contains(officer)) {
            officers.add(officer);
            officer.getProjects().add(this);
        }
    }
}
