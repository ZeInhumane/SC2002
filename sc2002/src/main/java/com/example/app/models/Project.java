package com.example.app.models;

import jakarta.persistence.*;
import java.util.Date;

@Entity
public class Project {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String projectName;

    @Temporal(TemporalType.DATE)
    private Date applicationOpenDate;

    @Temporal(TemporalType.DATE)
    private Date applicationCloseDate;

    private Boolean visibility;

    @Enumerated(EnumType.STRING)
    private ProjectStatus projectStatus;

    @ManyToOne
    @JoinColumn(name = "manager_id")
    private Manager manager;

    @ManyToOne
    @JoinColumn(name = "officer_id")
    private Officer officer;

    public Project() { }

    public Project(String projectName, Date applicationOpenDate, Date applicationCloseDate, Boolean visibility,
                   ProjectStatus statusStr, Manager manager, Officer officer) {
        this.projectName = projectName;
        this.applicationOpenDate = applicationOpenDate;
        this.applicationCloseDate = applicationCloseDate;
        this.visibility = visibility;
        this.projectStatus = statusStr;
        this.manager = manager;
        this.officer = officer;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getProjectName() { return projectName; }
    public void setProjectName(String projectName) { this.projectName = projectName; }

    public Date getApplicationOpenDate() { return applicationOpenDate; }
    public void setApplicationOpenDate(Date applicationOpenDate) { this.applicationOpenDate = applicationOpenDate; }

    public Date getApplicationCloseDate() { return applicationCloseDate; }
    public void setApplicationCloseDate(Date applicationCloseDate) { this.applicationCloseDate = applicationCloseDate; }

    public Boolean getVisibility() { return visibility; }
    public void setVisibility(Boolean visibility) { this.visibility = visibility; }

    public ProjectStatus getProjectStatus() { return projectStatus; }
    public void setProjectStatus(ProjectStatus projectStatus) { this.projectStatus = projectStatus; }

    public Manager getManager() { return manager; }
    public void setManager(Manager manager) { this.manager = manager; }

    public Officer getOfficer() { return officer; }
    public void setOfficer(Officer officer) { this.officer = officer; }
}

// enum ProjectStatus {
//     PENDING,
//     BOOKED,
//     SUCCESSFUL,
//     UNSUCCESSFUL
// }

